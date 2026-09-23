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
package org.slf4j.scoped.spi;

import java.util.Map;

/**
 * Service-provider interface for {@link org.slf4j.scoped.ScopedMDC}.
 *
 * <p>Implementations are discovered with {@link java.util.ServiceLoader}.
 * List the implementation class in
 * {@code META-INF/services/org.slf4j.scoped.spi.ScopedMDCAdapter}.
 * The class must be public and must have a public no-argument constructor.
 *
 * <p>The built-in {@link org.slf4j.scoped.DefaultScopedMDCAdapter} is ignored
 * when any other provider is present. If several other providers are present,
 * the first one returned by {@code ServiceLoader} is used and a warning is
 * reported. Set the {@code slf4j.scopedMDCAdapter} system property to the
 * fully qualified class name of a provider to bypass {@code ServiceLoader}.
 *
 * <p>{@link #put(String, String)} and {@link #putAll(Map)} return a
 * {@link Binding}. Further keys are added with {@link Binding#put(String, String)}
 * before {@link Binding#run(Runnable)} or {@link Binding#call(CallableOp)}
 * enters the scope. {@code put} on the adapter copies the current scope and
 * adds to that copy. {@code put} on a binding adds to that binding only and
 * does not read the scope again.
 *
 * <p>While a binding runs, {@link #getPropertyMap()} must report that
 * binding's entries. When it returns or throws, the previous map must be
 * restored. Outside any scope {@link #getPropertyMap()} returns an empty
 * map, never {@code null}.
 *
 * <p>This interface was modeled on the logback-scoped-mdc project authored
 * by Filip Egeric.</p>
 *
 * @since 3.0.0
 */
public interface ScopedMDCAdapter {

    /**
     * Operation invoked by {@link Binding#call(CallableOp)}. Unlike
     * {@link java.util.concurrent.Callable}, it may throw a checked exception.
     *
     * @param <R> result type
     * @param <X> exception type thrown by {@link #call()}
     */
    @FunctionalInterface
    interface CallableOp<R, X extends Throwable> {

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return the computed result
         * @throws X if unable to compute a result
         */
        R call() throws X;
    }

    /**
     * A scope that has not been entered yet. Modelled on
     * {@code ch.qos.logback.classic.scoped.ScopedMDC.Binding}.
     *
     * <p>Each {@link #put(String, String)} returns a new binding. The
     * receiver is left unchanged, so one binding can be entered more than
     * once.
     */
    interface Binding {

        /**
         * Returns a new binding with {@code key} and {@code value} added.
         *
         * @param key the key
         * @param value the value
         * @return a new binding
         */
        Binding put(String key, String value);

        /**
         * Runs {@code op} with this binding installed as the current scope.
         *
         * @param op the operation to run
         */
        void run(Runnable op);

        /**
         * Calls {@code op} with this binding installed as the current scope.
         *
         * @param op the operation to call
         * @param <R> the result type
         * @param <X> the exception type
         * @return the result of {@code op}
         * @throws X if {@code op} throws
         */
        <R, X extends Throwable> R call(CallableOp<R, X> op) throws X;
    }

    /**
     * Returns the map bound in the current scope, or an empty map when no
     * scope is bound. The result is never {@code null}. Implementations
     * should return an unmodifiable map.
     *
     * @return the current scoped map, never {@code null}
     */
    Map<String, String> getPropertyMap();

    /**
     * Returns a binding that adds {@code key} and {@code value} to a copy of
     * the current scope. The current scope is not modified.
     *
     * @param key the key
     * @param value the value
     * @return a binding that runs code in the new scope
     */
    Binding put(String key, String value);

    /**
     * Returns a binding that adds every entry in {@code entries} to a copy of
     * the current scope. Entries in {@code entries} override entries already
     * in scope. The current scope is not modified.
     *
     * @param entries the entries to add
     * @return a binding that runs code in the new scope
     */
    Binding putAll(Map<String, String> entries);
}
