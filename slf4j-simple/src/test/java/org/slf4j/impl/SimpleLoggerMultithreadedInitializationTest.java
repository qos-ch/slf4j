/**
 * Copyright (c) 2004-2016 QOS.ch
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

import static org.junit.Assert.assertTrue;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerAccessingThread;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;

public class SimpleLoggerMultithreadedInitializationTest {

    final static int THREAD_COUNT = 4 + Runtime.getRuntime().availableProcessors() * 2;

    private final AtomicLong eventCount = new AtomicLong(0);
    private final PrintStream oldErr = System.err;
    private final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);

    final int diff = new Random().nextInt(10000);
    final String loggerName = this.getClass().getName();
    StringPrintStream sps = new StringPrintStream(oldErr, true);

    @Before
    public void setup() {
        System.out.println("THREAD_COUNT=" + THREAD_COUNT);
        System.setErr(sps);
        System.setProperty(SimpleLogger.LOG_FILE_KEY, "System.err");
        LoggerFactoryFriend.reset();
    }

    @After
    public void tearDown() throws Exception {
        LoggerFactoryFriend.reset();
        System.clearProperty(SimpleLogger.LOG_FILE_KEY);
        System.setErr(oldErr);
    }

    @Test
    public void multiThreadedInitialization() throws InterruptedException, BrokenBarrierException {

        @SuppressWarnings("unused")
        LoggerAccessingThread[] accessors = harness();

        Logger logger = LoggerFactory.getLogger(loggerName + diff);
        logger.info("hello");
        eventCount.getAndIncrement();

        int NUM_LINES_IN_SLF4J_REPLAY_WARNING = 3;
        
        long expected = eventCount.get() + NUM_LINES_IN_SLF4J_REPLAY_WARNING;
        int actual = sps.stringList.size();
        assertTrue(expected + " >= " + actual, expected >= actual);
        assertTrue(expected + " < " + actual + " + 10", expected < actual + 10);
    }

    private LoggerAccessingThread[] harness() throws InterruptedException, BrokenBarrierException {
        final LoggerAccessingThread[] threads = new LoggerAccessingThread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            LoggerAccessingThread simpleLoggerThread = new LoggerAccessingThread(barrier, i, eventCount);
            threads[i] = simpleLoggerThread;
            simpleLoggerThread.start();
        }

        barrier.await();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
        return threads;
    }


    static class StringPrintStream extends PrintStream {

        public static final String LINE_SEP = System.getProperty("line.separator");
        PrintStream other;
        boolean duplicate = false;

        List<String> stringList = Collections.synchronizedList(new ArrayList<String>());

        public StringPrintStream(PrintStream ps, boolean duplicate) {
            super(ps);
            other = ps;
            this.duplicate = duplicate;
        }

        public StringPrintStream(PrintStream ps) {
            this(ps, false);
        }

        public void print(String s) {
            if (duplicate)
                other.print(s);
            stringList.add(s);
        }

        public void println(String s) {
            if (duplicate)
                other.println(s);
            stringList.add(s);
        }

        public void println(Object o) {
            if (duplicate)
                other.println(o);
            stringList.add(o.toString());
        }
    };

}
