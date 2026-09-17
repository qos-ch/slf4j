/*
 *  Copyright (C) 2004-2026, QOS.ch
 *  All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CallerDataTest {

    @Test
    public void testBasic() {
        Throwable t = new Throwable();
        StackTraceElement[] steArray = t.getStackTrace();

        StackTraceElement[] cda = CallerData.extract(t, CallerDataTest.class.getName(), 100, null);
        assertNotNull(cda);
        assertTrue(cda.length > 0);
        assertEquals(steArray.length - 1, cda.length);
    }

    /**
     * This test verifies that in case caller data cannot be extracted,
     * CallerData.extract does not throw an exception.
     */
    @Test
    public void testDeferredProcessing() {
        StackTraceElement[] cda = CallerData.extract(new Throwable(), "com.inexistent.foo", 10, null);
        assertNotNull(cda);
        assertEquals(0, cda.length);
    }

    @Test
    public void testNullThrowable() {
        assertNull(CallerData.extract(null, CallerDataTest.class.getName(), 10, null));
    }

    @Test
    public void testMaxDepth() {
        Throwable t = new Throwable();
        StackTraceElement[] cda = CallerData.extract(t, CallerDataTest.class.getName(), 1, null);
        assertEquals(1, cda.length);
    }

    @Test
    public void naInstanceHasNAValues() {
        StackTraceElement na = CallerData.naInstance();
        assertEquals(CallerData.NA, na.getClassName());
        assertEquals(CallerData.NA, na.getMethodName());
        assertEquals(CallerData.NA, na.getFileName());
        assertEquals(CallerData.LINE_NA, na.getLineNumber());
    }
}
