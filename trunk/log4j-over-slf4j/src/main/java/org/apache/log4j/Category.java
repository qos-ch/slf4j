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
package org.apache.log4j;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.Logger</code> class by delegation of all calls 
 * to a {@link org.slf4j.Logger.Logger} instance.
 * </p>
 * 
 * <p>
 * Log4j's <code>trace</code>, <code>debug()</code>, <code>info()</code>, 
 * <code>warn()</code>, <code>error()</code> printing methods are directly 
 * mapped to their SLF4J equivalents. Log4j's <code>fatal()</code> 
 * printing method is mapped to SLF4J's <code>error()</code> method 
 * with a FATAL marker.
 * 
 * @author S&eacute;bastien Pennec
 * @author Ceki G&uuml;lc&uuml;
 */

public class Category {

  private String name;

  private org.slf4j.Logger slf4jLogger;
  private org.slf4j.spi.LocationAwareLogger locationAwareLogger;
  
  private static Marker FATAL_MARKER = MarkerFactory.getMarker("FATAL");

  Category(String name) {
    this.name = name;
    slf4jLogger = LoggerFactory.getLogger(name);
    if(slf4jLogger instanceof LocationAwareLogger) {
      locationAwareLogger = (LocationAwareLogger) slf4jLogger;
    }
  }

  public static Logger getLogger(String name) {
    return Log4jLoggerFactory.getLogger(name);
  }

  public static Logger getLogger(Class clazz) {
    return getLogger(clazz.getName());
  }

  /**
   * Does the obvious.
   * 
   * @return
   */
  public static Logger getRootLogger() {
    return getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
  }

  /**
   * Returns the obvious.
   * 
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Return the level in effect for this category/logger.
   * 
   * <p>
   * The result is computed by simulation.
   * 
   * @return
   */
  public Level getEffectiveLevel() {
    if(slf4jLogger.isTraceEnabled()) {
      return Level.TRACE;
    }
    if(slf4jLogger.isDebugEnabled()) {
      return Level.DEBUG;
    }
    if(slf4jLogger.isInfoEnabled()) {
      return Level.INFO;
    }
    if(slf4jLogger.isWarnEnabled()) {
      return Level.WARN;
    }
    return Level.ERROR;
  }

  /**
   * Returns the assigned {@link Level}, if any, for this Category.
   * This implementation always returns null.
   * 
   * @return Level - the assigned Level, can be <code>null</code>.
   */
  final public Level getLevel() {
    return null;
  }


  /**
   * @deprecated Please use {@link #getLevel} instead.
  */
  final public Level getPriority() {
    return null;
  }
  
  /**
   * Delegates to {@link org.slf4j.Logger#isTraceEnabled} 
   * method of SLF4J.
   */
  public boolean isTraceEnabled() {
    return slf4jLogger.isTraceEnabled();
  }

  /**
   * Delegates to {@link org.slf4j.Logger#isDebugEnabled} method in  SLF4J
   */
  public boolean isDebugEnabled() {
    return slf4jLogger.isDebugEnabled();
  }

  /**
   * Delegates to {@link org.slf4j.Logger#isInfoEnabled} method in  SLF4J
   */
  public boolean isInfoEnabled() {
    return slf4jLogger.isInfoEnabled();
  }

  /**
   * Delegates to {@link org.slf4j.Logger#isWarnEnabled} method in  SLF4J
   */
  public boolean isWarnEnabled() {
    return slf4jLogger.isWarnEnabled();
  }
  
  /**
   * Delegates to {@link org.slf4j.Logger#isErrorEnabled} method in SLF4J
   */
  public boolean isErrorEnabled() {
    return slf4jLogger.isErrorEnabled();
  }
  
  /**
   * Delegates to {@link #isEnabledFor(Level)}.
   * 
   * @param p the priority to check against
   * @return true if this logger is enabled for the given priority, false otehrwise.
   */
  public boolean isEnabledFor(Priority p) {
    return isEnabledFor(Level.toLevel(p.level));
  }

  /**
   * Determines whether the level passes as parameter is enabled in
   * the underlying SLF4J logger. Each log4j level is mapped directly to
   * its SLF4J equivalent, except for FATAL which is mapped as ERROR. 
   * 
   * @param l the level to check against
   * @return true if this logger is enabled for the given level, false otehrwise.
   */
  public boolean isEnabledFor(Level l) {
    switch (l.level) {
    case Level.TRACE_INT:
      return slf4jLogger.isTraceEnabled();
    case Level.DEBUG_INT:
      return slf4jLogger.isDebugEnabled();
    case Level.INFO_INT:
      return slf4jLogger.isInfoEnabled();
    case Level.WARN_INT:
      return slf4jLogger.isWarnEnabled();
    case Level.ERROR_INT:
      return slf4jLogger.isErrorEnabled();
    case Priority.FATAL_INT:
      return slf4jLogger.isErrorEnabled();
    }
    return false;
  }

  /**
   * Delegates to {@link org.slf4j.Logger#trace(String)} method in SLF4J.
   */
  public void trace(Object message) {
    // casting to String as SLF4J only accepts String instances, not Object
    // instances.
    slf4jLogger.trace(convertToString(message));
  }

  /**
   * Delegates to {@link org.slf4j.Logger#trace(String,Throwable)} 
   * method in SLF4J.
   */
  public void trace(Object message, Throwable t) {
    slf4jLogger.trace(convertToString(message), t);
  }
  
  /**
   * Delegates to {@link org.slf4j.Logger#debug(String)} method of
   * SLF4J.
   */
  public void debug(Object message) {
    // casting to String as SLF4J only accepts String instances, not Object
    // instances.
    slf4jLogger.debug(convertToString(message));
  }

  /**
   * Delegates to {@link org.slf4j.Logger#debug(String,Throwable)} 
   * method in SLF4J.
   */
  public void debug(Object message, Throwable t) {
    slf4jLogger.debug(convertToString(message), t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#info(String)} 
   * method in SLF4J.
   */
  public void info(Object message) {
    slf4jLogger.info(convertToString(message));
  }

  /**
   * Delegates to {@link org.slf4j.Logger#info(String,Throwable)} 
   * method in SLF4J.
   */
  public void info(Object message, Throwable t) {
    slf4jLogger.info(convertToString(message), t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#warn(String)} 
   * method in SLF4J.
   */
  public void warn(Object message) {
    slf4jLogger.warn(convertToString(message));
  }

  /**
   * Delegates to {@link org.slf4j.Logger#warn(String,Throwable)} 
   * method in SLF4J.
   */
  public void warn(Object message, Throwable t) {
    slf4jLogger.warn(convertToString(message), t);
  }

  
  /**
   * Delegates to {@link org.slf4j.Logger#error(String)} 
   * method in SLF4J.
   */
  public void error(Object message) {
    slf4jLogger.error(convertToString(message));
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String,Throwable)} 
   * method in SLF4J.
   */
  public void error(Object message, Throwable t) {
    slf4jLogger.error(convertToString(message), t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String)} 
   * method in SLF4J.
   */
  public void fatal(Object message) {
    slf4jLogger.error(FATAL_MARKER, convertToString(message));
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String,Throwable)} 
   * method in SLF4J. In addition, the call is marked with a marker named "FATAL".
   */
  public void fatal(Object message, Throwable t) {
    slf4jLogger.error(FATAL_MARKER, convertToString(message), t);
  }

  public void log(String FQCN, Priority p, Object msg, Throwable t) {
    int levelInt = priorityToLevelInt(p);
    if(locationAwareLogger != null) {
      if(msg != null) {
        locationAwareLogger.log(null, FQCN, levelInt, msg.toString(), t); 
      } else {
        locationAwareLogger.log(null, FQCN, levelInt, null, t); 
      }
    } else {
      throw new UnsupportedOperationException("The logger ["+slf4jLogger+"] does not seem to be location aware.");
    }
   
  }
  
  private int priorityToLevelInt(Priority p) {
    switch (p.level) {
    case Level.TRACE_INT:
      return LocationAwareLogger.TRACE_INT;
    case Priority.DEBUG_INT:
      return LocationAwareLogger.DEBUG_INT;
    case Priority.INFO_INT:
      return LocationAwareLogger.INFO_INT;
    case Priority.WARN_INT:
      return LocationAwareLogger.WARN_INT;
    case Priority.ERROR_INT:
      return LocationAwareLogger.ERROR_INT;
    case Priority.FATAL_INT:
      return LocationAwareLogger.ERROR_INT;
    default:
      throw new IllegalStateException("Unknown Priority " + p);
    }
  }
  
  private final String convertToString(Object message) {
    if (message == null) {
      return (String)message;
    } else {
      return message.toString();
    }
  }

}
