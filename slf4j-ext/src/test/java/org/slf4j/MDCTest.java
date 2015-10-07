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

import junit.framework.TestCase;

import java.util.HashMap;

public class MDCTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
        MDC.clear();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
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
        MDC.put("k", "v");
        assertEquals("v", MDC.get("k"));

        MDC.setContextMap(new HashMap<String, String>() {{
            put("ka", "va");
            put("kb", "vb");
        }});
        assertNull(MDC.get("k"));

        assertEquals("va", MDC.get("ka"));
        assertEquals("vb", MDC.get("kb"));
    }

    public void testMDCCloseable() {
        MDC.remove("someKey");
        assertNull("before closeables", MDC.get("someKey"));

        MDC.MDCCloseable mdcCloseable1 = MDC.putCloseable("someKey", "someValue");
        assertEquals("during closeable1", "someValue", MDC.get("someKey"));

        MDC.MDCCloseable mdcCloseable2 = MDC.putCloseable("someKey", "someOtherValue");
        assertEquals("during closeable2", "someOtherValue", MDC.get("someKey"));

        mdcCloseable2.close();
        assertEquals("after closeable2", "someValue", MDC.get("someKey"));

        mdcCloseable1.close();
        assertNull("after closeable1", MDC.get("someKey"));
    }

}
