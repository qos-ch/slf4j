package org.slf4j.dummyExt;

import junit.framework.TestCase;

import org.slf4j.MDC;
import org.slf4j.ext.MDCStrLookup;

public class MDCStrLookupTest extends TestCase {


    public MDCStrLookupTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLookup() throws Exception {
        MDC.put("key", "value");
        MDC.put("number", "2");
        MDCStrLookup lookup = new MDCStrLookup();
        assertEquals("value", lookup.lookup("key"));
        assertEquals("2", lookup.lookup("number"));
        assertEquals(null, lookup.lookup(null));
        assertEquals(null, lookup.lookup(""));
        assertEquals(null, lookup.lookup("other"));
    }
}
