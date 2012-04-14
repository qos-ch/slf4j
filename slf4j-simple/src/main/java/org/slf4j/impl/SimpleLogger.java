/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.impl;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintStream;

/**
 * A simple (and direct) implementation that logs messages of level INFO or
 * higher on the console (<code>System.err<code>).
 *
 * <p>The output includes the relative time in milliseconds, thread
 * name, the level, logger name, and the message followed by the line
 * separator for the host.  In log4j terms it amounts to the "%r [%t]
 * %level %logger - %m%n" pattern. </p>
 *
 * <p>Sample output follows.</p>
<pre>
176 [main] INFO examples.Sort - Populating an array of 2 elements in reverse order.
225 [main] INFO examples.SortAlgo - Entered the sort method.
304 [main] INFO examples.SortAlgo - Dump of integer array:
317 [main] INFO examples.SortAlgo - Element [0] = 0
331 [main] INFO examples.SortAlgo - Element [1] = 1
343 [main] INFO examples.Sort - The next log statement should be an error message.
346 [main] ERROR examples.SortAlgo - Tried to dump an uninitialized array.
        at org.log4j.examples.SortAlgo.dump(SortAlgo.java:58)
        at org.log4j.examples.Sort.main(Sort.java:64)
467 [main] INFO  examples.Sort - Exiting main method.
</pre>
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class SimpleLogger extends MarkerIgnoringBase {

  private static final long serialVersionUID = -6560244151660620173L;

  /**
   * Mark the time when this class gets loaded into memory.
   */
  private static long startTime = System.currentTimeMillis();
  public static final String LINE_SEPARATOR = System
      .getProperty("line.separator");
  private static String INFO_STR = "INFO";
  private static String WARN_STR = "WARN";
  private static String ERROR_STR = "ERROR";

  /**
   * Package access allows only {@link SimpleLoggerFactory} to instantiate
   * SimpleLogger instances.
   */
  SimpleLogger(String name) {
    this.name = name;
  }

  /**
   * Always returns false.
   *
   * @return always false
   */
  public boolean isTraceEnabled() {
    return false;
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the TRACE
   * level.
   */
  public void trace(String msg) {
    // NOP
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the TRACE
   * level.
   */
  public void trace(String format, Object param1) {
    // NOP
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the TRACE
   * level.
   */
  public void trace(String format, Object param1, Object param2) {
    // NOP
  }

  public void trace(String format, Object[] argArray) {
    // NOP
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the TRACE
   * level.
   */
  public void trace(String msg, Throwable t) {
    // NOP
  }

  /**
   * Always returns false.
   *
   * @return always false
   */
  public boolean isDebugEnabled() {
    return false;
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the DEBUG
   * level.
   */
  public void debug(String msg) {
    // NOP
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the DEBUG
   * level.
   */
  public void debug(String format, Object param1) {
    // NOP
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the DEBUG
   * level.
   */
  public void debug(String format, Object param1, Object param2) {
    // NOP
  }

  public void debug(String format, Object[] argArray) {
    // NOP
  }

  /**
   * A NOP implementation, as this logger is permanently disabled for the DEBUG
   * level.
   */
  public void debug(String msg, Throwable t) {
    // NOP
  }

  /**
   * This is our internal implementation for logging regular (non-parameterized)
   * log messages.
   *
   * @param level
   * @param message
   * @param t
   * @param printStream
   */
  private void log(String level, String message, Throwable t, PrintStream printStream) {
    StringBuffer buf = new StringBuffer();

    long millis = System.currentTimeMillis();
    buf.append(millis - startTime);

    buf.append(" [");
    buf.append(Thread.currentThread().getName());
    buf.append("] ");

    buf.append(level);
    buf.append(" ");

    buf.append(name);
    buf.append(" - ");

    buf.append(message);

    buf.append(LINE_SEPARATOR);

    printStream.print(buf.toString());
    if (t != null) {
      t.printStackTrace(printStream);
    }
    printStream.flush();
  }

  /**
   * For formatted messages, first substitute arguments and then log.
   *
   * @param level
   * @param format
   * @param arg1
   * @param arg2
   * @param printStream
   */
  private void formatAndLog(String level, String format, Object arg1,
                            Object arg2, PrintStream printStream) {
    FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
    log(level, tp.getMessage(), tp.getThrowable(), printStream);
  }

  /**
   * For formatted messages, first substitute arguments and then log.
   *
   * @param level
   * @param format
   * @param argArray
   * @param printStream
   */
  private void formatAndLog(String level, String format, Object[] argArray, PrintStream printStream) {
    FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
    log(level, tp.getMessage(), tp.getThrowable(), printStream);
  }

  /**
   * Always returns true.
   */
  public boolean isInfoEnabled() {
    return true;
  }

  /**
   * A simple implementation which always logs messages of level INFO according
   * to the format outlined above.
   */
  public void info(String msg) {
    log(INFO_STR, msg, null, System.err);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * INFO according to the format outlined above.
   */
  public void info(String format, Object arg) {
    formatAndLog(INFO_STR, format, arg, null, System.out);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * INFO according to the format outlined above.
   */
  public void info(String format, Object arg1, Object arg2) {
    formatAndLog(INFO_STR, format, arg1, arg2, System.out);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * INFO according to the format outlined above.
   */
  public void info(String format, Object[] argArray) {
    formatAndLog(INFO_STR, format, argArray, System.out);
  }

  /**
   * Log a message of level INFO, including an exception.
   */
  public void info(String msg, Throwable t) {
    log(INFO_STR, msg, t, System.out);
  }

  /**
   * Always returns true.
   */
  public boolean isWarnEnabled() {
    return true;
  }

  /**
   * A simple implementation which always logs messages of level WARN according
   * to the format outlined above.
   */
  public void warn(String msg) {
    log(WARN_STR, msg, null, System.err);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * WARN according to the format outlined above.
   */
  public void warn(String format, Object arg) {
    formatAndLog(WARN_STR, format, arg, null, System.err);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * WARN according to the format outlined above.
   */
  public void warn(String format, Object arg1, Object arg2) {
    formatAndLog(WARN_STR, format, arg1, arg2, System.err);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * WARN according to the format outlined above.
   */
  public void warn(String format, Object[] argArray) {
    formatAndLog(WARN_STR, format, argArray, System.err);
  }

  /**
   * Log a message of level WARN, including an exception.
   */
  public void warn(String msg, Throwable t) {
    log(WARN_STR, msg, t, System.err);
  }

  /**
   * Always returns true.
   */
  public boolean isErrorEnabled() {
    return true;
  }

  /**
   * A simple implementation which always logs messages of level ERROR according
   * to the format outlined above.
   */
  public void error(String msg) {
    log(ERROR_STR, msg, null, System.err);
  }

  /**
   * Perform single parameter substitution before logging the message of level
   * ERROR according to the format outlined above.
   */
  public void error(String format, Object arg) {
    formatAndLog(ERROR_STR, format, arg, null, System.err);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * ERROR according to the format outlined above.
   */
  public void error(String format, Object arg1, Object arg2) {
    formatAndLog(ERROR_STR, format, arg1, arg2, System.err);
  }

  /**
   * Perform double parameter substitution before logging the message of level
   * ERROR according to the format outlined above.
   */
  public void error(String format, Object[] argArray) {
    formatAndLog(ERROR_STR, format, argArray, System.err);
  }

  /**
   * Log a message of level ERROR, including an exception.
   */
  public void error(String msg, Throwable t) {
    log(ERROR_STR, msg, t, System.err);
  }
}
