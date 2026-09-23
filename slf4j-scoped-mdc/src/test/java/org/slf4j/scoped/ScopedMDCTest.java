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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.slf4j.scoped.spi.ScopedMDCAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ScopedMDCTest {

    @Test
    public void noServiceLoaderProviderUsesTheBuiltInAdapter() {
        List<ScopedMDCAdapter> found = new ArrayList<>();
        ServiceLoader.load(ScopedMDCAdapter.class).forEach(found::add);
        assertTrue(found.isEmpty());
        assertTrue(ScopedMDC.getAdapter() instanceof DefaultScopedMDCAdapter);
    }

    @Test
    public void unboundReturnsNullAndEmptyMap() {
        assertNull(ScopedMDC.get("any"));
        assertTrue(ScopedMDC.getPropertyMap().isEmpty());
    }

    @Test
    public void putAndGetWithinScope() {
        ScopedMDC.put("key1", "value1").run(() -> {
            assertEquals("value1", ScopedMDC.get("key1"));
            assertNull(ScopedMDC.get("missing"));
        });
    }

    @Test
    public void getPropertyMapReturnsAllEntries() {
        ScopedMDC.put("a", "1").put("b", "2").run(() -> {
            Map<String, String> map = ScopedMDC.getPropertyMap();
            assertEquals(2, map.size());
            assertEquals("1", map.get("a"));
            assertEquals("2", map.get("b"));
        });
    }

    @Test
    public void nestedScopeInheritsParentValues() {
        ScopedMDC.put("parent", "pval").run(() -> {
            ScopedMDC.put("child", "cval").run(() -> {
                assertEquals("pval", ScopedMDC.get("parent"));
                assertEquals("cval", ScopedMDC.get("child"));
            });
        });
    }

    @Test
    public void nestedScopeCanOverrideParentValue() {
        ScopedMDC.put("key", "original").run(() -> {
            assertEquals("original", ScopedMDC.get("key"));

            ScopedMDC.put("key", "overridden").run(() -> {
                assertEquals("overridden", ScopedMDC.get("key"));
            });

            assertEquals("original", ScopedMDC.get("key"));
        });
    }

    @Test
    public void parentScopeUnaffectedAfterNestedScopeExits() {
        ScopedMDC.put("parent", "pval").run(() -> {
            ScopedMDC.put("child", "cval").run(() -> {
                // child scope active
            });
            assertNull(ScopedMDC.get("child"));
            assertEquals("pval", ScopedMDC.get("parent"));
        });
    }

    @Test
    public void putChainingOnBinding() {
        ScopedMDC.put("a", "1")
                 .put("b", "2")
                 .put("c", "3")
                 .run(() -> {
                     assertEquals("1", ScopedMDC.get("a"));
                     assertEquals("2", ScopedMDC.get("b"));
                     assertEquals("3", ScopedMDC.get("c"));
                 });
    }

    @Test
    public void putAllMergesWithCurrentScope() {
        ScopedMDC.put("existing", "val").run(() -> {
            ScopedMDC.putAll(Map.of("new1", "v1", "new2", "v2")).run(() -> {
                assertEquals("val", ScopedMDC.get("existing"));
                assertEquals("v1", ScopedMDC.get("new1"));
                assertEquals("v2", ScopedMDC.get("new2"));
            });
        });
    }

    @Test
    public void putAllOverridesCurrentScope() {
        ScopedMDC.put("key", "old").run(() -> {
            ScopedMDC.putAll(Map.of("key", "new")).run(() -> {
                assertEquals("new", ScopedMDC.get("key"));
            });
            assertEquals("old", ScopedMDC.get("key"));
        });
    }

    @Test
    public void callReturnsValue() throws Exception {
        String result = ScopedMDC.put("key", "value").call(() -> {
            assertEquals("value", ScopedMDC.get("key"));
            return "result";
        });
        assertEquals("result", result);
    }

    @Test
    public void callPropagatesException() {
        assertThrows(IllegalStateException.class, () ->
            ScopedMDC.put("key", "value").call(() -> {
                throw new IllegalStateException("test");
            })
        );
    }

    @Test
    public void callPropagatesCheckedException() {
        IOException thrown = assertThrows(IOException.class, () ->
            ScopedMDC.put("key", "value").call(() -> {
                throw new IOException("test");
            })
        );
        assertEquals("test", thrown.getMessage());
        assertNull(ScopedMDC.get("key"));
    }

    @Test
    public void scopeIsClearedWhenRunThrows() {
        assertThrows(IllegalStateException.class, () ->
            ScopedMDC.put("key", "value").run(() -> {
                assertEquals("value", ScopedMDC.get("key"));
                throw new IllegalStateException("boom");
            })
        );
        assertNull(ScopedMDC.get("key"));
        assertTrue(ScopedMDC.getPropertyMap().isEmpty());
    }

    @Test
    public void scopedValuesNotVisibleOutsideScope() {
        ScopedMDC.put("key", "value").run(() -> {
            assertEquals("value", ScopedMDC.get("key"));
        });
        assertNull(ScopedMDC.get("key"));
        assertTrue(ScopedMDC.getPropertyMap().isEmpty());
    }

    @Test
    public void propertyMapIsUnmodifiable() {
        ScopedMDC.put("key", "value").run(() -> {
            Map<String, String> map = ScopedMDC.getPropertyMap();
            assertThrows(UnsupportedOperationException.class, () -> map.put("new", "val"));
        });
    }

    @Test
    public void bindingCanBeEnteredMoreThanOnce() {
        ScopedMDCAdapter.Binding binding = ScopedMDC.put("key", "value");
        binding.run(() -> assertEquals("value", ScopedMDC.get("key")));
        assertNull(ScopedMDC.get("key"));
        binding.run(() -> assertEquals("value", ScopedMDC.get("key")));
        assertNull(ScopedMDC.get("key"));
    }

    @Test
    public void plainChildThreadDoesNotSeeScopedValues() throws Exception {
        AtomicReference<String> captured = new AtomicReference<>("unset");
        ScopedMDC.put("requestId", "abc-123").run(() -> {
            Thread thread = new Thread(() -> captured.set(ScopedMDC.get("requestId")));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        });
        assertNull(captured.get());
    }

    @Test
    public void childThreadInheritsScopedValuesViaStructuredTaskScope() throws Exception {
        AtomicReference<String> captured = new AtomicReference<>();

        ScopedMDC.put("requestId", "abc-123").run(() -> {
            try {
                // StructuredTaskScope is still a preview API. Calling it by
                // reflection keeps this class file free of the preview flag,
                // which surefire cannot load in the Maven JVM. The forked
                // test JVM is started with --enable-preview.
                forkStructuredTask(() -> {
                    captured.set(ScopedMDC.get("requestId"));
                    return null;
                });
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        assertEquals("abc-123", captured.get());
    }

    private static void forkStructuredTask(Callable<Void> task) throws Exception {
        Class<?> scopeClass = Class.forName("java.util.concurrent.StructuredTaskScope");
        Object scope = scopeClass.getMethod("open").invoke(null);
        try {
            scopeClass.getMethod("fork", Callable.class).invoke(scope, task);
            scopeClass.getMethod("join").invoke(scope);
        } finally {
            ((AutoCloseable) scope).close();
        }
    }

    @Test
    public void getAdapterReturnsTheSameInstance() {
        assertSame(ScopedMDC.getAdapter(), ScopedMDC.getAdapter());
    }
}
