/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 * 
 * All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.impl;
import org.slf4j.Logger;

import java.util.logging.Level;


/**
 * A wrapper over {@link java.util.logging.Logger
 * java.util.logging.Logger} which conforms to the {@link Logger}
 * interface.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class JDK14Logger implements Logger {
  final java.util.logging.Logger logger;

  // WARN: JDK14Logger constructor should have only package access so that
  // only JDK14LoggerFA be able to create one.
  JDK14Logger(java.util.logging.Logger logger) {
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
