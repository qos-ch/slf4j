
package org.slf4j.impl;

import org.slf4j.Logger;
import org.slf4j.ILoggerFactory;


/**
 * NOPLoggerFactory is am implementation of {@link ILoggerFactory}
 * which always returns the unique instance of NOPLogger.
 * 
 * @author Ceki Gulcu
 */
public class XLoggerFA implements ILoggerFactory {
  
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
