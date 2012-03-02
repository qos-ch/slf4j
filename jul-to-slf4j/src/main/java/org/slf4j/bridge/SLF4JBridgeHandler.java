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
package org.slf4j.bridge;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

// Based on http://bugzilla.slf4j.org/show_bug.cgi?id=38

/**
 * Bridge/route all JUL log records to the SLF4J API.
 *
 * <p>
 * Essentially, the idea is to install on the root logger an instance of
 * SLF4JBridgeHandler as the sole JUL handler in the system. Subsequently, the
 * SLF4JBridgeHandler instance will redirect all JUL log records are redirected
 * to the SLF4J API based on the following mapping of levels:
 *
 * <pre>
 * FINEST  -&gt; TRACE
 * FINER   -&gt; DEBUG
 * FINE    -&gt; DEBUG
 * INFO    -&gt; INFO
 * WARNING -&gt; WARN
 * SEVER   -&gt; ERROR
 * </pre>
 *
 * Usage:
 *
 * <pre>
 * // call only once during initialization time of your application
 * SLF4JBridgeHandler.install();
 *
 * // usual pattern: get a Logger and then log a message
 * java.util.logging.Logger julLogger = java.util.logging.Logger
 *     .getLogger(&quot;org.wombat&quot;);
 * julLogger.fine(&quot;hello world&quot;); // this will get redirected to SLF4J
 * </pre>
 *
 * <p>
 * Please note that translating a java.util.logging event into SLF4J incurs the
 * cost of constructing {@link LogRecord} instance regardless of whether the
 * SLF4J logger is disabled for the given level. <b>Consequently, j.u.l. to
 * SLF4J translation can seriously impact on the cost of disabled logging
 * statements (60 fold increase) and a measurable impact on enabled log
 * statements (20% overall increase). </b>
 * </p>
 *
 * <p>
 * If application performance is a concern, then use of SLF4JBridgeHandler is
 * appropriate only if few j.u.l. logging statements are in play.
 *
 * @author Christian Stein
 * @author Joern Huxhorn
 * @author Ceki G&uuml;lc&uuml;
 * @author Darryl Smith
 *
 * @since 1.5.1
 */
public class SLF4JBridgeHandler extends Handler {

  // The caller is java.util.logging.Logger
  private static final String FQCN = java.util.logging.Logger.class.getName();
  private static final String UNKNOWN_LOGGER_NAME = "unknown.jul.logger";

  private static final int TRACE_LEVEL_THRESHOLD = Level.FINEST.intValue();
  private static final int DEBUG_LEVEL_THRESHOLD = Level.FINE.intValue();
  private static final int INFO_LEVEL_THRESHOLD = Level.INFO.intValue();
  private static final int WARN_LEVEL_THRESHOLD = Level.WARNING.intValue();

  /**
   * Adds a SLF4JBridgeHandler instance to jul's root logger.
   *
   * <p>
   * This handler will redirect jul logging to SLF4J. However, only logs enabled
   * in j.u.l. will be redirected. For example, if a log statement invoking a
   * j.u.l. logger disabled that statement, by definition, will <em>not</em>
   * reach any SLF4JBridgeHandler instance and cannot be redirected.
   */
  public static void install() {
    if (!isInstalled()) {
      getRootLogger().addHandler(
              new SLF4JBridgeHandler());
    }
  }

  /**
   * Removes previously installed SLF4JBridgeHandler instances. See also
   * {@link #install()}.
   *
   * @throws SecurityException
   *           A <code>SecurityException</code> is thrown, if a security manager
   *           exists and if the caller does not have
   *           LoggingPermission("control").
   */
  public static void uninstall() throws SecurityException {
    java.util.logging.Logger rootLogger = getRootLogger();
    Handler[] handlers = rootLogger.getHandlers();
    for (int i = 0; i < handlers.length; i++) {
      if (handlers[i] instanceof SLF4JBridgeHandler) {
        rootLogger.removeHandler(handlers[i]);
      }
    }
  }

  /**
     * Returns true if SLF4JBridgeHandler has been previously installed, returns false otherwise.
     * @return
     * @throws SecurityException
     */
    public static boolean isInstalled() throws SecurityException {
        java.util.logging.Logger rootLogger = getRootLogger();
        Handler[] handlers = rootLogger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
          if (handlers[i] instanceof SLF4JBridgeHandler) {
            return true;
          }
        }
        return false;
      }

  private static java.util.logging.Logger getRootLogger() {
    return LogManager.getLogManager().getLogger("");
  }

  /**
   * Initialize this handler.
   *
   */
  public SLF4JBridgeHandler() {
  }

  /**
   * No-op implementation.
   */
  public void close() {
    // empty
  }

  /**
   * No-op implementation.
   */
  public void flush() {
    // empty
  }

  /**
   * Return the Logger instance that will be used for logging.
   */
  protected Logger getSLF4JLogger(LogRecord record) {
    String name = record.getLoggerName();
    if (name == null) {
      name = UNKNOWN_LOGGER_NAME;
    }
    return LoggerFactory.getLogger(name);
  }

  protected void callLocationAwareLogger(LocationAwareLogger lal,
      LogRecord record) {
    int julLevelValue = record.getLevel().intValue();
    int slf4jLevel;

    if (julLevelValue <= TRACE_LEVEL_THRESHOLD) {
      slf4jLevel = LocationAwareLogger.TRACE_INT;
    } else if (julLevelValue <= DEBUG_LEVEL_THRESHOLD) {
      slf4jLevel = LocationAwareLogger.DEBUG_INT;
    } else if (julLevelValue <= INFO_LEVEL_THRESHOLD) {
      slf4jLevel = LocationAwareLogger.INFO_INT;
    } else if (julLevelValue <= WARN_LEVEL_THRESHOLD) {
      slf4jLevel = LocationAwareLogger.WARN_INT;
    } else {
      slf4jLevel = LocationAwareLogger.ERROR_INT;
    }
    String i18nMessage = getMessageI18N(record);
    lal.log(null, FQCN, slf4jLevel, i18nMessage, null, record.getThrown());
  }

  protected void callPlainSLF4JLogger(Logger slf4jLogger, LogRecord record) {
    String i18nMessage = getMessageI18N(record);
    int julLevelValue = record.getLevel().intValue();
    if (julLevelValue <= TRACE_LEVEL_THRESHOLD) {
      slf4jLogger.trace(i18nMessage, record.getThrown());
    } else if (julLevelValue <= DEBUG_LEVEL_THRESHOLD) {
      slf4jLogger.debug(i18nMessage, record.getThrown());
    } else if (julLevelValue <= INFO_LEVEL_THRESHOLD) {
      slf4jLogger.info(i18nMessage, record.getThrown());
    } else if (julLevelValue <= WARN_LEVEL_THRESHOLD) {
      slf4jLogger.warn(i18nMessage, record.getThrown());
    } else {
      slf4jLogger.error(i18nMessage, record.getThrown());
    }
  }

  /**
   * Get the record's message, possibly via a resource bundle.
   *
   * @param record
   * @return
   */
  private String getMessageI18N(LogRecord record) {
    String message = record.getMessage();

    if (message == null) {
      return null;
    }

    ResourceBundle bundle = record.getResourceBundle();
    if (bundle != null) {
      try {
        message = bundle.getString(message);
      } catch (MissingResourceException e) {
      }
    }
    Object[] params = record.getParameters();
    if (params != null) {
      message = MessageFormat.format(message, params);
    }
    return message;
  }

  /**
   * Publish a LogRecord.
   * <p>
   * The logging request was made initially to a Logger object, which
   * initialized the LogRecord and forwarded it here.
   * <p>
   * This handler ignores the Level attached to the LogRecord, as SLF4J cares
   * about discarding log statements.
   *
   * @param record
   *          Description of the log event. A null record is silently ignored
   *          and is not published.
   */
  public void publish(LogRecord record) {
    // Silently ignore null records.
    if (record == null) {
      return;
    }

    Logger slf4jLogger = getSLF4JLogger(record);
    String message = record.getMessage(); // can be null!
    // this is a check to avoid calling the underlying logging system
    // with a null message. While it is legitimate to invoke j.u.l. with
    // a null message, other logging frameworks do not support this.
    // see also http://bugzilla.slf4j.org/show_bug.cgi?id=108
    if (message == null) {
      message = "";
    }
    if (slf4jLogger instanceof LocationAwareLogger) {
      callLocationAwareLogger((LocationAwareLogger) slf4jLogger, record);
    } else {
      callPlainSLF4JLogger(slf4jLogger, record);
    }
  }

}
