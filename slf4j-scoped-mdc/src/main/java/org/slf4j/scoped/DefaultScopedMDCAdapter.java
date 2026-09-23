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
import java.util.HashMap;
import java.util.Map;

import org.slf4j.scoped.spi.ScopedMDCAdapter;

/**
 * {@link ScopedValue} implementation of {@link ScopedMDCAdapter}.
 *
 * <p>All instances share one scoped slot. Values are inherited by tasks
 * forked from {@link java.util.concurrent.StructuredTaskScope} and are not
 * inherited by threads started with the {@link Thread} API.
 *
 * <p>This class is not a {@link java.util.ServiceLoader} provider. It is used
 * when no provider is found.
 *
 * @since 3.0.0
 */
public final class DefaultScopedMDCAdapter implements ScopedMDCAdapter {

    private static final ScopedValue<Map<String, String>> SCOPED_MDC = ScopedValue.newInstance();

    @Override
    public Map<String, String> getPropertyMap() {
        return SCOPED_MDC.orElse(Collections.emptyMap());
    }

    @Override
    public Binding put(String key, String value) {
        Map<String, String> merged = copyOfCurrent();
        merged.put(key, value);
        return new DefaultBinding(merged);
    }

    @Override
    public Binding putAll(Map<String, String> entries) {
        Map<String, String> merged = copyOfCurrent();
        merged.putAll(entries);
        return new DefaultBinding(merged);
    }

    private static Map<String, String> copyOfCurrent() {
        return new HashMap<>(SCOPED_MDC.orElse(Collections.emptyMap()));
    }

    private static final class DefaultBinding implements Binding {

        private final Map<String, String> map;

        DefaultBinding(Map<String, String> map) {
            this.map = Collections.unmodifiableMap(new HashMap<>(map));
        }

        @Override
        public Binding put(String key, String value) {
            Map<String, String> merged = new HashMap<>(map);
            merged.put(key, value);
            return new DefaultBinding(merged);
        }

        @Override
        public void run(Runnable op) {
            ScopedValue.where(SCOPED_MDC, map).run(op);
        }

        @Override
        public <R, X extends Throwable> R call(CallableOp<R, X> op) throws X {
            return ScopedValue.where(SCOPED_MDC, map).call(() -> op.call());
        }
    }
}
