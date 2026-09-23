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
 * Thread-local adapter used to prove that {@link ScopedMDC} delegates to a
 * provider selected by the system property. Not registered as a service.
 */
public class RecordingScopedMDCAdapter implements ScopedMDCAdapter {

    private final ThreadLocal<Map<String, String>> current = new ThreadLocal<>();

    @Override
    public Map<String, String> getPropertyMap() {
        Map<String, String> map = current.get();
        if (map == null) {
            return Collections.emptyMap();
        }
        return map;
    }

    @Override
    public Binding put(String key, String value) {
        Map<String, String> merged = new HashMap<>(getPropertyMap());
        merged.put(key, value);
        return new RecordingBinding(merged);
    }

    @Override
    public Binding putAll(Map<String, String> entries) {
        Map<String, String> merged = new HashMap<>(getPropertyMap());
        merged.putAll(entries);
        return new RecordingBinding(merged);
    }

    private final class RecordingBinding implements Binding {

        private final Map<String, String> map;

        RecordingBinding(Map<String, String> map) {
            this.map = Collections.unmodifiableMap(new HashMap<>(map));
        }

        @Override
        public Binding put(String key, String value) {
            Map<String, String> merged = new HashMap<>(map);
            merged.put(key, value);
            return new RecordingBinding(merged);
        }

        @Override
        public void run(Runnable op) {
            Map<String, String> previous = current.get();
            current.set(map);
            try {
                op.run();
            } finally {
                restore(previous);
            }
        }

        @Override
        public <R, X extends Throwable> R call(CallableOp<R, X> op) throws X {
            Map<String, String> previous = current.get();
            current.set(map);
            try {
                return op.call();
            } finally {
                restore(previous);
            }
        }
    }

    private void restore(Map<String, String> previous) {
        if (previous == null) {
            current.remove();
        } else {
            current.set(previous);
        }
    }
}
