package org.slf4j.impl;

import java.util.Date;

public class SimpleLogEvent {

  public final SimpleLogger logger;
  public final Date timestamp;
  public final int level;
  public final String message;
  public final Throwable t;

  public SimpleLogEvent(SimpleLogger logger, Date timestamp, int level, String message, Throwable t) {
    this.logger = logger;
    this.timestamp = timestamp;
    this.level = level;
    this.message = message;
    this.t = t;
  }

}