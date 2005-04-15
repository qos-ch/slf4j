package org.slf4j.impl;

import org.slf4j.LoggerFactoryAdapter;
import org.slf4j.ULogger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class JDK14LoggerFA implements LoggerFactoryAdapter {
  Map map;

  public JDK14LoggerFA() {
    map = new HashMap();
  }

  /* (non-Javadoc)
   * @see org.slf4j.LoggerFactoryAdapter#getLogger(java.lang.String)
   */
  public ULogger getLogger(String name) {
    ULogger ulogger = (ULogger) map.get(name);
    if (ulogger == null) {
      Logger logger = Logger.getLogger(name);
      ulogger = new JDK14Logger(logger);
      map.put(name, ulogger);
    }
    return ulogger;
  }

  /* (non-Javadoc)
   * @see org.slf4j.LoggerFactoryAdapter#getLogger(java.lang.String, java.lang.String)
   */
  public ULogger getLogger(String domainName, String subDomainName) {
    return getLogger(domainName);
  }
}
