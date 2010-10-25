/*
 * Copyright (c) 2004-2008 QOS.ch
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
 */

package org.slf4j;

import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * The <code>LoggerFactory</code> is a utility class producing Loggers for
 * various logging APIs, most notably for log4j, logback and JDK 1.4 logging.
 * Other implementations such as {@link org.slf4j.impl.NOPLogger NOPLogger} and
 * {@link org.slf4j.impl.SimpleLogger SimpleLogger} are also supported.
 * 
 * <p>
 * <code>LoggerFactory</code> is essentially a wrapper around an
 * {@link ILoggerFactory} instance bound with <code>LoggerFactory</code> at
 * compile time.
 * 
 * <p>
 * Please note that all methods in <code>LoggerFactory</code> are static.
 * 
 * <p>
 * This is a reduced an modified version to support CLDC 1.1
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Robert Elliot
 * @author Marcel Patzlaff
 */
public final class LoggerFactory {
    static final String UNSUCCESSFUL_INIT_MSG= "org.slf4j.LoggerFactory could not be successfully initialized.";

    static final int UNINITIALIZED= 0;
    static final int ONGOING_INITILIZATION= 1;
    static final int FAILED_INITILIZATION= 2;
    static final int SUCCESSFUL_INITILIZATION= 3;
    static final int NOP_FALLBACK_INITILIZATION= 4;

    static int INITIALIZATION_STATE= UNINITIALIZED;
    static NOPLoggerFactory NOP_FALLBACK_FACTORY= new NOPLoggerFactory();

    // private constructor prevents instantiation
    private LoggerFactory() {
    }

    private final static void bind() {
        try {
            // the next line does the binding
            StaticLoggerBinder.getSingleton();
            INITIALIZATION_STATE= SUCCESSFUL_INITILIZATION;
        } catch (NoClassDefFoundError ncde) {
            String msg= ncde.getMessage();
            if (msg != null && msg.indexOf("org/slf4j/impl/StaticLoggerBinder") != -1) {
                INITIALIZATION_STATE= NOP_FALLBACK_INITILIZATION;
                System.err.println("Defaulting to no-operation (NOP) logger implementation");
            } else {
                failedBinding(ncde);
                throw ncde;
            }
        } catch (java.lang.Error nsme) {
            String msg= nsme.getMessage();
            if (msg != null && msg.indexOf("org.slf4j.impl.StaticLoggerBinder.getSingleton()") != -1) {
                INITIALIZATION_STATE= FAILED_INITILIZATION;
                System.err.println("slf4j-cldc-api 1.6.x (or later) is incompatible with this binding.");
                System.err.println("Your binding is version 1.5.5 or earlier.");
                System.err.println("Upgrade your binding to version 1.6.x. or 2.0.x");
            }
            throw nsme;
        } catch (Exception e) {
            failedBinding(e);
            throw new RuntimeException("Unexpected initialization failure: " + e.getMessage());
        }
    }

    static void failedBinding(Throwable t) {
        INITIALIZATION_STATE= FAILED_INITILIZATION;
        System.err.println("Failed to instantiate SLF4J LoggerFactory");
        t.printStackTrace();
    }

    /**
     * Return a logger named according to the name parameter using the
     * statically bound {@link ILoggerFactory} instance.
     * 
     * @param name
     *            The name of the logger.
     * @return logger
     */
    public static Logger getLogger(String name) {
        ILoggerFactory iLoggerFactory= getILoggerFactory();
        return iLoggerFactory.getLogger(name);
    }

    /**
     * Return a logger named corresponding to the class passed as parameter,
     * using the statically bound {@link ILoggerFactory} instance.
     * 
     * @param clazz
     *            the returned logger will be named after clazz
     * @return logger
     */
    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * Return the {@link ILoggerFactory} instance in use.
     * 
     * <p>
     * ILoggerFactory instance is bound with this class at compile time.
     * 
     * @return the ILoggerFactory instance in use
     */
    public static ILoggerFactory getILoggerFactory() {
        if (INITIALIZATION_STATE == UNINITIALIZED) {
            INITIALIZATION_STATE= ONGOING_INITILIZATION;
            bind();

        }
        switch (INITIALIZATION_STATE) {
            case SUCCESSFUL_INITILIZATION:
                return StaticLoggerBinder.getSingleton().getLoggerFactory();
            case NOP_FALLBACK_INITILIZATION:
                return NOP_FALLBACK_FACTORY;
            case FAILED_INITILIZATION:
                throw new RuntimeException(UNSUCCESSFUL_INIT_MSG);
            case ONGOING_INITILIZATION:
                // support re-entrant behavior.
                // See also http://bugzilla.slf4j.org/show_bug.cgi?id=106
                return NOP_FALLBACK_FACTORY;
        }
        throw new RuntimeException("Unreachable code");
    }
}
