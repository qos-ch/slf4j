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
package org.slf4j.simple;

import static org.junit.Assert.assertNull;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Test whether invoking the SLF4J API causes problems or not.
 * 
 * @author Ceki Gulcu
 *
 */
public class InvocationTest {

    PrintStream old = System.err;

    @Before
    public void setUp() throws Exception {
        System.setErr(new SilentPrintStream(old));
    }

    @After
    public void tearDown() throws Exception {
        System.setErr(old);
    }

    @Test
    public void test1() {
        Logger logger = LoggerFactory.getLogger("test1");
        logger.debug("Hello world.");
    }

    @Test
    public void test2() {
        Integer i1 = Integer.valueOf(1);
        Integer i2 = Integer.valueOf(2);
        Integer i3 = Integer.valueOf(3);
        Exception e = new Exception("This is a test exception.");
        Logger logger = LoggerFactory.getLogger("test2");

        logger.debug("Hello world 1.");
        logger.debug("Hello world {}", i1);
        logger.debug("val={} val={}", i1, i2);
        logger.debug("val={} val={} val={}", new Object[] { i1, i2, i3 });

        logger.debug("Hello world 2", e);
        logger.info("Hello world 2.");

        logger.warn("Hello world 3.");
        logger.warn("Hello world 3", e);

        logger.error("Hello world 4.");
        logger.error("Hello world {}", Integer.valueOf(3));
        logger.error("Hello world 4.", e);
    }

    // http://jira.qos.ch/browse/SLF4J-69
    // formerly http://bugzilla.slf4j.org/show_bug.cgi?id=78
    @Test
    public void testNullParameter_BUG78() {
        Logger logger = LoggerFactory.getLogger("testNullParameter_BUG78");
        String[] parameters = null;
        String msg = "hello {}";
        logger.info(msg, (Object[]) parameters);
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
        assertNull(MDC.get("k"));
        MDC.remove("k");
        assertNull(MDC.get("k"));
        MDC.clear();
    }
}
