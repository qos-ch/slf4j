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
import java.util.Random;

import junit.framework.TestCase;

/**
 * Test that our BubbleSort algorithm is correctly implemented.
 * 
 * @author Ceki
 *
 */
public class BubbleSortTest extends TestCase {
  
  public void testSmoke() {
     int[] a = new int[] {5,3,2,7};
     BubbleSort.sort(a);
     int i = 0;
     assertEquals(2, a[i++]);
     assertEquals(3, a[i++]);
     assertEquals(5, a[i++]);
     assertEquals(7, a[i++]);
  }
  
  public void testEmpty() {
    int[] a = new int[] {};
    BubbleSort.sort(a);
  }
  
  public void testSorted() {
    int[] a = new int[] {3,30,300,3000};
    BubbleSort.sort(a);
    int i = 0;
    assertEquals(3, a[i++]);
    assertEquals(30, a[i++]);
    assertEquals(300, a[i++]);
    assertEquals(3000, a[i++]);
  }
  
  public void testInverted() {
    int[] a = new int[] {3000,300,30,3};
    BubbleSort.sort(a);
    int i = 0;
    assertEquals(3, a[i++]);
    assertEquals(30, a[i++]);
    assertEquals(300, a[i++]);
    assertEquals(3000, a[i++]);
  }

  public void testWithSameEntry() {
    int[] a = new int[] {10,20,10,20};
    BubbleSort.sort(a);
    int i = 0;
    assertEquals(10, a[i++]);
    assertEquals(10, a[i++]);
    assertEquals(20, a[i++]);
    assertEquals(20, a[i++]);
  }

  
  public void testRandom() {
    int len = 100;
    Random random = new Random(156);
    int[] a = new int[len];
    int[] witness = new int[len];
    for(int i = 0; i < len; i++) {
      int r = random.nextInt();
      a[i] = r;
      witness[i] = r;
    }
   BubbleSort.sort(a);
   Arrays.sort(witness);
   assertTrue(Arrays.equals(witness, a));
  }

}
