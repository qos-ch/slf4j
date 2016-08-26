package org.slf4j.impl;

import java.util.Date;

public class CompositeLogListener implements SimpleLogListener {

  private final SimpleLogListener[] listeners;

  public CompositeLogListener(SimpleLogListener[] listeners) {this.listeners = listeners;}

  public void log(String logName, Date timestamp, int level, String threadName, String message, Throwable t) {
    for(int i = 0; i < listeners.length; i++) {
      try {
        listeners[i].log(logName, timestamp, level, threadName, message, t);
      } catch (Exception e) {
        // do not log it, as we are inside the logging system
        e.printStackTrace();
      }
    }
  }

}
