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

import static org.junit.Assert.fail;

import java.util.logging.Handler;

import org.junit.After;
import org.junit.Before;

public class JDK14MultithreadedInitializationTest extends MultithreadedInitializationTest {

    java.util.logging.Logger julRootLogger = java.util.logging.Logger.getLogger("");
    java.util.logging.Logger julOrgLogger = java.util.logging.Logger.getLogger("org");

    @Before
    public void addRecordingHandler() {
        System.out.println("THREAD_COUNT=" + THREAD_COUNT);
        removeAllHandlers(julRootLogger);
        removeAllHandlers(julOrgLogger);
        julOrgLogger.addHandler(new CountingHandler());
    }

    private void removeAllHandlers(java.util.logging.Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            logger.removeHandler(handlers[i]);
        }
    }

    @After
    public void tearDown() throws Exception {
        removeAllHandlers(julOrgLogger);
    }

    protected long getRecordedEventCount() {
        CountingHandler ra = findRecordingHandler();
        if (ra == null) {
            fail("failed to fing RecordingHandler");
        }
        return ra.eventCount.get();
    }

    private CountingHandler findRecordingHandler() {
        Handler[] handlers = julOrgLogger.getHandlers();
        for (Handler h : handlers) {
            if (h instanceof CountingHandler)
                return (CountingHandler) h;
        }
        return null;
    }

}
