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

import java.util.Enumeration;
import java.util.Vector;

/**
 * <p>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.LogManager</code> class (as found in log4j 1.2)
 * delegating all calls to SLF4J.
 * 
 * <p>
 * This implementation does <b>NOT</b> implement the setRepositorySelector(),
 * getLoggerRepository(), exists(), getCurrentLoggers(), shutdown() and
 * resetConfiguration() methods which do not have SLF4J equivalents.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * */
public class LogManager {

  public static Logger getRootLogger() {
    return Log4jLoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
  }

  public static Logger getLogger(final String name) {
    return Log4jLoggerFactory.getLogger(name);
  }

  public static Logger getLogger(final Class clazz) {
    return Log4jLoggerFactory.getLogger(clazz.getName());
  }
  

  /**
   * This bogus implementation returns an empty enumeration.
   * @return
   */
  public static Enumeration getCurrentLoggers() {
    return new Vector().elements();
  }

  /**
   * Implemented as NOP.
   */
  public static void shutdown() {
  }

  /**
   * Implemented as NOP.
   */
  public static void resetConfiguration() {
  }
}
