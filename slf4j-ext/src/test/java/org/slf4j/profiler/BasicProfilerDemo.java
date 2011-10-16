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
package org.slf4j.profiler;

/**
 * 
 * This demo illustrates usage of SLF4J profilers.
 * 
 * <p>
 * We have been given the task of generating a large number, say N, of random
 * integers. We need to transform that array into a smaller array containing
 * only prime numbers. The new array has to be sorted.
 * 
 * <p>
 * While tackling this problem, we would like to measure the time spent in each
 * subtask.
 * 
 * <p>
 * A typical output for this demo would be:
 * 
 * <pre>
   + Profiler [BASIC]
   |-- elapsed time                      [A]   213.186 milliseconds.
   |-- elapsed time                      [B]  2499.107 milliseconds.
   |-- elapsed time                  [OTHER]  3300.752 milliseconds.
   |-- Total                         [BASIC]  6014.161 milliseconds.
  </pre>
 * 
 * @author Ceki Gulcu
 */
public class BasicProfilerDemo {

  public static void main(String[] args) {
    // create a profiler called "BASIC"
    Profiler profiler = new Profiler("BASIC");
    profiler.start("A");
    doA();

    profiler.start("B");
    doB();

    profiler.start("OTHER");
    doOther();
    profiler.stop().print();
  }

  static private void doA() {
    delay(200);
  }

  static private void doB() {
    delay(2500);
  }

  static private void doOther() {
    delay(3300);
  }

  static private void delay(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }
}
