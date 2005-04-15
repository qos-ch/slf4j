package org.slf4j.impl;


import org.slf4j.ULogger;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A wrapper over @{link java.utill.Logger} which conforms to the 
 * {@link ULogger} interface.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class JDK14Logger implements ULogger {
  final Logger logger;

  // WARN: JDK14Logger constructor should have only package access so that
  // only JDK14LoggerFA be able to create one.
  JDK14Logger(Logger logger) {
    this.logger = logger;
  }

  /**
   * Is the logger instance enabled for the DEBUG level?
   * @return
   */
  public boolean isDebugEnabled() {
    return logger.isLoggable(Level.FINE);
  }

  //

  /**
   * Log a message object with the DEBUG level.
   * @param msg - the message object to be logged
   */
  public void debug(Object msg) {
    logger.fine(String.valueOf(msg));
  }

  /**
   * Log a parameterized message object at the DEBUG level.
   *
   * <p>This form is useful in avoiding the superflous object creation
   * problem when invoking this method while it is disabled.
   * </p>
   * @param parameterizedMsg - the parameterized message object
   * @param param1 - the parameter
   */
  public void debug(Object parameterizedMsg, Object param1) {
    if (logger.isLoggable(Level.FINE)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1);
        logger.fine(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.fine(parameterizedMsg.toString());
      }
    }
  }

  /**
   * Log a parameterized message object at the DEBUG level.
   *
   * <p>This form is useful in avoiding the superflous object creation
   * problem when invoking this method while it is disabled.
   * </p>
   * @param parameterizedMsg - the parameterized message object
   * @param param1 - the first parameter
   * @param param2 - the second parameter
   */
  public void debug(String parameterizedMsg, Object param1, Object param2) {
    if (logger.isLoggable(Level.FINE)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1, param2);
        logger.fine(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.fine(parameterizedMsg.toString());
      }
    }
  }

  public void debug(Object msg, Throwable t) {
    logger.log(Level.FINE, msg.toString(), t);
  }

  public boolean isInfoEnabled() {
    return logger.isLoggable(Level.INFO);
  }

  public void info(Object msg) {
    logger.info(msg.toString());
  }

  public void info(Object parameterizedMsg, Object param1) {
    if (logger.isLoggable(Level.INFO)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1);
        logger.info(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.info(parameterizedMsg.toString());
      }
    }
  }

  public void info(String parameterizedMsg, Object param1, Object param2) {
    if (logger.isLoggable(Level.INFO)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1, param2);
        logger.info(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.info(parameterizedMsg.toString());
      }
    }
  }

  public void info(Object msg, Throwable t) {
    logger.log(Level.INFO, msg.toString(), t);
  }

  public boolean isWarnEnabled() {
    return logger.isLoggable(Level.WARNING);
  }

  public void warn(Object msg) {
    logger.warning(msg.toString());
  }

  public void warn(Object parameterizedMsg, Object param1) {
    if (logger.isLoggable(Level.WARNING)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1);
        logger.warning(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.warning(parameterizedMsg.toString());
      }
    }
  }

  public void warn(String parameterizedMsg, Object param1, Object param2) {
    if (logger.isLoggable(Level.WARNING)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1, param2);
        logger.warning(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.warning(parameterizedMsg.toString());
      }
    }
  }

  public void warn(Object msg, Throwable t) {
    logger.log(Level.WARNING, msg.toString(), t);
  }

  public boolean isErrorEnabled() {
    return logger.isLoggable(Level.SEVERE);
  }

  public void error(Object msg) {
    logger.severe(msg.toString());
  }

  public void error(Object parameterizedMsg, Object param1) {
    if (logger.isLoggable(Level.WARNING)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1);
        logger.severe(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.severe(parameterizedMsg.toString());
      }
    }
  }
  
  public void error(String parameterizedMsg, Object param1, Object param2) {
    if (logger.isLoggable(Level.WARNING)) {
      if (parameterizedMsg instanceof String) {
        String msgStr = (String) parameterizedMsg;
        msgStr = MessageFormatter.format(msgStr, param1, param2);
        logger.severe(msgStr);
      } else {
        // To be failsafe, we handle the case where 'messagePattern' is not
        // a String. Unless the user makes a mistake, this should not happen.
        logger.severe(parameterizedMsg.toString());
      }
    }
  }
  public void error(Object msg, Throwable t) {
    logger.log(Level.SEVERE, msg.toString(), t);
  }
}
