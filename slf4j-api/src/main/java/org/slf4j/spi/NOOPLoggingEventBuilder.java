package org.slf4j.spi;

import org.slf4j.Marker;

import java.time.temporal.ChronoUnit;

/**
 * A {@link LoggingEventBuilder} that does nothing. It's used by {@link org.slf4j.Logger} whenever the desired logging level is not enabled.
 */
public class NOOPLoggingEventBuilder implements LoggingEventBuilder {

  public static final LoggingEventBuilder INSTANCE = new NOOPLoggingEventBuilder();

  @Override
  public LoggingEventBuilder withCause(Throwable cause) {
    return this;
  }

  @Override
  public LoggingEventBuilder withMarker(Marker marker) {
    return this;
  }

  @Override
  public LoggingEventBuilder every(long amountOfTime, ChronoUnit unit) {
    return this;
  }

  @Override
  public LoggingEventBuilder every(int amountOfCalls) {
    return this;
  }

  @Override
  public void log(String message) {

  }

  @Override
  public void log(String message, Object arg) {

  }

  @Override
  public void log(String message, Object arg1, Object arg2) {

  }

  @Override
  public void log(String message, Object arg1, Object arg2, Object arg3) {

  }

  @Override
  public void log(String message, Object arg1, Object arg2, Object arg3, Object arg4) {

  }

  @Override
  public void log(String message, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {

  }

  @Override
  public void log(String message, Object... args) {

  }
}
