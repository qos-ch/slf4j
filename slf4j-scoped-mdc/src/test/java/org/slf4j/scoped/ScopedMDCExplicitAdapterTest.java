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
package org.slf4j.scoped;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Runs against a provider named by {@link ScopedMDC#ADAPTER_PROPERTY_KEY},
 * so the facade's merge and restore behavior is not tied to {@code ScopedValue}.
 */
public class ScopedMDCExplicitAdapterTest {

    private String previous;

    @Before
    public void installExplicitAdapter() {
        previous = System.getProperty(ScopedMDC.ADAPTER_PROPERTY_KEY);
        System.setProperty(ScopedMDC.ADAPTER_PROPERTY_KEY, RecordingScopedMDCAdapter.class.getName());
        ScopedMDCAdapterLoader.reset();
    }

    @After
    public void restoreProperty() {
        if (previous == null) {
            System.clearProperty(ScopedMDC.ADAPTER_PROPERTY_KEY);
        } else {
            System.setProperty(ScopedMDC.ADAPTER_PROPERTY_KEY, previous);
        }
        ScopedMDCAdapterLoader.reset();
    }

    @Test
    public void facadeUsesTheNamedAdapter() {
        assertTrue(ScopedMDC.getAdapter() instanceof RecordingScopedMDCAdapter);

        ScopedMDC.put("parent", "pval").run(() -> {
            assertEquals("pval", ScopedMDC.get("parent"));
            ScopedMDC.put("child", "cval").put("parent", "overridden").run(() -> {
                assertEquals("overridden", ScopedMDC.get("parent"));
                assertEquals("cval", ScopedMDC.get("child"));
                Map<String, String> map = ScopedMDC.getPropertyMap();
                assertEquals(2, map.size());
            });
            assertEquals("pval", ScopedMDC.get("parent"));
            assertNull(ScopedMDC.get("child"));
        });

        assertNull(ScopedMDC.get("parent"));
        assertTrue(ScopedMDC.getPropertyMap().isEmpty());
    }

    @Test
    public void callOnTheNamedAdapterReturnsAndRestores() throws Exception {
        String result = ScopedMDC.put("key", "value").call(() -> {
            assertEquals("value", ScopedMDC.get("key"));
            return "result";
        });
        assertEquals("result", result);
        assertNull(ScopedMDC.get("key"));
    }
}
