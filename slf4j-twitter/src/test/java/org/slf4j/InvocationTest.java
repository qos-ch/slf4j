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
package org.slf4j;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test whether invoking the SLF4J API causes problems or not.
 *
 * @author Ceki Gulcu
 */
public class InvocationTest {

    Level oldLevel;
    java.util.logging.Logger root = java.util.logging.Logger.getLogger("");

    ListHandler listHandler = new ListHandler();

    @Before
    public void setUp() throws Exception {
        oldLevel = root.getLevel();
        root.setLevel(Level.FINE);
        // removeAllHandlers(root);
        root.addHandler(listHandler);
    }

    @After
    public void tearDown() throws Exception {
        root.setLevel(oldLevel);
        removeListHandlers(root);
    }

    @Test
    public void test1() {
        Logger logger = LoggerFactory.getLogger("test1");
        logger.debug("Hello world.");
        assertLogMessage("Hello world.", 0);
    }

    @Test
    public void verifyMessageFormatting() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Integer i3 = new Integer(3);
        Exception e = new Exception("This is a test exception.");
        Logger logger = LoggerFactory.getLogger("test2");

        int index = 0;
        logger.debug("Hello world");
        assertLogMessage("Hello world", index++);

        logger.debug("Hello world {}", i1);
        assertLogMessage("Hello world " + i1, index++);

        logger.debug("val={} val={}", i1, i2);
        assertLogMessage("val=1 val=2", index++);

        logger.debug("val={} val={} val={}", new Object[] { i1, i2, i3 });
        assertLogMessage("val=1 val=2 val=3", index++);

        logger.debug("Hello world 2", e);
        assertLogMessage("Hello world 2", index);
        assertException(e.getClass(), index++);
        logger.info("Hello world 2.");

        logger.warn("Hello world 3.");
        logger.warn("Hello world 3", e);

        logger.error("Hello world 4.");
        logger.error("Hello world {}", new Integer(3));
        logger.error("Hello world 4.", e);
    }

    @Test
    public void testNull() {
        Logger logger = LoggerFactory.getLogger("testNull");
        logger.debug(null);
        logger.info(null);
        logger.warn(null);
        logger.error(null);

        Exception e = new Exception("This is a test exception.");
        logger.debug(null, e);
        logger.info(null, e);
        logger.warn(null, e);
        logger.error(null, e);
    }

    @Test
    public void testMarker() {
        Logger logger = LoggerFactory.getLogger("testMarker");
        Marker blue = MarkerFactory.getMarker("BLUE");
        logger.debug(blue, "hello");
        logger.info(blue, "hello");
        logger.warn(blue, "hello");
        logger.error(blue, "hello");

        logger.debug(blue, "hello {}", "world");
        logger.info(blue, "hello {}", "world");
        logger.warn(blue, "hello {}", "world");
        logger.error(blue, "hello {}", "world");

        logger.debug(blue, "hello {} and {} ", "world", "universe");
        logger.info(blue, "hello {} and {} ", "world", "universe");
        logger.warn(blue, "hello {} and {} ", "world", "universe");
        logger.error(blue, "hello {} and {} ", "world", "universe");
    }

    @Test
    public void testMDC() {
        MDC.put("k", "v");
        assertNotNull(MDC.get("k"));
        assertEquals("v", MDC.get("k"));

        MDC.remove("k");
        assertNull(MDC.get("k"));

        MDC.put("k1", "v1");
        assertEquals("v1", MDC.get("k1"));
        MDC.clear();
        assertNull(MDC.get("k1"));

        try {
            MDC.put(null, "x");
            fail("null keys are invalid");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertLogMessage(String expected, int index) {
        LogRecord logRecord = listHandler.recordList.get(index);
        Assert.assertNotNull(logRecord);
        assertEquals(expected, logRecord.getMessage());
    }

    private void assertException(Class<? extends Throwable> exceptionType, int index) {
        LogRecord logRecord = listHandler.recordList.get(index);
        Assert.assertNotNull(logRecord);
        assertEquals(exceptionType, logRecord.getThrown().getClass());
    }

    void removeListHandlers(java.util.logging.Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler h : handlers) {
            if (h instanceof ListHandler)
                logger.removeHandler(h);
        }
    }

    static private class ListHandler extends java.util.logging.Handler {

        List<LogRecord> recordList = new ArrayList<LogRecord>();

        @Override
        public void publish(LogRecord record) {
            recordList.add(record);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    }
}
