package org.apache.juli.logging.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogConfigurationException;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class SLF4JLogFactory extends LogFactory {

  /**
   * The {@link org.apache.commons.logging.Log}instances that have already been
   * created, keyed by logger name.
   */
  private final ConcurrentMap<String, Log> loggerMap;

  public SLF4JLogFactory() {
    super();
    this.loggerMap = new ConcurrentHashMap<String, Log>();
  }

  /**
   * <p>
   * Construct (if necessary) and return a <code>Log</code> instance, using the
   * factory's current set of configuration attributes.
   * </p>
   * <p>
   * <strong>NOTE</strong> - Depending upon the implementation of the
   * <code>LogFactory</code> you are using, the <code>Log</code> instance you
   * are returned may or may not be local to the current application, and may or
   * may not be returned again on a subsequent call with the same name argument.
   * </p>
   * 
   * @param name
   *          Logical name of the <code>Log</code> instance to be returned (the
   *          meaning of this name is only known to the underlying logging
   *          implementation that is being wrapped)
   * @exception LogConfigurationException
   *              if a suitable <code>Log</code> instance cannot be returned
   */
  @Override
  public Log getInstance(final String name) throws LogConfigurationException {
    final Log instance = this.loggerMap.get(name);
    if (instance != null) {
      return instance;
    } else {
      Log newInstance;
      final Logger slf4jLogger = LoggerFactory.getLogger(name);
      if (slf4jLogger instanceof LocationAwareLogger) {
        newInstance = new SLF4JLocationAwareLog(
            (LocationAwareLogger) slf4jLogger);
      } else {
        newInstance = new SLF4JLog(slf4jLogger);
      }
      final Log oldInstance = this.loggerMap.putIfAbsent(name, newInstance);
      return oldInstance == null ? newInstance : oldInstance;
    }
  }

  @Override
  public void release() {
    this.loggerMap.clear();
  }
}
