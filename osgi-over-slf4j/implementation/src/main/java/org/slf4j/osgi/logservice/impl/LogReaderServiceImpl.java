/**
 * Copyright (c) 2015 QOS.ch
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
 */

package org.slf4j.osgi.logservice.impl;

import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

import java.util.Enumeration;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of {@link LogReaderService}. OSGi log messages are sent to SLF4J for log
 * output but the most recent log messages are also retained in the LogReaderService for viewing
 * by anyone who utilizes this service.
 * <p/>
 * Reference: Section 101.4 Log Reader Service in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author Matt Bishop
 */
class LogReaderServiceImpl implements LogReaderService {

  private final Log log;
  private final CopyOnWriteArrayList<LogListener> listeners;

  LogReaderServiceImpl(Log log) {
    this.log = log;
    this.listeners = new CopyOnWriteArrayList<LogListener>();
  }

  public void addLogListener(LogListener listener) {
    //Spec says that only a single instance of a listener should be added, so using addIfAbsent().
    listeners.addIfAbsent(listener);
    log.addLogListener(listener);
  }

  public void removeLogListener(LogListener listener) {
    listeners.remove(listener);
    log.removeLogListener(listener);
  }

  public Enumeration getLog() {
    return log.entriesSnapshot().elements();
  }

  void stop() {
    for (LogListener listener : listeners) {
      log.removeLogListener(listener);
    }
    listeners.clear();
  }
}
