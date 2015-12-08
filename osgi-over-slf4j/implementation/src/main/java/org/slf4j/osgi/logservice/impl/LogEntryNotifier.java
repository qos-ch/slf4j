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

/**
 * Notifies LogReaderService listeners of new log entries asynchronously.
 *
 * @author Matt Bishop
*/
class LogEntryNotifier implements Runnable {

    private static final LogEntry SKIP_ENTRY = new ImmutableLogEntry(null, Integer.MIN_VALUE, "Skip LogEntry");

    private final BlockingQueue<LogEntry> entryQueue;
    private final Iterable<LogListener> logListeners;

    private volatile boolean running = true;

    LogEntryNotifier(BlockingQueue<LogEntry> entryQueue, Iterable<LogListener> logListeners) {
        this.entryQueue = entryQueue;
        this.logListeners = logListeners;
    }

    public void run() {
        while (running) {
            try {
                LogEntry entry = entryQueue.take();
                if (SKIP_ENTRY.equals(entry)) {
                    continue;
                }
                for (LogListener logListener : logListeners) {
                    try {
                        logListener.logged(entry);
                    } catch (Exception e) {
                        //ignore and continue with the next listener
                    }
                }
            } catch (InterruptedException e) {
                //ignore and continue
            }
        }
    }

    public void stopRunning() {
        running = false;
        entryQueue.offer(SKIP_ENTRY);
    }
}
