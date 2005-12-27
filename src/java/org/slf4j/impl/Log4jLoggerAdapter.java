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


/**
 * A wrapper over {@link org.apache.log4j.Logger
 * org.apache.log4j.Logger} in conformance with the {@link Logger}
 * interface. Note that the logging levels mentioned in this class
 * refer to those defined in the {@link org.apache.log4j.Level} class.

 * @author <a href="http://www.qos.ch/log4j/">Ceki G&uuml;lc&uuml;</a>
 */
public final class Log4jLoggerAdapter implements Logger {
  final org.apache.log4j.Logger logger;
  
  /**
   * Following the pattern discussed in pages 162 through 168 of 
   * "The complete log4j manual".
   */
  final static String FQCN = Log4jLoggerAdapter.class.getName();
  
  // WARN: Log4jLoggerAdapter constructor should have only package access so that
  // only Log4jLoggerFactory be able to create one.
  Log4jLoggerAdapter(org.apache.log4j.Logger logger) {
    this.logger = logger;
  }

  public String getName() {
   return logger.getName();
  }
  
  /**
   * Is this logger instance enabled for the DEBUG level?
   *
   * @return True if this Logger is enabled for level DEBUG, false
   * otherwise.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }


  /**
   * Log a message object at level DEBUG.
   * @param msg - the message object to be logged
   */
  public void debug(String msg) {
    logger.log(FQCN, Level.DEBUG, msg, null);
  }

  /**
   * Log a message at level DEBUG according to the specified format and
   * argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for level DEBUG. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void debug(String format, Object arg) {
    if (logger.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.log(FQCN, Level.DEBUG, msgStr, null);
    }
  }

  /**
   * Log a message at level DEBUG according to the specified format and
   * arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the DEBUG level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void debug(String format, Object arg1, Object arg2) {
    if (logger.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.log(FQCN, Level.DEBUG, msgStr, null);
    }
  }
  
  public void debug(String format, Object[] argArray) {
    if (logger.isDebugEnabled()) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      logger.log(FQCN, Level.DEBUG, msgStr, null);
    }
  }

  /**
   * Log an exception (throwable) at  level DEBUG with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void debug(String msg, Throwable t) {
    logger.log(FQCN, Level.DEBUG, msg, t);
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

  /**
   * Log a message object at the INFO level.
   *
   * @param msg - the message object to be logged
   */
  public void info(String msg) {
    logger.log(FQCN, Level.INFO, msg, null);
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
      logger.log(FQCN, Level.INFO, msgStr, null);
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
      logger.log(FQCN, Level.INFO, msgStr, null);
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
    logger.log(FQCN, Level.INFO, msg, t);
  }

  /**
   * Is this logger instance enabled for the WARN level?
   *
   * @return True if this Logger is enabled for the WARN level,
   * false otherwise.
   */
  public boolean isWarnEnabled() {
    return logger.isEnabledFor(Level.WARN);
  }
  
  /**
   * Log a message object at the WARN level.
   *
   * @param msg - the message object to be logged
   */
  public void warn(String msg) {
    logger.log(FQCN, Level.WARN, msg, null);
  }

  /**
   * Log a message at the WARN level according to the specified
   * format and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARN level. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void warn(String format, Object arg) {
    if (logger.isEnabledFor(Level.WARN)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.log(FQCN, Level.WARN, msgStr, null);
    }
  }

  /**
   * Log a message at the WARN level according to the specified
   * format and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARN level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void warn(String format, Object arg1, Object arg2) {
    if (logger.isEnabledFor(Level.WARN)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.log(FQCN, Level.WARN, msgStr, null);
    }
  }

  /**
   * Log an exception (throwable) at the WARN level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void warn(String msg, Throwable t) {
    logger.log(FQCN, Level.WARN, msg, t);
  }

  /**
   * Is this logger instance enabled for level ERROR?
   *
   * @return True if this Logger is enabled for level ERROR, false
   * otherwise.
   */
  public boolean isErrorEnabled() {
    return logger.isEnabledFor(Level.ERROR);
  }

  /**
   * Log a message object at the ERROR level.
   *
   * @param msg - the message object to be logged
   */
  public void error(String msg) {
    logger.log(FQCN, Level.ERROR, msg, null);
  }

  /**
   * Log a message at the ERROR level according to the specified
   * format and argument.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the ERROR level. </p>
   *
   * @param format the format string
   * @param arg  the argument
   */
  public void error(String format, Object arg) {
    if (logger.isEnabledFor(Level.ERROR)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.log(FQCN, Level.ERROR, msgStr, null);
    }
  }

  /**
   * Log a message at the ERROR level according to the specified
   * format and arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the ERROR level. </p>
   *
   * @param format the format string
   * @param arg1  the first argument
   * @param arg2  the second argument
   */
  public void error(String format, Object arg1, Object arg2) {
    if (logger.isEnabledFor(Level.ERROR)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.log(FQCN, Level.ERROR, msgStr, null);
    }
  }

  /**
   * Log an exception (throwable) at the ERROR level with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void error(String msg, Throwable t) {
    logger.log(FQCN, Level.ERROR, msg, t);
  }
}
