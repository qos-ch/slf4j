package org.slf4j.helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerAccessingThread;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventRecodingLogger;

abstract public class MultithreadedInitializationTest {
    final protected static int THREAD_COUNT = 4 + Runtime.getRuntime().availableProcessors() * 2;

    private final List<Logger> createdLoggers = Collections.synchronizedList(new ArrayList<Logger>());

    final private AtomicLong eventCount = new AtomicLong(0);
    final private CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);

    int diff = new Random().nextInt(10000);

    @Test
    public void multiThreadedInitialization() throws InterruptedException, BrokenBarrierException {
        @SuppressWarnings("unused")
        LoggerAccessingThread[] accessors = harness();

        Logger logger = LoggerFactory.getLogger(getClass().getName());
        logger.info("hello");
        eventCount.getAndIncrement();

        assertAllSubstLoggersAreFixed();
        long recordedEventCount = getRecordedEventCount();
        int LENIENCY_COUNT = 16;

        long expectedEventCount = eventCount.get() + extraLogEvents();

        assertTrue(expectedEventCount + " >= " + recordedEventCount, expectedEventCount >= recordedEventCount);
        assertTrue(expectedEventCount + " < " + recordedEventCount + "+" + LENIENCY_COUNT, expectedEventCount < recordedEventCount + LENIENCY_COUNT);
    }

    abstract protected long getRecordedEventCount();

    protected int extraLogEvents() {
        return 0;
    }

    private void assertAllSubstLoggersAreFixed() {
        for (Logger logger : createdLoggers) {
            if (logger instanceof SubstituteLogger) {
                SubstituteLogger substLogger = (SubstituteLogger) logger;
                if (substLogger.delegate() instanceof EventRecodingLogger)
                    fail("substLogger " + substLogger.getName() + " has a delegate of type EventRecodingLogger");
            }
        }
    }

    private LoggerAccessingThread[] harness() throws InterruptedException, BrokenBarrierException {
        LoggerAccessingThread[] threads = new LoggerAccessingThread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new LoggerAccessingThread(barrier, createdLoggers, i, eventCount);
            threads[i].start();
        }

        // trigger barrier
        barrier.await();

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }

        return threads;
    }
}
