package org.slf4j.impl;

public interface SimpleLogListener {

  boolean isAlive();

  /**
   * @return true if handling the log record was successful
   */
  boolean log(String logName, long timestamp, int level, String threadName, String message, Throwable t);

}
