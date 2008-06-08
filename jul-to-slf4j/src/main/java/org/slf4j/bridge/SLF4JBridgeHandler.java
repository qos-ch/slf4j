/*
 * Copyright (c) 2004-2008 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 */

package org.slf4j.bridge;

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
 * <p>Essentially, the idea is to install on the root logger an instance of 
 * SLF4JBridgeHandler as the sole JUL handler in the system. Subsequently, the 
 * SLF4JBridgeHandler instance will redirect all JUL log records are redirected to 
 * the SLF4J API based on the following mapping of levels:
 * 
 * <pre>
 * FINEST  -> TRACE
 * FINER   -> DEBUG
 * FINE    -> DEBUG
 * INFO    -> INFO
 * WARNING -> WARN
 * SEVER   -> ERROR
 * </pre>
 * 
 * Usage:
 * 
 * <pre>
 *   // call only once during initialization time of your application
 *   SLF4JHandler.install();
 *   
 *   // usual pattern: get a Logger and then log a message
 *   java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("org.wombat");
 *   julLogger.fine("hello world"); // this will get redirected to SLF4J
 * </pre>
 * 
 * @author Christian Stein
 * @author Joern Huxhorn        
 * @author Ceki G&uml;lc&uml;
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
   * Resets the entire JUL logging system and adds new SLF4JHandler instance to
   * the root logger.
   */
  public static void install() {
    LogManager.getLogManager().reset();
    LogManager.getLogManager().getLogger("").addHandler(new SLF4JBridgeHandler());
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

  protected void callLocationAwareLogger(LocationAwareLogger lal, LogRecord record) {
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

  protected void callPlainSLF4JLogger(Logger slf4jLogger, LogRecord record) {
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
    // Silently ignore null records.
    if (record == null) {
      return;
    }
    
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
