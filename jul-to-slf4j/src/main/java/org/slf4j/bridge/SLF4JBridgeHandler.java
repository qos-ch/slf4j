package org.slf4j.bridge;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * JUL bridge/router for SLF4J.
 * 
 * @author Christian Stein
 * @since 1.5.1
 */
public class SLF4JBridgeHandler extends Handler {

  private static final String FQCN = SLF4JBridgeHandler.class.getName();

  private static final int TRACE_LEVEL_THRESHOLD = Level.FINEST.intValue();
  private static final int DEBUG_LEVEL_THRESHOLD = Level.FINE.intValue();
  private static final int INFO_LEVEL_THRESHOLD = Level.INFO.intValue();
  private static final int WARN_LEVEL_THRESHOLD = Level.WARNING.intValue();

  /**
   * Resets the entire JUL logging system and adds a single SLF4JHandler
   * instance to the root logger.
   * <p>
   * Same as: <code>SLF4JHandler.install(new SLF4JHandler(true, true));</code>
   */
  public static void install() {
    install(new SLF4JBridgeHandler(true, true));
  }

  /**
   * Resets the entire JUL logging system and adds the SLF4JHandler instance to
   * the root logger.
   * 
   * <pre><code>
   * SLF4JHandler handler = new SLF4JHandler(true, true);
   * handler.setFilter(...);
   * handler.setFormatter(...);
   * handler.setErrorManager(...);
   * SLF4JHandler.install(handler);
   * </code></pre>
   */
  public static void install(SLF4JBridgeHandler handler) {
    LogManager.getLogManager().reset();
    LogManager.getLogManager().getLogger("").addHandler(handler);
  }

  /**
   * Rereads the JUL configuration.
   * 
   * @see LogManager#readConfiguration();
   * @throws Exception
   *                 A <code>SecurityException</code> is thrown, if a security
   *                 manager exists and if the caller does not have
   *                 LoggingPermission("control"). <code>IOException</code> if
   *                 there are IO problems reading the configuration.
   */
  public static void uninstall() throws Exception {
    LogManager.getLogManager().readConfiguration();
  }

  protected final boolean classname;

  protected final boolean format;

  /**
   * Initialize this handler.
   * 
   * @param classname
   *                Use the source class name provided by the LogRecord to get
   *                the SLF4J Logger name. If <code>false</code>, the raw
   *                name of the JUL logger is used.
   * @param format
   *                If <code>true</code>, use the attached formatter if
   *                available. If <code>false</code> the formatter is ignored.
   */
  public SLF4JBridgeHandler(boolean classname, boolean format) {
    this.classname = classname;
    this.format = format;
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
    String name = null;
    if (classname) {
      if (name == null) {
        name = record.getSourceClassName();
      }
    }
    if (name == null) {
      name = record.getLoggerName();
    }
    if (name == null) {
      name = SLF4JBridgeHandler.class.getName();
    }
    return LoggerFactory.getLogger(name);
  }

  public void callLocationAwareLogger(LocationAwareLogger lal, LogRecord record) {
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
    lal.log(null, FQCN, slf4jLevel, record.getMessage(), record.getThrown());
  }

  public void callPlainSLF4JLogger(Logger slf4jLogger, LogRecord record) {
    int julLevelValue = record.getLevel().intValue();
    if (julLevelValue <= TRACE_LEVEL_THRESHOLD) {
      slf4jLogger.trace(record.getMessage(), record.getThrown());
    } else if (julLevelValue <= DEBUG_LEVEL_THRESHOLD) {
      slf4jLogger.debug(record.getMessage(), record.getThrown());
    } else if (julLevelValue <= INFO_LEVEL_THRESHOLD) {
      slf4jLogger.info(record.getMessage(), record.getThrown());
    } else if (julLevelValue <= WARN_LEVEL_THRESHOLD) {
      slf4jLogger.warn(record.getMessage(), record.getThrown());
    } else {
      slf4jLogger.error(record.getMessage(), record.getThrown());
    }
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
   *                Description of the log event. A null record is silently
   *                ignored and is not published.
   */
  public void publish(LogRecord record) {
    /*
     * Silently ignore null records.
     */
    if (record == null) {
      return;
    }
    /*
     * Get our SLF4J logger for publishing the record.
     */
    Logger slf4jLogger = getSLF4JLogger(record);
    String message = record.getMessage(); // can be null!
    if (message == null) {
      return;
    }
    if (slf4jLogger instanceof LocationAwareLogger) {
      callLocationAwareLogger((LocationAwareLogger) slf4jLogger, record);
    } else {
      callPlainSLF4JLogger(slf4jLogger, record);
    }
  }

}
