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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Maintains a list of {@link org.osgi.service.log.LogListener}s and asychronously notifies them when
 * a new LogEntry has been created.
 * <p/>
 * Reference: Section 101.4 Log Reader Service in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author Matt Bishop
 */
class LogListeners {

    private final CopyOnWriteArrayList<LogListener> logListeners;
    private final BlockingQueue<LogEntry> entryQueue;
    private final LogEntryNotifier logEntryNotifier;


    LogListeners() {
        logListeners = new CopyOnWriteArrayList<LogListener>();
        entryQueue = new LinkedBlockingQueue<LogEntry>();
        logEntryNotifier = new LogEntryNotifier(entryQueue, logListeners);

        Thread notifierThread = new Thread(logEntryNotifier, "LogReaderService Notifier");
        notifierThread.setDaemon(true);
        notifierThread.start();
    }

    void logEntryAdded(LogEntry entry) {
        entryQueue.offer(entry);
    }

    void addLogListener(LogListener listener) {
        //Spec says that only a single instance of a listener should be added, so using addIfAbsent().
        logListeners.addIfAbsent(listener);
    }

    void removeLogListener(LogListener listener) {
        logListeners.remove(listener);
    }

    void stop() {
        logEntryNotifier.stopRunning();
        logListeners.clear();
        entryQueue.clear();
    }
}
