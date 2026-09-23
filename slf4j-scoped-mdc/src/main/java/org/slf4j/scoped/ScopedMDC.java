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

import java.util.Collections;
import java.util.Map;

import org.slf4j.scoped.spi.ScopedMDCAdapter;
import org.slf4j.scoped.spi.ScopedMDCAdapter.Binding;

/**
 * <p>A scoped diagnostic context for virtual threads and structured concurrency.
 * </p>
 *
 * <p>The built-in provider stores the context in a {@link java.lang.ScopedValue}.
 * Unlike {@link org.slf4j.MDC}, values are inherited by tasks forked from
 * {@link java.util.concurrent.StructuredTaskScope} and are not inherited by
 * threads created with the {@link Thread} API. Nested scopes inherit entries
 * from the enclosing scope and may override them. The enclosing scope is
 * restored when the nested scope exits, including when the nested operation
 * throws.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>
 * ScopedMDC.put("requestId", "abc-123")
 *          .put("userId", "user-42")
 *          .run(() -&gt; {
 *              logger.info("Processing request");
 *          });
 * </pre>
 *
 * <p>The storage behind this class is a {@link ScopedMDCAdapter} loaded with
 * {@link java.util.ServiceLoader}. A logging backend can supply its own
 * provider by listing the implementation in
 * {@code META-INF/services/org.slf4j.scoped.spi.ScopedMDCAdapter}.
 * There is no dependency on a logging implementation. The built-in
 * {@link DefaultScopedMDCAdapter} is used when no provider is found.
 *
 * <p>Set the {@value #ADAPTER_PROPERTY_KEY} system property to a provider
 * class name to select that provider and skip {@code ServiceLoader}.
 * The class must be public and must have a public no-argument constructor.
 *
 * <p>Backends that need to read the context, for example a pattern converter,
 * must call {@link #get(String)} or {@link #getPropertyMap()}. The built-in
 * scoped slot is not part of the API.
 *
 * @since 3.0.0
 */
public final class ScopedMDC {

    /**
     * System property for naming the {@link ScopedMDCAdapter} class to use.
     * When set, service loading is skipped.
     *
     * @since 3.0.0
     */
    public static final String ADAPTER_PROPERTY_KEY = ScopedMDCAdapterLoader.ADAPTER_PROPERTY_KEY;

    private ScopedMDC() {
    }

    /**
     * Returns the adapter currently in use, loading it on the first call.
     *
     * @return the adapter, never {@code null}
     */
    public static ScopedMDCAdapter getAdapter() {
        return ScopedMDCAdapterLoader.getAdapter();
    }

    /**
     * Returns the value associated with {@code key} in the current scope,
     * or {@code null} if the key is absent or no scope is bound.
     *
     * @param key the key to look up
     * @return the value, or {@code null}
     */
    public static String get(String key) {
        return getPropertyMap().get(key);
    }

    /**
     * Returns the current scoped map. The result is empty when no scope is
     * bound, and is never {@code null}. The built-in provider returns an
     * unmodifiable map.
     *
     * @return the current scoped map, never {@code null}
     */
    public static Map<String, String> getPropertyMap() {
        Map<String, String> map = getAdapter().getPropertyMap();
        if (map == null) {
            return Collections.emptyMap();
        }
        return map;
    }

    /**
     * Returns a binding that adds {@code key} and {@code value} to a copy of
     * the current scope. The current scope is not modified. Further keys are
     * added with {@link Binding#put(String, String)}.
     *
     * @param key the key
     * @param value the value
     * @return a binding that runs code in the new scope
     */
    public static Binding put(String key, String value) {
        return getAdapter().put(key, value);
    }

    /**
     * Returns a binding that adds every entry in {@code entries} to a copy of
     * the current scope. Entries in {@code entries} override entries already
     * in scope. The current scope is not modified.
     *
     * @param entries the entries to add
     * @return a binding that runs code in the new scope
     */
    public static Binding putAll(Map<String, String> entries) {
        return getAdapter().putAll(entries);
    }
}
