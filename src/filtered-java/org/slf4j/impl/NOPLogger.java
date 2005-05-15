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
 * A no operation (NOP) implementation of {@link Logger}.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class NOPLogger implements Logger {

  /**
   * The unique instance of NOPLogger.
   */
  public final static NOPLogger NOP_LOGGER = new NOPLogger();
  
  /**
   * There is no point in creating multiple instances of NullLogger.
   * Hence, the private access modifier.
   */
  private NOPLogger() {
  }
  
  /* Always returns false.
   * 
   * @see org.slf4j.Logger#isDebugEnabled()
   */
  public boolean isDebugEnabled() {
    return false;
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#debug(java.lang.Object)
   */
  public void debug(Object msg) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#debug(java.lang.Object, java.lang.Object)
   */
  public void debug(Object parameterizedMsg, Object param1) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#debug(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public void debug(String parameterizedMsg, Object param1, Object param2) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#debug(java.lang.Object, java.lang.Throwable)
   */
  public void debug(Object msg, Throwable t) {
    // NOP
  }

  /* Always returns false.
   * @see org.slf4j.Logger#isInfoEnabled()
   */
  public boolean isInfoEnabled() {
    // NOP
    return false;
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#info(java.lang.Object)
   */
  public void info(Object msg) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#info(java.lang.Object, java.lang.Object)
   */
  public void info(Object parameterizedMsg, Object param1) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#info(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public void info(String parameterizedMsg, Object param1, Object param2) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#info(java.lang.Object, java.lang.Throwable)
   */
  public void info(Object msg, Throwable t) {
    // NOP
  }

  /* Always returns false.
   * @see org.slf4j.Logger#isWarnEnabled()
   */
  public boolean isWarnEnabled() {
    return false;
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#warn(java.lang.Object)
   */
  public void warn(Object msg) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#warn(java.lang.Object, java.lang.Object)
   */
  public void warn(Object parameterizedMsg, Object param1) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#warn(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public void warn(String parameterizedMsg, Object param1, Object param2) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#warn(java.lang.Object, java.lang.Throwable)
   */
  public void warn(Object msg, Throwable t) {
    // NOP
  }

  /* Always returns false.
   * @see org.slf4j.Logger#isErrorEnabled()
   */
  public boolean isErrorEnabled() {
    return false;
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#error(java.lang.Object)
   */
  public void error(Object msg) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#error(java.lang.Object, java.lang.Object)
   */
  public void error(Object parameterizedMsg, Object param1) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#error(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public void error(String parameterizedMsg, Object param1, Object param2) {
    // NOP
  }

  /* A NOP implementation.
   * @see org.slf4j.Logger#error(java.lang.Object, java.lang.Throwable)
   */
  public void error(Object msg, Throwable t) {
    // NOP
  }

}
