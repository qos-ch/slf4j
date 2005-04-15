package org.slf4j.impl;

import org.slf4j.ULogger;


/**
 * A no operation (NOP) implementation of {@link ULogger}.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class NOPLogger implements ULogger {

  /**
   * The unique instance of NOPLogger.
   */
  public final static NOPLogger NOP_LOGGER = new NOPLogger();
  
  /**
   * There is no point in people creating multiple instances of NullLogger. 
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
