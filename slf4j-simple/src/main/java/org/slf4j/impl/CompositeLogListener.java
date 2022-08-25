package org.slf4j.impl;

public class CompositeLogListener implements SimpleLogListener {

  private final SimpleLogListener[] listeners;

  public CompositeLogListener(SimpleLogListener[] listeners) {this.listeners = listeners;}

  public boolean log(String logName, long timestamp, int level, String threadName, String message, Throwable t) {
    boolean result = true;
    for(int i = 0; i < listeners.length; i++) {
      try {
        if (!listeners[i].log(logName, timestamp, level, threadName, message, t)) {
          result = false;
        }
      } catch (Exception e) {
        // do not log it, as we are inside the logging system
        System.err.println("Error while logging at " + System.currentTimeMillis());
        e.printStackTrace();
        result = false;
      }
    }
    return result;
  }

  public boolean isAlive() {
    for(int i = 0; i < listeners.length; i++) {
      if (!listeners[i].isAlive()) {
        return false;
      }
    }
    return true;
  }

}
