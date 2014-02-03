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
package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * SubstituteLoggerFactory is an trivial implementation of
 * {@link ILoggerFactory} which always returns the unique instance of NOPLogger.
 * <p/>
 * <p>
 * It used as a temporary substitute for the real ILoggerFactory during its
 * auto-configuration which may re-enter LoggerFactory to obtain logger
 * instances. See also http://bugzilla.slf4j.org/show_bug.cgi?id=106
 * <p/>
 * <p>
 * Logger implementations can swap out the NOPLogger with actual Logger
 * implementation once they are properly configured by changing the delegate
 * in {@link org.slf4j.helpers.SubstitutableLogger}
 * </p>
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class SubstituteLoggerFactory implements ILoggerFactory {

  // keep a record of requested logger names
  final ConcurrentMap<String, SubstitutableLogger> loggers = new ConcurrentHashMap<String, SubstitutableLogger>();

  public Logger getLogger(String name) {
    SubstitutableLogger logger;
    synchronized (loggers) {
      logger = loggers.get(name);
      if (logger == null) {
        logger = new SubstitutableLogger(name);
        loggers.put(name, logger);
      }
    }
    return logger;
  }

  public List getLoggerNameList() {
    List<String> copy = new ArrayList<String>();
    synchronized (loggers) {
      copy.addAll(loggers.keySet());
    }
    return copy;
  }

  public Iterable<SubstitutableLogger> getLoggers() {
    return loggers.values();
  }

  public void clear() {
    loggers.clear();
  }
}
