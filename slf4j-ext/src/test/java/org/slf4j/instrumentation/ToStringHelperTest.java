/**
 * Copyright (c) 2004-2011 QOS.ch
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
package org.slf4j.instrumentation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ToStringHelperTest {

    @Test
    public void testRenderer() {
        assertEquals("", "null", ToStringHelper.render(null));
        assertEquals("", "a", ToStringHelper.render("a"));
        assertEquals("", "[]", ToStringHelper.render(new String[0]));

        assertEquals("", "[a]", ToStringHelper.render(new String[] { "a" }));

        assertEquals("", "[a, b]", ToStringHelper.render(new String[] { "a", "b" }));

        assertEquals("", "[a, b, c]", ToStringHelper.render(new String[] { "a", "b", "c" }));

        assertEquals("", "[[a], [b, c]]", ToStringHelper.render(new String[][] { { "a" }, { "b", "c" } }));

        assertEquals("", "[0, [a], [b, c]]", ToStringHelper.render(new Object[] { "0", new String[] { "a" }, new Object[] { "b", "c" } }));

        assertEquals("", "[1]", ToStringHelper.render(new int[] { 1 }));

        assertEquals("", "[1, 2, 3]", ToStringHelper.render(new int[] { 1, 2, 3 }));

        assertEquals("", "[1, 2, 3]", ToStringHelper.render(new long[] { 1, 2, 3 }));

        assertEquals("", "[1, 2, 3]", ToStringHelper.render(new short[] { 1, 2, 3 }));

        assertEquals("", "[[1, 2], [], [3, 4]]", ToStringHelper.render(new byte[][] { { 1, 2 }, {}, { 3, 4 } }));

        assertEquals("", "[1.0, 2.0, 3.0]", ToStringHelper.render(new float[] { 1, 2, 3 }));

        assertEquals("", "[1.0, 2.0, 3.0]", ToStringHelper.render(new double[] { 1, 2, 3 }));

        assertEquals("", "[[1.0, 2.0, 3.0]]", ToStringHelper.render(new double[][] { { 1, 2, 3 } }));

        assertEquals("", "[true, false, true]", ToStringHelper.render(new boolean[] { true, false, true }));
    }

}
