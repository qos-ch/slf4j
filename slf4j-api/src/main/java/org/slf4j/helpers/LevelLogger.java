package org.slf4j.helpers;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.EnumMap;
import java.util.Map;

/**
 * Wrapper around {@link Logger} for purpose to log at programmatically chosen {@link Level}
 *
 * @author Vladimir Vasa
 */
public final class LevelLogger {

  // TODO: @FunctionalInterface - uncomment if java 8
  private interface MsgLog {
    void log(String msg);
  }

  // TODO: @FunctionalInterface - uncomment if java 8
  private interface FormatLog {
    void log(String format, Object... arguments);
  }

  // TODO: @FunctionalInterface - uncomment if java 8
  private interface ThrowableLog {
    void log(String msg, Throwable t);
  }

  // TODO: @FunctionalInterface - uncomment if java 8
  private interface LevelEnabled {
    boolean isEnabled();
  }

  private final Logger logger;
  private final Map<Level, MsgLog> msgLogMap = new EnumMap<Level, MsgLog>(Level.class);
  private final Map<Level, FormatLog> formatLogMap = new EnumMap<Level, FormatLog>(Level.class);
  private final Map<Level, ThrowableLog> throwableLogMap = new EnumMap<Level, ThrowableLog>(Level.class);
  private final Map<Level, LevelEnabled> levelEnabledMap = new EnumMap<Level, LevelEnabled>(Level.class);

  /**
   * @param logger {@link Logger} instance to wrap, cannot be null!
   * @throws IllegalArgumentException If {@code logger} param is null.
   */
  public LevelLogger(final Logger logger) {
    if(logger == null) {
      throw new IllegalArgumentException("logger is null");
    }

    this.logger = logger;

    // TODO: Use method references if java 8
    msgLogMap.put(Level.TRACE, new MsgLog() {
      @Override
      public void log(String msg) {
        logger.trace(msg);
      }
    });
    msgLogMap.put(Level.DEBUG, new MsgLog() {
      @Override
      public void log(String msg) {
        logger.debug(msg);
      }
    });
    msgLogMap.put(Level.INFO, new MsgLog() {
      @Override
      public void log(String msg) {
        logger.info(msg);
      }
    });
    msgLogMap.put(Level.WARN, new MsgLog() {
      @Override
      public void log(String msg) {
        logger.warn(msg);
      }
    });
    msgLogMap.put(Level.ERROR, new MsgLog() {
      @Override
      public void log(String msg) {
        logger.error(msg);
      }
    });

    formatLogMap.put(Level.TRACE, new FormatLog() {
      @Override
      public void log(String format, Object... arguments) {
        logger.trace(format, arguments);
      }
    });
    formatLogMap.put(Level.DEBUG, new FormatLog() {
      @Override
      public void log(String format, Object... arguments) {
        logger.debug(format, arguments);
      }
    });
    formatLogMap.put(Level.INFO, new FormatLog() {
      @Override
      public void log(String format, Object... arguments) {
        logger.info(format, arguments);
      }
    });
    formatLogMap.put(Level.WARN, new FormatLog() {
      @Override
      public void log(String format, Object... arguments) {
        logger.warn(format, arguments);
      }
    });
    formatLogMap.put(Level.ERROR, new FormatLog() {
      @Override
      public void log(String format, Object... arguments) {
        logger.error(format, arguments);
      }
    });

    throwableLogMap.put(Level.TRACE, new ThrowableLog() {
      @Override
      public void log(String msg, Throwable t) {
        logger.trace(msg, t);
      }
    });
    throwableLogMap.put(Level.DEBUG, new ThrowableLog() {
      @Override
      public void log(String msg, Throwable t) {
        logger.debug(msg, t);
      }
    });
    throwableLogMap.put(Level.INFO, new ThrowableLog() {
      @Override
      public void log(String msg, Throwable t) {
        logger.info(msg, t);
      }
    });
    throwableLogMap.put(Level.WARN, new ThrowableLog() {
      @Override
      public void log(String msg, Throwable t) {
        logger.warn(msg, t);
      }
    });
    throwableLogMap.put(Level.ERROR, new ThrowableLog() {
      @Override
      public void log(String msg, Throwable t) {
        logger.error(msg, t);
      }
    });

    levelEnabledMap.put(Level.TRACE, new LevelEnabled() {
      @Override
      public boolean isEnabled() {
        return logger.isTraceEnabled();
      }
    });
    levelEnabledMap.put(Level.DEBUG, new LevelEnabled() {
      @Override
      public boolean isEnabled() {
        return logger.isDebugEnabled();
      }
    });
    levelEnabledMap.put(Level.INFO, new LevelEnabled() {
      @Override
      public boolean isEnabled() {
        return logger.isInfoEnabled();
      }
    });
    levelEnabledMap.put(Level.WARN, new LevelEnabled() {
      @Override
      public boolean isEnabled() {
        return logger.isWarnEnabled();
      }
    });
    levelEnabledMap.put(Level.ERROR, new LevelEnabled() {
      @Override
      public boolean isEnabled() {
        return logger.isErrorEnabled();
      }
    });
  }

  /**
   * @return Wrapped {@link Logger} instance
   */
  final public Logger getLogger() {
    return logger;
  }

  /**
   * Log a message at the {@code level}
   *
   * @param level {@link Level} to log
   * @param msg   the message string to be logged
   */
  final public void log(Level level, String msg) {
    if(level == null) {
      return;
    }
    msgLogMap.get(level).log(msg);
  }

  /**
   * Log a message at the {@code level} according to the specified format
   * and argument.
   * <p/>
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the {@code level}. </p>
   *
   * @param level     {@link Level} to log
   * @param format    the format string
   * @param arguments a list of arguments
   */
  final public void log(Level level, String format, Object... arguments) {
    if(level == null) {
      return;
    }
    formatLogMap.get(level).log(format, arguments);
  }

  /**
   * Log an exception (throwable) at the {@code level} with an
   * accompanying message.
   *
   * @param level {@link Level} to log
   * @param msg   the message accompanying the exception
   * @param t     the exception (throwable) to log
   */
  final public void log(Level level, String msg, Throwable t) {
    if(level == null) {
      return;
    }
    throwableLogMap.get(level).log(msg, t);
  }

  /**
   * Is the wrapped {@link Logger} instance enabled for the {@code level}?
   *
   * @param level queried {@link Level}
   * @return True if the wrapped {@link Logger} is enabled for the {@code level},
   * false otherwise.
   */
  final public boolean isEnabled(Level level) {
    return level != null && levelEnabledMap.get(level).isEnabled();
  }
}
