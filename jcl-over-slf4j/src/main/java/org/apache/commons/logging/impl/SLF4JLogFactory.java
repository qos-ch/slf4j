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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>
 * Concrete subclass of {@link LogFactory} which always delegates to the
 * {@link LoggerFactory org.slf4j.LoggerFactory} class.
 * 
 * <p>
 * This factory generates instances of {@link SLF4JLog}. It will remember
 * previously created instances for the same name, and will return them on
 * repeated requests to the <code>getInstance()</code> method.
 * 
 * <p>
 * This implementation ignores any configured attributes.
 * </p>
 * 
 * @author Rod Waldhoff
 * @author Craig R. McClanahan
 * @author Richard A. Sitze
 * @author Ceki G&uuml;lc&uuml;
 */
@SuppressWarnings("rawtypes")
public class SLF4JLogFactory extends LogFactory {

  // ----------------------------------------------------------- Constructors

  /**
   * The {@link org.apache.commons.logging.Log}instances that have already been
   * created, keyed by logger name.
   */
  ConcurrentMap<String, Log> loggerMap;

  /**
   * Public no-arguments constructor required by the lookup mechanism.
   */
  public SLF4JLogFactory() {
    loggerMap = new ConcurrentHashMap<String, Log>();
  }

  // ----------------------------------------------------- Manifest Constants

  /**
   * The name of the system property identifying our {@link Log}implementation
   * class.
   */
  public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";

  // ----------------------------------------------------- Instance Variables

  /**
   * Configuration attributes.
   */
  protected Hashtable attributes = new Hashtable();

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
  @SuppressWarnings("unchecked")
  public String[] getAttributeNames() {

    List<String> names = new ArrayList<String>();
    Enumeration<String> keys = attributes.keys();
    while (keys.hasMoreElements()) {
      names.add((String) keys.nextElement());
    }
    String results[] = new String[names.size()];
    for (int i = 0; i < results.length; i++) {
      results[i] = (String) names.get(i);
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
   * @param name
   *          Logical name of the <code>Log</code> instance to be returned
   *          (the meaning of this name is only known to the underlying logging
   *          implementation that is being wrapped)
   * 
   * @exception LogConfigurationException
   *              if a suitable <code>Log</code> instance cannot be returned
   */
  public Log getInstance(String name) throws LogConfigurationException {
    Log instance = loggerMap.get(name);
    if (instance != null) {
      return instance;
    } else {
      Log newInstance;
      Logger slf4jLogger = LoggerFactory.getLogger(name);
      if (slf4jLogger instanceof LocationAwareLogger) {
        newInstance = new SLF4JLocationAwareLog((LocationAwareLogger) slf4jLogger);
      } else {
        newInstance = new SLF4JLog(slf4jLogger);
      }
      Log oldInstance = loggerMap.putIfAbsent(name, newInstance);
      return oldInstance == null ? newInstance : oldInstance;
    }
  }

  /**
   * Release any internal references to previously created
   * {@link org.apache.commons.logging.Log}instances returned by this factory.
   * This is useful in environments like servlet containers, which implement
   * application reloading by throwing away a ClassLoader. Dangling references
   * to objects in that class loader would prevent garbage collection.
   */
  public void release() {
    // This method is never called by jcl-over-slf4j classes. However,
    // in certain deployment scenarios, in particular if jcl-over-slf4j.jar
    // is
    // in the the web-app class loader and the official commons-logging.jar is
    // deployed in some parent class loader (e.g. commons/lib), then it is
    // possible
    // for the parent class loader to mask the classes shipping in
    // jcl-over-slf4j.jar.
    System.out.println("WARN: The method " + SLF4JLogFactory.class
        + "#release() was invoked.");
    System.out
        .println("WARN: Please see http://www.slf4j.org/codes.html#release for an explanation.");
    System.out.flush();
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
  @SuppressWarnings("unchecked")
  public void setAttribute(String name, Object value) {

    if (value == null) {
      attributes.remove(name);
    } else {
      attributes.put(name, value);
    }

  }
}