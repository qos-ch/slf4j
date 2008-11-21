/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.logging.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Implementation of {@link Log org.apache.commons.logging.Log} interface which 
 * delegates all processing to a wrapped {@link Logger org.slf4j.Logger} instance.
 * 
 * <p>JCL's FATAL level is mapped to ERROR. All other levels map one to one.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class SLF4JLocationAwareLog implements Log, Serializable {

  private static final long serialVersionUID = -2379157579039314822L;
  
  // in both Log4jLogger and Jdk14Logger classes in the original JCL, the 
  // logger instance is transient
  private transient LocationAwareLogger logger;

  private static final String FQCN = SLF4JLocationAwareLog.class.getName();
  
  SLF4JLocationAwareLog(LocationAwareLogger logger) {
    this.logger = logger;
  }

  /**
   * Delegates to the <code>isTraceEnabled<code> method of the wrapped 
   * <code>org.slf4j.Logger</code> instance.
   */
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }
  
  /**
   * Directly delegates to the wrapped <code>org.slf4j.Logger</code> instance.
   */
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }
  
  /**
   * Directly delegates to the wrapped <code>org.slf4j.Logger</code> instance.
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  /**
   * Directly delegates to the wrapped <code>org.slf4j.Logger</code> instance.
   */
  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }
  
  /**
   * Directly delegates to the wrapped <code>org.slf4j.Logger</code> instance.
   */
  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  /**
   * Delegates to the <code>isErrorEnabled<code> method of the wrapped 
   * <code>org.slf4j.Logger</code> instance.
   */
  public boolean isFatalEnabled() {
    return logger.isErrorEnabled();
  }


  /**
   * Converts the input parameter to String and then delegates to 
   * the debug method of the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   */
  public void trace(Object message) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), null);
  }

  /**
   * Converts the first input parameter to String and then delegates to 
   * the debug method of the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   * @param t the exception to log
   */
  public void trace(Object message, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), t);
  }

  /**
   * Converts the input parameter to String and then delegates to the wrapped 
   * <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String} 
   */
  public void debug(Object message) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), null);
  }

  /**
   * Converts the first input parameter to String and then delegates to 
   * the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   * @param t the exception to log
   */
  public void debug(Object message, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), t);
  }

  /**
   * Converts the input parameter to String and then delegates to the wrapped 
   * <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String} 
   */
  public void info(Object message) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), null);
  }

  /**
   * Converts the first input parameter to String and then delegates to 
   * the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   * @param t the exception to log
   */
  public void info(Object message, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), t);
  }

  /**
   * Converts the input parameter to String and then delegates to the wrapped 
   * <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   */
  public void warn(Object message) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), null);
  }

  /**
   * Converts the first input parameter to String and then delegates to 
   * the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   * @param t the exception to log
   */
  public void warn(Object message, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), t);
  }

  /**
   * Converts the input parameter to String and then delegates to the wrapped 
   * <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   */
  public void error(Object message) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null);
  }

  /**
   * Converts the first input parameter to String and then delegates to 
   * the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   * @param t the exception to log
   */
  public void error(Object message, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), t);
  }


 
  /**
   * Converts the input parameter to String and then delegates to 
   * the error method of the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   */
  public void fatal(Object message) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null);
  }

  /**
   * Converts the first input parameter to String and then delegates to 
   * the error method of the wrapped <code>org.slf4j.Logger</code> instance.
   * 
   * @param message the message to log. Converted to {@link String}  
   * @param t the exception to log
   */
  public void fatal(Object message, Throwable t) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), t);
  }

}