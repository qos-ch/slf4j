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

import java.lang.ScopedValue.CallableOp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A scoped diagnostic context for virtual threads and structured concurrency.
 *
 * <p>Values are stored in a {@link ScopedValue}. Unlike {@code org.slf4j.MDC},
 * they are inherited by tasks forked from
 * {@link java.util.concurrent.StructuredTaskScope} and are not inherited by
 * threads created with the {@link Thread} API. Nested scopes inherit entries
 * from the enclosing scope and may override them. The enclosing scope is
 * restored when the nested scope exits, including when the nested operation
 * throws.
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
 * @since 3.0.0
 */
public final class ScopedMDC {

    private static final ScopedValue<Map<String, String>> SCOPED_MDC = ScopedValue.newInstance();

    private ScopedMDC() {
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
     * Returns an unmodifiable view of the current scope. The result is empty
     * when no scope is bound, and is never {@code null}.
     *
     * @return the current scoped map, never {@code null}
     */
    public static Map<String, String> getPropertyMap() {
        return SCOPED_MDC.orElse(Collections.emptyMap());
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
        Map<String, String> merged = new HashMap<>(getPropertyMap());
        merged.put(key, value);
        return new Binding(merged);
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
        Map<String, String> merged = new HashMap<>(getPropertyMap());
        merged.putAll(entries);
        return new Binding(merged);
    }

    /**
     * A scope that can be entered with {@link #run(Runnable)} or
     * {@link #call(CallableOp)}. Further entries can be added with
     * {@link #put(String, String)} before the scope is entered. A binding
     * does not modify the scope that created it.
     */
    public static final class Binding {

        private final Map<String, String> map;

        Binding(Map<String, String> map) {
            this.map = Collections.unmodifiableMap(new HashMap<>(map));
        }

        /**
         * Returns a new binding with {@code key} and {@code value} added.
         * This binding is unchanged.
         *
         * @param key the key
         * @param value the value
         * @return a new binding
         */
        public Binding put(String key, String value) {
            Map<String, String> merged = new HashMap<>(map);
            merged.put(key, value);
            return new Binding(merged);
        }

        /**
         * Runs {@code op} in this scope.
         *
         * @param op the operation to run
         */
        public void run(Runnable op) {
            ScopedValue.where(SCOPED_MDC, map).run(op);
        }

        /**
         * Calls {@code op} in this scope and returns its result.
         *
         * @param op the operation to call
         * @param <R> the result type
         * @param <X> the exception type
         * @return the result of {@code op}
         * @throws X if {@code op} throws
         */
        public <R, X extends Throwable> R call(CallableOp<R, X> op) throws X {
            return ScopedValue.where(SCOPED_MDC, map).call(op);
        }
    }
}
