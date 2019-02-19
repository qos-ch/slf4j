package org.slf4j.spi;

import org.slf4j.Marker;

import java.time.temporal.ChronoUnit;

public interface LoggingEventBuilder {

  /**
   * Associates an exception to this log event.
   *
   * @param cause the exception we want to associate this log event to
   * @return this instance of {@link LoggingEventBuilder}
   */
  LoggingEventBuilder withCause(Throwable cause);

  /**
   * Specify the {@link Marker} to use to log this log event.
   *
   * @param marker the marker we want to use to log this log event
   * @return this instance of {@link LoggingEventBuilder}
   */
  LoggingEventBuilder withMarker(Marker marker);

  /**
   * Configures this {@link LoggingEventBuilder} to log at most every amount of time.
   * <p>
   * For example, say we want to log at most every 2 seconds.
   * If our code attempts to log multiple times for 5 seconds, we'll see only 3 log entries in our log files: the first one, another one after 2 seconds, and another one after 4 seconds.
   *
   * @param amountOfTime the amount of time to wait between log entries
   * @param unit         the unit of time expressed by amountOfTime
   * @return this instance of {@link LoggingEventBuilder}
   * @throws IllegalStateException if this {@link LoggingEventBuilder} was already configured to limit logging by quantity, see {@link #every(int)}.
   */
  LoggingEventBuilder every(long amountOfTime, ChronoUnit unit);

  /**
   * Configures this {@link LoggingEventBuilder} to log at most every number of times
   * <p>
   * For example, say we want to log at most every 5 times.
   * If our code attempts to log 8 times, we'll see 2 log entries in our log files: the first one, and the fifth one.
   *
   * @param amountOfCalls the amount of calls that must be made before logging
   * @return this instance of {@link LoggingEventBuilder}
   * @throws IllegalStateException if this {@link LoggingEventBuilder} was already configured to limit logging by time, see {@link #every(long, ChronoUnit)}.
   */
  LoggingEventBuilder every(int amountOfCalls);

  /**
   * Logs a message with no params
   *
   * @param message the log message
   */
  void log(String message);

  /**
   * Logs a message with one param
   *
   * @param format the log message
   * @param arg    a log message param
   */
  void log(String format, Object arg);

  /**
   * Logs a message with two params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   */
  void log(String format, Object arg1, Object arg2);

  /**
   * Logs a message with three params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   * @param arg3   a log message param
   */
  void log(String format, Object arg1, Object arg2, Object arg3);

  /**
   * Logs a message with four params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   * @param arg3   a log message param
   * @param arg4   a log message param
   */
  void log(String format, Object arg1, Object arg2, Object arg3, Object arg4);

  /**
   * Logs a message with five params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   * @param arg3   a log message param
   * @param arg4   a log message param
   * @param arg5   a log message param
   */
  void log(String format, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5);

  /**
   * Logs a message with varying number of params
   *
   * @param format the log message
   * @param args   log message params
   */
  void log(String format, Object... args);

}
