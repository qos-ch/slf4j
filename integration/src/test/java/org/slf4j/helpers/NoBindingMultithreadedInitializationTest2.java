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
package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventRecodingLogger;

import junit.framework.TestCase;

public class NoBindingMultithreadedInitializationTest2 extends TestCase {
	final protected static int THREAD_COUNT = 4 + Runtime.getRuntime().availableProcessors() * 2;

	private final List<Logger> createdLoggers = Collections.synchronizedList(new ArrayList<Logger>());

	protected final AtomicLong eventCount = new AtomicLong(0);
	final private CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);

	int diff = new Random().nextInt(10000);


    public NoBindingMultithreadedInitializationTest2(String name) {
        super(name);
    }
	
	public void testNoBindingMultiThreadedInitialization() throws InterruptedException, BrokenBarrierException {
		@SuppressWarnings("unused")
		LoggerAccessingThread2[] accessors = harness();

		Logger logger = LoggerFactory.getLogger(getClass().getName());
		logger.info("hello");
		eventCount.getAndIncrement();

		assertAllSubstLoggersAreFixed();
		long recordedEventCount = getRecordedEventCount();
		int LENIENCY_COUNT = 16;

		long expectedEventCount = eventCount.get() + extraLogEvents();

		assertTrue(expectedEventCount + " >= " + recordedEventCount, expectedEventCount >= recordedEventCount);
		assertTrue(expectedEventCount + " < " + recordedEventCount + "+" + LENIENCY_COUNT,
				expectedEventCount < recordedEventCount + LENIENCY_COUNT);
	}

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

	private LoggerAccessingThread2[] harness() throws InterruptedException, BrokenBarrierException {
		LoggerAccessingThread2[] threads = new LoggerAccessingThread2[THREAD_COUNT];
		for (int i = 0; i < THREAD_COUNT; i++) {
			threads[i] = new LoggerAccessingThread2(barrier, createdLoggers, i, eventCount);
			threads[i].start();
		}

		// trigger barrier
		barrier.await();

		for (int i = 0; i < THREAD_COUNT; i++) {
			threads[i].join();
		}

		return threads;
	}

	final String loggerName = this.getClass().getName();

	protected long getRecordedEventCount() {
		return eventCount.get();
	}

}
