package org.slf4j.event;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.lang.reflect.InvocationTargetException;

/**
 * Allows invoking logger methods over some abstract log level.
 *
 * NOTE: This implementation uses reflection. Useful for testing.
 */
public class LevelScopedLoggerInvoker {

  private Level level;
  private Logger logger;

  public LevelScopedLoggerInvoker(Logger logger, Level level) {
    this.level = level;
    this.logger = logger;
  }


  public void log(String msg) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), String.class).invoke(logger, msg);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public void log(String format, Object arg) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), String.class, Object.class).invoke(logger, format, arg);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public void log(String format, Object arg1, Object arg2) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), String.class, Object.class, Object.class).invoke(logger, format, arg1, arg2);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }

  }

  public void log(String format, Object... arguments) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), String.class, Object[].class).invoke(logger, format, arguments);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public void log(String msg, Throwable t) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), String.class, Throwable.class).invoke(logger, msg, t);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private String capitalizeLoggerName() {
    char[] chars = level.name().toLowerCase().toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return new String(chars);
  }

  public boolean isEnabled() {
    try {
      String methodName = "is" + capitalizeLoggerName() + "Enabled";
      return (Boolean) logger.getClass().getMethod(methodName).invoke(logger);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isEnabled(Marker marker) {
    try {
      String methodName = "is" + capitalizeLoggerName() + "Enabled";
      return (Boolean) logger.getClass().getMethod(methodName).invoke(logger);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }

  }

  public void log(Marker marker, String msg) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), Marker.class, String.class).invoke(logger, marker, msg);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public void log(Marker marker, String format, Throwable t) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), Marker.class, String.class, Throwable.class).invoke(logger, marker, format, t);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }

  }

  public void log(Marker marker, String format, Object arg1, Object arg2) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), Marker.class, String.class, Object.class, Object.class).invoke(logger, marker, format, arg1, arg2);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }

  }

  public void log(Marker marker, String format, Object... argArray) {
    try {
      logger.getClass().getMethod(level.name().toLowerCase(), Marker.class, String.class, Object[].class).invoke(logger, marker, format, argArray);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
