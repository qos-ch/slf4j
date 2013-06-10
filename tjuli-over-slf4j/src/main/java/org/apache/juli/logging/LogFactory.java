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

package org.apache.juli.logging;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>
 * Factory for creating {@link Log} instances, which is backed by slf4j.
 * </p>
 * 
 * @author Craig R. McClanahan
 * @author Costin Manolache
 * @author Richard A. Sitze
 * @author Ceki G&uuml;lc&uuml;
 * @author Attila Kiraly
 */
public class LogFactory {

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
	 * <p>
	 * Setting this system property value allows the <code>Hashtable</code> used
	 * to store classloaders to be substituted by an alternative implementation.
	 * <p>
	 * This property is not used but preserved here for compatibility.
	 */
	public static final String HASHTABLE_IMPLEMENTATION_PROPERTY = "org.apache.commons.logging.LogFactory.HashtableImpl";

	/**
	 * The {@link org.apache.juli.logging.Log}instances that have already been
	 * created, keyed by logger name.
	 */
	private final ConcurrentMap<String, Log> loggerMap = new ConcurrentHashMap<String, Log>();

	private static LogFactory singleton = new LogFactory();

	Properties logConfig;

	/**
	 * Private constructor that is not available for public use.
	 */
	private LogFactory() {
		logConfig = new Properties();
	}

	// hook for syserr logger - class level
	void setLogConfig(Properties p) {
		logConfig = p;
	}

	/**
	 * Convenience method to derive a name from the specified class and call
	 * <code>getInstance(String)</code> with it.
	 * 
	 * @param clazz
	 *            Class for which a suitable Log name will be derived
	 * 
	 * @exception LogConfigurationException
	 *                if a suitable <code>Log</code> instance cannot be returned
	 */
	public Log getInstance(Class<?> clazz) throws LogConfigurationException {
		return getInstance(clazz.getName());

	}

	/**
	 * <p>
	 * Construct (if necessary) and return a <code>Log</code> instance, using
	 * the factory's current set of configuration attributes.
	 * </p>
	 * 
	 * @param name
	 *            Logical name of the <code>Log</code> instance to be returned
	 *            (the meaning of this name is only known to the underlying
	 *            logging implementation that is being wrapped)
	 * 
	 * @exception LogConfigurationException
	 *                if a suitable <code>Log</code> instance cannot be returned
	 */
	public Log getInstance(String name) throws LogConfigurationException {
		Log instance = loggerMap.get(name);
		if (instance != null)
			return instance;
		else {
			Log newInstance;
			Logger slf4jLogger = LoggerFactory.getLogger(name);
			if (slf4jLogger instanceof LocationAwareLogger)
				newInstance = new SLF4JLocationAwareLog(
						(LocationAwareLogger) slf4jLogger);
			else
				newInstance = new SLF4JLog(slf4jLogger);
			Log oldInstance = loggerMap.putIfAbsent(name, newInstance);
			return oldInstance == null ? newInstance : oldInstance;
		}
	}

	/**
	 * Release any internal references to previously created
	 * {@link org.apache.juli.logging.Log} instances returned by this factory.
	 * This is useful in environments like servlet containers, which implement
	 * application reloading by throwing away a ClassLoader. Dangling references
	 * to objects in that class loader would prevent garbage collection.
	 */
	public void release() {
	}

	/**
	 * Return the configuration attribute with the specified name (if any), or
	 * <code>null</code> if there is no such attribute.
	 * 
	 * @param name
	 *            Name of the attribute to return
	 */
	public Object getAttribute(String name) {
		return logConfig.get(name);
	}

	/**
	 * Return an array containing the names of all currently defined
	 * configuration attributes. If there are no such attributes, a zero length
	 * array is returned.
	 */
	public String[] getAttributeNames() {
		String result[] = new String[logConfig.size()];
		return logConfig.keySet().toArray(result);
	}

	/**
	 * Remove any configuration attribute associated with the specified name. If
	 * there is no such attribute, no action is taken.
	 * 
	 * @param name
	 *            Name of the attribute to remove
	 */
	public void removeAttribute(String name) {
		logConfig.remove(name);
	}

	/**
	 * Set the configuration attribute with the specified name. Calling this
	 * with a <code>null</code> value is equivalent to calling
	 * <code>removeAttribute(name)</code>.
	 * 
	 * @param name
	 *            Name of the attribute to set
	 * @param value
	 *            Value of the attribute to set, or <code>null</code> to remove
	 *            any setting for this attribute
	 */
	public void setAttribute(String name, Object value) {
		logConfig.put(name, value);
	}

	// --------------------------------------------------------- Static Methods

	/**
	 * <p>
	 * Construct (if necessary) and return a <code>LogFactory</code> instance,
	 * using the following ordered lookup procedure to determine the name of the
	 * implementation class to be loaded.
	 * </p>
	 * <ul>
	 * <li>The <code>org.apache.commons.logging.LogFactory</code> system
	 * property.</li>
	 * <li>The JDK 1.3 Service Discovery mechanism</li>
	 * <li>Use the properties file <code>commons-logging.properties</code> file,
	 * if found in the class path of this class. The configuration file is in
	 * standard <code>java.util.Properties</code> format and contains the fully
	 * qualified name of the implementation class with the key being the system
	 * property defined above.</li>
	 * <li>Fall back to a default implementation class (
	 * <code>org.apache.commons.logging.impl.LogFactoryImpl</code>).</li>
	 * </ul>
	 * 
	 * <p>
	 * <em>NOTE</em> - If the properties file method of identifying the
	 * <code>LogFactory</code> implementation class is utilized, all of the
	 * properties defined in this file will be set as configuration attributes
	 * on the corresponding <code>LogFactory</code> instance.
	 * </p>
	 * 
	 * @exception LogConfigurationException
	 *                if the implementation class is not available or cannot be
	 *                instantiated.
	 */
	public static LogFactory getFactory() throws LogConfigurationException {
		return singleton;
	}

	/**
	 * Convenience method to return a named logger, without the application
	 * having to care about factories.
	 * 
	 * @param clazz
	 *            Class from which a log name will be derived
	 * 
	 * @exception LogConfigurationException
	 *                if a suitable <code>Log</code> instance cannot be returned
	 */
	public static Log getLog(Class<?> clazz) throws LogConfigurationException {
		return getFactory().getInstance(clazz);
	}

	/**
	 * Convenience method to return a named logger, without the application
	 * having to care about factories.
	 * 
	 * @param name
	 *            Logical name of the <code>Log</code> instance to be returned
	 *            (the meaning of this name is only known to the underlying
	 *            logging implementation that is being wrapped)
	 * 
	 * @exception LogConfigurationException
	 *                if a suitable <code>Log</code> instance cannot be returned
	 */
	public static Log getLog(String name) throws LogConfigurationException {
		return getFactory().getInstance(name);
	}

	/**
	 * Release any internal references to previously created {@link LogFactory}
	 * instances that have been associated with the specified class loader (if
	 * any), after calling the instance method <code>release()</code> on each of
	 * them.
	 * 
	 * @param classLoader
	 *            ClassLoader for which to release the LogFactory
	 */
	public static void release(ClassLoader classLoader) {
		// since SLF4J based JCL does not make use of classloaders, there is
		// nothing to do here
	}

	/**
	 * Release any internal references to previously created {@link LogFactory}
	 * instances, after calling the instance method <code>release()</code> on
	 * each of them. This is useful in environments like servlet containers,
	 * which implement application reloading by throwing away a ClassLoader.
	 * Dangling references to objects in that class loader would prevent garbage
	 * collection.
	 */
	public static void releaseAll() {
		// since SLF4J based JCL does not make use of classloaders, there is
		// nothing to do here
	}

	/**
	 * Returns a string that uniquely identifies the specified object, including
	 * its class.
	 * <p>
	 * The returned string is of form "classname@hashcode", ie is the same as
	 * the return value of the Object.toString() method, but works even when the
	 * specified object's class has overidden the toString method.
	 * 
	 * @param o
	 *            may be null.
	 * @return a string of form classname@hashcode, or "null" if param o is
	 *         null.
	 * @since 1.1
	 */
	public static String objectId(Object o) {
		if (o == null)
			return "null";
		else
			return o.getClass().getName() + "@" + System.identityHashCode(o);
	}
}