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

import java.io.PrintStream;
import java.util.Random;

import junit.framework.TestCase;

public class MissingSingletonMethodAssertionTest extends TestCase {

    StringPrintStream sps = new StringPrintStream(System.err);
    PrintStream old = System.err;
    int diff = 1024 + new Random().nextInt(10000);

    public MissingSingletonMethodAssertionTest(String name) {
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
        try {
            Logger logger = LoggerFactory.getLogger(this.getClass());
            String msg = "hello world " + diff;
            logger.info(msg);
            fail("NoSuchMethodError expected");
        } catch (NoSuchMethodError e) {
        }

        int lineCount = sps.stringList.size();
        assertTrue("number of lines should be 3 but was " + lineCount, lineCount == 3);

        // expected output:
        // SLF4J: slf4j-api 1.6.x (or later) is incompatible with this binding.
        // SLF4J: Your binding is version 1.4.x or earlier.
        // SLF4J: Upgrade your binding to version 1.6.x. or 2.0.x

        {
            String s = (String) sps.stringList.get(0);
            assertTrue(s.contains("SLF4J: slf4j-api 1.6.x (or later) is incompatible with this binding."));
        }
        {
            String s = (String) sps.stringList.get(1);
            assertTrue(s.contains("SLF4J: Your binding is version 1.5.5 or earlier."));
        }
        {
            String s = (String) sps.stringList.get(2);
            assertTrue(s.contains("SLF4J: Upgrade your binding to version 1.6.x."));
        }

    }
}
