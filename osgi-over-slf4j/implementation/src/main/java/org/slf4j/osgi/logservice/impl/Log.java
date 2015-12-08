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

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;

import java.util.Vector;

/**
 * Represents the OSGi <i>Log</i>. Maintains the entries in the log as well as listeners.
 * <p/>
 * Reference: Section 101.2 Log Service in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author Matt Bishop
 */
class Log {

    private final LogEntryStore logEntryStore;
    private final LogListeners logListeners;

    Log(LogEntryStore logEntryStore, LogListeners logListeners) {
        this.logEntryStore = logEntryStore;
        this.logListeners = logListeners;
    }


    void stop() {
        logListeners.stop();
        logEntryStore.stop();
    }

    public void addLogEntry(LogEntry logEntry) {
        logEntryStore.addLogEntry(logEntry);
        //log listeners get every logEntry created, regardless of the logDebug setting in LogEntryStore. See section 101.4
        logListeners.logEntryAdded(logEntry);
    }

    public boolean debugEnabled() {
        return logEntryStore.debugEnabled();
    }

    public void addLogListener(LogListener listener) {
        logListeners.addLogListener(listener);
    }

    public void removeLogListener(LogListener listener) {
        logListeners.removeLogListener(listener);
    }

    public Vector<LogEntry> entriesSnapshot() {
        return logEntryStore.entriesSnapshot();
    }
}
