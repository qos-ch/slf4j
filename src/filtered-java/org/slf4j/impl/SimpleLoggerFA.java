package org.slf4j.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactoryAdapter;
import org.slf4j.ULogger;


/**
 * An implementation of {@link LoggerFactoryAdapter} which always returns
 * {@link SimpleLogger} instances.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class SimpleLoggerFA implements LoggerFactoryAdapter {

  Map map;
  
  public SimpleLoggerFA() {
    map = new HashMap();
  }


  /**
   * Return an appropriate {@link SimpleLogger} instance by name. At this time,
   * 
   */
  /**
   * Return an appropriate {@link SimpleLogger} instance.
   * */
  public ULogger getLogger(String name) {
    ULogger ulogger = (ULogger) map.get(name);
    if(ulogger == null) {
      ulogger = new SimpleLogger(name);
      map.put(name, ulogger);
    }
    return ulogger;
  }

  /*
   *  (non-Javadoc)
   * @see org.slf4j.LoggerFactoryAdapter#getLogger(java.lang.String, java.lang.String)
   */
  public ULogger getLogger(String domainName, String subDomainName) {
    return getLogger(domainName);
  }
  
  
}
