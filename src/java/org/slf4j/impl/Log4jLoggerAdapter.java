/*
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
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
 *
 */

package org.slf4j.impl;


import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.Marker;


/**
 * A wrapper over {@link org.apache.log4j.Logger
 * org.apache.log4j.Logger} in conformance with the {@link Logger}
 * interface. Note that the logging levels mentioned in this class
 * refer to those defined in the java.util.logging package.

 * @author Ceki G&uuml;lc&uuml;
 */
public final class Log4jLoggerAdapter implements Logger {
  final org.apache.log4j.Logger logger;

  // WARN: Log4jLoggerAdapter constructor should have only package access so that
  // only Log4jLoggerFactory be able to create one.
  Log4jLoggerAdapter(org.apache.log4j.Logger logger) {
    this.logger = logger;
  }

  public String getName() {
   return logger.getName();
  }
  
  /**
   * Is this logger instance enabled for the FINE level?
   *
   * @return True if this Logger is enabled for level FINE, false
   * otherwise.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  public boolean isDebugEnabled(Marker marker) {
    return isDebugEnabled();
  }

  //

  /**
   * Log a message object at level FINE.
   * @param msg - the message object to be logged
   */
  public void debug(String msg) {
    logger.debug(msg);
  }

  public void debug(Marker marker, String msg) {
    debug(msg);
  }

  /**
   * Log a message at level FINE according to the specified format and
   * argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for level FINE. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void debug(String format, Object arg) {
    if (logger.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.debug(msgStr);
    }
  }

  public void debug(Marker marker, String format, Object arg) {
    debug(format, arg);
  }

  /**
   * Log a message at level FINE according to the specified format and
   * arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the FINE level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void debug(String format, Object arg1, Object arg2) {
    if (logger.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.debug(msgStr);
    }
  }

  public void debug(Marker marker, String format, Object arg1, Object arg2) {
    debug(format, arg1, arg2);
  }

  /**
   * Log an exception (throwable) at  level FINE with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void debug(String msg, Throwable t) {
    logger.debug(msg, t);
  }

  public void debug(Marker marker, String msg, Throwable t) {
    debug(msg, t);
  }

  /**
   * Is this logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false
   * otherwise.
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  public final boolean isInfoEnabled(Marker marker) {
    return isInfoEnabled();
  }

  /**
   * Log a message object at the INFO level.
   *
   * @param msg - the message object to be logged
   */
  public void info(String msg) {
    logger.info(msg);
  }

  public void info(Marker marker, String msg) {
    logger.info(msg);
  }

  /**
   * Log a message at level INFO according to the specified format and
   * argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the INFO level. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void info(String format, Object arg) {
    if (logger.isInfoEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.info(msgStr);
    }
  }

  /**
   * Log a message at the INFO level according to the specified format
   * and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the INFO level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void info(String format, Object arg1, Object arg2) {
    if (logger.isInfoEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.info(msgStr);
    }
  }

  /**
   * Log an exception (throwable) at the INFO level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void info(String msg, Throwable t) {
    logger.info(msg, t);
  }

  public void info(Marker marker, String format, Object arg) {
      info(format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
      info(format, arg1, arg2);
    }

    public void info(Marker marker, String msg, Throwable t) {
       info(msg, t);
    }
    
  /**
   * Is this logger instance enabled for the WARNING level?
   *
   * @return True if this Logger is enabled for the WARNING level,
   * false otherwise.
   */
  public boolean isWarnEnabled() {
    return logger.isEnabledFor(Level.WARN);
  }
  
  public boolean isWarnEnabled(Marker marker) {
      return isWarnEnabled();
  }

  /**
   * Log a message object at the WARNING level.
   *
   * @param msg - the message object to be logged
   */
  public void warn(String msg) {
    logger.warn(msg);
  }

  /**
   * Log a message at the WARNING level according to the specified
   * format and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARNING level. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void warn(String format, Object arg) {
    if (logger.isEnabledFor(Level.WARN)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.warn(msgStr);
    }
  }

  /**
   * Log a message at the WARNING level according to the specified
   * format and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARNING level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void warn(String format, Object arg1, Object arg2) {
    if (logger.isEnabledFor(Level.WARN)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.warn(msgStr);
    }
  }

  /**
   * Log an exception (throwable) at the WARNING level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void warn(String msg, Throwable t) {
    logger.warn(msg, t);
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

  public void warn(Marker marker, String msg, Throwable t) {
    warn(msg, t);
  }

  /**
   * Is this logger instance enabled for level SEVERE?
   *
   * @return True if this Logger is enabled for level SEVERE, false
   * otherwise.
   */
  public boolean isErrorEnabled() {
    return logger.isEnabledFor(Level.ERROR);
  }

  public boolean isErrorEnabled(Marker marker) {
      return isErrorEnabled();
    }
  
  /**
   * Log a message object at the SEVERE level.
   *
   * @param msg - the message object to be logged
   */
  public void error(String msg) {
    logger.equals(msg);
  }

  /**
   * Log a message at the SEVERE level according to the specified
   * format and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the SEVERE level. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void error(String format, Object arg) {
    if (logger.isEnabledFor(Level.ERROR)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.error(msgStr);
    }
  }

  /**
   * Log a message at the SEVERE level according to the specified
   * format and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the SEVERE level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void error(String format, Object arg1, Object arg2) {
    if (logger.isEnabledFor(Level.ERROR)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.error(msgStr);
    }
  }

  /**
   * Log an exception (throwable) at the SEVERE level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void error(String msg, Throwable t) {
    logger.error(msg, t);
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

  public void error(Marker marker, String msg, Throwable t) {
    error(msg, t);
  }
}
