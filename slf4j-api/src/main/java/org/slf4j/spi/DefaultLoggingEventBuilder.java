package org.slf4j.spi;

import org.slf4j.Logger;
import org.slf4j.Marker;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder {

  private static class LogEveryAmountOfTime {

    private final long amount;
    private final ChronoUnit unit;

    public LogEveryAmountOfTime(long amount, ChronoUnit unit) {
      this.amount = amount;
      this.unit = unit;
    }

  }

  private static class LogEveryNumberOfCalls {

    private final int amount;

    public LogEveryNumberOfCalls(int amount) {
      this.amount = amount;
    }

  }

  private static final Object[] EMPTY_ARRAY = new Object[0];
  private static final String FQCN = DefaultLoggingEventBuilder.class.getName();
  private static final LoggingEventBuilderStats STATS = new LoggingEventBuilderStats();

  private final boolean isLocationAwareLogger;
  private final Logger logger;
  private final int level;
  private final BiConsumer<String, Object[]> loggerMethod;
  private final TriConsumer<Marker, String, Object[]> loggerMethodWithMarker;

  private Throwable cause;
  private Marker marker;
  private LogEveryAmountOfTime logEveryAmountOfTime;
  private LogEveryNumberOfCalls logEveryNumberOfCalls;

  public DefaultLoggingEventBuilder(Logger logger, int level, BiConsumer<String, Object[]> loggerMethod, TriConsumer<Marker, String, Object[]> loggerMethodWithMarker) {
    this.isLocationAwareLogger = logger instanceof LocationAwareLogger;
    this.logger = logger;
    this.level = level;
    this.loggerMethod = loggerMethod;
    this.loggerMethodWithMarker = loggerMethodWithMarker;
  }

  @Override
  public LoggingEventBuilder withCause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  @Override
  public LoggingEventBuilder withMarker(Marker marker) {
    this.marker = marker;
    return this;
  }

  @Override
  public LoggingEventBuilder every(long amountOfTime, ChronoUnit unit) {
    if(logEveryNumberOfCalls != null) {
      throw new IllegalStateException("We cannot filter log by time frame AND number of calls: pick one please");
    }
    this.logEveryAmountOfTime = new LogEveryAmountOfTime(amountOfTime, unit);
    return this;
  }

  @Override
  public LoggingEventBuilder every(int amountOfCalls) {
    if(logEveryAmountOfTime != null) {
      throw new IllegalStateException("We cannot filter log by time frame AND number of calls: pick one please");
    }
    this.logEveryNumberOfCalls = new LogEveryNumberOfCalls(amountOfCalls);
    return this;
  }

  @Override
  public void log(String message) {
    logInternal(message, EMPTY_ARRAY);
  }

  @Override
  public void log(String format, Object arg) {
    logInternal(format, new Object[]{arg});
  }

  @Override
  public void log(String format, Object arg1, Object arg2) {
    logInternal(format, new Object[]{arg1, arg2});
  }

  @Override
  public void log(String format, Object arg1, Object arg2, Object arg3) {
    logInternal(format, new Object[]{arg1, arg2, arg3});
  }

  @Override
  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4) {
    logInternal(format, new Object[]{arg1, arg2, arg3, arg4});
  }

  @Override
  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
    logInternal(format, new Object[]{arg1, arg2, arg3, arg4, arg5});
  }

  @Override
  public void log(String format, Object... args) {
    logInternal(format, args);
  }

  private void logInternal(String format, Object[] args) {
    if(logEveryAmountOfTime != null || logEveryNumberOfCalls != null) {
      String caller = getCaller();
      if(logEveryNumberOfCalls != null
              && !STATS.recordCallThenCheckIfNumberOfCallsMatchesAmount(caller, logEveryNumberOfCalls.amount)) {
        return;
      }

      if(logEveryAmountOfTime != null
              && !STATS.recordCallAndCheckIfEnoughTimePassed(caller, logEveryAmountOfTime.amount, logEveryAmountOfTime.unit)) {
        return;
      }
    }

    args = toStrings(args);

    if(isLocationAwareLogger) {
      ((LocationAwareLogger) logger).log(marker, FQCN, level, format, args, cause);
    } else {
      Object[] newArgs = args;
      if(cause != null) {
        newArgs = new Object[args.length + 1];
        System.arraycopy(args, 0, newArgs, 0, args.length);
        newArgs[newArgs.length - 1] = cause;
      }
      if(marker != null) {
        loggerMethodWithMarker.accept(marker, format, args);
      } else {
        loggerMethod.accept(format, newArgs);
      }
    }
  }

  private String getCaller() {
    return Arrays.stream(new Throwable().getStackTrace())
            .filter(trace -> !trace.getClassName().equals(getClass().getName()))
            .findFirst()
            .map(trace -> trace.getClassName() + ":" + trace.getMethodName() + "@" + trace.getLineNumber())
            .orElseThrow(IllegalStateException::new);
  }

  private Object toString(Object arg) {
    if(arg instanceof Supplier) {
      arg = toString(((Supplier) arg).get());
    }

    return arg;
  }

  private Object[] toStrings(Object[] args) {
    if(args == null) {
      return null;
    }

    for(int i = 0; i < args.length; i++) {
      args[i] = toString(args[i]);
    }
    return args;
  }

}
