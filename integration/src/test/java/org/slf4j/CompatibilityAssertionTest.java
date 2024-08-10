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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.slf4j.helpers.Reporter.SLF4J_INTERNAL_VERBOSITY_KEY;

import java.io.PrintStream;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompatibilityAssertionTest {

    StringPrintStream sps = new StringPrintStream(System.err);
    PrintStream old = System.err;
    int diff = 1024 + new Random().nextInt(10000);

    @Before
    public void setUp() throws Exception {
        System.setProperty(SLF4J_INTERNAL_VERBOSITY_KEY, "debug");
        System.setErr(sps);
    }

    @After
    public void tearDown() throws Exception {
        System.clearProperty(SLF4J_INTERNAL_VERBOSITY_KEY);
        System.setErr(old);
    }

    @Test
    public void test() throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String msg = "hello world " + diff;
        logger.info(msg);
        assertEquals(2, sps.stringList.size());

        String s0 = (String) sps.stringList.get(0);
        assertTrue(s0.startsWith("SLF4J(D): Connected with provider of type [org.slf4j.simple.SimpleServiceProvider]"));

        String s1 = (String) sps.stringList.get(1);
        assertTrue(s1.contains(msg));

    }
}
