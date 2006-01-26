// TOTO

package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * This class serves as base for adapters or native implementations of logging systems 
 * lacking Marker support. In this implementation, methods taking marker data 
 * simply invoke the corresponding method without the Marker argument, ignoring 
 * any Marker data passed as argument.
 * 
 * @author Ceki Gulcu
 */
public abstract class MarkerIgnoringBase implements Logger {

  public boolean isDebugEnabled(Marker marker) {
    return isDebugEnabled();
  }

  public void debug(Marker marker, String msg) {
    debug(msg);
  }

  public void debug(Marker marker, String format, Object arg) {
    debug(format, arg);
  }

  public void debug(Marker marker, String format, Object arg1, Object arg2) {
    debug(format, arg1, arg2);
  }

  public void debug(Marker marker, String format, Object[] argArray) {
    debug(format, argArray);
  }

  public void debug(Marker marker, String msg, Throwable t) {
    debug(msg, t);
  }

  public boolean isInfoEnabled(Marker marker) {
    return isInfoEnabled();
  }

  public void info(Marker marker, String msg) {
    info(msg);
  }

  public void info(Marker marker, String format, Object arg) {
    info(format, arg);
  }

  public void info(Marker marker, String format, Object arg1, Object arg2) {
    info(format, arg1, arg2);
  }

  public void info(Marker marker, String format, Object[] argArray) {
    info(format, argArray);
  }

  public void info(Marker marker, String msg, Throwable t) {
    info(msg, t);
  }

  public boolean isWarnEnabled(Marker marker) {
    return isWarnEnabled();
  }

  public void warn(Marker marker, String msg) {
    warn(msg);
  }

  public void warn(Marker marker, String format, Object arg) {
    warn(format, arg);
  }

  public void warn(Marker marker, String format, Object arg1, Object arg2) {
    warn(format, arg1, arg2);
  }

  public void warn(Marker marker, String format, Object[] argArray) {
    warn(format, argArray);
  }

  public void warn(Marker marker, String msg, Throwable t) {
    warn(msg, t);
  }

 
  public boolean isErrorEnabled(Marker marker) {
    return isErrorEnabled();
  }

  public void error(Marker marker, String msg) {
    error(msg);
  }

  public void error(Marker marker, String format, Object arg) {
    error(format, arg);
  }

  public void error(Marker marker, String format, Object arg1, Object arg2) {
    error(format, arg1, arg2);
  }

  public void error(Marker marker, String format, Object[] argArray) {
    error(format, argArray);
  }

  public void error(Marker marker, String msg, Throwable t) {
    error(msg, t);
  }

}
