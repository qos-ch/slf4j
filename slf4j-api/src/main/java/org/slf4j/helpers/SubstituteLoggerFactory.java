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

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * SubstituteLoggerFactory is an trivial implementation of
 * {@link ILoggerFactory} which always returns the unique instance of NOPLogger.
 * 
 * <p>
 * It used as a temporary substitute for the real ILoggerFactory during its
 * auto-configuration which may re-enter LoggerFactory to obtain logger
 * instances. See also http://bugzilla.slf4j.org/show_bug.cgi?id=106
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class SubstituteLoggerFactory implements ILoggerFactory {

  // keep a record of requested logger names
  final List loggerNameList = new ArrayList();

  public Logger getLogger(String name) {
    synchronized (loggerNameList) {
      loggerNameList.add(name);
    }
    return NOPLogger.NOP_LOGGER;
  }

  public List getLoggerNameList() {
    List copy = new ArrayList();
    synchronized (loggerNameList) {
      copy.addAll(loggerNameList);
    }
    return copy;
  }

}
