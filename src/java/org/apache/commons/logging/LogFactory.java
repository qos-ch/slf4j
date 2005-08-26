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

package org.apache.commons.logging;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;


/**
 * <p>Factory for creating {@link Log} instances, with discovery and
 * configuration features similar to that employed by standard Java APIs
 * such as JAXP.</p>
 *
 * <p><strong>IMPLEMENTATION NOTE</strong> - This implementation is heavily
 * based on the SAXParserFactory and DocumentBuilderFactory implementations
 * (corresponding to the JAXP pluggability APIs) found in Apache Xerces.</p>
 *
 * @author Craig R. McClanahan
 * @author Costin Manolache
 * @author Richard A. Sitze
 * @version $Revision: 1.27 $ $Date: 2004/06/06 21:15:12 $
 */

public abstract class LogFactory {


    // ----------------------------------------------------- Manifest Constants


    /**
     * The name of the property used to identify the LogFactory implementation
     * class name.
     */
    public static final String FACTORY_PROPERTY =
        "org.apache.commons.logging.LogFactory";

    /**
     * The fully qualified class name of the fallback <code>LogFactory</code>
     * implementation class to use, if no other can be found.
     */
    public static final String FACTORY_DEFAULT =
        "org.apache.commons.logging.impl.LogFactoryImpl";

    /**
     * The name of the properties file to search for.
     */
    public static final String FACTORY_PROPERTIES =
        "commons-logging.properties";

    /**
     * JDK1.3+ <a href="http://java.sun.com/j2se/1.3/docs/guide/jar/jar.html#Service%20Provider">
     * 'Service Provider' specification</a>.
     * 
     */
    protected static final String SERVICE_ID =
        "META-INF/services/org.apache.commons.logging.LogFactory";


    // ----------------------------------------------------------- Constructors


    /**
     * Protected constructor that is not available for public use.
     */
    protected LogFactory() { }


    // --------------------------------------------------------- Public Methods


    /**
     * Return the configuration attribute with the specified name (if any),
     * or <code>null</code> if there is no such attribute.
     *
     * @param name Name of the attribute to return
     */
    public abstract Object getAttribute(String name);


    /**
     * Return an array containing the names of all currently defined
     * configuration attributes.  If there are no such attributes, a zero
     * length array is returned.
     */
    public abstract String[] getAttributeNames();


    /**
     * Convenience method to derive a name from the specified class and
     * call <code>getInstance(String)</code> with it.
     *
     * @param clazz Class for which a suitable Log name will be derived
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public abstract Log getInstance(Class clazz)
        throws LogConfigurationException;


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
    public abstract Log getInstance(String name)
        throws LogConfigurationException;


    /**
     * Release any internal references to previously created {@link Log}
     * instances returned by this factory.  This is useful in environments
     * like servlet containers, which implement application reloading by
     * throwing away a ClassLoader.  Dangling references to objects in that
     * class loader would prevent garbage collection.
     */
    public abstract void release();


    /**
     * Remove any configuration attribute associated with the specified name.
     * If there is no such attribute, no action is taken.
     *
     * @param name Name of the attribute to remove
     */
    public abstract void removeAttribute(String name);


    /**
     * Set the configuration attribute with the specified name.  Calling
     * this with a <code>null</code> value is equivalent to calling
     * <code>removeAttribute(name)</code>.
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set, or <code>null</code>
     *  to remove any setting for this attribute
     */
    public abstract void setAttribute(String name, Object value);


    // ------------------------------------------------------- Static Variables


    /**
     * The previously constructed <code>LogFactory</code> instances, keyed by
     * the <code>ClassLoader</code> with which it was created.
     */
    protected static Hashtable factories = new Hashtable();


    // --------------------------------------------------------- Static Methods


    /**
     * <p>Construct (if necessary) and return a <code>LogFactory</code>
     * instance, using the following ordered lookup procedure to determine
     * the name of the implementation class to be loaded.</p>
     * <ul>
     * <li>The <code>org.apache.commons.logging.LogFactory</code> system
     *     property.</li>
     * <li>The JDK 1.3 Service Discovery mechanism</li>
     * <li>Use the properties file <code>commons-logging.properties</code>
     *     file, if found in the class path of this class.  The configuration
     *     file is in standard <code>java.util.Properties</code> format and
     *     contains the fully qualified name of the implementation class
     *     with the key being the system property defined above.</li>
     * <li>Fall back to a default implementation class
     *     (<code>org.apache.commons.logging.impl.LogFactoryImpl</code>).</li>
     * </ul>
     *
     * <p><em>NOTE</em> - If the properties file method of identifying the
     * <code>LogFactory</code> implementation class is utilized, all of the
     * properties defined in this file will be set as configuration attributes
     * on the corresponding <code>LogFactory</code> instance.</p>
     *
     * @exception LogConfigurationException if the implementation class is not
     *  available or cannot be instantiated.
     */
    public static LogFactory getFactory() throws LogConfigurationException {

        // Identify the class loader we will be using
        ClassLoader contextClassLoader =
            (ClassLoader)AccessController.doPrivileged(
                new PrivilegedAction() {
                    public Object run() {
                        return getContextClassLoader();
                    }
                });

        // Return any previously registered factory for this class loader
        LogFactory factory = getCachedFactory(contextClassLoader);
        if (factory != null)
            return factory;


        // Load properties file.
        // Will be used one way or another in the end.

        Properties props=null;
        try {
            InputStream stream = getResourceAsStream(contextClassLoader,
                                                     FACTORY_PROPERTIES);

            if (stream != null) {
                props = new Properties();
                props.load(stream);
                stream.close();
            }
        } catch (IOException e) {
        } catch (SecurityException e) {
        }


        // First, try the system property
        try {
            String factoryClass = System.getProperty(FACTORY_PROPERTY);
            if (factoryClass != null) {
                factory = newFactory(factoryClass, contextClassLoader);
            }
        } catch (SecurityException e) {
            ;  // ignore
        }


        // Second, try to find a service by using the JDK1.3 jar
        // discovery mechanism. This will allow users to plug a logger
        // by just placing it in the lib/ directory of the webapp ( or in
        // CLASSPATH or equivalent ). This is similar to the second
        // step, except that it uses the (standard?) jdk1.3 location in the jar.

        if (factory == null) {
            try {
                InputStream is = getResourceAsStream(contextClassLoader,
                                                     SERVICE_ID);

                if( is != null ) {
                    // This code is needed by EBCDIC and other strange systems.
                    // It's a fix for bugs reported in xerces
                    BufferedReader rd;
                    try {
                        rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    } catch (java.io.UnsupportedEncodingException e) {
                        rd = new BufferedReader(new InputStreamReader(is));
                    }

                    String factoryClassName = rd.readLine();
                    rd.close();

                    if (factoryClassName != null &&
                        ! "".equals(factoryClassName)) {

                        factory= newFactory( factoryClassName, contextClassLoader );
                    }
                }
            } catch( Exception ex ) {
                ;
            }
        }


        // Third try a properties file.
        // If the properties file exists, it'll be read and the properties
        // used. IMHO ( costin ) System property and JDK1.3 jar service
        // should be enough for detecting the class name. The properties
        // should be used to set the attributes ( which may be specific to
        // the webapp, even if a default logger is set at JVM level by a
        // system property )

        if (factory == null  &&  props != null) {
            String factoryClass = props.getProperty(FACTORY_PROPERTY);
            if (factoryClass != null) {
                factory = newFactory(factoryClass, contextClassLoader);
            }
        }


        // Fourth, try the fallback implementation class

        if (factory == null) {
            factory = newFactory(FACTORY_DEFAULT, LogFactory.class.getClassLoader());
        }

        if (factory != null) {
            /**
             * Always cache using context class loader.
             */
            cacheFactory(contextClassLoader, factory);

            if( props!=null ) {
                Enumeration names = props.propertyNames();
                while (names.hasMoreElements()) {
                    String name = (String) names.nextElement();
                    String value = props.getProperty(name);
                    factory.setAttribute(name, value);
                }
            }
        }

        return factory;
    }


    /**
     * Convenience method to return a named logger, without the application
     * having to care about factories.
     *
     * @param clazz Class from which a log name will be derived
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public static Log getLog(Class clazz)
        throws LogConfigurationException {

        return (getFactory().getInstance(clazz));

    }


    /**
     * Convenience method to return a named logger, without the application
     * having to care about factories.
     *
     * @param name Logical name of the <code>Log</code> instance to be
     *  returned (the meaning of this name is only known to the underlying
     *  logging implementation that is being wrapped)
     *
     * @exception LogConfigurationException if a suitable <code>Log</code>
     *  instance cannot be returned
     */
    public static Log getLog(String name)
        throws LogConfigurationException {

        return (getFactory().getInstance(name));

    }


    /**
     * Release any internal references to previously created {@link LogFactory}
     * instances that have been associated with the specified class loader
     * (if any), after calling the instance method <code>release()</code> on
     * each of them.
     *
     * @param classLoader ClassLoader for which to release the LogFactory
     */
    public static void release(ClassLoader classLoader) {

        synchronized (factories) {
            LogFactory factory = (LogFactory) factories.get(classLoader);
            if (factory != null) {
                factory.release();
                factories.remove(classLoader);
            }
        }

    }


    /**
     * Release any internal references to previously created {@link LogFactory}
     * instances, after calling the instance method <code>release()</code> on
     * each of them.  This is useful in environments like servlet containers,
     * which implement application reloading by throwing away a ClassLoader.
     * Dangling references to objects in that class loader would prevent
     * garbage collection.
     */
    public static void releaseAll() {

        synchronized (factories) {
            Enumeration elements = factories.elements();
            while (elements.hasMoreElements()) {
                LogFactory element = (LogFactory) elements.nextElement();
                element.release();
            }
            factories.clear();
        }

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Return the thread context class loader if available.
     * Otherwise return null.
     *
     * The thread context class loader is available for JDK 1.2
     * or later, if certain security conditions are met.
     *
     * @exception LogConfigurationException if a suitable class loader
     * cannot be identified.
     */
    protected static ClassLoader getContextClassLoader()
        throws LogConfigurationException
    {
        ClassLoader classLoader = null;

        try {
            // Are we running on a JDK 1.2 or later system?
            Method method = Thread.class.getMethod("getContextClassLoader", null);

            // Get the thread context class loader (if there is one)
            try {
                classLoader = (ClassLoader)method.invoke(Thread.currentThread(), null);
            } catch (IllegalAccessException e) {
                throw new LogConfigurationException
                    ("Unexpected IllegalAccessException", e);
            } catch (InvocationTargetException e) {
                /**
                 * InvocationTargetException is thrown by 'invoke' when
                 * the method being invoked (getContextClassLoader) throws
                 * an exception.
                 *
                 * getContextClassLoader() throws SecurityException when
                 * the context class loader isn't an ancestor of the
                 * calling class's class loader, or if security
                 * permissions are restricted.
                 *
                 * In the first case (not related), we want to ignore and
                 * keep going.  We cannot help but also ignore the second
                 * with the logic below, but other calls elsewhere (to
                 * obtain a class loader) will trigger this exception where
                 * we can make a distinction.
                 */
                if (e.getTargetException() instanceof SecurityException) {
                    ;  // ignore
                } else {
                    // Capture 'e.getTargetException()' exception for details
                    // alternate: log 'e.getTargetException()', and pass back 'e'.
                    throw new LogConfigurationException
                        ("Unexpected InvocationTargetException", e.getTargetException());
                }
            }
        } catch (NoSuchMethodException e) {
            // Assume we are running on JDK 1.1
            classLoader = LogFactory.class.getClassLoader();
        }

        // Return the selected class loader
        return classLoader;
    }

    /**
     * Check cached factories (keyed by contextClassLoader)
     */
    private static LogFactory getCachedFactory(ClassLoader contextClassLoader)
    {
        LogFactory factory = null;

        if (contextClassLoader != null)
            factory = (LogFactory) factories.get(contextClassLoader);

        return factory;
    }

    private static void cacheFactory(ClassLoader classLoader, LogFactory factory)
    {
        if (classLoader != null && factory != null)
            factories.put(classLoader, factory);
    }

    /**
     * Return a new instance of the specified <code>LogFactory</code>
     * implementation class, loaded by the specified class loader.
     * If that fails, try the class loader used to load this
     * (abstract) LogFactory.
     *
     * @param factoryClass Fully qualified name of the <code>LogFactory</code>
     *  implementation class
     * @param classLoader ClassLoader from which to load this class
     *
     * @exception LogConfigurationException if a suitable instance
     *  cannot be created
     */
    protected static LogFactory newFactory(final String factoryClass,
                                           final ClassLoader classLoader)
        throws LogConfigurationException
    {
        Object result = AccessController.doPrivileged(
            new PrivilegedAction() {
                public Object run() {
                    // This will be used to diagnose bad configurations
                    // and allow a useful message to be sent to the user
                    Class logFactoryClass = null;
                    try {
                        if (classLoader != null) {
                            try {
                                // First the given class loader param (thread class loader)

                                // Warning: must typecast here & allow exception
                                // to be generated/caught & recast properly.
                                logFactoryClass = classLoader.loadClass(factoryClass);
                                return (LogFactory) logFactoryClass.newInstance();

                            } catch (ClassNotFoundException ex) {
                                if (classLoader == LogFactory.class.getClassLoader()) {
                                    // Nothing more to try, onwards.
                                    throw ex;
                                }
                                // ignore exception, continue
                            } catch (NoClassDefFoundError e) {
                                if (classLoader == LogFactory.class.getClassLoader()) {
                                    // Nothing more to try, onwards.
                                    throw e;
                                }

                            } catch(ClassCastException e){

                              if (classLoader == LogFactory.class.getClassLoader()) {
                                    // Nothing more to try, onwards (bug in loader implementation).
                                    throw e;
                               }
                            }
                            // Ignore exception, continue
                        }

                        /* At this point, either classLoader == null, OR
                         * classLoader was unable to load factoryClass.
                         * Try the class loader that loaded this class:
                         * LogFactory.getClassLoader().
                         *
                         * Notes:
                         * a) LogFactory.class.getClassLoader() may return 'null'
                         *    if LogFactory is loaded by the bootstrap classloader.
                         * b) The Java endorsed library mechanism is instead
                         *    Class.forName(factoryClass);
                         */
                        // Warning: must typecast here & allow exception
                        // to be generated/caught & recast properly.
                        logFactoryClass = Class.forName(factoryClass);
                        return (LogFactory) logFactoryClass.newInstance();
                    } catch (Exception e) {
                        // Check to see if we've got a bad configuration
                        if (logFactoryClass != null
                            && !LogFactory.class.isAssignableFrom(logFactoryClass)) {
                            return new LogConfigurationException(
                                "The chosen LogFactory implementation does not extend LogFactory."
                                + " Please check your configuration.",
                                e);
                        }
                        return new LogConfigurationException(e);
                    }
                }
            });

        if (result instanceof LogConfigurationException)
            throw (LogConfigurationException)result;

        return (LogFactory)result;
    }

    private static InputStream getResourceAsStream(final ClassLoader loader,
                                                   final String name)
    {
        return (InputStream)AccessController.doPrivileged(
            new PrivilegedAction() {
                public Object run() {
                    if (loader != null) {
                        return loader.getResourceAsStream(name);
                    } else {
                        return ClassLoader.getSystemResourceAsStream(name);
                    }
                }
            });
    }
}
