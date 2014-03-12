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

import org.slf4j.Logger;
import org.slf4j.ILoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JDK14LoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriately named {@link JDK14LoggerAdapter} instance.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class JDK14LoggerFactory implements ILoggerFactory {

  // key: name (String), value: a JDK14LoggerAdapter;
  Map loggerMap;

  public JDK14LoggerFactory() {
    loggerMap = new HashMap();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.slf4j.ILoggerFactory#getLogger(java.lang.String)
   */
  public synchronized Logger getLogger(String name) {
    Logger ulogger = null;
    // protect against concurrent access of loggerMap
    synchronized (this) {
      // the root logger is called "" in JUL
      if(name.equalsIgnoreCase(Logger.ROOT_LOGGER_NAME)) {
        name = "";
      }
      ulogger = (Logger) loggerMap.get(name);
      if (ulogger == null) {
        java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(name);
        ulogger = new JDK14LoggerAdapter(logger);
        loggerMap.put(name, ulogger);
      }
    }
    return ulogger;
  }
}
