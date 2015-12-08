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

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Stores a list of LogEntry instances.
 *
 * @author Matt Bishop
 */
class LogEntryStore implements ManagedService {

  static final String SIZE_PROPERTY = "maxSize";
  static final int DEFAULT_SIZE = 100;

  static final String DEBUG_PROPERTY = "storeDebug";
  static final boolean DEFAULT_DEBUG = false;

  private final Queue<LogEntry> logEntries = new ConcurrentLinkedQueue<LogEntry>();

  private volatile int logSize = DEFAULT_SIZE;
  private volatile boolean logDebug = DEFAULT_DEBUG;


  void addLogEntry(LogEntry entry) {
    if (entry == null) {
      throw new NullPointerException("cannot add null entries to the LogEventStore");
    }

    if (logDebug || entry.getLevel() != LogService.LOG_DEBUG) {
      logEntries.offer(entry);
      flushStaleEntries();
    }
  }

  boolean debugEnabled() {
    return logDebug;
  }

  Vector<LogEntry> entriesSnapshot() {
    Vector<LogEntry> snapshot = new Vector<LogEntry>(logEntries);
    //entries must be ordered newest to oldest
    Collections.reverse(snapshot);
    return snapshot;
  }

  void stop() {
    logEntries.clear();
  }


  public void updated(Dictionary properties) throws ConfigurationException {
    if (properties == null) {
      return;
    }

    Object possibleSize = properties.get(SIZE_PROPERTY);
    if (possibleSize != null) {
      if (possibleSize instanceof Integer) {
        int configSize = (Integer) possibleSize;
        if (configSize < 1 || configSize > 10000) {
          throw new ConfigurationException(SIZE_PROPERTY, "Must be in the range of 1 to 10000");
        }
        logSize = configSize;
        flushStaleEntries();
      } else {
        throw new ConfigurationException(SIZE_PROPERTY, "Must be an Integer");
      }
    }

    Object possibleDebug = properties.get(DEBUG_PROPERTY);
    if (possibleDebug != null) {
      if (possibleDebug instanceof Boolean) {
        logDebug = (Boolean) possibleDebug;
      } else {
        throw new ConfigurationException(DEBUG_PROPERTY, "Must be a Boolean");
      }
    }
  }

  private void flushStaleEntries() {
    while (logEntries.size() > logSize) {
      logEntries.poll();
    }
  }
}
