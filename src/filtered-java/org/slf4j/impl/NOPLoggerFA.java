package org.slf4j.impl;

import org.slf4j.LoggerFactoryAdapter;
import org.slf4j.ULogger;


/**
 * NOPLoggerFA is am implementation of {@link LoggerFactoryAdapter}
 * which always returns the unique instance of NOPLogger.
 * 
 * @author Ceki Gulcu
 */
public class NOPLoggerFA implements LoggerFactoryAdapter {
  
  public NOPLoggerFA() {
    // nothing to do
  }
  
  public ULogger getLogger(String name) {
    return NOPLogger.NOP_LOGGER;
  }
  public ULogger getLogger(String domainName, String subDomainName) {
    return NOPLogger.NOP_LOGGER;  
  }  
}
