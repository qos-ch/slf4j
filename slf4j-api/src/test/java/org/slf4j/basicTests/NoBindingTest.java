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
package org.slf4j.basicTests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.BasicMarker;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.helpers.Reporter;

public class NoBindingTest {

    int diff = new Random().nextInt(10000);

    @Before
    public void setUp() throws Exception {
        System.setProperty(Reporter.SLF4J_INTERNAL_VERBOSITY_KEY, "debug");
    }
    @After
    public void tearDown() throws Exception {
        System.clearProperty(Reporter.SLF4J_INTERNAL_VERBOSITY_KEY);
    }

    @Test
    public void testLogger() {
        Logger logger = LoggerFactory.getLogger(NoBindingTest.class);
        logger.debug("hello" + diff);
        assertTrue(logger instanceof NOPLogger);
    }

    @Test
    public void testMDC() {
        MDC.put("k" + diff, "v");
        assertNull(MDC.get("k"));
    }

    @Test
    public void testMarker() {
        Marker m = MarkerFactory.getMarker("a" + diff);
        assertTrue(m instanceof BasicMarker);
    }
}
