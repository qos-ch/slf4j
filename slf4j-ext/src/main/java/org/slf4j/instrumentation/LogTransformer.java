/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
/**
 *
 */
package org.slf4j.instrumentation;

import static org.slf4j.helpers.MessageFormatter.format;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.slf4j.helpers.MessageFormatter;

/**
 * <p>
 * LogTransformer does the work of analyzing each class, and if appropriate add
 * log statements to each method to allow logging entry/exit.
 * 
 * <p>
 * This class is based on the article <a href="http://today.java.net/pub/a/today/2008/04/24/add-logging-at-class-load-time-with-instrumentation.html"
 * >Add Logging at Class Load Time with Java Instrumentation</a>.
 * 
 */
public class LogTransformer implements ClassFileTransformer {

    /**
     * Builder provides a flexible way of configuring some of many options on the
     * parent class instead of providing many constructors.
     *
     * <a href="http://rwhansen.blogspot.com/2007/07/theres-builder-pattern-that-joshua.html">http://rwhansen.blogspot.com/2007/07/theres-builder-pattern-that-joshua.html</a>
     *
     */
    public static class Builder {

        /**
         * Build and return the LogTransformer corresponding to the options set in
         * this Builder.
         *
         * @return
         */
        public LogTransformer build() {
            if (verbose) {
                System.err.println("Creating LogTransformer");
            }
            return new LogTransformer(this);
        }

        boolean addEntryExit;

        /**
         * Should each method log entry (with parameters) and exit (with parameters
         * and return value)?
         *
         * @param b
         *          value of flag
         * @return
         */
        public Builder addEntryExit(boolean b) {
            addEntryExit = b;
            return this;
        }

        boolean addVariableAssignment;

        // private Builder addVariableAssignment(boolean b) {
        // System.err.println("cannot currently log variable assignments.");
        // addVariableAssignment = b;
        // return this;
        // }

        boolean verbose;

        /**
         * Should LogTransformer be verbose in what it does? This currently list the
         * names of the classes being processed.
         *
         * @param b
         * @return
         */
        public Builder verbose(boolean b) {
            verbose = b;
            return this;
        }

        String[] ignore = { "org/slf4j/", "ch/qos/logback/", "org/apache/log4j/" };

        public Builder ignore(String[] strings) {
            this.ignore = strings;
            return this;
        }

        private String level = "info";

        public Builder level(String level) {
            level = level.toLowerCase();
            if (level.equals("info") || level.equals("debug") || level.equals("trace")) {
                this.level = level;
            } else {
                if (verbose) {
                    System.err.println("level not info/debug/trace : " + level);
                }
            }
            return this;
        }
    }

    private final String level;
    private final String levelEnabled;

    private LogTransformer(Builder builder) {
        String s = "WARNING: javassist not available on classpath for javaagent, log statements will not be added";
        try {
            if (Class.forName("javassist.ClassPool") == null) {
                System.err.println(s);
            }
        } catch (ClassNotFoundException e) {
            System.err.println(s);
        }

        this.addEntryExit = builder.addEntryExit;
        // this.addVariableAssignment = builder.addVariableAssignment;
        this.verbose = builder.verbose;
        this.ignore = builder.ignore;
        this.level = builder.level;
        this.levelEnabled = "is" + builder.level.substring(0, 1).toUpperCase() + builder.level.substring(1) + "Enabled";
    }

    private final boolean addEntryExit;
    // private boolean addVariableAssignment;
    private final boolean verbose;
    private final String[] ignore;

    public byte[] transform(ClassLoader loader, String className, Class<?> clazz, ProtectionDomain domain, byte[] bytes) {

        try {
            return transform0(className, clazz, domain, bytes);
        } catch (Exception e) {
            System.err.println("Could not instrument " + className);
            e.printStackTrace();
            return bytes;
        }
    }

    /**
     * transform0 sees if the className starts with any of the namespaces to
     * ignore, if so it is returned unchanged. Otherwise it is processed by
     * doClass(...)
     *
     * @param className
     * @param clazz
     * @param domain
     * @param bytes
     * @return
     */

    private byte[] transform0(String className, Class<?> clazz, ProtectionDomain domain, byte[] bytes) {

        try {
            for (String s : ignore) {
                if (className.startsWith(s)) {
                    return bytes;
                }
            }
            String slf4jName = "org.slf4j.LoggerFactory";
            try {
                if (domain != null && domain.getClassLoader() != null) {
                    domain.getClassLoader().loadClass(slf4jName);
                } else {
                    if (verbose) {
                        System.err.println("Skipping " + className + " as it doesn't have a domain or a class loader.");
                    }
                    return bytes;
                }
            } catch (ClassNotFoundException e) {
                if (verbose) {
                    System.err.println("Skipping " + className + " as slf4j is not available to it");
                }
                return bytes;
            }
            if (verbose) {
                System.err.println("Processing " + className);
            }
            return doClass(className, clazz, bytes);
        } catch (Throwable e) {
            System.out.println("e = " + e);
            return bytes;
        }
    }

    private String loggerName;

    /**
     * doClass() process a single class by first creates a class description from
     * the byte codes. If it is a class (i.e. not an interface) the methods
     * defined have bodies, and a static final logger object is added with the
     * name of this class as an argument, and each method then gets processed with
     * doMethod(...) to have logger calls added.
     *
     * @param name
     *          class name (slashes separate, not dots)
     * @param clazz
     * @param b
     * @return
     */
    private byte[] doClass(String name, Class<?> clazz, byte[] b) {
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.makeClass(new ByteArrayInputStream(b));
            if (cl.isInterface() == false) {

                loggerName = "_____log";

                // We have to declare the log variable.

                String pattern1 = "private static org.slf4j.Logger {};";
                String loggerDefinition = format(pattern1, loggerName).getMessage();
                CtField field = CtField.make(loggerDefinition, cl);

                // and assign it the appropriate value.

                String pattern2 = "org.slf4j.LoggerFactory.getLogger({}.class);";
                String replace = name.replace('/', '.');
                String getLogger = format(pattern2, replace).getMessage();

                cl.addField(field, getLogger);

                // then check every behaviour (which includes methods). We are
                // only
                // interested in non-empty ones, as they have code.
                // NOTE: This will be changed, as empty methods should be
                // instrumented too.

                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (CtBehavior method : methods) {
                    if (method.isEmpty() == false) {
                        doMethod(method);
                    }
                }
                b = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("Could not instrument " + name + ", " + e);
            e.printStackTrace(System.err);
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }

    /**
     * process a single method - this means add entry/exit logging if requested.
     * It is only called for methods with a body.
     *
     * @param method
     *          method to work on
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    private void doMethod(CtBehavior method) throws NotFoundException, CannotCompileException {

        String signature = JavassistHelper.getSignature(method);
        String returnValue = JavassistHelper.returnValue(method);

        if (addEntryExit) {
            String messagePattern = "if ({}.{}()) {}.{}(\">> {}\");";
            Object[] arg1 = new Object[] { loggerName, levelEnabled, loggerName, level, signature };
            String before = MessageFormatter.arrayFormat(messagePattern, arg1).getMessage();
            // System.out.println(before);
            method.insertBefore(before);

            String messagePattern2 = "if ({}.{}()) {}.{}(\"<< {}{}\");";
            Object[] arg2 = new Object[] { loggerName, levelEnabled, loggerName, level, signature, returnValue };
            String after = MessageFormatter.arrayFormat(messagePattern2, arg2).getMessage();
            // System.out.println(after);
            method.insertAfter(after);
        }
    }
}
