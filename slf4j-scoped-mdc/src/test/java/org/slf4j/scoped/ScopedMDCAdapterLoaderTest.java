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

import java.util.List;

import org.junit.Test;
import org.slf4j.scoped.spi.ScopedMDCAdapter;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ScopedMDCAdapterLoaderTest {

    @Test
    public void builtInAdapterIsUsedWhenItIsTheOnlyProvider() {
        ScopedMDCAdapter builtIn = new DefaultScopedMDCAdapter();
        assertSame(builtIn, ScopedMDCAdapterLoader.select(List.of(builtIn)));
    }

    @Test
    public void nonDefaultProviderIsPreferredOverTheBuiltInAdapter() {
        ScopedMDCAdapter custom = new RecordingScopedMDCAdapter();
        ScopedMDCAdapter selected = ScopedMDCAdapterLoader.select(List.of(new DefaultScopedMDCAdapter(), custom));
        assertSame(custom, selected);
    }

    @Test
    public void builtInAdapterIsIgnoredWhereverItAppears() {
        ScopedMDCAdapter custom = new RecordingScopedMDCAdapter();
        ScopedMDCAdapter selected = ScopedMDCAdapterLoader.select(List.of(custom, new DefaultScopedMDCAdapter()));
        assertSame(custom, selected);
    }

    @Test
    public void firstNonDefaultProviderWins() {
        ScopedMDCAdapter first = new RecordingScopedMDCAdapter();
        ScopedMDCAdapter second = new RecordingScopedMDCAdapter();
        ScopedMDCAdapter selected = ScopedMDCAdapterLoader.select(List.of(new DefaultScopedMDCAdapter(), first, second));
        assertSame(first, selected);
    }

    @Test
    public void emptyProviderListFallsBackToTheBuiltInAdapter() {
        assertTrue(ScopedMDCAdapterLoader.select(List.of()) instanceof DefaultScopedMDCAdapter);
    }

    @Test
    public void loadWithoutSystemPropertyReturnsTheBuiltInAdapter() {
        String previous = System.getProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY);
        System.clearProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY);
        try {
            assertTrue(ScopedMDCAdapterLoader.load() instanceof DefaultScopedMDCAdapter);
        } finally {
            restore(previous);
        }
    }

    @Test
    public void loadHonorsSystemProperty() {
        String previous = System.getProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY);
        System.setProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY, RecordingScopedMDCAdapter.class.getName());
        try {
            assertTrue(ScopedMDCAdapterLoader.load() instanceof RecordingScopedMDCAdapter);
        } finally {
            restore(previous);
        }
    }

    @Test
    public void unknownExplicitAdapterFails() {
        String previous = System.getProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY);
        System.setProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY, "org.slf4j.scoped.DoesNotExist");
        try {
            IllegalStateException thrown = assertThrows(IllegalStateException.class, ScopedMDCAdapterLoader::load);
            assertTrue(thrown.getCause() instanceof ClassNotFoundException);
        } finally {
            restore(previous);
        }
    }

    @Test
    public void explicitClassThatIsNotAnAdapterFails() {
        String previous = System.getProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY);
        System.setProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY, String.class.getName());
        try {
            assertThrows(IllegalStateException.class, ScopedMDCAdapterLoader::load);
        } finally {
            restore(previous);
        }
    }

    private static void restore(String previous) {
        if (previous == null) {
            System.clearProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY);
        } else {
            System.setProperty(ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY, previous);
        }
    }
}
