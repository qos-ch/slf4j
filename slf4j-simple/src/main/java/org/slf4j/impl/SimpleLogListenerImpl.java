package org.slf4j.impl;

import org.slf4j.spi.LocationAwareLogger;

import java.io.PrintStream;

public class SimpleLogListenerImpl implements SimpleLogListener {

  private static final int LOG_LEVEL_TRACE = LocationAwareLogger.TRACE_INT;
  private static final int LOG_LEVEL_DEBUG = LocationAwareLogger.DEBUG_INT;
  private static final int LOG_LEVEL_INFO = LocationAwareLogger.INFO_INT;
  private static final int LOG_LEVEL_WARN = LocationAwareLogger.WARN_INT;
  private static final int LOG_LEVEL_ERROR = LocationAwareLogger.ERROR_INT;

  private final PrintStream targetStream;
  private final StackTracePrinter stackTracePrinter;
  private final TimeAdjuster timeAdjuster;
  private final boolean showDateTime;
  private final SimpleMicroDateFormat dateFormatter;
  private final long startTime;
  private final boolean showThreadName;
  private final boolean levelInBrackets;
  private final String warnLevelString;

  public SimpleLogListenerImpl(PrintStream targetStream, StackTracePrinter stackTracePrinter, TimeAdjuster timeAdjuster, boolean showDateTime,
                               SimpleMicroDateFormat dateFormatter, long startTime, boolean showThreadName,
                               boolean levelInBrackets, String warnLevelString) {
    this.targetStream = targetStream;
    this.stackTracePrinter = stackTracePrinter;
    this.timeAdjuster = timeAdjuster;
    this.showDateTime = showDateTime;
    this.dateFormatter = dateFormatter;
    this.startTime = startTime;
    this.showThreadName = showThreadName;
    this.levelInBrackets = levelInBrackets;
    this.warnLevelString = warnLevelString;
  }

  public boolean isAlive() {
    return targetStream != null;
  }

  public void log(String logName, long timestamp, int level, String threadName, String message, Throwable t) {
    StringBuffer buf = new StringBuffer(32);

    // Append date-time if so configured
    if (showDateTime) {
      if (dateFormatter != null) {
        buf.append(dateFormatter.format(timeAdjuster.adjustTime(timestamp)));
        buf.append(' ');
      } else {
        buf.append(timestamp - startTime);
        buf.append(' ');
      }
    }

    // Append current thread name if so configured
    if (showThreadName) {
      buf.append('[');
      buf.append(threadName);
      buf.append("] ");
    }

    if (levelInBrackets)
      buf.append('[');

    // Append a readable representation of the log level
    switch (level) {
      case LOG_LEVEL_TRACE:
        buf.append("TRACE");
        break;
      case LOG_LEVEL_DEBUG:
        buf.append("DEBUG");
        break;
      case LOG_LEVEL_INFO:
        buf.append("INFO");
        break;
      case LOG_LEVEL_WARN:
        buf.append(warnLevelString);
        break;
      case LOG_LEVEL_ERROR:
        buf.append("ERROR");
        break;
    }
    if (levelInBrackets)
      buf.append(']');
    buf.append(' ');

    buf.append(logName).append(" - ");

    // Append the message
    buf.append(message);

    write(buf, t);
  }

  void write(StringBuffer buf, Throwable t) {
    targetStream.println(buf.toString());
    if (t != null) {
      stackTracePrinter.printStackTrace(t, targetStream);
    }
    targetStream.flush();
  }

}
