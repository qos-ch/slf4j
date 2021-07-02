package org.slf4j.log4j12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.helpers.MDCAdapterTestBase;
import org.slf4j.spi.MDCAdapter;

public class Log4jMDCAdapterTest extends MDCAdapterTestBase {
    
    protected MDCAdapter instantiateMDC() {
        return new Log4jMDCAdapter();
    }
    
    
    @Test
    public void testClearingMDC() {
        mdc.put("testKey", "testValue");
        assertFalse(mdc.getCopyOfContextMap().isEmpty());
        mdc.clear();
        assertTrue(mdc.getCopyOfContextMap().isEmpty());
    }

    

}
