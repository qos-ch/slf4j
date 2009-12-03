/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.CH
 * 
 * All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 * 
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.helpers;

import junit.framework.TestCase;

/**
 * @author Ceki Gulcu
 * 
 */
public class MessageFormatterTest extends TestCase {

  Integer i1 = new Integer(1);
  Integer i2 = new Integer(2);
  Integer i3 = new Integer(3);
  Integer[] ia0 = new Integer[] { i1, i2, i3 };
  Integer[] ia1 = new Integer[] { new Integer(10), new Integer(20),
      new Integer(30) };
  
  String result;
  
  
  public void testNull() {
    result = MessageFormatter.format(null, i1);
    assertEquals(null, result);
  }

  public void testNullParam() {
    result = MessageFormatter.format("Value is {}.", null);
    assertEquals("Value is null.", result);

    result = MessageFormatter.format("Val1 is {}, val2 is {}.", null, null);
    assertEquals("Val1 is null, val2 is null.", result);

    result = MessageFormatter.format("Val1 is {}, val2 is {}.", i1, null);
    assertEquals("Val1 is 1, val2 is null.", result);

    result = MessageFormatter.format("Val1 is {}, val2 is {}.", null, i2);
    assertEquals("Val1 is null, val2 is 2.", result);

    result = MessageFormatter.arrayFormat("Val1 is {}, val2 is {}, val3 is {}",
        new Integer[] { null, null, null });
    assertEquals("Val1 is null, val2 is null, val3 is null", result);

    result = MessageFormatter.arrayFormat("Val1 is {}, val2 is {}, val3 is {}",
        new Integer[] { null, i2, i3 });
    assertEquals("Val1 is null, val2 is 2, val3 is 3", result);

    result = MessageFormatter.arrayFormat("Val1 is {}, val2 is {}, val3 is {}",
        new Integer[] { null, null, i3 });
    assertEquals("Val1 is null, val2 is null, val3 is 3", result);
  }

  public void testOneParameter() {
    result = MessageFormatter.format("Value is {}.", i3);
    assertEquals("Value is 3.", result);

    result = MessageFormatter.format("Value is {", i3);
    assertEquals("Value is {", result);

    result = MessageFormatter.format("{} is larger than 2.", i3);
    assertEquals("3 is larger than 2.", result);

    result = MessageFormatter.format("No subst", i3);
    assertEquals("No subst", result);

    result = MessageFormatter.format("Incorrect {subst", i3);
    assertEquals("Incorrect {subst", result);

    result = MessageFormatter.format("Value is {bla} {}", i3);
    assertEquals("Value is {bla} 3", result);

    result = MessageFormatter.format("Escaped \\{} subst", i3);
    assertEquals("Escaped {} subst", result);

    result = MessageFormatter.format("{Escaped", i3);
    assertEquals("{Escaped", result);

    result = MessageFormatter.format("\\{}Escaped", i3);
    assertEquals("{}Escaped", result);

    result = MessageFormatter.format("File name is {{}}.", "App folder.zip");
    assertEquals("File name is {App folder.zip}.", result);

    // escaping the escape character
    result = MessageFormatter
        .format("File name is C:\\\\{}.", "App folder.zip");
    assertEquals("File name is C:\\App folder.zip.", result);
  }

  public void testTwoParameters() {
    result = MessageFormatter.format("Value {} is smaller than {}.", i1, i2);
    assertEquals("Value 1 is smaller than 2.", result);

    result = MessageFormatter.format("Value {} is smaller than {}", i1, i2);
    assertEquals("Value 1 is smaller than 2", result);

    result = MessageFormatter.format("{}{}", i1, i2);
    assertEquals("12", result);

    result = MessageFormatter.format("Val1={}, Val2={", i1, i2);
    assertEquals("Val1=1, Val2={", result);

    result = MessageFormatter.format("Value {} is smaller than \\{}", i1, i2);
    assertEquals("Value 1 is smaller than {}", result);

    result = MessageFormatter.format("Value {} is smaller than \\{} tail", i1,
        i2);
    assertEquals("Value 1 is smaller than {} tail", result);

    result = MessageFormatter.format("Value {} is smaller than \\{", i1, i2);
    assertEquals("Value 1 is smaller than \\{", result);

    result = MessageFormatter
        .format("Value {} is smaller than {tail", i1, i2);
    assertEquals("Value 1 is smaller than {tail", result);

    result = MessageFormatter.format("Value \\{} is smaller than {}", i1, i2);
    assertEquals("Value {} is smaller than 1", result);
  }

  
  public void testExceptionInToString() {
    Object o = new Object() {
      public String toString() {
        throw new IllegalStateException("a");
      }
    };
    result = MessageFormatter.format("Troublesome object {}", o);
    assertEquals("Troublesome object [FAILED toString()]", result);
    
  }
  
  public void testNullArray() {
    String msg0 = "msg0";
    String msg1 = "msg1 {}";
    String msg2 = "msg2 {} {}";
    String msg3 = "msg3 {} {} {}";

    Object[] args = null;

    result = MessageFormatter.arrayFormat(msg0, args);
    assertEquals(msg0, result);

    result = MessageFormatter.arrayFormat(msg1, args);
    assertEquals(msg1, result);

    result = MessageFormatter.arrayFormat(msg2, args);
    assertEquals(msg2, result);

    result = MessageFormatter.arrayFormat(msg3, args);
    assertEquals(msg3, result);
  }

  // tests the case when the parameters are supplied in a single array
  public void testArrayFormat() {
    result = MessageFormatter.arrayFormat(
        "Value {} is smaller than {} and {}.", ia0);
    assertEquals("Value 1 is smaller than 2 and 3.", result);

    result = MessageFormatter.arrayFormat("{}{}{}", ia0);
    assertEquals("123", result);

    result = MessageFormatter.arrayFormat("Value {} is smaller than {}.", ia0);
    assertEquals("Value 1 is smaller than 2.", result);

    result = MessageFormatter.arrayFormat("Value {} is smaller than {}", ia0);
    assertEquals("Value 1 is smaller than 2", result);

    result = MessageFormatter.arrayFormat("Val={}, {, Val={}", ia0);
    assertEquals("Val=1, {, Val=2", result);

    result = MessageFormatter.arrayFormat("Val={}, {, Val={}", ia0);
    assertEquals("Val=1, {, Val=2", result);

    result = MessageFormatter.arrayFormat("Val1={}, Val2={", ia0);
    assertEquals("Val1=1, Val2={", result);
  }

  public void testArrayValues() {
    Integer p0 = i1;
    Integer[] p1 = new Integer[] { i2, i3 };

    result = MessageFormatter.format("{}{}", p0, p1);
    assertEquals("1[2, 3]", result);

    // Integer[]
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", p1 });
    assertEquals("a[2, 3]", result);

    // byte[]
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a",
        new byte[] { 1, 2 } });
    assertEquals("a[1, 2]", result);

    // int[]
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a",
        new int[] { 1, 2 } });
    assertEquals("a[1, 2]", result);

    // float[]
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a",
        new float[] { 1, 2 } });
    assertEquals("a[1.0, 2.0]", result);

    // double[]
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a",
        new double[] { 1, 2 } });
    assertEquals("a[1.0, 2.0]", result);

  }

  public void testMultiDimensionalArrayValues() {
    Integer[][] multiIntegerA = new Integer[][] { ia0, ia1 };
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a",
        multiIntegerA });
    assertEquals("a[[1, 2, 3], [10, 20, 30]]", result);

    int[][] multiIntA = new int[][] { { 1, 2 }, { 10, 20 } };
    result = MessageFormatter.arrayFormat("{}{}",
        new Object[] { "a", multiIntA });
    assertEquals("a[[1, 2], [10, 20]]", result);

    float[][] multiFloatA = new float[][] { { 1, 2 }, { 10, 20 } };
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a",
        multiFloatA });
    assertEquals("a[[1.0, 2.0], [10.0, 20.0]]", result);

    Object[][] multiOA = new Object[][] { ia0, ia1 };
    result = MessageFormatter
        .arrayFormat("{}{}", new Object[] { "a", multiOA });
    assertEquals("a[[1, 2, 3], [10, 20, 30]]", result);

    Object[][][] _3DOA = new Object[][][] { multiOA, multiOA };
    result = MessageFormatter.arrayFormat("{}{}", new Object[] { "a", _3DOA });
    assertEquals("a[[[1, 2, 3], [10, 20, 30]], [[1, 2, 3], [10, 20, 30]]]",
        result);
  }

  public void testCyclicArrays() {
    {
      Object[] cyclicA = new Object[1];
      cyclicA[0] = cyclicA;
      assertEquals("[[...]]", MessageFormatter.arrayFormat("{}", cyclicA));
    }
    {
      Object[] a = new Object[2];
      a[0] = i1;
      Object[] c = new Object[] {i3, a};
      Object[] b = new Object[] {i2, c};
      a[1] = b;
      assertEquals("1[2, [3, [1, [...]]]]", MessageFormatter.arrayFormat("{}{}", a));
    }
  }
}
