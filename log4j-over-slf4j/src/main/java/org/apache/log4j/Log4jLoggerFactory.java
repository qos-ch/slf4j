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
package org.apache.log4j;

import java.util.Hashtable;

import org.slf4j.helpers.Util;

/**
 * This class is a factory that creates and maintains org.apache.log4j.Loggers
 * wrapping org.slf4j.Loggers.
 * 
 * It keeps a hashtable of all created org.apache.log4j.Logger instances so that
 * all newly created instances are not dulpicates of existing loggers.
 * 
 * @author S&eacute;bastien Pennec
 */
class Log4jLoggerFactory {

  // String, Logger
  private static Hashtable log4jLoggers = new Hashtable();

  private static final String LOG4J_DELEGATION_LOOP_URL = "http://www.slf4j.org/codes.html#log4jDelegationLoop";
  
  // check for delegation loops
  static {
    try {
      Class.forName("org.slf4j.impl.Log4jLoggerFactory");
      String part1 = "Detected both log4j-over-slf4j.jar AND slf4j-log4j12.jar on the class path, preempting StackOverflowError. ";
      String part2 = "See also " + LOG4J_DELEGATION_LOOP_URL
          + " for more details.";

      Util.report(part1);
      Util.report(part2);
            throw new IllegalStateException(part1 + part2);
    } catch (ClassNotFoundException e) {
      // this is the good case
    }
  }

  public static synchronized Logger getLogger(String name) {
    if (log4jLoggers.containsKey(name)) {
      return (org.apache.log4j.Logger) log4jLoggers.get(name);
    } else {
      Logger log4jLogger = new Logger(name);

      log4jLoggers.put(name, log4jLogger);
      return log4jLogger;
    }
  }

}
