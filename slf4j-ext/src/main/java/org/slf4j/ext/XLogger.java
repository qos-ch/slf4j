package org.slf4j.ext;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A utility that provides standard mechanisms for logging certain kinds of
 * activities.
 * 
 * @author Ralph Goers
 * @author Ceki Gulcu
 */
public class XLogger implements Logger {

  private static final String FQCN = XLogger.class.getName();
  static Marker FLOW_MARKER = MarkerFactory.getMarker("FLOW");
  static Marker ENTRY_MARKER = MarkerFactory.getMarker("ENTER");
  static Marker EXIT_MARKER = MarkerFactory.getMarker("EXIT");

  static Marker EXCEPTION_MARKER = MarkerFactory.getMarker("EXCEPTION");
  static Marker THROWING_MARKER = MarkerFactory.getMarker("EXCEPTION");
  static Marker CATCHING_MARKER = MarkerFactory.getMarker("EXCEPTION");

  static String EXIT_MESSAGE_0 = "exit";
  static String EXIT_MESSAGE_1 = "exit with {}";

  static String ENTRY_MESSAGE_0 = "entry";
  static String ENTRY_MESSAGE_1 = "entry with ({})";
  static String ENTRY_MESSAGE_2 = "entry with ({}, {})";
  static String ENTRY_MESSAGE_3 = "entry with ({}, {}, {})";
  static String ENTRY_MESSAGE_4 = "entry with ({}, {}, {}, {})";
  static int ENTRY_MESSAGE_ARRAY_LEN = 5;
  static String[] ENTRY_MESSAGE_ARRAY = new String[ENTRY_MESSAGE_ARRAY_LEN];
  static {
    ENTRY_MARKER.add(FLOW_MARKER);
    EXIT_MARKER.add(FLOW_MARKER);
    THROWING_MARKER.add(EXCEPTION_MARKER);
    CATCHING_MARKER.add(EXCEPTION_MARKER);

    ENTRY_MESSAGE_ARRAY[0] = ENTRY_MESSAGE_0;
    ENTRY_MESSAGE_ARRAY[1] = ENTRY_MESSAGE_1;
    ENTRY_MESSAGE_ARRAY[2] = ENTRY_MESSAGE_2;
    ENTRY_MESSAGE_ARRAY[3] = ENTRY_MESSAGE_3;
    ENTRY_MESSAGE_ARRAY[4] = ENTRY_MESSAGE_4;
  }

  final Logger logger;

  /**
   * Given an underlying logger, constuct an XLogger
   * 
   * @param logger underlying logger
   */
  public XLogger(Logger logger) {

    if (logger instanceof LocationAwareLogger) {
      this.logger = logger;
    } else {
      // if the logger is not location aware, assume that
      // there is no point in further effort
      this.logger = NOPLogger.NOP_LOGGER;
    }
  }

  /**
   * Log method entry.
   * 
   * @param argArray
   *                supplied parameters
   */
  public void entry(Object... argArray) {
    if (logger.isTraceEnabled(ENTRY_MARKER)) {
      String messagePattern = null;
      if (argArray.length <= ENTRY_MESSAGE_ARRAY_LEN) {
        messagePattern = ENTRY_MESSAGE_ARRAY[argArray.length];
      } else {
        messagePattern = buildMessagePattern(argArray.length);
      }
      String formattedMessage = MessageFormatter.arrayFormat(messagePattern,
          argArray);
      ((LocationAwareLogger) logger).log(ENTRY_MARKER, FQCN,
          LocationAwareLogger.TRACE_INT, formattedMessage, null);
    }
  }

  /**
   * Log method exit
   */
  public void exit() {
    if (logger.isTraceEnabled(ENTRY_MARKER)) {
      ((LocationAwareLogger) logger).log(EXIT_MARKER, FQCN,
          LocationAwareLogger.TRACE_INT, EXIT_MESSAGE_0, null);
    }
  }

  /**
   * Log method exit
   * 
   * @param result
   *                The result of the method being exited
   */
  public void exit(Object result) {
    if (logger.isTraceEnabled(ENTRY_MARKER)) {
      String formattedMessage = MessageFormatter.format(EXIT_MESSAGE_0, result);
      ((LocationAwareLogger) logger).log(EXIT_MARKER, FQCN,
          LocationAwareLogger.TRACE_INT, formattedMessage, null);
    }
  }

  /**
   * Log an exception being thrown
   * 
   * @param throwable
   *                the exception being caught.
   */
  public void throwing(Throwable throwable) {
    ((LocationAwareLogger) logger).log(THROWING_MARKER, FQCN,
        LocationAwareLogger.ERROR_INT, "throwing", throwable);
  }

  /**
   * Log an exception being caught
   * 
   * @param throwable
   *                the exception being caught.
   */
  public void catching(Throwable throwable) {
    ((LocationAwareLogger) logger).log(CATCHING_MARKER, FQCN,
        LocationAwareLogger.ERROR_INT, "catching", throwable);
  }

  private static String buildMessagePattern(int len) {
    StringBuilder sb = new StringBuilder();
    sb.append(" entry with (");
    for (int i = 0; i < len; i++) {
      sb.append("{}");
      if (i != len - 1)
        sb.append(", ");
    }
    sb.append(')');
    return sb.toString();
  }

  // =====================================
  // org.slf4j.Logger methods
  // =====================================

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isTraceEnabled(Marker marker) {
    return logger.isTraceEnabled(marker);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String msg) {
    logger.trace(msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object arg) {
    logger.trace(format, arg);
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object arg1, Object arg2) {
    logger.trace(format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object[] argArray) {
    logger.trace(format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String msg, Throwable t) {
    logger.trace(msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String msg) {
    logger.trace(marker, msg);
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object arg) {
    logger.trace(marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object arg1, Object arg2) {
    logger.trace(marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object[] argArray) {
    logger.trace(marker, format, argArray);
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String msg, Throwable t) {
    logger.trace(marker, msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isDebugEnabled(Marker marker) {
    return logger.isDebugEnabled(marker);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String msg) {
    logger.debug(msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object arg) {
    logger.debug(format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object arg1, Object arg2) {
    logger.debug(format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object[] argArray) {
    logger.debug(format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String msg, Throwable t) {
    logger.debug(msg, t);
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String msg) {
    logger.debug(marker, msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object arg) {
    logger.debug(marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object arg1, Object arg2) {
    logger.debug(marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object[] argArray) {
    logger.debug(marker, format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String msg, Throwable t) {
    logger.debug(marker, msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isInfoEnabled(Marker marker) {
    return logger.isInfoEnabled(marker);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String msg) {
    logger.info(msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object arg) {
    logger.info(format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object arg1, Object arg2) {
    logger.info(format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object[] argArray) {
    logger.info(format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String msg, Throwable t) {
    logger.info(msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String msg) {
    logger.info(marker, msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object arg) {
    logger.info(marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object arg1, Object arg2) {
    logger.info(marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object[] argArray) {
    logger.info(marker, format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String msg, Throwable t) {
    logger.info(marker, msg, t);
  }

  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isWarnEnabled(Marker marker) {
    return logger.isWarnEnabled(marker);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String msg) {
    logger.warn(msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object arg) {
    logger.warn(format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object arg1, Object arg2) {
    logger.warn(format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object[] argArray) {
    logger.warn(format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String msg, Throwable t) {
    logger.warn(msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String msg) {
    logger.warn(marker, msg);
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object arg) {
    logger.warn(marker, format, arg);
  }
  
  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object arg1, Object arg2) {
    logger.warn(marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object[] argArray) {
    logger.warn(marker, format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String msg, Throwable t) {
    logger.warn(marker, msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public boolean isErrorEnabled(Marker marker) {
    return logger.isErrorEnabled(marker);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String msg) {
    logger.error(msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object arg) {
    logger.error(format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void errot(String format, Object arg1, Object arg2) {
    logger.error(format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object[] argArray) {
    logger.error(format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String msg, Throwable t) {
    logger.error(msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String msg) {
    logger.error(marker, msg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object arg) {
    logger.error(marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object arg1, Object arg2) {
    logger.error(format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object arg1, Object arg2) {
    logger.error(marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object[] argArray) {
    logger.error(marker, format, argArray);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String msg, Throwable t) {
    logger.error(marker, msg, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public String getName() {
    return logger.getName();
  }

}
