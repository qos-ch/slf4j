package org.slf4j.helpers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BasicMDCAdapterTest extends MDCAdapterTestBase {

    @Test
    public void testClearingMDC() {
        mdc.put("testKey", "testValue");
        assertFalse(mdc.getCopyOfContextMap().isEmpty());
        mdc.clear();
        assertNull(mdc.getCopyOfContextMap());
    }
}
