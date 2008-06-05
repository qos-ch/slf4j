/* 
 * Copyright (c) 2004-2008 QOS.ch
 * All rights reserved.
 * 
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * 
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package org.slf4j.impl;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * A wrapper over {@link org.apache.commons.logging.Log
 * org.apache.commons.logging.Log} in conformance with the {@link Logger}
 * interface.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public final class JCLLoggerAdapter extends MarkerIgnoringBase {
  final Log log;
  final String name;
  
  // WARN: JCLLoggerAdapter constructor should have only package access so
  // that only JCLLoggerFactory be able to create one.
  JCLLoggerAdapter(Log log, String name) {
    this.log = log;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Delegates to the {@link Log#isTraceEnabled} method of the underlying
   * {@link Log} instance. 
   */
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  //

  /**
   * Delegates to the {@link Log#trace(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * @param msg - the message object to be logged
   */
  public void trace(String msg) {
    log.trace(msg);
  }

  /**
   * Delegates to the {@link Log#trace(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level TRACE.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg
   *          the argument
   */
  public void trace(String format, Object arg) {
    if (log.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      log.trace(msgStr);
    }
  }

  /**
   * Delegates to the {@link Log#trace(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level TRACE.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg1
   *          the first argument
   * @param arg2
   *          the second argument
   */
  public void trace(String format, Object arg1, Object arg2) {
    if (log.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      log.trace(msgStr);
    }
  }
  

  /**
   * Delegates to the {@link Log#trace(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level TRACE.
   * </p>
   * 
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void trace(String format, Object[] argArray) {
    if (log.isDebugEnabled()) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      log.trace(msgStr);
    }
  }
  
  /**
   * Delegates to the {@link Log#trace(java.lang.Object, java.lang.Throwable)} method of 
   * the underlying {@link Log} instance.
   * 
   * @param msg
   *          the message accompanying the exception
   * @param t
   *          the exception (throwable) to log
   */
  public void trace(String msg, Throwable t) {
      log.trace(msg, t);
  }

  
  /**
   * Delegates to the {@link Log#isDebugEnabled} method of the underlying
   * {@link Log} instance. 
   */
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  //

  /**
   * Delegates to the {@link Log#debug(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * @param msg - the message object to be logged
   */
  public void debug(String msg) {
    log.debug(msg);
  }

  /**
   * Delegates to the {@link Log#debug(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level DEBUG.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg
   *          the argument
   */
  public void debug(String format, Object arg) {
    if (log.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      log.debug(msgStr);
    }
  }

  /**
   * Delegates to the {@link Log#debug(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level DEBUG.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg1
   *          the first argument
   * @param arg2
   *          the second argument
   */
  public void debug(String format, Object arg1, Object arg2) {
    if (log.isDebugEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      log.debug(msgStr);
    }
  }
  

  /**
   * Delegates to the {@link Log#debug(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level DEBUG.
   * </p>
   * 
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void debug(String format, Object[] argArray) {
    if (log.isDebugEnabled()) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      log.debug(msgStr);
    }
  }
  
  /**
   * Delegates to the {@link Log#debug(java.lang.Object, java.lang.Throwable)} method of 
   * the underlying {@link Log} instance.
   * 
   * @param msg
   *          the message accompanying the exception
   * @param t
   *          the exception (throwable) to log
   */
  public void debug(String msg, Throwable t) {
      log.debug(msg, t);
  }

  /**
   * Delegates to the {@link Log#isInfoEnabled} method of the underlying
   * {@link Log} instance. 
   */
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  /**
   * Delegates to the {@link Log#debug(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * @param msg - the message object to be logged
   */
  public void info(String msg) {
    log.info(msg);
  }

  /**
   * Delegates to the {@link Log#info(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level INFO.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg
   *          the argument
   */

  public void info(String format, Object arg) {
    if (log.isInfoEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      log.info(msgStr);
    }
  }
  /**
   * Delegates to the {@link Log#info(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level INFO.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg1
   *          the first argument
   * @param arg2
   *          the second argument
   */
  public void info(String format, Object arg1, Object arg2) {
    if (log.isInfoEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      log.info(msgStr);
    }
  }

  /**
   * Delegates to the {@link Log#info(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level INFO.
   * </p>
   * 
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void info(String format, Object[] argArray) {
    if (log.isInfoEnabled()) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      log.info(msgStr);
    }
  }
  
  
  /**
   * Delegates to the {@link Log#info(java.lang.Object, java.lang.Throwable)} method of 
   * the underlying {@link Log} instance.
   * 
   * @param msg
   *          the message accompanying the exception
   * @param t
   *          the exception (throwable) to log
   */
  public void info(String msg, Throwable t) {
    log.info(msg, t);
  }

  /**
   * Delegates to the {@link Log#isWarnEnabled} method of the underlying
   * {@link Log} instance. 
   */
  public boolean isWarnEnabled() {
    return log.isWarnEnabled();
  }

  /**
   * Delegates to the {@link Log#warn(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * @param msg - the message object to be logged
   */
  public void warn(String msg) {
    log.warn(msg);
  }

  /**
   * Delegates to the {@link Log#warn(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level WARN.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg
   *          the argument
   */
  public void warn(String format, Object arg) {
    if (log.isWarnEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      log.warn(msgStr);
    }
  }
  
  /**
   * Delegates to the {@link Log#warn(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level WARN.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg1
   *          the first argument
   * @param arg2
   *          the second argument
   */
  public void warn(String format, Object arg1, Object arg2) {
    if (log.isWarnEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      log.warn(msgStr);
    }
  }
  
  /**
   * Delegates to the {@link Log#warn(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level WARN.
   * </p>
   * 
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void warn(String format, Object[] argArray) {
    if (log.isWarnEnabled()) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      log.warn(msgStr);
    }
  }
  

  /**
   * Delegates to the {@link Log#warn(java.lang.Object, java.lang.Throwable)} method of 
   * the underlying {@link Log} instance.
   * 
   * @param msg
   *          the message accompanying the exception
   * @param t
   *          the exception (throwable) to log
   */
  
  public void warn(String msg, Throwable t) {
    log.warn(msg, t);
  }


  /**
   * Delegates to the {@link Log#isErrorEnabled} method of the underlying
   * {@link Log} instance. 
   */
  public boolean isErrorEnabled() {
    return log.isErrorEnabled();
  }

  /**
   * Delegates to the {@link Log#error(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * @param msg - the message object to be logged
   */
  public void error(String msg) {
    log.error(msg);
  }

  /**
   * Delegates to the {@link Log#error(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level ERROR.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg
   *          the argument
   */
  public void error(String format, Object arg) {
    if (log.isErrorEnabled()) {
      String msgStr = MessageFormatter.format(format, arg);
      log.error(msgStr);
    }
  }
  
  /**
   * Delegates to the {@link Log#error(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level ERROR.
   * </p>
   * 
   * @param format
   *          the format string
   * @param arg1
   *          the first argument
   * @param arg2
   *          the second argument
   */
  public void error(String format, Object arg1, Object arg2) {
    if (log.isErrorEnabled()) {
      String msgStr = MessageFormatter.format(format, arg1, arg2);
      log.error(msgStr);
    }
  }

  /**
   * Delegates to the {@link Log#error(java.lang.Object)} method of the underlying
   * {@link Log} instance.
   * 
   * <p>
   * However, this form avoids superfluous object creation when the logger is disabled
   * for level ERROR.
   * </p>
   * 
   * @param format the format string
   * @param argArray an array of arguments
   */
  public void error(String format, Object[] argArray) {
    if (log.isErrorEnabled()) {
      String msgStr = MessageFormatter.arrayFormat(format, argArray);
      log.error(msgStr);
    }
  }
  
  
  /**
   * Delegates to the {@link Log#error(java.lang.Object, java.lang.Throwable)} method of 
   * the underlying {@link Log} instance.
   * 
   * @param msg
   *          the message accompanying the exception
   * @param t
   *          the exception (throwable) to log
   */
  
  public void error(String msg, Throwable t) {
    log.error(msg, t);
  }

}
