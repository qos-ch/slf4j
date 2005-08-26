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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Concrete subclass of {@link LogFactory}that implements the following
 * algorithm to dynamically select a logging implementation class to instantiate
 * a wrapper for.
 * </p>
 * <ul>
 * <li>Use a factory configuration attribute named
 * <code>org.apache.commons.logging.Log</code> to identify the requested
 * implementation class.</li>
 * <li>Use the <code>org.apache.commons.logging.Log</code> system property to
 * identify the requested implementation class.</li>
 * <li>If <em>Log4J</em> is available, return an instance of
 * <code>org.apache.commons.logging.impl.Log4JLogger</code>.</li>
 * <li>If <em>JDK 1.4 or later</em> is available, return an instance of
 * <code>org.apache.commons.logging.impl.Jdk14Logger</code>.</li>
 * <li>Otherwise, return an instance of
 * <code>org.apache.commons.logging.impl.SimpleLog</code>.</li>
 * </ul>
 * 
 * <p>
 * If the selected {@link Log}implementation class has a
 * <code>setLogFactory()</code> method that accepts a {@link LogFactory}
 * parameter, this method will be called on each newly created instance to
 * identify the associated factory. This makes factory configuration attributes
 * available to the Log instance, if it so desires.
 * </p>
 * 
 * <p>
 * This factory will remember previously created <code>Log</code> instances
 * for the same name, and will return them on repeated requests to the
 * <code>getInstance()</code> method. This implementation ignores any
 * configured attributes.
 * </p>
 * 
 * @author Rod Waldhoff
 * @author Craig R. McClanahan
 * @author Richard A. Sitze
 */

public class SLF4FLogFactory extends LogFactory {

  // ----------------------------------------------------------- Constructors

  /**
   * The {@link org.apache.commons.logging.Log}instances that have already been
   * created, keyed by logger name.
   */
  Map loggerMap;

  /**
   * Public no-arguments constructor required by the lookup mechanism.
   */
  public SLF4FLogFactory() {
    loggerMap = new HashMap();
  }

  // ----------------------------------------------------- Manifest Constants

  /**
   * The name of the system property identifying our {@link Log}implementation
   * class.
   */
  public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";

  /**
   * The deprecated system property used for backwards compatibility with the
   * old {@link org.apache.commons.logging.LogSource}class.
   */
  protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";

  /**
   * <p>
   * The name of the {@link Log}interface class.
   * </p>
   */
  private static final String LOG_INTERFACE = "org.apache.commons.logging.Log";

  // ----------------------------------------------------- Instance Variables

  /**
   * Configuration attributes.
   */
  protected Hashtable attributes = new Hashtable();

  /**
   * Name of the class implementing the Log interface.
   */
  private String logClassName;

  // --------------------------------------------------------- Public Methods

  /**
   * Return the configuration attribute with the specified name (if any), or
   * <code>null</code> if there is no such attribute.
   * 
   * @param name
   *          Name of the attribute to return
   */
  public Object getAttribute(String name) {

    return (attributes.get(name));

  }

  /**
   * Return an array containing the names of all currently defined configuration
   * attributes. If there are no such attributes, a zero length array is
   * returned.
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
   * Convenience method to derive a name from the specified class and call
   * <code>getInstance(String)</code> with it.
   * 
   * @param clazz
   *          Class for which a suitable Log name will be derived
   * 
   * @exception LogConfigurationException
   *              if a suitable <code>Log</code> instance cannot be returned
   */
  public Log getInstance(Class clazz) throws LogConfigurationException {

    return (getInstance(clazz.getName()));

  }

  /**
   * <p>
   * Construct (if necessary) and return a <code>Log</code> instance, using
   * the factory's current set of configuration attributes.
   * </p>
   * 
   * <p>
   * <strong>NOTE </strong>- Depending upon the implementation of the
   * <code>LogFactory</code> you are using, the <code>Log</code> instance
   * you are returned may or may not be local to the current application, and
   * may or may not be returned again on a subsequent call with the same name
   * argument.
   * </p>
   * 
   * @param name
   *          Logical name of the <code>Log</code> instance to be returned
   *          (the meaning of this name is only known to the underlying logging
   *          implementation that is being wrapped)
   * 
   * @exception LogConfigurationException
   *              if a suitable <code>Log</code> instance cannot be returned
   */
  public Log getInstance(String name) throws LogConfigurationException {

    Log instance = (Log) loggerMap.get(name);
    if (instance == null) {
      instance = newInstance(name);
      loggerMap.put(name, instance);
    }
    return (instance);

  }

  /**
   * Release any internal references to previously created
   * {@link org.apache.commons.logging.Log}instances returned by this factory.
   * This is useful in environments like servlet containers, which implement
   * application reloading by throwing away a ClassLoader. Dangling references
   * to objects in that class loader would prevent garbage collection.
   */
  public void release() {
    throw new UnsupportedOperationException("SLF4J bound commons-logging does not need to implement release().");
  }

  /**
   * Remove any configuration attribute associated with the specified name. If
   * there is no such attribute, no action is taken.
   * 
   * @param name
   *          Name of the attribute to remove
   */
  public void removeAttribute(String name) {
    attributes.remove(name);
  }

  /**
   * Set the configuration attribute with the specified name. Calling this with
   * a <code>null</code> value is equivalent to calling
   * <code>removeAttribute(name)</code>.
   * 
   * @param name
   *          Name of the attribute to set
   * @param value
   *          Value of the attribute to set, or <code>null</code> to remove
   *          any setting for this attribute
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
   * Create and return a new {@link org.apache.commons.logging.Log}instance for
   * the specified name.
   * 
   * @param name
   *          Name of the new logger
   * 
   * @exception LogConfigurationException
   *              if a new instance cannot be created
   */
  protected Log newInstance(String name) throws LogConfigurationException {
    Logger logger = LoggerFactory.getLogger(name);
    return new SLF4JLog(logger);
  }

}