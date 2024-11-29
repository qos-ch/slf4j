/**
 * Copyright (c) 2004-2023 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j;

import org.slf4j.spi.MDCAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * <p>This class assists in the creation and removal (aka closing) of {@link org.slf4j.MDC MDC} entries.</p>
 *
 * <p>Typical Usage example:</p>
 *
 * <pre>
 *  MDCAmbit mdca = new MDCAmbit();
 *  try {
 *    mdca.put("k0", "v0");
 *    doSomething();
 *  } catch (RuntimeException e) {
 *    // here MDC.get("k0") would return "v0"
 *  } finally {
 *    // MDC remove "k0"
 *    mdca.clear();
 *  }
 * </pre>
 *
 * <p>It is also possible to chain {@link #put}, {@link #addKeys(String...)} and {@link #addKey(String)}
 * invocations.</p>
 *
 * <p>For example:</p>
 * <pre>
 *   MDCAmbit mdca = new MDCAmbit();
 *   try {
 *     // assume "k0" was added to MDC at an earlier stage
 *     mdca.addKey("k0").put("k1", "v1").put("k2, "v2");
 *     doSomething();
 *   } finally {
 *     // MDC remove "k0", "k1", "k2", clear the set of tracked keys
 *     mdca.clear();
 *   }
 * </pre>
 *
 * <p>The {@link #run(Runnable)} and {@link #call(Callable)} methods invoke the run/callable methods of
 * objects passed as parameter in a <code>try/finally</code> block, and afterwards invoking {@link #clear()}
 * method from within <code>finally</code>.
 *
 * </p>
 *
 * <pre>
 *    MDCAmbit mdca = new MDCAmbit();
 *    Runnable runnable = ...;
 *    mdca.put("k0", "v0").run(runnable);
 * </pre>
 *
 * @since 2.1.0
 */
public class MDCAmbit {

    /**
     * Set of keys under management of this instance
     */
    Set<String> keySet = new HashSet();

    MDCAdapter mdcAdapter;

    public MDCAmbit() {
        mdcAdapter = MDC.getMDCAdapter();
    }

    MDCAmbit(MDCAdapter mdcAdapter) {
        this.mdcAdapter = mdcAdapter;
    }

    /**
     * Put the key/value couple in the MDC and keep track of the key for later
     * removal by a call to {@link #clear()} }.
     *
     * @param key
     * @param value
     * @return  this instance
     */
    public MDCAmbit put(String key, String value) {
        mdcAdapter.put(key, value);
        keySet.add(key);
        return this;
    }

    /**
     * Keep track of a key for later removal by a call to {@link #clear()}.
     * .
     * @param key
     * @return this instance
     */
    public MDCAmbit addKey(String key) {
        keySet.add(key);
        return this;
    }

    /**
     *  Keep track of several keys for later removal by a call to {@link #clear()} .
     * @param keys
     * @return this instance
     */
    public MDCAmbit addKeys(String... keys) {
        if(keys == null)
            return this;

        for(String k: keys) {
            keySet.add(k);
        }
        return this;
    }

    /**
     * Run the runnable object passed as parameter within a try/finally block.
     *
     *  <p>Afterwards, the {@link #clear()} method will be called within the `finally` block.
     *  </p>

     * @param runnable
     */
    public void run(Runnable runnable) {
        try {
            runnable.run();
        } finally {
            clear();
        }
    }

    /**
     * Invoke the {@link Callable#call()}  method of the callable object passed as parameter within a try/finally block.
     *
     * <p>Afterwards, the {@link #clear()} method will be invoked within the `finally` block.
     * </p>
     * @param callable
     */
    public <T> T call(Callable<? extends T> callable) throws Exception {
        try {
            return callable.call();
        } finally {
            clear();
        }
    }

    /**
     * Clear tracked keys by calling {@link MDC#remove} on each key.
     *
     * <p>In addition, the set of tracked keys is cleared.</p>
     *
     * <p>This method is usually called from within finally statement of a
     * try/catch/finally block</p>
     *
     */
    public void clear() {
        for(String key: keySet) {
            mdcAdapter.remove(key);
        }
        keySet.clear();
    }
}
