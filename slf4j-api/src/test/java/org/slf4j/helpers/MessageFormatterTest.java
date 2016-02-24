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
package org.slf4j.helpers;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ceki Gulcu
 */
public class MessageFormatterTest {

    Integer i1 = new Integer(1);
    Integer i2 = new Integer(2);
    Integer i3 = new Integer(3);
    Integer[] ia0 = new Integer[] { i1, i2, i3 };
    Integer[] ia1 = new Integer[] { new Integer(10), new Integer(20), new Integer(30) };

    String result;

    @Test
    public void testNull() {
        result = MessageFormatter.format(null, i1).getMessage();
        assertEquals(null, result);
    }

    @Test
    public void nullParametersShouldBeHandledWithoutBarfing() {
        result = MessageFormatter.format("Value is {}.", null).getMessage();
        assertEquals("Value is null.", result);

        result = MessageFormatter.format("Val1 is {}, val2 is {}.", null, null).getMessage();
        assertEquals("Val1 is null, val2 is null.", result);

        result = MessageFormatter.format("Val1 is {}, val2 is {}.", i1, null).getMessage();
        assertEquals("Val1 is 1, val2 is null.", result);

        result = MessageFormatter.format("Val1 is {}, val2 is {}.", null, i2).getMessage();
        assertEquals("Val1 is null, val2 is 2.", result);

        result = MessageFormatter.arrayFormat("Val1 is {}, val2 is {}, val3 is {}", new Integer[] { null, null, null }).getMessage();
        assertEquals("Val1 is null, val2 is null, val3 is null", result);

        result = MessageFormatter.arrayFormat("Val1 is {}, val2 is {}, val3 is {}", new Integer[] { null, i2, i3 }).getMessage();
        assertEquals("Val1 is null, val2 is 2, val3 is 3", result);

        result = MessageFormatter.arrayFormat("Val1 is {}, val2 is {}, val3 is {}", new Integer[] { null, null, i3 }).getMessage();
        assertEquals("Val1 is null, val2 is null, val3 is 3", result);
    }

    @Test
    public void verifyOneParameterIsHandledCorrectly() {
        result = MessageFormatter.format("Value is {}.", i3).getMessage();
        assertEquals("Value is 3.", result);

        result = MessageFormatter.format("Value is {", i3).getMessage();
        assertEquals("Value is {", result);

        result = MessageFormatter.format("{} is larger than 2.", i3).getMessage();
        assertEquals("3 is larger than 2.", result);

        result = MessageFormatter.format("No subst", i3).getMessage();
        assertEquals("No subst", result);

        result = MessageFormatter.format("Incorrect {subst", i3).getMessage();
        assertEquals("Incorrect {subst", result);

        result = MessageFormatter.format("Value is {bla} {}", i3).getMessage();
        assertEquals("Value is {bla} 3", result);

        result = MessageFormatter.format("Escaped \\{} subst", i3).getMessage();
        assertEquals("Escaped {} subst", result);

        result = MessageFormatter.format("{Escaped", i3).getMessage();
        assertEquals("{Escaped", result);

        result = MessageFormatter.format("\\{}Escaped", i3).getMessage();
        assertEquals("{}Escaped", result);

        result = MessageFormatter.format("File name is {{}}.", "App folder.zip").getMessage();
        assertEquals("File name is {App folder.zip}.", result);

        // escaping the escape character
        result = MessageFormatter.format("File name is C:\\\\{}.", "App folder.zip").getMessage();
        assertEquals("File name is C:\\App folder.zip.", result);
    }

    @Test
    public void testTwoParameters() {
        result = MessageFormatter.format("Value {} is smaller than {}.", i1, i2).getMessage();
        assertEquals("Value 1 is smaller than 2.", result);

        result = MessageFormatter.format("Value {} is smaller than {}", i1, i2).getMessage();
        assertEquals("Value 1 is smaller than 2", result);

        result = MessageFormatter.format("{}{}", i1, i2).getMessage();
        assertEquals("12", result);

        result = MessageFormatter.format("Val1={}, Val2={", i1, i2).getMessage();
        assertEquals("Val1=1, Val2={", result);

        result = MessageFormatter.format("Value {} is smaller than \\{}", i1, i2).getMessage();
        assertEquals("Value 1 is smaller than {}", result);

        result = MessageFormatter.format("Value {} is smaller than \\{} tail", i1, i2).getMessage();
        assertEquals("Value 1 is smaller than {} tail", result);

        result = MessageFormatter.format("Value {} is smaller than \\{", i1, i2).getMessage();
        assertEquals("Value 1 is smaller than \\{", result);

        result = MessageFormatter.format("Value {} is smaller than {tail", i1, i2).getMessage();
        assertEquals("Value 1 is smaller than {tail", result);

        result = MessageFormatter.format("Value \\{} is smaller than {}", i1, i2).getMessage();
        assertEquals("Value {} is smaller than 1", result);
    }

    @Test
    public void testExceptionIn_toString() {
        Object o = new Object() {
            public String toString() {
                throw new IllegalStateException("a");
            }
        };
        result = MessageFormatter.format("Troublesome object {}", o).getMessage();
        assertEquals("Troublesome object [FAILED toString()]", result);

    }

    @Test
    public void testNullArray() {
        String msg0 = "msg0";
        String msg1 = "msg1 {}";
        String msg2 = "msg2 {} {}";
        String msg3 = "msg3 {} {} {}";

        Object[] args = null;

        result = MessageFormatter.arrayFormat(msg0, args).getMessage();
        assertEquals(msg0, result);

        result = MessageFormatter.arrayFormat(msg1, args).getMessage();
        assertEquals(msg1, result);

        result = MessageFormatter.arrayFormat(msg2, args).getMessage();
        assertEquals(msg2, result);

        result = MessageFormatter.arrayFormat(msg3, args).getMessage();
        assertEquals(msg3, result);
    }

    // tests the case when the parameters are supplied in a single array
    @Test
    public void testArrayFormat() {
        result = MessageFormatter.arrayFormat("Value {} is smaller than {} and {}.", ia0).getMessage();
        assertEquals("Value 1 is smaller than 2 and 3.", result);

        result = MessageFormatter.arrayFormat("{}{}{}", ia0).getMessage();
        assertEquals("123", result);

        result = MessageFormatter.arrayFormat("Value {} is smaller than {}.", ia0).getMessage();
        assertEquals("Value 1 is smaller than 2.", result);

        result = MessageFormatter.arrayFormat("Value {} is smaller than {}", ia0).getMessage();
        assertEquals("Value 1 is smaller than 2", result);

        result = MessageFormatter.arrayFormat("Val={}, {, Val={}", ia0).getMessage();
        assertEquals("Val=1, {, Val=2", result);

        result = MessageFormatter.arrayFormat("Val={}, {, Val={}", ia0).getMessage();
        assertEquals("Val=1, {, Val=2", result);

        result = MessageFormatter.arrayFormat("Val1={}, Val2={", ia0).getMessage();
        assertEquals("Val1=1, Val2={", result);
    }

    @Test
    public void testArrayValues() {
        Integer p0 = i1;
        Integer[] p1 = new Integer[] { i2, i3 };

        result = MessageFormatter.format("{}{}", p0, p1).getMessage();
        assertEquals("1[2, 3]", result);

        // Integer[]
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", p1 }).getMessage();
        assertEquals("a[2, 3]", result);

        // byte[]
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", new byte[] { 1, 2 } }).getMessage();
        assertEquals("a[1, 2]", result);

        // int[]
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", new int[] { 1, 2 } }).getMessage();
        assertEquals("a[1, 2]", result);

        // float[]
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", new float[] { 1, 2 } }).getMessage();
        assertEquals("a[1.0, 2.0]", result);

        // double[]
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", new double[] { 1, 2 } }).getMessage();
        assertEquals("a[1.0, 2.0]", result);

    }

    @Test
    public void testMultiDimensionalArrayValues() {
        Integer[][] multiIntegerA = new Integer[][] { ia0, ia1 };
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", multiIntegerA }).getMessage();
        assertEquals("a[[1, 2, 3], [10, 20, 30]]", result);

        int[][] multiIntA = new int[][] { { 1, 2 }, { 10, 20 } };
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", multiIntA }).getMessage();
        assertEquals("a[[1, 2], [10, 20]]", result);

        float[][] multiFloatA = new float[][] { { 1, 2 }, { 10, 20 } };
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", multiFloatA }).getMessage();
        assertEquals("a[[1.0, 2.0], [10.0, 20.0]]", result);

        Object[][] multiOA = new Object[][] { ia0, ia1 };
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", multiOA }).getMessage();
        assertEquals("a[[1, 2, 3], [10, 20, 30]]", result);

        Object[][][] _3DOA = new Object[][][] { multiOA, multiOA };
        result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", _3DOA }).getMessage();
        assertEquals("a[[[1, 2, 3], [10, 20, 30]], [[1, 2, 3], [10, 20, 30]]]", result);
    }

    @Test
    public void testCyclicArrays() {
        {
            Object[] cyclicA = new Object[1];
            cyclicA[0] = cyclicA;
            assertEquals("[[...]]", MessageFormatter.arrayFormat("{}", cyclicA).getMessage());
        }
        {
            Object[] a = new Object[2];
            a[0] = i1;
            Object[] c = new Object[] { i3, a };
            Object[] b = new Object[] { i2, c };
            a[1] = b;
            assertEquals("1[2, [3, [1, [...]]]]", MessageFormatter.arrayFormat("{}{}", a).getMessage());
        }
    }

    @Test
    public void testArrayThrowable() {
        FormattingTuple ft;
        Throwable t = new Throwable();
        Object[] ia = new Object[] { i1, i2, i3, t };
        Object[] iaWitness = new Object[] { i1, i2, i3 };

        ft = MessageFormatter.arrayFormat("Value {} is smaller than {} and {}.", ia);
        assertEquals("Value 1 is smaller than 2 and 3.", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("{}{}{}", ia);
        assertEquals("123", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("Value {} is smaller than {}.", ia);
        assertEquals("Value 1 is smaller than 2.", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("Value {} is smaller than {}", ia);
        assertEquals("Value 1 is smaller than 2", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("Val={}, {, Val={}", ia);
        assertEquals("Val=1, {, Val=2", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("Val={}, \\{, Val={}", ia);
        assertEquals("Val=1, \\{, Val=2", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("Val1={}, Val2={", ia);
        assertEquals("Val1=1, Val2={", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("Value {} is smaller than {} and {}.", ia);
        assertEquals("Value 1 is smaller than 2 and 3.", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("{}{}{}{}", ia);
        assertEquals("123{}", ft.getMessage());
        assertTrue(Arrays.equals(iaWitness, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

        ft = MessageFormatter.arrayFormat("1={}", new Object[] { i1 }, t);
        assertEquals("1=1", ft.getMessage());
        assertTrue(Arrays.equals(new Object[] { i1 }, ft.getArgArray()));
        assertEquals(t, ft.getThrowable());

    }
}
