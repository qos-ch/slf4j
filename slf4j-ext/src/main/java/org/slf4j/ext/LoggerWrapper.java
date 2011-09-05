package org.slf4j.ext;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A helper class wrapping an {@link org.slf4j.Logger} instance preserving
 * location information if the wrapped instance supports it.
 *
 * @author Ralph Goers
 * @author Ceki G&uuml;lc&uuml;
 * @author John Vasileff
 */
public class LoggerWrapper implements Logger {

  // To ensure consistency between two instances sharing the same name
  // (homonyms)
  // a LoggerWrapper should not contain any state beyond
  // the Logger instance it wraps.
  // Note that 'instanceofLAL' directly depends on Logger.
  // fqcn depend on the caller, but its value would not be different
  // between successive invocations of a factory class

  protected final Logger logger;
  final String fqcn;
  // is this logger instance a LocationAwareLogger
  protected final boolean instanceofLAL;

  public LoggerWrapper(Logger logger, String fqcn) {
    this.logger = logger;
    this.fqcn = fqcn;
    if (logger instanceof LocationAwareLogger) {
      instanceofLAL = true;
    } else {
      instanceofLAL = false;
    }
  }

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
  public String getName() {
    return logger.getName();
  }

  //////////////////////////////////////
  // TRACE
  //////////////////////////////////////

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format) {
    maybeLog(Level.TRACE, null, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Throwable t) {
    maybeLog(Level.TRACE, null, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object arg) {
    maybeLog(Level.TRACE, null, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object arg1, Object arg2) {
    maybeLog(Level.TRACE, null, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(String format, Object[] objects) {
    maybeLog(Level.TRACE, null, format, objects);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format) {
    maybeLog(Level.TRACE, marker, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Throwable t) {
    maybeLog(Level.TRACE, marker, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object arg) {
    maybeLog(Level.TRACE, marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object arg1,
      Object arg2) {
    maybeLog(Level.TRACE, marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void trace(Marker marker, String format, Object[] objects) {
    maybeLog(Level.TRACE, marker, format, objects);
  }

  //////////////////////////////////////
  // DEBUG
  //////////////////////////////////////

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format) {
    maybeLog(Level.DEBUG, null, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Throwable t) {
    maybeLog(Level.DEBUG, null, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object arg) {
    maybeLog(Level.DEBUG, null, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object arg1, Object arg2) {
    maybeLog(Level.DEBUG, null, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(String format, Object[] objects) {
    maybeLog(Level.DEBUG, null, format, objects);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format) {
    maybeLog(Level.DEBUG, marker, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Throwable t) {
    maybeLog(Level.DEBUG, marker, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object arg) {
    maybeLog(Level.DEBUG, marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object arg1,
      Object arg2) {
    maybeLog(Level.DEBUG, marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void debug(Marker marker, String format, Object[] objects) {
    maybeLog(Level.DEBUG, marker, format, objects);
  }

  //////////////////////////////////////
  // INFO
  //////////////////////////////////////

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format) {
    maybeLog(Level.INFO, null, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Throwable t) {
    maybeLog(Level.INFO, null, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object arg) {
    maybeLog(Level.INFO, null, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object arg1, Object arg2) {
    maybeLog(Level.INFO, null, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(String format, Object[] objects) {
    maybeLog(Level.INFO, null, format, objects);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format) {
    maybeLog(Level.INFO, marker, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Throwable t) {
    maybeLog(Level.INFO, marker, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object arg) {
    maybeLog(Level.INFO, marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object arg1,
      Object arg2) {
    maybeLog(Level.INFO, marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void info(Marker marker, String format, Object[] objects) {
    maybeLog(Level.INFO, marker, format, objects);
  }

  //////////////////////////////////////
  // WARN
  //////////////////////////////////////

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format) {
    maybeLog(Level.WARN, null, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Throwable t) {
    maybeLog(Level.WARN, null, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object arg) {
    maybeLog(Level.WARN, null, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object arg1, Object arg2) {
    maybeLog(Level.WARN, null, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(String format, Object[] objects) {
    maybeLog(Level.WARN, null, format, objects);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format) {
    maybeLog(Level.WARN, marker, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Throwable t) {
    maybeLog(Level.WARN, marker, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object arg) {
    maybeLog(Level.WARN, marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object arg1,
      Object arg2) {
    maybeLog(Level.WARN, marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void warn(Marker marker, String format, Object[] objects) {
    maybeLog(Level.WARN, marker, format, objects);
  }

  //////////////////////////////////////
  // ERROR
  //////////////////////////////////////

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format) {
    maybeLog(Level.ERROR, null, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Throwable t) {
    maybeLog(Level.ERROR, null, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object arg) {
    maybeLog(Level.ERROR, null, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object arg1, Object arg2) {
    maybeLog(Level.ERROR, null, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(String format, Object[] objects) {
    maybeLog(Level.ERROR, null, format, objects);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format) {
    maybeLog(Level.ERROR, marker, format);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Throwable t) {
    maybeLog(Level.ERROR, marker, format, t);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object arg) {
    maybeLog(Level.ERROR, marker, format, arg);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object arg1,
      Object arg2) {
    maybeLog(Level.ERROR, marker, format, arg1, arg2);
  }

  /**
   * Delegate to the appropriate method of the underlying logger.
   */
  public void error(Marker marker, String format, Object[] objects) {
    maybeLog(Level.ERROR, marker, format, objects);
  }

  //////////////////////////////////////
  // Private Utility Methods
  //////////////////////////////////////

  private boolean isEnabled(Level level) {
    switch (level) {
      case TRACE :
        return isTraceEnabled();
      case DEBUG :
        return isDebugEnabled();
      case INFO :
        return isInfoEnabled();
      case WARN :
        return isWarnEnabled();
      case ERROR :
        return isErrorEnabled();
    }
    return false;
  }

  private void maybeLog(Level level, Marker marker, String format) {
    if (isEnabled(level)) {
      log(level, marker, format);
    }
  }

  private void maybeLog(Level level, Marker marker, String format,
      Throwable t) {
    if (isEnabled(level)) {
      log(level, marker, format, t);
    }
  }

  private void maybeLog(Level level, Marker marker, String format,
      Object arg) {
    if (isEnabled(level)) {
      log(level, marker, format, arg);
    }
  }

  private void maybeLog(Level level, Marker marker, String format, Object arg1,
      Object arg2) {
    if (isEnabled(level)) {
      log(level, marker, format, arg1, arg2);
    }
  }

  private void maybeLog(Level level, Marker marker, String format,
      Object[] argArray) {
    if (isEnabled(level)) {
      log(level, marker, format, argArray);
    }
  }

  private void log(Level level, Marker marker, String format) {
    if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, level.getIntId(),
          format, null, null);
    } else {
      switch (level) {
        case TRACE :
          logger.trace(marker, format);
          break;
        case DEBUG :
          logger.debug(marker, format);
          break;
        case INFO :
          logger.info(marker, format);
          break;
        case WARN :
          logger.warn(marker, format);
          break;
        case ERROR :
          logger.error(marker, format);
          break;
      }
    }
  }

  private void log(Level level, Marker marker, String format, Throwable t) {
    if (instanceofLAL) {
      ((LocationAwareLogger) logger).log(marker, fqcn, level.getIntId(),
          format, null, t);
    } else {
      switch (level) {
        case TRACE :
          logger.trace(marker, format, t);
          break;
        case DEBUG :
          logger.debug(marker, format, t);
          break;
        case INFO :
          logger.info(marker, format, t);
          break;
        case WARN :
          logger.warn(marker, format, t);
          break;
        case ERROR :
          logger.error(marker, format, t);
          break;
      }
    }
  }

  private void log(Level level, Marker marker, String format, Object arg) {
    if (instanceofLAL) {
      FormattingTuple ft = MessageFormatter.format(format, arg);
      ((LocationAwareLogger) logger).log(marker, fqcn, level.getIntId(),
          ft.getMessage(), ft.getArgArray(), ft.getThrowable());
    } else {
      switch (level) {
        case TRACE :
          logger.trace(marker, format, arg);
          break;
        case DEBUG :
          logger.debug(marker, format, arg);
          break;
        case INFO :
          logger.info(marker, format, arg);
          break;
        case WARN :
          logger.warn(marker, format, arg);
          break;
        case ERROR :
          logger.error(marker, format, arg);
          break;
      }
    }
  }

  private void log(Level level, Marker marker, String format, Object arg1,
      Object arg2) {
    if (instanceofLAL) {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      ((LocationAwareLogger) logger).log(marker, fqcn, level.getIntId(),
          ft.getMessage(), ft.getArgArray(), ft.getThrowable());
    } else {
      switch (level) {
        case TRACE :
          logger.trace(marker, format, arg1, arg2);
          break;
        case DEBUG :
          logger.debug(marker, format, arg1, arg2);
          break;
        case INFO :
          logger.info(marker, format, arg1, arg2);
          break;
        case WARN :
          logger.warn(marker, format, arg1, arg2);
          break;
        case ERROR :
          logger.error(marker, format, arg1, arg2);
          break;
      }
    }
  }

  private void log(Level level, Marker marker, String format,
      Object[] argArray) {
    if (instanceofLAL) {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
      ((LocationAwareLogger) logger).log(marker, fqcn, level.getIntId(),
          ft.getMessage(), argArray, ft.getThrowable());
    } else {
      switch (level) {
        case TRACE :
          logger.trace(marker, format, argArray);
          break;
        case DEBUG :
          logger.debug(marker, format, argArray);
          break;
        case INFO :
          logger.info(marker, format, argArray);
          break;
        case WARN :
          logger.warn(marker, format, argArray);
          break;
        case ERROR :
          logger.error(marker, format, argArray);
          break;
      }
    }
  }

  private enum Level {
    TRACE(LocationAwareLogger.TRACE_INT),
    DEBUG(LocationAwareLogger.DEBUG_INT),
    INFO(LocationAwareLogger.INFO_INT),
    WARN(LocationAwareLogger.WARN_INT),
    ERROR(LocationAwareLogger.ERROR_INT);

    private int intId;

    private Level(int intId) {
      this.intId = intId;
    }

    public int getIntId() {
      return intId;
    }
  }
}
