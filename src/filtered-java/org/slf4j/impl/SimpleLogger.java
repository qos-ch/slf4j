package org.slf4j.impl;

import org.slf4j.ULogger;


/**
 * A simple implementation that logs messages of level INFO or higher on
 * the console (<code>System.out<code>). 
 * <p>
 * The output includes the relative time in milliseconds, thread name, the level,  
 * logger name, and the message followed by the line separator for the host. 
 * In log4j terms it amounts to the "%r  [%t] %level %logger - %m%n" pattern.
 * <pre>
176 [main] INFO examples.Sort - Populating an array of 2 elements in reverse order.
225 [main] INFO examples.SortAlgo - Entered the sort method.
304 [main] INFO SortAlgo.DUMP - Dump of interger array:
317 [main] INFO SortAlgo.DUMP - Element [0] = 0
331 [main] INFO SortAlgo.DUMP - Element [1] = 1
343 [main] INFO examples.Sort - The next log statement should be an error message.
346 [main] ERROR SortAlgo.DUMP - Tried to dump an uninitialized array.
        at org.log4j.examples.SortAlgo.dump(SortAlgo.java:58)
        at org.log4j.examples.Sort.main(Sort.java:64)
467 [main] INFO  examples.Sort - Exiting main method.
</pre>
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class SimpleLogger implements ULogger {

  String loggerName;
  
  /**
   * Mark the time when this class gets loaded into memory.
   */
  static private long startTime = System.currentTimeMillis();
  
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  
  static private String INFO_STR = "INFO";
  static private String WARN_STR = "WARN";
  static private String ERROR_STR = "ERROR";
  
  /**
   * Package access allows only {@link SimpleLoggerFA} to instantiate 
   * SimpleLogger instances.
   */
  SimpleLogger(String name) {
    this.loggerName = name;
  }
  
  /**
   * Always returns false.
   */
  public boolean isDebugEnabled() {
    return false;
  }

  /**
   * A NOP implementation.
   */
  public void debug(Object msg) {
    // NOP
  }

  /**
   * A NOP implementation.
   */
  public void debug(Object parameterizedMsg, Object param1) {
    // NOP
  }

  /**
   * A NOP implementation.
   */
  public void debug(String parameterizedMsg, Object param1, Object param2) {
    // NOP
  }

  /**
   * A NOP implementation.
   */
  public void debug(Object msg, Throwable t) {
    // NOP
  }

  /**
   * This is our internal implementation for logging regular (non-parameterized)
   * log messages.
   * 
   * @param level
   * @param message
   * @param t
   */
  private void log(String level, String message, Throwable t) {
    StringBuffer buf = new StringBuffer();
    
    long millis  = System.currentTimeMillis();
    buf.append(millis-startTime);
    
    buf.append(" [");
    buf.append(Thread.currentThread().getName());
    buf.append("] ");
    
    buf.append(level);
    buf.append(" ");
    
    buf.append(loggerName);
    buf.append(" - ");

    buf.append(message);

    buf.append(LINE_SEPARATOR);
    
    System.out.print(buf.toString());
    if(t != null) {
      t.printStackTrace(System.out);
    }
    System.out.flush();
  }
  /**
   * For parameterized messages, first substitute parameters and then log.
   * 
   * @param level
   * @param parameterizedMsg
   * @param param1
   * @param param2
   */
  private void parameterizedLog(String level, Object parameterizedMsg, Object param1, Object param2) {
    if (parameterizedMsg instanceof String) {
      String msgStr = (String) parameterizedMsg;
      msgStr = MessageFormatter.format(msgStr, param1, param2);
      log(level, msgStr, null);
    } else {
      // To be failsafe, we handle the case where 'messagePattern' is not
      // a String. Unless the user makes a mistake, this should not happen.
      log(level, parameterizedMsg.toString(), null);
    }
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
  public void info(Object msg) {
    log(INFO_STR, msg.toString(), null);
  }

  
  /**
   * Perform single parameter substituion before logging the message of level 
   * INFO according to the format outlined above.
   */
  public void info(Object parameterizedMsg, Object param1) {
    parameterizedLog(INFO_STR, parameterizedMsg, param1, null);
  }

  /**
   * Perform double parameter substituion before logging the message of level 
   * INFO according to the format outlined above.
   */
  
  public void info(String parameterizedMsg, Object param1, Object param2) {
    parameterizedLog(INFO_STR, parameterizedMsg, param1, param2);
  }

  /** 
   * Log a message of level INFO, including an exception.
   */
  public void info(Object msg, Throwable t) {
    log(INFO_STR, msg.toString(), t);
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
  public void warn(Object msg) {
    log(WARN_STR, msg.toString(), null);
  }

  /**
   * Perform single parameter substituion before logging the message of level 
   * WARN according to the format outlined above.
   */
  public void warn(Object parameterizedMsg, Object param1) {
    parameterizedLog(WARN_STR, parameterizedMsg, param1, null);
  }

  /**
   * Perform double parameter substituion before logging the message of level 
   * WARN according to the format outlined above.
   */
  public void warn(String parameterizedMsg, Object param1, Object param2) {
    parameterizedLog(WARN_STR, parameterizedMsg, param1, param2);
  }

  /**
   * Log a message of level WARN, including an exception.
   */
  public void warn(Object msg, Throwable t) {
    log(WARN_STR, msg.toString(), t);
  }

  /**
   * Always returns true.
   */
  public boolean isErrorEnabled() {
    return true;
  }

  /**
   * A simple implementation which always logs messages of level ERROR acoording
   * to the format outlined above.
   */
  public void error(Object msg) {
    log(ERROR_STR, msg.toString(), null);
  }


  /**
   * Perform single parameter substituion before logging the message of level 
   * ERROR according to the format outlined above.
   */
  public void error(Object parameterizedMsg, Object param1) {
    parameterizedLog(ERROR_STR, parameterizedMsg, param1, null);
  }

  /**
   * Perform double parameter substituion before logging the message of level 
   * ERROR according to the format outlined above.
   */
  public void error(String parameterizedMsg, Object param1, Object param2) {
    parameterizedLog(ERROR_STR, parameterizedMsg, param1, param2);
  }

  /** 
   * Log a message of level ERROR, including an exception.
   */
  public void error(Object msg, Throwable t) {
    log(ERROR_STR, msg.toString(), t);
  }

}
