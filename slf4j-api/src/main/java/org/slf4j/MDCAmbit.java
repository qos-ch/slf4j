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

/**
 * This class assists in the creation and removal (aka closing) of MDC entries.
 *
 * <p>Typical Usage example:</p>
 *
 * <pre>
 *  MDCHelper mdch = new MDCHelper();
 *  try {
 *    mdch.put("k0", "v0");
 *    throw new RuntimeException();
 *  } catch (RuntimeException e) {
 *    // here MDC.get("k0") would return "v0"
 *  } finally {
 *    // MDC remove "k0"
 *    mdch.removeSet();
 *  }
 * </pre>
 *
 * <p>It is also possible to chain {@link #put} invocations. For example:</p>
 * <pre>
 *   MDCHelper mdch = new MDCHelper();
 *   try {
 *     // assume "k0" was added to MDC at an earlier stage
 *     mdch.addKey("k0").put("k1", "v1").put("k2, "v2");
 *   } finally {
 *     // MDC remove "k0", "k1", "k2"
 *     mdch.removeSet();
 *   }
 * </pre>
 * @since 2.0.10
 */
public class MDCHelper  {


    /**
     * Set of keys under management of this instance
     */
    Set<String> keySet = new HashSet();

    MDCAdapter mdcAdapter;

    public MDCHelper() {
        mdcAdapter = MDC.getMDCAdapter();
    }

    MDCHelper(MDCAdapter mdcAdapter) {
        this.mdcAdapter = mdcAdapter;
    }

    /**
     * Put the key/value couple in the MDC and keep track of the key for later
     *  removal by a call to {@link #removeSet()}.
     *
     * @param key
     * @param value
     * @return  this instance
     */
    public MDCHelper put(String key, String value) {
        mdcAdapter.put(key, value);
        keySet.add(key);
        return this;
    }

    /**
     * Keep track of a key for later removal by a call to {@link #removeSet()}.
     * @param key
     * @return this instance
     */
    public MDCHelper addKey(String key) {
        keySet.add(key);
        return this;
    }

    /**
     *  Keep track of several keys for later removal by a call to {@link #removeSet()}.
     * @param keys
     * @return this instance
     */
    public MDCHelper addKeys(String... keys) {
        if(keys == null)
            return this;

        for(String k: keys) {
            keySet.add(k);
        }
        return this;
    }


    /**
     * Remove tracked keys by calling {@link MDC#remove} on each key.
     *
     * <p>The set of tracked keys is cleared.</p>
     *
     * <p>This method is usually called from within finally statement of a
     * try/catch/finally block</p>
     *
     */
    public void removeSet() {
        for(String key: keySet) {
            mdcAdapter.remove(key);
        }
        keySet.clear();
    }
}
