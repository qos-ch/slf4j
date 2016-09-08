package org.slf4j.impl;

public class TimeAdjusterImpl implements TimeAdjuster {

  public static final TimeAdjusterImpl INSTANCE = new TimeAdjusterImpl();

  public long adjustTime(long timeInMillis) {
    return timeInMillis;
  }

}
