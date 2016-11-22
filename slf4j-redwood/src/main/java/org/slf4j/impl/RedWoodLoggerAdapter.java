package org.slf4j.impl;

import edu.stanford.nlp.util.logging.Redwood.RedwoodChannels;
import edu.stanford.nlp.util.logging.Redwood;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

public class RedWoodLoggerAdapter extends MarkerIgnoringBase implements LocationAwareLogger {

  transient final RedwoodChannels channels;
  transient final String name;

  static String SELF = RedWoodLoggerAdapter.class.getName();
  static String SUPER = MarkerIgnoringBase.class.getName();


  RedWoodLoggerAdapter(String name, RedwoodChannels channels) {
    this.channels = channels;
    this.name = name.substring(name.lastIndexOf(".") + 1);
  }

  @Override
  public boolean isTraceEnabled() {
    return true;
  }

  @Override
  public void trace(String msg) {
    trace(msg, (Throwable) null);
  }

  @Override
  public void trace(String format, Object arg) {
    FormattingTuple ft = MessageFormatter.format(format, arg);
    trace(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    trace(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void trace(String format, Object... argArray) {
    FormattingTuple ft = MessageFormatter.format(format, argArray);
    trace(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void trace(String msg, Throwable throwable) {
    mytrace(msg, throwable);
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public void debug(String msg) {
    debug(msg, (Throwable) null);
  }

  @Override
  public void debug(String format, Object arg) {
    FormattingTuple ft = MessageFormatter.format(format, arg);
    debug(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    debug(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void debug(String format, Object... argArray) {
    FormattingTuple ft = MessageFormatter.format(format, argArray);
    debug(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void debug(String msg, Throwable throwable) {
    mydebug(msg, throwable);
  }

  @Override
  public boolean isInfoEnabled() {
    return true;
  }

  @Override
  public void info(String msg) {
    info(msg, (Throwable) null);
  }

  @Override
  public void info(String format, Object arg) {
    FormattingTuple ft = MessageFormatter.format(format, arg);
    info(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    info(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void info(String format, Object... argArray) {
    FormattingTuple ft = MessageFormatter.format(format, argArray);
    info(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void info(String msg, Throwable throwable) {
    myinfo(msg, throwable);
  }

  @Override
  public boolean isWarnEnabled() {
    return true;
  }

  @Override
  public void warn(String msg) {
    warn(msg, (Throwable) null);
  }

  @Override
  public void warn(String format, Object arg) {
    FormattingTuple ft = MessageFormatter.format(format, arg);
    warn(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void warn(String format, Object... argArray) {
    FormattingTuple ft = MessageFormatter.format(format, argArray);
    warn(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    warn(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void warn(String msg, Throwable throwable) {
    mywarn(msg, throwable);
  }

  @Override
  public boolean isErrorEnabled() {
    return true;
  }

  @Override
  public void error(String msg) {
    error(msg, (Throwable) null);
  }

  @Override
  public void error(String format, Object arg) {
    FormattingTuple ft = MessageFormatter.format(format, arg);
    error(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    error(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void error(String format, Object... argArray) {
    FormattingTuple ft = MessageFormatter.format(format, argArray);
    error(ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void error(String msg, Throwable throwable) {
    myerror(msg, throwable);
  }

  private void mytrace(String msg, Throwable throwable) {
    mylog(channels.channels(RedWoodLogLevel.TRACE), msg,throwable);
  }

  private void mydebug(String msg, Throwable throwable) {
    mylog(channels.channels(RedWoodLogLevel.DEBUG, Redwood.DBG), msg, throwable);
  }

  private void myinfo(String msg, Throwable throwable) {
    mylog(channels.channels(RedWoodLogLevel.INFO), msg, throwable);
  }

  private void mywarn(String msg, Throwable throwable) {
    mylog(channels.channels(RedWoodLogLevel.WARN, Redwood.WARN), msg, throwable);
  }

  private void myerror(String msg, Throwable throwable) {
    mylog(channels.channels(RedWoodLogLevel.ERROR, Redwood.ERR), msg, throwable);
  }

  private void mylog(RedwoodChannels channels, String msg, Throwable throwable) {
    if (throwable != null) {
      StringWriter sw = new StringWriter();
      throwable.printStackTrace(new PrintWriter(sw));
      channels.log("[" + name + "]" + msg + "\n" + sw);
    } else {
      channels.log("[" + name + "]" + msg);
    }
  }

  @Override
  public void log(Marker marker, String callerFQCN, int level, String message, Object[] argArray,
      Throwable t) {
    String s = fillCallerData(callerFQCN);
    switch (level) {
    case LocationAwareLogger.TRACE_INT:
      trace(marker, message, t);
      break;
    case LocationAwareLogger.DEBUG_INT:
      debug(marker, message, t);
      break;
    case LocationAwareLogger.INFO_INT:
      info(marker, message, t);
      break;
    case LocationAwareLogger.WARN_INT:
      warn(marker, message, t);
      break;
    case LocationAwareLogger.ERROR_INT:
      error(marker, message, t);
      break;
    default:
      throw new IllegalStateException("Level number " + level + " is not recognized.");
    }
  }

  final private String fillCallerData(String callerFQCN) {
    StackTraceElement[] steArray = new Throwable().getStackTrace();

    int selfIndex = -1;
    for (int i = 0; i < steArray.length; i++) {
      final String className = steArray[i].getClassName();
      if (className.equals(callerFQCN) || className.equals(SUPER)) {
        selfIndex = i;
        break;
      }
    }

    int found = -1;
    for (int i = selfIndex + 1; i < steArray.length; i++) {
      final String className = steArray[i].getClassName();
      if (!(className.equals(callerFQCN) || className.equals(SUPER))) {
        found = i;
        break;
      }
    }

    if (found != -1) {
      StackTraceElement ste = steArray[found];
      // setting the class name has the side effect of setting
      // the needToInferCaller variable to false.
      return ste.toString();
    }
    return callerFQCN;
  }
}
