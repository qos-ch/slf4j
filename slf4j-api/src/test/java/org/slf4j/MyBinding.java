package org.slf4j;

public class MyBinding implements IStaticLoggerBinder {
  static class MyLogger implements Logger {
    public String getName() {
      return null;
    }

    public boolean isTraceEnabled() {
      return false;
    }

    public void trace(String msg) {
    }

    public void trace(String format, Object arg) {
    }

    public void trace(String format, Object arg1, Object arg2) {
    }

    public void trace(String format, Object[] argArray) {
    }

    public void trace(String msg, Throwable t) {
    }

    public boolean isTraceEnabled(Marker marker) {
      return false;
    }

    public void trace(Marker marker, String msg) {
    }

    public void trace(Marker marker, String format, Object arg) {
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
    }

    public void trace(Marker marker, String format, Object[] argArray) {
    }

    public void trace(Marker marker, String msg, Throwable t) {
    }

    public boolean isDebugEnabled() {
      return false;
    }

    public void debug(String msg) {
    }

    public void debug(String format, Object arg) {
    }

    public void debug(String format, Object arg1, Object arg2) {
    }

    public void debug(String format, Object[] argArray) {
    }

    public void debug(String msg, Throwable t) {
    }

    public boolean isDebugEnabled(Marker marker) {
      return false;
    }

    public void debug(Marker marker, String msg) {
    }

    public void debug(Marker marker, String format, Object arg) {
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
    }

    public void debug(Marker marker, String format, Object[] argArray) {
    }

    public void debug(Marker marker, String msg, Throwable t) {
    }

    public boolean isInfoEnabled() {
      return false;
    }

    public void info(String msg) {
    }

    public void info(String format, Object arg) {
    }

    public void info(String format, Object arg1, Object arg2) {
    }

    public void info(String format, Object[] argArray) {
    }

    public void info(String msg, Throwable t) {
    }

    public boolean isInfoEnabled(Marker marker) {
      return false;
    }

    public void info(Marker marker, String msg) {
    }

    public void info(Marker marker, String format, Object arg) {
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
    }

    public void info(Marker marker, String format, Object[] argArray) {
    }

    public void info(Marker marker, String msg, Throwable t) {
    }

    public boolean isWarnEnabled() {
      return false;
    }

    public void warn(String msg) {
    }

    public void warn(String format, Object arg) {
    }

    public void warn(String format, Object[] argArray) {
    }

    public void warn(String format, Object arg1, Object arg2) {
    }

    public void warn(String msg, Throwable t) {
    }

    public boolean isWarnEnabled(Marker marker) {
      return false;
    }

    public void warn(Marker marker, String msg) {
    }

    public void warn(Marker marker, String format, Object arg) {
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
    }

    public void warn(Marker marker, String format, Object[] argArray) {
    }

    public void warn(Marker marker, String msg, Throwable t) {
    }

    public boolean isErrorEnabled() {
      return false;
    }

    public void error(String msg) {
    }

    public void error(String format, Object arg) {
    }

    public void error(String format, Object arg1, Object arg2) {
    }

    public void error(String format, Object[] argArray) {
    }

    public void error(String msg, Throwable t) {
    }

    public boolean isErrorEnabled(Marker marker) {
      return false;
    }

    public void error(Marker marker, String msg) {
    }

    public void error(Marker marker, String format, Object arg) {
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
    }

    public void error(Marker marker, String format, Object[] argArray) {
    }

    public void error(Marker marker, String msg, Throwable t) {
    }
  }
  
  private static class MyLoggerFactory implements ILoggerFactory {
    public Logger getLogger(String name) {
      return new MyLogger();
    }
  }

  public ILoggerFactory getLoggerFactory() {
    return new MyLoggerFactory();
  }

  public String getRequestedApiVersion() {
    return "6.0";
  }
}
