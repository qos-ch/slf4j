package org.slf4j.reload4j;

import org.junit.Test;
import org.slf4j.helpers.MDCAdapterTestBase;
import org.slf4j.spi.MDCAdapter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Reload4jMDCAdapterTest extends MDCAdapterTestBase {
    
    protected MDCAdapter instantiateMDC() {
        return new Reload4jMDCAdapter();
    }
    
    
    @Test
    public void testClearingMDC() {
        mdc.put("testKey", "testValue");
        assertFalse(mdc.getCopyOfContextMap().isEmpty());
        mdc.clear();
        assertTrue(mdc.getCopyOfContextMap().isEmpty());
    }

    @Test
    public void testSetContextMap() {
        Map<String, String> map0 = new HashMap<>();
        map0.put("key0", "val0");

        mdc.setContextMap(map0);
        Map map1 = mdc.getCopyOfContextMap();

        assertEquals(map0, map1);
    }
    

}
