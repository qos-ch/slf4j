/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.apache.commons.logging.impl;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Concrete subclass of {@link LogFactory} that implements the
 * following algorithm to dynamically select a logging implementation
 * class to instantiate a wrapper for.</p>
 * <ul>
 * <li>Use a factory configuration attribute named
 *     <code>org.apache.commons.logging.Log</code> to identify the
 *     requested implementation class.</li>
 * <li>Use the <code>org.apache.commons.logging.Log</code> system property
 *     to identify the requested implementation class.</li>
 * <li>If <em>Log4J</em> is available, return an instance of
 *     <code>org.apache.commons.logging.impl.Log4JLogger</code>.</li>
 * <li>If <em>JDK 1.4 or later</em> is available, return an instance of
 *     <code>org.apache.commons.logging.impl.Jdk14Logger</code>.</li>
 * <li>Otherwise, return an instance of
 *     <code>org.apache.commons.logging.impl.SimpleLog</code>.</li>
 * </ul>
 *
 * <p>If the selected {@link Log} implementation class has a
 * <code>setLogFactory()</code> method that accepts a {@link LogFactory}
 * parameter, this method will be called on each newly created instance
 * to identify the associated factory.  This makes factory configuration
 * attributes available to the Log instance, if it so desires.</p>
 *
 * <p>This factory will remember previously created <code>Log</code> instances
 * for the same name, and will return them on repeated requests to the
 * <code>getInstance()</code> method.  This implementation ignores any
 * configured attributes.</p>
 *
 * @author Rod Waldhoff
 * @author Craig R. McClanahan
 * @author Richard A. Sitze
 * @version $Revision: 1.33 $ $Date: 2004/03/06 21:52:59 $
 */

public class LogFactoryImpl extends LogFactory {

    // ----------------------------------------------------------- Constructors


    /**
     * Public no-arguments constructor required by the lookup mechanism.
     */
    public LogFactoryImpl() {
        super();
    }


    // ----------------------------------------------------- Manifest Constants


    /**
     * The name of the system property identifying our {@link Log}
     * implementation class.
     */
    public static final String LOG_PROPERTY =
        "org.apache.commons.logging.Log";


    /**
     * The deprecated system property used for backwards compatibility with
     * the old {@link org.apache.commons.logging.LogSource} class.
     */
    protected static final String LOG_PROPERTY_OLD =
        "org.apache.commons.logging.log";


    /**
     * <p>The name of the {@link Log} interface class.</p>
     */
    private static final String LOG_INTERFACE =
        "org.apache.commons.logging.Log";


    // ----------------------------------------------------- Instance Variables


    /**
     * Configuration attributes.
     */
    protected Hashtable attributes = new Hashtable();


    /**
     * The {@link org.apache.commons.logging.Log} instances that have
     * already been created, keyed by logger name.
     */
    protected Hashtable instances = new Hashtable();


    /**
     * Name of the class implementing the Log interface.
     */
    private String logClassName;


    /**
     * The one-argument constructor of the
     * {@link org.apache.commons.logging.Log}
     * implementation class that will be used to create new instances.
     * This value is initialized by <code>getLogConstructor()</code>,
     * and then returned repeatedly.
     */
    protected Constructor logConstructor = null;


    /**
     * The signature of the Constructor to be used.
     */
    protected Class logConstructorSignature[] =
    { java.lang.String.class };


    /**
     * The one-argument <code>setLogFactory</code> method of the selected
     * {@link org.apache.commons.logging.Log} method, if it exists.
     */
    protected Method logMethod = null;


    /**
     * The signature of the <code>setLogFactory</code> method to be used.
     */
    protected Class logMethodSignature[] =
    { LogFactory.class };


    // --------------------------------------------------------- Public Methods


    /**
     * Return the configuration attribute with the specified name (if any),
     * or <code>null</code> if there is no such attribute.
     *
     * @param name Name of the attribute to return
     */
    public Object getAttribute(String name) {

        return (attributes.get(name));

    }


    /**
     * Return an array containing the names of all currently defined
     * configuration attributes.  If there are no such attributes, a zero
     * length array is returned.
     */
    public String[] getAttributeNames() {

        Vector names = new Vector();
        Enumeration keys = attributes.keys();
        while (keys.hasMoreElements()) {
            names.addElement((String) keys.nextElement());
        }
        String results[] = new String[names.size()];
        for (int i = 0; i < results.length; i++) {
            results[i] = (String) names.elementAt(i);
        }
        return (results);

    }


    /**
     * Convenience method to derive a name from the specified class and
     * call <code>getInstance(String)</code> with it.
     *
     * @param clazz Class for which a suitable Log name will be derived
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public Log getInstance(Class clazz) throws LogConfigurationException {

        return (getInstance(clazz.getName()));

    }


    /**
     * <p>Construct (if necessary) and return a <code>Log</code> instance,
     * using the factory's current set of configuration attributes.</p>
     *
     * <p><strong>NOTE</strong> - Depending upon the implementation of
     * the <code>LogFactory</code> you are using, the <code>Log</code>
     * instance you are returned may or may not be local to the current
     * application, and may or may not be returned again on a subsequent
     * call with the same name argument.</p>
     *
     * @param name Logical name of the <code>Log</code> instance to be
     *  returned (the meaning of this name is only known to the underlying
     *  logging implementation that is being wrapped)
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public Log getInstance(String name) throws LogConfigurationException {

        Log instance = (Log) instances.get(name);
        if (instance == null) {
            instance = newInstance(name);
            instances.put(name, instance);
        }
        return (instance);

    }


    /**
     * Release any internal references to previously created
     * {@link org.apache.commons.logging.Log}
     * instances returned by this factory.  This is useful in environments
     * like servlet containers, which implement application reloading by
     * throwing away a ClassLoader.  Dangling references to objects in that
     * class loader would prevent garbage collection.
     */
    public void release() {

        instances.clear();
    }


    /**
     * Remove any configuration attribute associated with the specified name.
     * If there is no such attribute, no action is taken.
     *
     * @param name Name of the attribute to remove
     */
    public void removeAttribute(String name) {

        attributes.remove(name);

    }


    /**
     * Set the configuration attribute with the specified name.  Calling
     * this with a <code>null</code> value is equivalent to calling
     * <code>removeAttribute(name)</code>.
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set, or <code>null</code>
     *  to remove any setting for this attribute
     */
    public void setAttribute(String name, Object value) {

        if (value == null) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }

    }


    // ------------------------------------------------------ Protected Methods



    /**
     * Return the fully qualified Java classname of the {@link Log}
     * implementation we will be using.
     */
    protected String getLogClassName() {

        // Return the previously identified class name (if any)
        if (logClassName != null) {
            return logClassName;
        }

        logClassName = (String) getAttribute(LOG_PROPERTY);

        if (logClassName == null) { // @deprecated
            logClassName = (String) getAttribute(LOG_PROPERTY_OLD);
        }

        if (logClassName == null) {
            try {
                logClassName = System.getProperty(LOG_PROPERTY);
            } catch (SecurityException e) {
                ;
            }
        }

        if (logClassName == null) { // @deprecated
            try {
                logClassName = System.getProperty(LOG_PROPERTY_OLD);
            } catch (SecurityException e) {
                ;
            }
        }

        if ((logClassName == null) && isLog4JAvailable()) {
            logClassName = "org.apache.commons.logging.impl.Log4JLogger";
        }

        if ((logClassName == null) && isJdk14Available()) {
            logClassName = "org.apache.commons.logging.impl.Jdk14Logger";
        }

        if ((logClassName == null) && isJdk13LumberjackAvailable()) {
            logClassName = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
        }

        if (logClassName == null) {
            logClassName = "org.apache.commons.logging.impl.SimpleLog";
        }

        return (logClassName);

    }


    /**
     * <p>Return the <code>Constructor</code> that can be called to instantiate
     * new {@link org.apache.commons.logging.Log} instances.</p>
     *
     * <p><strong>IMPLEMENTATION NOTE</strong> - Race conditions caused by
     * calling this method from more than one thread are ignored, because
     * the same <code>Constructor</code> instance will ultimately be derived
     * in all circumstances.</p>
     *
     * @exception LogConfigurationException if a suitable constructor
     *  cannot be returned
     */
    protected Constructor getLogConstructor()
        throws LogConfigurationException {

        // Return the previously identified Constructor (if any)
        if (logConstructor != null) {
            return logConstructor;
        }

        String logClassName = getLogClassName();

        // Attempt to load the Log implementation class
        Class logClass = null;
        Class logInterface = null;
        try {
            logInterface = this.getClass().getClassLoader().loadClass
                (LOG_INTERFACE);
            logClass = loadClass(logClassName);
            if (logClass == null) {
                throw new LogConfigurationException
                    ("No suitable Log implementation for " + logClassName);
            }
            if (!logInterface.isAssignableFrom(logClass)) {
                Class interfaces[] = logClass.getInterfaces();
                for (int i = 0; i < interfaces.length; i++) {
                    if (LOG_INTERFACE.equals(interfaces[i].getName())) {
                        throw new LogConfigurationException
                            ("Invalid class loader hierarchy.  " +
                             "You have more than one version of '" +
                             LOG_INTERFACE + "' visible, which is " +
                             "not allowed.");
                    }
                }
                throw new LogConfigurationException
                    ("Class " + logClassName + " does not implement '" +
                     LOG_INTERFACE + "'.");
            }
        } catch (Throwable t) {
            throw new LogConfigurationException(t);
        }

        // Identify the <code>setLogFactory</code> method (if there is one)
        try {   // protected Class logMethodSignature[] = { LogFactory.class };
            logMethod = logClass.getMethod("setLogFactory",
                                           logMethodSignature);          
        } catch (Throwable t) {
            logMethod = null;
        }

        // Identify the corresponding constructor to be used
        try {   // protected Class logConstructorSignature[] =    { java.lang.String.class };
            logConstructor = logClass.getConstructor(logConstructorSignature);
            return (logConstructor);
        } catch (Throwable t) {
            throw new LogConfigurationException
                ("No suitable Log constructor " +
                 logConstructorSignature+ " for " + logClassName, t);
        }
    }


    /**
     * MUST KEEP THIS METHOD PRIVATE.
     *
     * <p>Exposing this method outside of
     * <code>org.apache.commons.logging.LogFactoryImpl</code>
     * will create a security violation:
     * This method uses <code>AccessController.doPrivileged()</code>.
     * </p>
     *
     * Load a class, try first the thread class loader, and
     * if it fails use the loader that loaded this class.
     */
    private static Class loadClass( final String name )
        throws ClassNotFoundException
    {
        Object result = AccessController.doPrivileged(
            new PrivilegedAction() {
                public Object run() {
                    ClassLoader threadCL = getContextClassLoader();
                    if (threadCL != null) {
                        try {
                            return threadCL.loadClass(name);
                        } catch( ClassNotFoundException ex ) {
                            // ignore
                        }
                    }
                    try {
                        return Class.forName( name );
                    } catch (ClassNotFoundException e) {
                        return e;
                    }
                }
            });

        if (result instanceof Class)
            return (Class)result;

        throw (ClassNotFoundException)result;
    }


    /**
     * Is <em>JDK 1.3 with Lumberjack</em> logging available?
     */
    protected boolean isJdk13LumberjackAvailable() {

        try {
            loadClass("java.util.logging.Logger");
            loadClass("org.apache.commons.logging.impl.Jdk13LumberjackLogger");
            return (true);
        } catch (Throwable t) {
            return (false);
        }

    }


    /**
     * <p>Return <code>true</code> if <em>JDK 1.4 or later</em> logging
     * is available.  Also checks that the <code>Throwable</code> class
     * supports <code>getStackTrace()</code>, which is required by
     * Jdk14Logger.</p>
     */
    protected boolean isJdk14Available() {

        try {
            loadClass("java.util.logging.Logger");
            loadClass("org.apache.commons.logging.impl.Jdk14Logger");
            Class throwable = loadClass("java.lang.Throwable");
            if (throwable.getDeclaredMethod("getStackTrace", null) == null) {
                return (false);
            }
            return (true);
        } catch (Throwable t) {
            return (false);
        }
    }


    /**
     * Is a <em>Log4J</em> implementation available?
     */
    protected boolean isLog4JAvailable() {

        try {
            loadClass("org.apache.log4j.Logger");
            loadClass("org.apache.commons.logging.impl.Log4JLogger");
            return (true);
        } catch (Throwable t) {
            return (false);
        }
    }


    /**
     * Create and return a new {@link org.apache.commons.logging.Log}
     * instance for the specified name.
     *
     * @param name Name of the new logger
     *
     * @exception LogConfigurationException if a new instance cannot
     *  be created
     */
    protected Log newInstance(String name) throws LogConfigurationException {

        Log instance = null;
        try {
            Object params[] = new Object[1];
            params[0] = name;
            instance = (Log) getLogConstructor().newInstance(params);
            if (logMethod != null) {
                params[0] = this;
                logMethod.invoke(instance, params);
            }
            return (instance);
        } catch (InvocationTargetException e) {
            Throwable c = e.getTargetException();
            if (c != null) {
                throw new LogConfigurationException(c);
            } else {
                throw new LogConfigurationException(e);
            }
        } catch (Throwable t) {
            throw new LogConfigurationException(t);
        }

    }


}
