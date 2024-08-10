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
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class MultiBindingAssertionTest {

    StringPrintStream sps = new StringPrintStream(System.err);
    PrintStream old = System.err;
    int diff = 1024 + new Random().nextInt(10000);

    @Before
    public void setUp() throws Exception {
        System.setErr(sps);
    }

    @After
    public void tearDown() throws Exception {
        System.setErr(old);
    }

    @Test
    public void test() throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String msg = "hello world " + diff;
        logger.info(msg);
        List<String> list = sps.stringList;
        int line = 0;

        assertMsgContains(list, line++, "Class path contains multiple SLF4J providers.");
        assertMsgContains(list, line++, "Found provider");
        assertMsgContains(list, line++, "Found provider");
        assertMsgContains(list, line++, "See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.");
        //assertMsgContains(list, line++, "SLF4J(D): Connected with provider of type [");
    }

    void assertMsgContains(List<String> strList, int index, String msg) {
        assertTrue(((String) strList.get(index)).contains(msg));
    }
}
