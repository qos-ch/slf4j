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

import org.slf4j.Logger;
import java.util.logging.Level;


/**
 * A wrapper over {@link java.util.logging.Logger
 * java.util.logging.Logger} in conformance with the {@link Logger}
 * interface. Note that the logging levels mentioned in this class
 * refer to those defined in the java.util.logging package.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public final class JDK14LoggerAdapter implements Logger {
  final java.util.logging.Logger logger;

  // WARN: JDK14LoggerAdapter constructor should have only package access so that
  // only JDK14LoggerFactory be able to create one.
  JDK14LoggerAdapter(java.util.logging.Logger logger) {
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
    return logger.isLoggable(Level.FINE);
  }

  //

  /**
   * Log a message object at level FINE.
   * @param msg - the message object to be logged
   */
  public void debug(String msg) {
    logger.fine(msg);
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
    if (logger.isLoggable(Level.FINE)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.fine(msgStr);
    }
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
    if (logger.isLoggable(Level.FINE)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.fine(msgStr);
    }
  }

  
  /**
   * Log a message at level FINE according to the specified format and
   * arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the FINE level. </p>
   *
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void debug(String format, Object[] argArray) {
    if (logger.isLoggable(Level.FINE)) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      logger.fine(msgStr);
    }
  }
  
   /**
   * Log an exception (throwable) at  level FINE with an
   * accompanying message.
   *
   * @param msg the message accompanying the exception
   * @param t the exception (throwable) to log
   */
  public void debug(String msg, Throwable t) {
    logger.log(Level.FINE, msg, t);
  }

  /**
   * Is this logger instance enabled for the INFO level?
   *
   * @return True if this Logger is enabled for the INFO level, false
   * otherwise.
   */
  public boolean isInfoEnabled() {
    return logger.isLoggable(Level.INFO);
  }


  /**
   * Log a message object at the INFO level.
   *
   * @param msg - the message object to be logged
   */
  public void info(String msg) {
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
    if (logger.isLoggable(Level.INFO)) {
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
    if (logger.isLoggable(Level.INFO)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.info(msgStr);
    }
  }
  
  /**
   * Log a message at level INFO according to the specified format and
   * arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the INFO level. </p>
   *
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void info(String format, Object[] argArray) {
    if (logger.isLoggable(Level.INFO)) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
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
    logger.log(Level.INFO, msg, t);
  }
    
  /**
   * Is this logger instance enabled for the WARNING level?
   *
   * @return True if this Logger is enabled for the WARNING level,
   * false otherwise.
   */
  public boolean isWarnEnabled() {
    return logger.isLoggable(Level.WARNING);
  }
  
  /**
   * Log a message object at the WARNING level.
   *
   * @param msg - the message object to be logged
   */
  public void warn(String msg) {
    logger.warning(msg);
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
    if (logger.isLoggable(Level.WARNING)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.warning(msgStr);
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
    if (logger.isLoggable(Level.WARNING)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.warning(msgStr);
    }
  }

  /**
   * Log a message at level WARNING according to the specified format and
   * arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the WARNING level. </p>
   *
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void warn(String format, Object[] argArray) {
    if (logger.isLoggable(Level.WARNING)) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      logger.warning(msgStr);
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
    logger.log(Level.WARNING, msg.toString(), t);
  }

  /**
   * Is this logger instance enabled for level SEVERE?
   *
   * @return True if this Logger is enabled for level SEVERE, false
   * otherwise.
   */
  public boolean isErrorEnabled() {
    return logger.isLoggable(Level.SEVERE);
  }

  /**
   * Log a message object at the SEVERE level.
   *
   * @param msg - the message object to be logged
   */
  public void error(String msg) {
    logger.severe(msg);
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
    if (logger.isLoggable(Level.SEVERE)) {
      String msgStr = MessageFormatter.format(format, arg);
      logger.severe(msgStr);
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
    if (logger.isLoggable(Level.SEVERE)) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      logger.severe(msgStr);
    }
  }

  
  /**
   * Log a message at level INFO according to the specified format and
   * arguments.
   *
   * <p>This form avoids superfluous object creation when the logger
   * is disabled for the INFO level. </p>
   *
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void error(String format, Object[] argArray) {
    if (logger.isLoggable(Level.SEVERE)) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      logger.severe(msgStr);
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
    logger.log(Level.SEVERE, msg, t);
  }

}
