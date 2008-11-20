package org.slf4j.ext;

import org.slf4j.LoggerFactory;

/**
 * 
 * This class is essentially a wrapper around an
 * {@link LoggerFactory} producing {@link XLogger} instances.
 * 
 * <p>Contrary to {@link LoggerFactory#getLogger(String)} method of 
 * {@link LoggerFactory}, each call to {@link getXLogger} 
 * produces a new instance of XLogger. This should not matter because an 
 * XLogger instance does not have any state beyond that of the Logger instance 
 * it wraps.
 * 
 * @author Ralph Goers
 * @author Ceki G&uuml;lc&uuml;
 */
public class XLoggerFactory {

  /**
   * Get an XLogger instance by name.
   * 
   * @param name
   * @return
   */
  public static XLogger getXLogger(String name) {
    return new XLogger(LoggerFactory.getLogger(name));
  }

  /**
   * Get a new XLogger instance by class. The returned XLogger
   * will be named after the class.
   * 
   * @param clazz
   * @return
   */
  @SuppressWarnings("unchecked")
  public static XLogger getXLogger(Class clazz) {
    return getXLogger(clazz.getName());
  }
}
