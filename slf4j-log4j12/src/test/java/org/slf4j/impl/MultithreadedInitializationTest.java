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
package org.slf4j.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultithreadedInitializationTest {

    // value of LogManager.DEFAULT_CONFIGURATION_KEY;
    static String CONFIG_FILE_KEY = "log4j.configuration";

    final static int THREAD_COUNT = 4 + Runtime.getRuntime().availableProcessors() * 2;

    private static AtomicLong EVENT_COUNT = new AtomicLong(0);

    final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);

    int diff = new Random().nextInt(10000);
    String loggerName = "org.slf4j.impl.RecursiveInitializationTest";

    @After
    public void tearDown() throws Exception {
        System.clearProperty(CONFIG_FILE_KEY);
    }

    @Test
    public void multiThreadedInitialization() throws InterruptedException, BrokenBarrierException {
        System.out.println("THREAD_COUNT=" + THREAD_COUNT);
        System.setProperty(CONFIG_FILE_KEY, "recursiveInitWithActivationDelay.properties");
        LoggerAccessingThread[] accessors = harness();

        for (LoggerAccessingThread accessor : accessors) {
            EVENT_COUNT.getAndIncrement();
            accessor.logger.info("post harness");
        }

        Logger logger = LoggerFactory.getLogger(loggerName + ".slowInitialization-" + diff);
        logger.info("hello");
        EVENT_COUNT.getAndIncrement();

        List<LoggingEvent> events = getRecordedEvents();
        assertEquals(EVENT_COUNT.get(), events.size());
    }

    private List<LoggingEvent> getRecordedEvents() {
        org.apache.log4j.Logger root = LogManager.getRootLogger();

        RecursiveAppender ra = (RecursiveAppender) root.getAppender("RECURSIVE");
        return ra.events;
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
