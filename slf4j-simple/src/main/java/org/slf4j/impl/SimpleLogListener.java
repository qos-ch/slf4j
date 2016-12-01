package org.slf4j.impl;

import java.util.Date;

public interface SimpleLogListener {

  boolean isAlive();

  void log(String logName, long timestamp, int level, String threadName, String message, Throwable t);

}
