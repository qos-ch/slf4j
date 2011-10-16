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
package org.apache.log4j;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.Category</code> class (as found in log4j 1.2) by
 * delegation of all calls to a {@link org.slf4j.Logger} instance.
 * </p>
 *
 * <p>
 * Log4j's <code>trace</code>, <code>debug()</code>, <code>info()</code>,
 * <code>warn()</code>, <code>error()</code> printing methods are directly
 * mapped to their SLF4J equivalents. Log4j's <code>fatal()</code> printing
 * method is mapped to SLF4J's <code>error()</code> method with a FATAL marker.
 *
 * @author S&eacute;bastien Pennec
 * @author Ceki G&uuml;lc&uuml;
 */
public class Category {

  private static final String CATEGORY_FQCN = Category.class.getName();

  private String name;

  protected org.slf4j.Logger slf4jLogger;
  private org.slf4j.spi.LocationAwareLogger locationAwareLogger;

  private static Marker FATAL_MARKER = MarkerFactory.getMarker("FATAL");

  Category(String name) {
    this.name = name;
    slf4jLogger = LoggerFactory.getLogger(name);
    if (slf4jLogger instanceof LocationAwareLogger) {
      locationAwareLogger = (LocationAwareLogger) slf4jLogger;
    }
  }

  public static Category getInstance(Class clazz) {
    return Log4jLoggerFactory.getLogger(clazz.getName());
  }

  public static Category getInstance(String name) {
    return Log4jLoggerFactory.getLogger(name);
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
    if (slf4jLogger.isTraceEnabled()) {
      return Level.TRACE;
    }
    if (slf4jLogger.isDebugEnabled()) {
      return Level.DEBUG;
    }
    if (slf4jLogger.isInfoEnabled()) {
      return Level.INFO;
    }
    if (slf4jLogger.isWarnEnabled()) {
      return Level.WARN;
    }
    return Level.ERROR;
  }

  /**
   * Returns the assigned {@link Level}, if any, for this Category. This
   * implementation always returns null.
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
   * Delegates to {@link org.slf4j.Logger#isDebugEnabled} method in SLF4J
   */
  public boolean isDebugEnabled() {
    return slf4jLogger.isDebugEnabled();
  }

  /**
   * Delegates to {@link org.slf4j.Logger#isInfoEnabled} method in SLF4J
   */
  public boolean isInfoEnabled() {
    return slf4jLogger.isInfoEnabled();
  }

  /**
   * Delegates tob {@link org.slf4j.Logger#isWarnEnabled} method in SLF4J
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
   * Determines whether the priority passed as parameter is enabled in the
   * underlying SLF4J logger. Each log4j priority is mapped directly to its
   * SLF4J equivalent, except for FATAL which is mapped as ERROR.
   *
   * @param p
   *          the priority to check against
   * @return true if this logger is enabled for the given level, false
   *         otherwise.
   */
  public boolean isEnabledFor(Priority p) {
    switch (p.level) {
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

  void differentiatedLog(Marker marker, String fqcn, int level, Object message,
      Throwable t) {

    String m = convertToString(message);
    if (locationAwareLogger != null) {
      locationAwareLogger.log(marker, fqcn, level, m, null, t);
    } else {
      switch (level) {
      case LocationAwareLogger.TRACE_INT:
        slf4jLogger.trace(marker, m);
        break;
      case LocationAwareLogger.DEBUG_INT:
        slf4jLogger.debug(marker, m);
        break;
      case LocationAwareLogger.INFO_INT:
        slf4jLogger.info(marker, m);
        break;
      case LocationAwareLogger.WARN_INT:
        slf4jLogger.warn(marker, m);
        break;
      case LocationAwareLogger.ERROR_INT:
        slf4jLogger.error(marker, m);
        break;
      }
    }
  }

  /**
   * Delegates to {@link org.slf4j.Logger#debug(String)} method of SLF4J.
   */
  public void debug(Object message) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.DEBUG_INT,
        message, null);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#debug(String,Throwable)} method in
   * SLF4J.
   */
  public void debug(Object message, Throwable t) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.DEBUG_INT,
        message, t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#info(String)} method in SLF4J.
   */
  public void info(Object message) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.INFO_INT,
        message, null);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#info(String,Throwable)} method in
   * SLF4J.
   */
  public void info(Object message, Throwable t) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.INFO_INT,
        message, t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#warn(String)} method in SLF4J.
   */
  public void warn(Object message) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.WARN_INT,
        message, null);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#warn(String,Throwable)} method in
   * SLF4J.
   */
  public void warn(Object message, Throwable t) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.WARN_INT,
        message, t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String)} method in SLF4J.
   */
  public void error(Object message) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.ERROR_INT,
        message, null);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String,Throwable)} method in
   * SLF4J.
   */
  public void error(Object message, Throwable t) {
    differentiatedLog(null, CATEGORY_FQCN, LocationAwareLogger.ERROR_INT,
        message, t);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String)} method in SLF4J.
   */
  public void fatal(Object message) {
    differentiatedLog(FATAL_MARKER, CATEGORY_FQCN,
        LocationAwareLogger.ERROR_INT, message, null);
  }

  /**
   * Delegates to {@link org.slf4j.Logger#error(String,Throwable)} method in
   * SLF4J. In addition, the call is marked with a marker named "FATAL".
   */
  public void fatal(Object message, Throwable t) {
    differentiatedLog(FATAL_MARKER, CATEGORY_FQCN,
        LocationAwareLogger.ERROR_INT, message, t);
  }

  // See also http://bugzilla.slf4j.org/show_bug.cgi?id=168
  public void log(String FQCN, Priority p, Object msg, Throwable t) {
    int levelInt = priorityToLevelInt(p);
    differentiatedLog(null, FQCN, levelInt, msg, t);
  }

  public void log(Priority p, Object message, Throwable t) {
    int levelInt = priorityToLevelInt(p);
    differentiatedLog(null, CATEGORY_FQCN, levelInt, message, t);
  }

  public void log(Priority p, Object message) {
    int levelInt = priorityToLevelInt(p);
    differentiatedLog(null, CATEGORY_FQCN, levelInt, message, null);
  }

  private int priorityToLevelInt(Priority p) {
    switch (p.level) {
    case Level.TRACE_INT:
    case Level.X_TRACE_INT:
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

  protected final String convertToString(Object message) {
    if (message == null) {
      return (String) message;
    } else {
      return message.toString();
    }
  }

}
