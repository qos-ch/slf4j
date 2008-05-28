package org.slf4j.bridge;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JUL bridge/router for SLF4J.
 * 
 * @author Christian Stein
 * @since 1.5.1
 */
public class SLF4JBridgeHandler extends Handler {

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
  protected Logger getPublisher(LogRecord record) {
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

  /**
   * Returns {@code Level.ALL} as SLF4J cares about discarding log statements.
   */
  public final synchronized Level getLevel() {
    return Level.ALL;
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
    Logger publisher = getPublisher(record);
    Throwable thrown = record.getThrown(); // can be null!
    String message = record.getMessage(); // can be null!
    if (format && getFormatter() != null) {
      try {
        message = getFormatter().format(record);
      } catch (Exception ex) {
        reportError(null, ex, ErrorManager.FORMAT_FAILURE);
        return;
      }
    }
    if (message == null) {
      return;
    }
    /*
     * TRACE
     */
    if (record.getLevel().intValue() <= Level.FINEST.intValue()) {
      publisher.trace(message, thrown);
      return;
    }
    /*
     * DEBUG
     */
    if (record.getLevel() == Level.FINER) {
      publisher.debug(message, thrown);
      return;
    }
    if (record.getLevel() == Level.FINE) {
      publisher.debug(message, thrown);
      return;
    }
    /*
     * INFO
     */
    if (record.getLevel() == Level.CONFIG) {
      publisher.info(message, thrown);
      return;
    }
    if (record.getLevel() == Level.INFO) {
      publisher.info(message, thrown);
      return;
    }
    /*
     * WARN
     */
    if (record.getLevel() == Level.WARNING) {
      publisher.warn(message);
      return;
    }
    /*
     * ERROR
     */
    if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
      publisher.error(message, thrown);
      return;
    }
    /*
     * Still here? Fallback and out.
     */
    publishFallback(record, publisher);
  }

  /**
   * Called by publish if no level value matched.
   * <p>
   * This implementation uses SLF4Js DEBUG level.
   * 
   * @param record
   *                to publish
   * @param publisher
   *                who logs out
   */
  protected void publishFallback(LogRecord record, Logger publisher) {
    publisher.debug(record.getMessage(), record.getThrown());
  }

}
