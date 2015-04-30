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

import java.io.PrintStream;
import java.util.Random;

import junit.framework.TestCase;

public class CompatibilityAssertionTest extends TestCase {

    StringPrintStream sps = new StringPrintStream(System.err);
    PrintStream old = System.err;
    int diff = 1024 + new Random().nextInt(10000);

    public CompatibilityAssertionTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        System.setErr(sps);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        System.setErr(old);
    }

    public void test() throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String msg = "hello world " + diff;
        logger.info(msg);
        assertEquals(1, sps.stringList.size());
        String s0 = (String) sps.stringList.get(0);
        assertTrue(s0.contains(msg));
    }
}
