package org.slf4j.impl;

import java.util.Date;

public interface SimpleLogListener {

  void log(String logName, Date timestamp, int level, String threadName, String message, Throwable t);

}
