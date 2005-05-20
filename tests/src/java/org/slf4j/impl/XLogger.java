/*
 * Copyright (c) 2004-2005 SLF4J.ORG
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
 * A NOP Logger implementation.
 */
public class XLogger implements Logger {

  /**
   * The unique instance of NOPLogger.
   */
  public final static XLogger X_LOGGER = new XLogger();
  
  
  private XLogger() { }
  

  public boolean isDebugEnabled() {  return false; }

  public void debug(Object msg) {
  }

  public void debug(Object parameterizedMsg, Object param1) {  }

  public void debug(String parameterizedMsg, Object param1, Object param2) {  }

  public void debug(Object msg, Throwable t) {  }

  public boolean isInfoEnabled() {  return false;
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

  public void warn(Object msg, Throwable t) {
  }

  public boolean isErrorEnabled() {
    return false;
  }

  public void error(Object msg) {
  }

  public void error(Object parameterizedMsg, Object param1) {
  }

  public void error(String parameterizedMsg, Object param1, Object param2) {
  }

  public void error(Object msg, Throwable t) {
  }
}

