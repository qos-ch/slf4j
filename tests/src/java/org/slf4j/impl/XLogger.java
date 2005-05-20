
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

