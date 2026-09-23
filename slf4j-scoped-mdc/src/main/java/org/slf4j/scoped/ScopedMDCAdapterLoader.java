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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.helpers.Reporter;
import org.slf4j.scoped.spi.ScopedMDCAdapter;

/**
 * Locates the {@link ScopedMDCAdapter} used by {@link ScopedMDC}.
 */
final class ScopedMDCAdapterLoader {

    /**
     * System property naming a {@link ScopedMDCAdapter} implementation.
     * When set, {@link ServiceLoader} is not consulted.
     */
    static final String ADAPTER_PROPERTY_KEY = "slf4j.scopedMDCAdapter";

    private static final AtomicReference<ScopedMDCAdapter> ADAPTER = new AtomicReference<>();

    private ScopedMDCAdapterLoader() {
    }

    static ScopedMDCAdapter getAdapter() {
        ScopedMDCAdapter current = ADAPTER.get();
        if (current == null) {
            ScopedMDCAdapter loaded = load();
            if (ADAPTER.compareAndSet(null, loaded)) {
                Reporter.info("ScopedMDC bound to adapter [" + loaded.getClass().getName() + "]");
                current = loaded;
            } else {
                current = ADAPTER.get();
            }
        }
        return current;
    }

    /**
     * Drops the cached adapter so the next call to {@link #getAdapter()}
     * loads it again. For tests.
     */
    static void reset() {
        ADAPTER.set(null);
    }

    static ScopedMDCAdapter load() {
        ClassLoader classLoader = ScopedMDCAdapterLoader.class.getClassLoader();
        String explicit = System.getProperty(ADAPTER_PROPERTY_KEY);
        if (explicit != null && !explicit.isBlank()) {
            return instantiate(explicit.trim(), classLoader);
        }
        return select(loadFromServiceLoader(classLoader));
    }

    /**
     * Chooses one adapter from providers already instantiated.
     * Package-private so tests can cover the selection rules without
     * standing up extra jars.
     */
    static ScopedMDCAdapter select(List<ScopedMDCAdapter> found) {
        if (found == null || found.isEmpty()) {
            Reporter.warn("No ScopedMDCAdapter providers were found. Using " + DefaultScopedMDCAdapter.class.getName() + ".");
            return new DefaultScopedMDCAdapter();
        }

        List<ScopedMDCAdapter> preferred = new ArrayList<>();
        for (ScopedMDCAdapter adapter : found) {
            if (!(adapter instanceof DefaultScopedMDCAdapter)) {
                preferred.add(adapter);
            }
        }
        List<ScopedMDCAdapter> candidates = preferred.isEmpty() ? found : preferred;
        if (candidates.size() > 1) {
            Reporter.warn("Class path contains multiple ScopedMDCAdapter providers.");
            for (ScopedMDCAdapter adapter : candidates) {
                Reporter.warn("Found provider [" + adapter.getClass().getName() + "]");
            }
            Reporter.warn("Actual provider is of type [" + candidates.get(0).getClass().getName() + "]");
        }
        return candidates.get(0);
    }

    private static List<ScopedMDCAdapter> loadFromServiceLoader(ClassLoader classLoader) {
        List<ScopedMDCAdapter> found = new ArrayList<>();
        ServiceLoader<ScopedMDCAdapter> serviceLoader;
        if (classLoader == null) {
            serviceLoader = ServiceLoader.load(ScopedMDCAdapter.class);
        } else {
            serviceLoader = ServiceLoader.load(ScopedMDCAdapter.class, classLoader);
        }
        Iterator<ScopedMDCAdapter> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            try {
                found.add(iterator.next());
            } catch (ServiceConfigurationError e) {
                Reporter.error("A ScopedMDCAdapter provider failed to instantiate:\n" + e.getMessage());
            }
        }
        return found;
    }

    private static ScopedMDCAdapter instantiate(String className, ClassLoader classLoader) {
        try {
            ClassLoader loader = classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
            Class<?> clazz = Class.forName(className, true, loader);
            if (!ScopedMDCAdapter.class.isAssignableFrom(clazz)) {
                throw new IllegalStateException("Specified ScopedMDCAdapter (" + className + ") does not implement " + ScopedMDCAdapter.class.getName());
            }
            Constructor<?> constructor = clazz.getConstructor();
            return (ScopedMDCAdapter) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Failed to instantiate ScopedMDCAdapter \"" + className + "\" specified via \"" + ADAPTER_PROPERTY_KEY + "\" system property", e);
        }
    }
}
