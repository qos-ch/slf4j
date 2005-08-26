// TOTO

package org.apache.commons.logging.impl;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;

/**
 * Implementation of {@link Log} interface which delegates all processing to
 * a {@link Logger} instance.
 * 
 * <p>JCL's FATAL and TRACE levels are mapped to ERROR and DEBUG respectively. All 
 * other levels map one to one.
 * 
 * @author <a href="http://www.qos.ch/log4j/">Ceki G&uuml;lc&uuml;</a>
 */
public class SLF4JLog implements Log {

  Logger logger;

  SLF4JLog(Logger logger) {
    this.logger = logger;
  }

  /**
   * Delegate to org.slf4j.Logger instance.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  public boolean isFatalEnabled() {
    return logger.isErrorEnabled();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.logging.Log#isInfoEnabled()
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  public boolean isTraceEnabled() {
    return logger.isDebugEnabled();
  }

  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  public void trace(Object message) {
    logger.debug(message.toString());
  }

  public void trace(Object message, Throwable t) {
    logger.debug(message.toString(), t);
  }

  public void debug(Object message) {
    logger.debug(message.toString());
  }

  public void debug(Object message, Throwable t) {
    logger.debug(message.toString(), t);
  }

  public void info(Object message) {
    logger.info(message.toString());
  }

  public void info(Object message, Throwable t) {
    logger.info(message.toString(), t);
  }

  public void warn(Object message) {
    logger.warn(message.toString());
  }

  public void warn(Object message, Throwable t) {
    logger.warn(message.toString(), t);
  }


  public void error(Object message) {
    logger.error(message.toString());
  }

  public void error(Object message, Throwable t) {
    logger.error(message.toString(), t);
  }

  public void fatal(Object message) {
    logger.error(message.toString());
  }

  public void fatal(Object message, Throwable t) {
    logger.error(message.toString(), t);
  }

}