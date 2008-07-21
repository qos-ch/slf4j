/*
 * Copyright (c) 2004-2008 QOS.ch
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
 */

package org.slf4j.profiler;

import junit.framework.TestCase;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class ProfilerTest  extends TestCase{

  Logger logger = LoggerFactory.getLogger(ProfilerTest.class);

  public void smoke() {
    Profiler profiler = new Profiler("SMOKE");
    System.out.println("Hello");
    profiler.stop();
  }

  public void testBasicProfiling() {
    Profiler profiler = new Profiler("BAS");

    profiler.start("doX");
    doX(1);

    profiler.start("doYYYYY");
    for (int i = 0; i < 5; i++) {
      doY(i);
    }
    profiler.start("doZ");
    doZ(2);
    profiler.stop().print();
  }

  public void X() {
    Profiler profiler = new Profiler("BASIC");
  

  profiler.start("Subtask_1");
  doX(1);
     
  profiler.start("Subtask_1");
  for (int i = 0; i < 5; i++) {
    doX(i);
  }
  profiler.start("doOther");
  doX(2);
  profiler.stop().print();
  }


  public void testNestedProfiling() {
    Profiler profiler = new Profiler("BAS");

    profiler.start("doX");
    doX(1);

    profiler.start("doYYYYY");
    for (int i = 0; i < 5; i++) {
      doY(i);
    }
    Profiler nested = profiler.startNested("subtask");
    doSubtask(nested);
    profiler.start("doZ");
    doZ(2);
    profiler.stop().print();
  }

  void doX(int millis) {
    delay(millis);
  }

  public void doSubtask(Profiler nested) {
    nested.start("n1");
    doX(1);

    nested.start("n2");
    doX(5);
    nested.stop();
  }

  void doY(int millis) {
    delay(millis);
  }

  void doZ(int millis) {
    delay(millis);
  }

  void delay(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }
}
