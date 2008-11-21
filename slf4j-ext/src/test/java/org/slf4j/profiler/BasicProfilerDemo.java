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
