/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
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
package org.slf4j.impl;

import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.Util;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>Simple implementation of {@link Logger} that sends all enabled log messages,
 * for all defined loggers, to the console ({@code System.err}).
 * The following system properties are supported to configure the behavior of this logger:</p>
 * <ul>
 * <li><code>org.slf4j.simplelogger.defaultlog</code> -
 * Default logging detail level for all instances of SimpleLogger.
 * Must be one of ("trace", "debug", "info", "warn", or "error").
 * If not specified, defaults to "info". </li>
 * <li><code>org.slf4j.simplelogger.log.xxxxx</code> -
 * Logging detail level for a SimpleLogger instance named "xxxxx".
 * Must be one of ("trace", "debug", "info", "warn", or "error").
 * If not specified, the default logging detail level is used.</li>
 * <li><code>org.slf4j.simplelogger.showdatetime</code> -
 * Set to <code>true</code> if you want the current date and time
 * to be included in output messages. Default is <code>true</code></li>
 * <li><code>org.slf4j.simplelogger.dateTimeFormat</code> -
 * The date and time format to be used in the output messages.
 * The pattern describing the date and time format is the same that is
 * used in <code>java.text.SimpleDateFormat</code>. If the format is not
 * specified or is invalid, the number of milliseonds since start up will be output.
 * </li>
 * <li><code>org.slf4j.simplelogger.showthreadname</code> -
 * Set to <code>true</code> if you want to output the current thread name.
 * Defaults to <code>true</code>.</li>
 * <li><code>org.slf4j.simplelogger.showlogname</code> -
 * Set to <code>true</code> if you want the Logger instance name to be
 * included in output messages. Defaults to <code>true</code>.</li>
 * <li><code>org.slf4j.simplelogger.showShortLogname</code> -
 * Set to <code>true</code> if you want the last component of the name to be
 * included in output messages. Defaults to <code>false</code>.</li>
 * </ul>
 * <p>In addition to looking for system properties with the names specified
 * above, this implementation also checks for a class loader resource named
 * <code>"simplelogger.properties"</code>, and includes any matching definitions
 * from this resource (if it exists).</p>
 * <p>With no configurationn, the default output includes the relative time in milliseconds,
 * thread name, the level, logger name, and the message followed by the line
 * separator for the host.  In log4j terms it amounts to the "%r [%t]
 * %level %logger - %m%n" pattern. </p>
 * <p>Sample output follows.</p>
 * <pre>
 * 176 [main] INFO examples.Sort - Populating an array of 2 elements in reverse order.
 * 225 [main] INFO examples.SortAlgo - Entered the sort method.
 * 304 [main] INFO examples.SortAlgo - Dump of integer array:
 * 317 [main] INFO examples.SortAlgo - Element [0] = 0
 * 331 [main] INFO examples.SortAlgo - Element [1] = 1
 * 343 [main] INFO examples.Sort - The next log statement should be an error message.
 * 346 [main] ERROR examples.SortAlgo - Tried to dump an uninitialized array.
 * at org.log4j.examples.SortAlgo.dump(SortAlgo.java:58)
 * at org.log4j.examples.Sort.main(Sort.java:64)
 * 467 [main] INFO  examples.Sort - Exiting main method.
 * </pre>
 * <p/>
 * <p>This implementation is heavily inspired by
 * <a href="http://commons.apache.org/logging/">Apache Commons Logging</a>'s SimpleLog.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author <a href="mailto:sanders@apache.org">Scott Sanders</a>
 * @author Rod Waldhoff
 * @author Robert Burrell Donkin
 * @author C&eacute;drik LIME
 */
public class SimpleLogger extends MarkerIgnoringBase {

  private static final long serialVersionUID = -632788891211436180L;

  /**
   * Mark the time when this class gets loaded into memory.
   */
  private static long START_TIME = System.currentTimeMillis();

  private static final String CONFIGURATION_FILE = "simplelogger.properties";

  /**
   * All system properties used by <code>SimpleLogger</code> start with this
   */
  private static final String systemPrefix = "org.slf4j.simplelogger.";

  /**
   * Properties loaded from simplelogger.properties
   */
  private static final Properties simpleLoggerProps = new Properties();

  /**
   * Include the instance name in the log message?
   */
  private static boolean SHOW_LOG_NAME = true;
  /**
   * Include the short name ( last component ) of the logger in the log
   * message. Defaults to true - otherwise we'll be lost in a flood of
   * messages without knowing who sends them.
   */
  private static boolean SHOW_SHORT_NAME = false;
  /**
   * Include the current time in the log message
   */
  private static boolean SHOW_DATE_TIME = false;

  /**
   * The date and time format to use in the log message
   */
  private static String DATE_TIME_FORMAT_STR = null;

  /**
   * Include the current thread name in the log message
   */
  private static boolean SHOW_THREAD_NAME = true;

  /**
   * The log file to write to. Default is null.
   */
  private static String logFile = null;
  /**
   * Used to format times.
   * <p/>
   * Any code that accesses this object should first obtain a lock on it,
   * ie use synchronized(dateFormatter); this requirement is
   * to fix an existing thread safety bug (SimpleDateFormat.format
   * is not thread-safe).
   */
  private static DateFormat DATE_FORMATTER = null;

  /**
   * "Trace" level logging.
   */
  public static final int LOG_LEVEL_TRACE = LocationAwareLogger.TRACE_INT;
  /**
   * "Debug" level logging.
   */
  public static final int LOG_LEVEL_DEBUG = LocationAwareLogger.DEBUG_INT;
  /**
   * "Info" level logging.
   */
  public static final int LOG_LEVEL_INFO = LocationAwareLogger.INFO_INT;
  /**
   * "Warn" level logging.
   */
  public static final int LOG_LEVEL_WARN = LocationAwareLogger.WARN_INT;
  /**
   * "Error" level logging.
   */
  public static final int LOG_LEVEL_ERROR = LocationAwareLogger.ERROR_INT;

  /**
   * Enable all logging levels
   */
  //public static final int LOG_LEVEL_ALL = (LOG_LEVEL_TRACE - 10);

  /**
   * Enable no logging levels
   */
  //public static final int LOG_LEVEL_OFF = (LOG_LEVEL_ERROR + 10);

  private static int defaultLogLevel = LOG_LEVEL_INFO;

  private static String getStringProperty(String name) {
    String prop = null;
    try {
      prop = System.getProperty(name);
    } catch (SecurityException e) {
      ; // Ignore
    }
    return (prop == null) ? simpleLoggerProps.getProperty(name) : prop;
  }

  private static String getStringProperty(String name, String defaultValue) {
    String prop = getStringProperty(name);
    return (prop == null) ? defaultValue : prop;
  }

  private static boolean getBooleanProperty(String name, boolean defaultValue) {
    String prop = getStringProperty(name);
    return (prop == null) ? defaultValue : "true".equalsIgnoreCase(prop);
  }


  // Initialize class attributes.
  // Load properties file, if found.
  // Override with system properties.
  static {
    // Add props from the resource simplelogger.properties
    InputStream in = (InputStream) AccessController.doPrivileged(
            new PrivilegedAction() {
              public Object run() {
                ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
                if (threadCL != null) {
                  return threadCL.getResourceAsStream(CONFIGURATION_FILE);
                } else {
                  return ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE);
                }
              }
            });
    if (null != in) {
      try {
        simpleLoggerProps.load(in);
        in.close();
      } catch (java.io.IOException e) {
        // ignored
      }
    }

    SHOW_LOG_NAME = getBooleanProperty(systemPrefix + "showlogname", SHOW_LOG_NAME);
    SHOW_SHORT_NAME = getBooleanProperty(systemPrefix + "showShortLogname", SHOW_SHORT_NAME);
    SHOW_DATE_TIME = getBooleanProperty(systemPrefix + "showdatetime", SHOW_DATE_TIME);
    SHOW_THREAD_NAME = getBooleanProperty(systemPrefix + "showthreadname", SHOW_THREAD_NAME);
    DATE_TIME_FORMAT_STR = getStringProperty(systemPrefix + "dateTimeFormat", DATE_TIME_FORMAT_STR);
    logFile = getStringProperty(systemPrefix + "logFile", logFile);
    String defaultLogLevelString = getStringProperty(systemPrefix + "defaultlog", null);
    if (defaultLogLevelString != null)
      defaultLogLevel = stringToLevel(defaultLogLevelString);

    try {
      DATE_FORMATTER = new SimpleDateFormat(DATE_TIME_FORMAT_STR);
    } catch (IllegalArgumentException e) {
      Util.report("Bad date format in " + CONFIGURATION_FILE + "; will default to relative time ", e);
    }
  }


  /** The name of this simple log instance */
  //protected String logName = null;// == name
  /**
   * The current log level
   */
  protected int currentLogLevel = LOG_LEVEL_INFO;
  /**
   * The short name of this simple log instance
   */
  private transient String shortLogName = null;

  /**
   * Package access allows only {@link SimpleLoggerFactory} to instantiate
   * SimpleLogger instances.
   */
  SimpleLogger(String name) {
    this.name = name;

    // Set initial log level
    this.currentLogLevel = LOG_LEVEL_INFO;

    // Set log level from properties
    String lvl = getStringProperty(systemPrefix + "log." + name);
    int i = String.valueOf(name).lastIndexOf(".");
    while (null == lvl && i > -1) {
      name = name.substring(0, i);
      lvl = getStringProperty(systemPrefix + "log." + name);
      i = String.valueOf(name).lastIndexOf(".");
    }

    if (null == lvl) {
      lvl = getStringProperty(systemPrefix + "defaultlog");
    }

    this.currentLogLevel = stringToLevel(lvl);
  }

  private static int stringToLevel(String lvl) {
    if ("trace".equalsIgnoreCase(lvl)) {
      return LOG_LEVEL_TRACE;
    } else if ("debug".equalsIgnoreCase(lvl)) {
      return LOG_LEVEL_DEBUG;
    } else if ("info".equalsIgnoreCase(lvl)) {
      return LOG_LEVEL_INFO;
    } else if ("warn".equalsIgnoreCase(lvl)) {
      return LOG_LEVEL_WARN;
    } else if ("error".equalsIgnoreCase(lvl)) {
      return LOG_LEVEL_ERROR;
    }
    // assume INFO by default
    return LOG_LEVEL_INFO;
  }


  /**
   * This is our internal implementation for logging regular (non-parameterized)
   * log messages.
   *
   * @param level   One of the LOG_LEVEL_XXX constants defining the log level
   * @param message The message itself
   * @param t       The exception whose stack trace should be logged
   */
  private void log(int level, String message, Throwable t) {
    if (!isLevelEnabled(level)) {
      return;
    }

    StringBuffer buf = new StringBuffer(32);

    // Append date-time if so configured
    if (SHOW_DATE_TIME) {
      if (DATE_FORMATTER != null) {
        buf.append(getFormattedDate());
        buf.append(' ');
      } else {
        buf.append(System.currentTimeMillis() - START_TIME);
        buf.append(' ');
      }
    }

    // Append current thread name if so configured
    if (SHOW_THREAD_NAME) {
      buf.append('[');
      buf.append(Thread.currentThread().getName());
      buf.append("] ");
    }

    // Append a readable representation of the log level
    switch (level) {
      case LOG_LEVEL_TRACE:
        buf.append("TRACE");
        break;
      case LOG_LEVEL_DEBUG:
        buf.append("DEBUG");
        break;
      case LOG_LEVEL_INFO:
        buf.append("INFO");
        break;
      case LOG_LEVEL_WARN:
        buf.append("WARN");
        break;
      case LOG_LEVEL_ERROR:
        buf.append("ERROR");
        break;
    }
    buf.append(' ');

    // Append the name of the log instance if so configured
    if (SHOW_SHORT_NAME) {
      if (shortLogName == null) shortLogName = computeShortName();
      buf.append(String.valueOf(shortLogName)).append(" - ");
    } else if (SHOW_LOG_NAME) {
      buf.append(String.valueOf(name)).append(" - ");
    }

    // Append the message
    buf.append(message);

    System.err.println(buf.toString());
    // Append stack trace if not null
    if (t != null) {
      t.printStackTrace(System.err);
    }
    System.err.flush();
  }

  private String getFormattedDate() {
    Date now = new Date();
    String dateText;
    synchronized (DATE_FORMATTER) {
      dateText = DATE_FORMATTER.format(now);
    }
    return dateText;
  }

  private String computeShortName() {
    return name.substring(name.lastIndexOf(".") + 1);
  }

  /**
   * For formatted messages, first substitute arguments and then log.
   *
   * @param level
   * @param format
   * @param arg1
   * @param arg2
   */
  private void formatAndLog(int level, String format, Object arg1,
                            Object arg2) {
    if (!isLevelEnabled(level)) {
      return;
    }
    FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
    log(level, tp.getMessage(), tp.getThrowable());
  }

  /**
   * For formatted messages, first substitute arguments and then log.
   *
   * @param level
   * @param format
   * @param arguments a list of 3 ore more arguments
   */
  private void formatAndLog(int level, String format, Object... arguments) {
    if (!isLevelEnabled(level)) {
      return;
    }
    FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
    log(level, tp.getMessage(), tp.getThrowable());
  }

  /**
   * Is the given log level currently enabled?
   *
   * @param logLevel is this level enabled?
   */
  protected boolean isLevelEnabled(int logLevel) {
    // log level are numerically ordered so can use simple numeric
    // comparison
    return (logLevel >= currentLogLevel);
  }

  /**
   * Are {@code trace} messages currently enabled?
   */
  public boolean isTraceEnabled() {
    return isLevelEnabled(LOG_LEVEL_TRACE);
  }

  /**
   * A simple implementation which logs messages of level TRACE according
   * to the format outlined above.
   */
  public void trace(String msg) {
    log(LOG_LEVEL_TRACE, msg, null);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * TRACE according to the format outlined above.
   */
  public void trace(String format, Object param1) {
    formatAndLog(LOG_LEVEL_TRACE, format, param1, null);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * TRACE according to the format outlined above.
   */
  public void trace(String format, Object param1, Object param2) {
    formatAndLog(LOG_LEVEL_TRACE, format, param1, param2);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * TRACE according to the format outlined above.
   */
  public void trace(String format, Object[] argArray) {
    formatAndLog(LOG_LEVEL_TRACE, format, argArray);
  }

  /**
   * Log a message of level TRACE, including an exception.
   */
  public void trace(String msg, Throwable t) {
    log(LOG_LEVEL_TRACE, msg, t);
  }

  /**
   * Are {@code debug} messages currently enabled?
   */
  public boolean isDebugEnabled() {
    return isLevelEnabled(LOG_LEVEL_DEBUG);
  }

  /**
   * A simple implementation which logs messages of level DEBUG according
   * to the format outlined above.
   */
  public void debug(String msg) {
    log(LOG_LEVEL_DEBUG, msg, null);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * DEBUG according to the format outlined above.
   */
  public void debug(String format, Object param1) {
    formatAndLog(LOG_LEVEL_DEBUG, format, param1, null);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * DEBUG according to the format outlined above.
   */
  public void debug(String format, Object param1, Object param2) {
    formatAndLog(LOG_LEVEL_DEBUG, format, param1, param2);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * DEBUG according to the format outlined above.
   */
  public void debug(String format, Object[] argArray) {
    formatAndLog(LOG_LEVEL_DEBUG, format, argArray);
  }

  /**
   * Log a message of level DEBUG, including an exception.
   */
  public void debug(String msg, Throwable t) {
    log(LOG_LEVEL_DEBUG, msg, t);
  }

  /**
   * Are {@code info} messages currently enabled?
   */
  public boolean isInfoEnabled() {
    return isLevelEnabled(LOG_LEVEL_INFO);
  }

  /**
   * A simple implementation which logs messages of level INFO according
   * to the format outlined above.
   */
  public void info(String msg) {
    log(LOG_LEVEL_INFO, msg, null);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * INFO according to the format outlined above.
   */
  public void info(String format, Object arg) {
    formatAndLog(LOG_LEVEL_INFO, format, arg, null);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * INFO according to the format outlined above.
   */
  public void info(String format, Object arg1, Object arg2) {
    formatAndLog(LOG_LEVEL_INFO, format, arg1, arg2);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * INFO according to the format outlined above.
   */
  public void info(String format, Object[] argArray) {
    formatAndLog(LOG_LEVEL_INFO, format, argArray);
  }

  /**
   * Log a message of level INFO, including an exception.
   */
  public void info(String msg, Throwable t) {
    log(LOG_LEVEL_INFO, msg, t);
  }

  /**
   * Are {@code warn} messages currently enabled?
   */
  public boolean isWarnEnabled() {
    return isLevelEnabled(LOG_LEVEL_WARN);
  }

  /**
   * A simple implementation which always logs messages of level WARN according
   * to the format outlined above.
   */
  public void warn(String msg) {
    log(LOG_LEVEL_WARN, msg, null);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * WARN according to the format outlined above.
   */
  public void warn(String format, Object arg) {
    formatAndLog(LOG_LEVEL_WARN, format, arg, null);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * WARN according to the format outlined above.
   */
  public void warn(String format, Object arg1, Object arg2) {
    formatAndLog(LOG_LEVEL_WARN, format, arg1, arg2);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * WARN according to the format outlined above.
   */
  public void warn(String format, Object[] argArray) {
    formatAndLog(LOG_LEVEL_WARN, format, argArray);
  }

  /**
   * Log a message of level WARN, including an exception.
   */
  public void warn(String msg, Throwable t) {
    log(LOG_LEVEL_WARN, msg, t);
  }

  /**
   * Are {@code error} messages currently enabled?
   */
  public boolean isErrorEnabled() {
    return isLevelEnabled(LOG_LEVEL_ERROR);
  }

  /**
   * A simple implementation which always logs messages of level ERROR according
   * to the format outlined above.
   */
  public void error(String msg) {
    log(LOG_LEVEL_ERROR, msg, null);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * ERROR according to the format outlined above.
   */
  public void error(String format, Object arg) {
    formatAndLog(LOG_LEVEL_ERROR, format, arg, null);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * ERROR according to the format outlined above.
   */
  public void error(String format, Object arg1, Object arg2) {
    formatAndLog(LOG_LEVEL_ERROR, format, arg1, arg2);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * ERROR according to the format outlined above.
   */
  public void error(String format, Object[] argArray) {
    formatAndLog(LOG_LEVEL_ERROR, format, argArray);
  }

  /**
   * Log a message of level ERROR, including an exception.
   */
  public void error(String msg, Throwable t) {
    log(LOG_LEVEL_ERROR, msg, t);
  }
}
