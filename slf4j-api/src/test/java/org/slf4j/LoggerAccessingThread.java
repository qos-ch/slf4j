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
package org.slf4j;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

public class LoggerAccessingThread extends Thread {
    private static int LOOP_LEN = 64;
    
    final CyclicBarrier barrier;
    final int count;
    final AtomicLong eventCount;
    List<Logger> loggerList;
    
    public LoggerAccessingThread(final CyclicBarrier barrier, List<Logger> loggerList, final int count, final AtomicLong eventCount) {
        this.barrier = barrier;
        this.loggerList = loggerList;
        this.count = count;
        this.eventCount = eventCount;
    }

    public void run() {
        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String loggerNamePrefix = this.getClass().getName();
        for (int i = 0; i < LOOP_LEN; i++) {
            Logger logger = LoggerFactory.getLogger(loggerNamePrefix + "-" + count + "-" + i);
            loggerList.add(logger);
            Thread.yield();
            logger.info("in run method");
            eventCount.getAndIncrement();
        }
    }
}
