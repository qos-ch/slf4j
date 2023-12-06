/**
 * Copyright (c) 2004-2023 QOS.ch
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j;

import org.junit.Test;
import org.slf4j.helpers.BasicMDCAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MDCAmbitTest {


    BasicMDCAdapter mdcAdapter = new BasicMDCAdapter();
    MDCAmbit mdca = new MDCAmbit(mdcAdapter);

    String k0 = "k0";
    String v0 = "v0";
    String k1 = "k1";
    String v1 = "v0";
    String k2 = "k2";
    String v2 = "v2";
    String kUnused = "kUnused";

    void throwRuntimeException() {
        throw new RuntimeException();
    }

    @Test
    public void smoke() {
        try {
            mdca.put(k0, v0);
            assertEquals(v0, mdcAdapter.get(k0));
            assertNull(mdcAdapter.get(kUnused));
            throwRuntimeException();
        } catch (RuntimeException e) {
            assertEquals(v0, mdcAdapter.get(k0));
        } finally {
            mdca.clear();
        }
        assertNull(mdcAdapter.get(k0));
    }

    @Test
    public void empty() {
        try {
            assertNull(mdcAdapter.get(kUnused));
        } finally {
            mdca.clear();
        }
        assertNull(mdcAdapter.get(k0));
    }

    @Test
    public void addKeyTest() {
        mdcAdapter.put(k0, v0);
        try {
            mdca.addKey(k0);
            assertEquals(v0, mdcAdapter.get(k0));
            assertNull(mdcAdapter.get(kUnused));
            throwRuntimeException();
        } catch (RuntimeException e) {
            assertEquals(v0, mdcAdapter.get(k0));
        } finally {
            mdca.clear();
        }
        assertNull(mdcAdapter.get(k0));
    }

    @Test
    public void combinedPut_addKeyTest() {
        mdcAdapter.put(k0, v0);
        try {
            mdca.addKey(k0).put(k1, v1).put(k2, v2);
            assertEquals(v0, mdcAdapter.get(k0));
            assertEquals(v1, mdcAdapter.get(k1));
            assertEquals(v2, mdcAdapter.get(k2));
            assertNull(mdcAdapter.get(kUnused));
            throwRuntimeException();
        } catch (RuntimeException e) {
            assertEquals(v0, mdcAdapter.get(k0));
            assertEquals(v1, mdcAdapter.get(k1));
            assertEquals(v2, mdcAdapter.get(k2));
        } finally {
            mdca.clear();
        }
        assertNull(mdcAdapter.get(k0));
        assertNull(mdcAdapter.get(k1));
        assertNull(mdcAdapter.get(k2));
    }


}
