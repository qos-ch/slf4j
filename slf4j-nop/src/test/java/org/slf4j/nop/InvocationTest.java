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
package org.slf4j.nop;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.function.Supplier;

/**
 * Test whether invoking the SLF4J API causes problems or not.
 * 
 * @author Ceki Gulcu
 *
 */
public class InvocationTest {

    @Test
    public void test1() {
        Logger logger = LoggerFactory.getLogger("test1");
        logger.debug("Hello world.");
    }

    @Test
    public void test2() {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(2);
        Integer i3 = new Integer(3);
        Supplier<String> s1 = () -> "supplier1";
        Supplier<String> s2 = () -> "supplier2";
        Exception e = new Exception("This is a test exception.");
        Logger logger = LoggerFactory.getLogger("test2");

        logger.debug("Hello world 1.");
        logger.debug(s1);
        logger.debug("Hello world {}", i1);
        logger.debug("val={} val={}", i1, i2);
        logger.debug("val={} val={} val={}", new Object[] { i1, i2, i3 });

        logger.debug("Hello world 2", e);
        logger.debug(e, s2);
        logger.info("Hello world 2.");
        logger.info(() -> "Hello world supplier 2.");
        logger.warn(() -> "Hello world supplier 3.");
        logger.warn(e, () -> "Hello world supplier 3.");

        logger.warn("Hello world 3.");
        logger.warn("Hello world 3", e);

        logger.error("Hello world 4.");
        logger.error("Hello world {}", new Integer(3));
        logger.error("Hello world 4.", e);
        logger.error(() -> "Hello world supplier 4.");
        logger.error(e, () -> "Hello world supplier 4.");
    }

    @Test
    public void testMsgNull() {
        Logger logger = LoggerFactory.getLogger("testNull");
        logger.debug((String)null);
        logger.info((String)null);
        logger.warn((String)null);
        logger.error((String)null);

        Exception e = new Exception("This is a test exception.");
        logger.debug(null, e);
        logger.info(null, e);
        logger.warn(null, e);
        logger.error(null, e);
    }

    @Test
    public void testMsgSupNull() {
        Logger logger = LoggerFactory.getLogger("testNull");
        logger.debug((Supplier<String>)null);
        logger.info((Supplier<String>)null);
        logger.warn((Supplier<String>)null);
        logger.error((Supplier<String>)null);

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
    public void testMarkerSup() {
        Logger logger = LoggerFactory.getLogger("testMarker");
        Marker blue = MarkerFactory.getMarker("BLUE");
        logger.debug(blue, () -> "hello");
        logger.info(blue, () -> "hello");
        logger.warn(blue, () -> "hello");
        logger.error(blue, () -> "hello");
    }

    @Test
    public void testMDC() {
        MDC.put("k", "v");
        assertNull(MDC.get("k"));
        MDC.remove("k");
        assertNull(MDC.get("k"));
        MDC.clear();
    }

    @Test
    public void testMDCCloseable() {
        MDC.MDCCloseable closeable = MDC.putCloseable("k", "v");
        assertNull(MDC.get("k"));
        closeable.close();
        assertNull(MDC.get("k"));
        MDC.clear();
    }
}
