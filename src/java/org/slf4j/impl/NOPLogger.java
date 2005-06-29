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


/**
 * A direct NOP (no operation) implementation of {@link Logger}.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class NOPLogger implements Logger {

  /**
   * The unique instance of NOPLogger.
   */
  public final static NOPLogger NOP_LOGGER = new NOPLogger();
  
  /**
   * There is no point in creating multiple instances of NullLogger,
   * hence the private access modifier.
   */
  private NOPLogger() {
  }
  
  /**
   * Always returns false.
   * @return always false
   */
  public boolean isDebugEnabled() {
    return false;
  }

  /** A NOP implementation. */
  public void debug(String msg) {
    // NOP
  }

  /** A NOP implementation.  */
  public void debug(String format, Object arg) {
    // NOP
  }

  /** A NOP implementation.  */
  public void debug(String format, Object arg1, Object arg2) {
    // NOP
  }

  /** A NOP implementation. */
  public void debug(String msg, Throwable t) {
    // NOP
  }

  /**
   * Always returns false.
   * @return always false
   */
  public boolean isInfoEnabled() {
    // NOP
    return false;
  }

  /** A NOP implementation. */
  public void info(String msg) {
    // NOP
  }

 /** A NOP implementation. */
  public void info(String format, Object arg1) {
    // NOP
  }

 /** A NOP implementation. */
  public void info(String format, Object arg1, Object arg2) {
    // NOP
  }

  /** A NOP implementation. */
  public void info(String msg, Throwable t) {
    // NOP
  }

  /**
   * Always returns false.
   * @return always false
   */
  public boolean isWarnEnabled() {
    return false;
  }

  /** A NOP implementation. */
  public void warn(String msg) {
    // NOP
  }

  /** A NOP implementation. */
  public void warn(String format, Object arg1) {
    // NOP
  }

  /** A NOP implementation. */
  public void warn(String format, Object arg1, Object arg2) {
    // NOP
  }

  /** A NOP implementation. */
  public void warn(String msg, Throwable t) {
    // NOP
  }

  /** A NOP implementation. */
  public boolean isErrorEnabled() {
    return false;
  }

  /** A NOP implementation. */ 
  public void error(String msg) {
    // NOP
  }

   /** A NOP implementation. */
  public void error(String format, Object arg1) {
    // NOP
  }

  /** A NOP implementation. */
  public void error(String format, Object arg1, Object arg2) {
    // NOP
  }

  /** A NOP implementation. */
  public void error(String msg, Throwable t) {
    // NOP
  }

}
