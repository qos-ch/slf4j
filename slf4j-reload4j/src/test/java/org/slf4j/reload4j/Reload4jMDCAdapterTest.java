/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
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

package org.slf4j.reload4j;

import org.junit.Test;
import org.slf4j.testing.MDCAdapterTestBase;
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
