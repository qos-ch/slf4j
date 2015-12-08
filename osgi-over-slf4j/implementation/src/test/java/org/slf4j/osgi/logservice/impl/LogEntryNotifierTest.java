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

import net.jodah.concurrentunit.Waiter;
import org.junit.Test;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class LogEntryNotifierTest {

    Waiter waiter = new Waiter();
    BlockingQueue<LogEntry> queue = new LinkedBlockingQueue<LogEntry>();
    List<LogListener> listeners = new CopyOnWriteArrayList<LogListener>();

    LogEntryNotifier classUnderTest = new LogEntryNotifier(queue, listeners);

    @Test
    public void testRun() throws Exception {
        final LogEntry expectedEntry = new ImmutableLogEntry(null, 1, "");

        LogListener listener = new LogListener() {
            public void logged(LogEntry actualEntry) {
                waiter.assertEquals(expectedEntry, actualEntry);
                waiter.resume();
            }
        };
        LogListener throwingListener = new LogListener() {
            public void logged(LogEntry entry) {
                throw new IllegalStateException();
            }
        };

        listeners.add(throwingListener);
        listeners.add(listener);

        Thread thread = new Thread(classUnderTest);
        thread.start();

        queue.add(expectedEntry);

        waiter.await(1000);

        classUnderTest.stopRunning();
        //this will block until the thread dies
        thread.join();
    }
}