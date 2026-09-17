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
