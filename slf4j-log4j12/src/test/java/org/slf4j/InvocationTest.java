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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.spi.LoggingEvent;

import junit.framework.TestCase;

/**
 * Test whether invoking the SLF4J API causes problems or not.
 * 
 * @author Ceki Gulcu
 * 
 */
public class InvocationTest extends TestCase {

    ListAppender listAppender = new ListAppender();
    org.apache.log4j.Logger root;

    public InvocationTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        root = org.apache.log4j.Logger.getRootLogger();
        root.addAppender(listAppender);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
        root.getLoggerRepository().resetConfiguration();
    }

    public void test1() {
        Logger logger = LoggerFactory.getLogger("test1");
        logger.debug("Hello world.");
        assertEquals(1, listAppender.list.size());
    }

    public void test2() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Integer i3 = new Integer(3);
        Exception e = new Exception("This is a test exception.");
        Logger logger = LoggerFactory.getLogger("test2");

        logger.trace("Hello trace.");

        logger.debug("Hello world 1.");
        logger.debug("Hello world {}", i1);
        logger.debug("val={} val={}", i1, i2);
        logger.debug("val={} val={} val={}", new Object[] { i1, i2, i3 });

        logger.debug("Hello world 2", e);
        logger.info("Hello world 2.");

        logger.warn("Hello world 3.");
        logger.warn("Hello world 3", e);

        logger.error("Hello world 4.");
        logger.error("Hello world {}", new Integer(3));
        logger.error("Hello world 4.", e);
        assertEquals(11, listAppender.list.size());
    }

    public void testNull() {
        Logger logger = LoggerFactory.getLogger("testNull");
        logger.trace(null);
        logger.debug(null);
        logger.info(null);
        logger.warn(null);
        logger.error(null);

        Exception e = new Exception("This is a test exception.");
        logger.debug(null, e);
        logger.info(null, e);
        logger.warn(null, e);
        logger.error(null, e);
        assertEquals(8, listAppender.list.size());
    }

    // http://bugzilla.slf4j.org/show_bug.cgi?id=78
    public void testNullParameter_BUG78() {
        Logger logger = LoggerFactory.getLogger("testNullParameter_BUG78");
        String[] parameters = null;
        String msg = "hello {}";

        logger.debug(msg, parameters);
        assertEquals(1, listAppender.list.size());
        LoggingEvent e = (LoggingEvent) listAppender.list.get(0);
        assertEquals(msg, e.getMessage());
    }

    public void testMarker() {
        Logger logger = LoggerFactory.getLogger("testMarker");
        Marker blue = MarkerFactory.getMarker("BLUE");
        logger.trace(blue, "hello");
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
        assertEquals(12, listAppender.list.size());
    }

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

    public void testMDCContextMapValues() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ka", "va");
        map.put("kb", "vb");

        MDC.put("k", "v");
        assertEquals("v", MDC.get("k"));
        MDC.setContextMap(map);
        assertNull(MDC.get("k"));
        assertEquals("va", MDC.get("ka"));
        assertEquals("vb", MDC.get("kb"));
    }

}
