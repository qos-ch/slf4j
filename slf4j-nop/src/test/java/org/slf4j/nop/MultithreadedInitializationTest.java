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
package org.slf4j.nop;

import static org.junit.Assert.assertEquals;
import static org.slf4j.helpers.Reporter.SLF4J_INTERNAL_VERBOSITY_KEY;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.slf4j.helpers.StringPrintStream;

public class MultithreadedInitializationTest {

    static int NUM_LINES_IN_SLF4J_CONNECTED_WITH_PROVIDER_INFO = 1;
    final static int THREAD_COUNT = 4 + Runtime.getRuntime().availableProcessors() * 2;

    private static final AtomicLong EVENT_COUNT = new AtomicLong(0);

    final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);

    int diff = new Random().nextInt(10000);
    String loggerName = "org.slf4j.impl.MultithreadedInitializationTest";
    private final PrintStream oldErr = System.err;
    StringPrintStream sps = new StringPrintStream(oldErr, false);

    @Before
    public void setup() {
        System.setProperty(SLF4J_INTERNAL_VERBOSITY_KEY, "debug");
        LoggerFactoryFriend.reset();
        System.setErr(sps);
    }

    @After
    public void tearDown() throws Exception {
        System.clearProperty(SLF4J_INTERNAL_VERBOSITY_KEY);
        LoggerFactoryFriend.reset();
        System.setErr(oldErr);
    }

    @Test
    public void multiThreadedInitialization() throws InterruptedException, BrokenBarrierException {
        System.out.println("THREAD_COUNT=" + THREAD_COUNT);
        LoggerAccessingThread[] accessors = harness();

        for (LoggerAccessingThread accessor : accessors) {
            EVENT_COUNT.getAndIncrement();
            accessor.logger.info("post harness");
        }

        Logger logger = LoggerFactory.getLogger(loggerName + ".slowInitialization-" + diff);
        logger.info("hello");
        EVENT_COUNT.getAndIncrement();

        assertEquals(NUM_LINES_IN_SLF4J_CONNECTED_WITH_PROVIDER_INFO, sps.stringList.size());
    }

    private static LoggerAccessingThread[] harness() throws InterruptedException, BrokenBarrierException {
        LoggerAccessingThread[] threads = new LoggerAccessingThread[THREAD_COUNT];
        final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new LoggerAccessingThread(barrier, i);
            threads[i].start();
        }

        barrier.await();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
        return threads;
    }

    static class LoggerAccessingThread extends Thread {
        final CyclicBarrier barrier;
        Logger logger;
        int count;

        LoggerAccessingThread(CyclicBarrier barrier, int count) {
            this.barrier = barrier;
            this.count = count;
        }

        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger = LoggerFactory.getLogger(this.getClass().getName() + "-" + count);
            logger.info("in run method");
            EVENT_COUNT.getAndIncrement();
        }
    };

//    public static class StringPrintStream extends PrintStream {
//
//        public static final String LINE_SEP = System.getProperty("line.separator");
//        PrintStream other;
//        List<String> stringList = new ArrayList<>();
//
//        public StringPrintStream(PrintStream ps) {
//            super(ps);
//            other = ps;
//        }
//
//        public void print(String s) {
//            other.print(s);
//            stringList.add(s);
//        }
//
//        public void println(String s) {
//            other.println(s);
//            stringList.add(s);
//        }
//
//        public void println(Object o) {
//            other.println(o);
//            stringList.add(o.toString());
//        }
//    };

}
