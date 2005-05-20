
package org.slf4j.impl;

import org.slf4j.LoggerFactoryAdapter;
import org.slf4j.Logger;


/**
 * NOPLoggerFA is am implementation of {@link LoggerFactoryAdapter}
 * which always returns the unique instance of NOPLogger.
 * 
 * @author Ceki Gulcu
 */
public class XLoggerFA implements LoggerFactoryAdapter {
  
  public XLoggerFA() {
    // nothing to do
  }
  
  public Logger getLogger(String name) {
    return XLogger.X_LOGGER;
  }
  public Logger getLogger(String domainName, String subDomainName) {
    return XLogger.X_LOGGER;  
  }  
}
