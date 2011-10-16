/**
 * Copyright (c) 2004-2011 QOS.ch
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
 *
 */
package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;

/**
 * <p>
 * Trivial implementation of Log that throws away all messages. No configurable
 * system properties are supported.
 * </p>
 * 
 * @author <a href="mailto:sanders@apache.org">Scott Sanders</a>
 * @author Rod Waldhoff
 * @version $Id: NoOpLog.java,v 1.8 2004/06/06 21:13:12 rdonkin Exp $
 */
public class NoOpLog implements Log, Serializable {
  private static final long serialVersionUID = 561423906191706148L;

  /** Convenience constructor */
  public NoOpLog() {
  }

  /** Base constructor */
  public NoOpLog(String name) {
  }

  /** Do nothing */
  public void trace(Object message) {
  }

  /** Do nothing */
  public void trace(Object message, Throwable t) {
  }

  /** Do nothing */
  public void debug(Object message) {
  }

  /** Do nothing */
  public void debug(Object message, Throwable t) {
  }

  /** Do nothing */
  public void info(Object message) {
  }

  /** Do nothing */
  public void info(Object message, Throwable t) {
  }

  /** Do nothing */
  public void warn(Object message) {
  }

  /** Do nothing */
  public void warn(Object message, Throwable t) {
  }

  /** Do nothing */
  public void error(Object message) {
  }

  /** Do nothing */
  public void error(Object message, Throwable t) {
  }

  /** Do nothing */
  public void fatal(Object message) {
  }

  /** Do nothing */
  public void fatal(Object message, Throwable t) {
  }

  /**
   * Debug is never enabled.
   * 
   * @return false
   */
  public final boolean isDebugEnabled() {
    return false;
  }

  /**
   * Error is never enabled.
   * 
   * @return false
   */
  public final boolean isErrorEnabled() {
    return false;
  }

  /**
   * Fatal is never enabled.
   * 
   * @return false
   */
  public final boolean isFatalEnabled() {
    return false;
  }

  /**
   * Info is never enabled.
   * 
   * @return false
   */
  public final boolean isInfoEnabled() {
    return false;
  }

  /**
   * Trace is never enabled.
   * 
   * @return false
   */
  public final boolean isTraceEnabled() {
    return false;
  }

  /**
   * Warn is never enabled.
   * 
   * @return false
   */
  public final boolean isWarnEnabled() {
    return false;
  }

}
