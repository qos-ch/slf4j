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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultithreadedInitializationTest {

    final static int THREAD_COUNT = 4 + Runtime.getRuntime().availableProcessors() * 2;

    private static AtomicLong EVENT_COUNT = new AtomicLong(0);

    final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);

    int diff = new Random().nextInt(10000);
    String loggerName = "org.slf4j.impl.MultithreadedInitializationTest";

    private static java.util.logging.Logger getRootLogger() {
        return LogManager.getLogManager().getLogger("");
    }

    @After
    public void tearDown() throws Exception {
        java.util.logging.Logger rootLogger = getRootLogger();
        Handler[] handlers = rootLogger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            if (handlers[i] instanceof RecordingHandler) {
                rootLogger.removeHandler(handlers[i]);
            }
        }
    }

    @Before
    public void addRecordingHandler() {
        getRootLogger().addHandler(new RecordingHandler());
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

        List<LogRecord> records = getRecordedEvents();
        assertEquals(EVENT_COUNT.get(), records.size());
    }

    private List<LogRecord> getRecordedEvents() {
        RecordingHandler ra = findRecordingHandler();
        if (ra == null) {
            fail("failed to fing RecordingHandler");
        }
        return ra.records;
    }

    RecordingHandler findRecordingHandler() {
        java.util.logging.Logger root = LogManager.getLogManager().getLogger("");
        Handler[] handlers = root.getHandlers();
        for (Handler h : handlers) {
            if (h instanceof RecordingHandler)
                return (RecordingHandler) h;
        }
        return null;
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

}
