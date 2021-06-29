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

import java.util.Hashtable;

import org.apache.commons.logging.impl.SLF4JLogFactory;

/**
 * <p>
 * Factory for creating {@link Log} instances, which always delegates to an
 * instance of {@link SLF4JLogFactory}.
 * 
 * 
 * 
 * @author Craig R. McClanahan
 * @author Costin Manolache
 * @author Richard A. Sitze
 * @author Ceki G&uuml;lc&uuml;
 */

@SuppressWarnings("rawtypes")
public abstract class LogFactory {

    static String UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J = "http://www.slf4j.org/codes.html#unsupported_operation_in_jcl_over_slf4j";

    static LogFactory logFactory = new SLF4JLogFactory();

    /**
     * The name (<code>priority</code>) of the key in the config file used to
     * specify the priority of that particular config file. The associated value
     * is a floating-point number; higher values take priority over lower values.
     * 
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String PRIORITY_KEY = "priority";

    /**
     * The name (<code>use_tccl</code>) of the key in the config file used to
     * specify whether logging classes should be loaded via the thread context
     * class loader (TCCL), or not. By default, the TCCL is used.
     * 
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String TCCL_KEY = "use_tccl";

    /**
     * The name of the property used to identify the LogFactory implementation
     * class name.
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String FACTORY_PROPERTY = "org.apache.commons.logging.LogFactory";

    /**
     * The fully qualified class name of the fallback <code>LogFactory</code>
     * implementation class to use, if no other can be found.
     * 
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String FACTORY_DEFAULT = "org.apache.commons.logging.impl.SLF4JLogFactory";

    /**
     * The name of the properties file to search for.
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String FACTORY_PROPERTIES = "commons-logging.properties";

    /**
     * JDK1.3+ <a href="http://java.sun.com/j2se/1.3/docs/guide/jar/jar.html#Service%20Provider">
     * 'Service Provider' specification</a>.
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    protected static final String SERVICE_ID = "META-INF/services/org.apache.commons.logging.LogFactory";

    /**
     * The name (<code>org.apache.commons.logging.diagnostics.dest</code>) of
     * the property used to enable internal commons-logging diagnostic output, in
     * order to get information on what logging implementations are being
     * discovered, what classloaders they are loaded through, etc.
     * 
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String DIAGNOSTICS_DEST_PROPERTY = "org.apache.commons.logging.diagnostics.dest";

    /**
     * <p>
     * Setting this system property value allows the <code>Hashtable</code> used
     * to store classloaders to be substituted by an alternative implementation.
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";

    /**
     * The previously constructed <code>LogFactory</code> instances, keyed by
     * the <code>ClassLoader</code> with which it was created.
     * 
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    protected static Hashtable factories = null;

    /**
     * <p>
     * This property is not used but preserved here for compatibility.
     */
    protected static LogFactory nullClassLoaderFactory = null;

    /**
     * Protected constructor that is not available for public use.
     */
    protected LogFactory() {
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Return the configuration attribute with the specified name (if any), or
     * <code>null</code> if there is no such attribute.
     * 
     * @param name Name of the attribute to return
     * @return configuration attribute
     */
    public abstract Object getAttribute(String name);

    /**
     * Return an array containing the names of all currently defined configuration
     * attributes. If there are no such attributes, a zero length array is
     * returned.
     * 
     * @return names of all currently defined configuration attributes
     */
    public abstract String[] getAttributeNames();

    /**
     * Convenience method to derive a name from the specified class and call
     * <code>getInstance(String)</code> with it.
     * 
     * @param clazz
     *                Class for which a suitable Log name will be derived
     * 
     * @exception LogConfigurationException
     *                    if a suitable <code>Log</code> instance cannot be
     *                    returned
     */
    public abstract Log getInstance(Class clazz) throws LogConfigurationException;

    /**
     * <p>
     * Construct (if necessary) and return a <code>Log</code> instance, using
     * the factory's current set of configuration attributes.
     * 
     * 
     * <p>
     * <strong>NOTE </strong>- Depending upon the implementation of the
     * <code>LogFactory</code> you are using, the <code>Log</code> instance
     * you are returned may or may not be local to the current application, and
     * may or may not be returned again on a subsequent call with the same name
     * argument.
     * 
     * 
     * @param name
     *                Logical name of the <code>Log</code> instance to be
     *                returned (the meaning of this name is only known to the
     *                underlying logging implementation that is being wrapped)
     * 
     * @exception LogConfigurationException
     *                    if a suitable <code>Log</code> instance cannot be
     *                    returned
     */
    public abstract Log getInstance(String name) throws LogConfigurationException;

    /**
     * Release any internal references to previously created {@link Log}instances
     * returned by this factory. This is useful in environments like servlet
     * containers, which implement application reloading by throwing away a
     * ClassLoader. Dangling references to objects in that class loader would
     * prevent garbage collection.
     */
    public abstract void release();

    /**
     * Remove any configuration attribute associated with the specified name. If
     * there is no such attribute, no action is taken.
     * 
     * @param name
     *                Name of the attribute to remove
     */
    public abstract void removeAttribute(String name);

    /**
     * Set the configuration attribute with the specified name. Calling this with
     * a <code>null</code> value is equivalent to calling
     * <code>removeAttribute(name)</code>.
     * 
     * @param name
     *                Name of the attribute to set
     * @param value
     *                Value of the attribute to set, or <code>null</code> to
     *                remove any setting for this attribute
     */
    public abstract void setAttribute(String name, Object value);

    // --------------------------------------------------------- Static Methods

    /**
     * <p>
     * Construct (if necessary) and return a <code>LogFactory</code> instance,
     * using the following ordered lookup procedure to determine the name of the
     * implementation class to be loaded.
     * 
     * <ul>
     * <li>The <code>org.apache.commons.logging.LogFactory</code> system
     * property.</li>
     * <li>The JDK 1.3 Service Discovery mechanism</li>
     * <li>Use the properties file <code>commons-logging.properties</code>
     * file, if found in the class path of this class. The configuration file is
     * in standard <code>java.util.Properties</code> format and contains the
     * fully qualified name of the implementation class with the key being the
     * system property defined above.</li>
     * <li>Fall back to a default implementation class (
     * <code>org.apache.commons.logging.impl.SLF4FLogFactory</code>).</li>
     * </ul>
     * 
     * <p>
     * <em>NOTE</em>- If the properties file method of identifying the
     * <code>LogFactory</code> implementation class is utilized, all of the
     * properties defined in this file will be set as configuration attributes on
     * the corresponding <code>LogFactory</code> instance.
     * 
     * 
     * @exception LogConfigurationException
     *                    if the implementation class is not available or cannot
     *                    be instantiated.
     */
    public static LogFactory getFactory() throws LogConfigurationException {
        return logFactory;
    }

    /**
     * Convenience method to return a named logger, without the application having
     * to care about factories.
     * 
     * @param clazz
     *                Class from which a log name will be derived
     * 
     * @exception LogConfigurationException
     *                    if a suitable <code>Log</code> instance cannot be
     *                    returned
     */
    public static Log getLog(Class clazz) throws LogConfigurationException {
        return (getFactory().getInstance(clazz));
    }

    /**
     * Convenience method to return a named logger, without the application having
     * to care about factories.
     * 
     * @param name
     *                Logical name of the <code>Log</code> instance to be
     *                returned (the meaning of this name is only known to the
     *                underlying logging implementation that is being wrapped)
     * 
     * @exception LogConfigurationException
     *                    if a suitable <code>Log</code> instance cannot be
     *                    returned
     */
    public static Log getLog(String name) throws LogConfigurationException {
        return (getFactory().getInstance(name));
    }

    /**
     * Release any internal references to previously created {@link LogFactory}
     * instances that have been associated with the specified class loader (if
     * any), after calling the instance method <code>release()</code> on each of
     * them.
     * 
     * @param classLoader
     *                ClassLoader for which to release the LogFactory
     */
    public static void release(ClassLoader classLoader) {
        // since SLF4J based JCL does not make use of classloaders, there is nothing
        // to do here
    }

    /**
     * Release any internal references to previously created {@link LogFactory}
     * instances, after calling the instance method <code>release()</code> on
     * each of them. This is useful in environments like servlet containers, which
     * implement application reloading by throwing away a ClassLoader. Dangling
     * references to objects in that class loader would prevent garbage
     * collection.
     */
    public static void releaseAll() {
        // since SLF4J based JCL does not make use of classloaders, there is nothing
        // to do here
    }

    /**
     * Returns a string that uniquely identifies the specified object, including
     * its class.
     * <p>
     * The returned string is of form "classname@hashcode", i.e. is the same as the
     * return value of the Object.toString() method, but works even when the
     * specified object's class has overridden the toString method.
     * 
     * @param o
     *                may be null.
     * @return a string of form classname@hashcode, or "null" if param o is null.
     * @since 1.1
     */
    public static String objectId(Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.getClass().getName() + "@" + System.identityHashCode(o);
        }
    }

    // protected methods which were added in JCL 1.1. These are not used
    // by SLF4JLogFactory

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static Object createFactory(String factoryClass, ClassLoader classLoader) {
        throw new UnsupportedOperationException(
                        "Operation [factoryClass] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static ClassLoader directGetContextClassLoader() {
        throw new UnsupportedOperationException(
                        "Operation [directGetContextClassLoader] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        throw new UnsupportedOperationException(
                        "Operation [getContextClassLoader] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static ClassLoader getClassLoader(Class clazz) {
        throw new UnsupportedOperationException(
                        "Operation [getClassLoader] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static boolean isDiagnosticsEnabled() {
        throw new UnsupportedOperationException(
                        "Operation [isDiagnosticsEnabled] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static void logRawDiagnostic(String msg) {
        throw new UnsupportedOperationException(
                        "Operation [logRawDiagnostic] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static LogFactory newFactory(final String factoryClass, final ClassLoader classLoader, final ClassLoader contextClassLoader) {
        throw new UnsupportedOperationException(
                        "Operation [logRawDiagnostic] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

    /**
     * This method exists to ensure signature compatibility.
     */
    protected static LogFactory newFactory(final String factoryClass, final ClassLoader classLoader) {
        throw new UnsupportedOperationException(
                        "Operation [newFactory] is not supported in jcl-over-slf4j. See also " + UNSUPPORTED_OPERATION_IN_JCL_OVER_SLF4J);
    }

}