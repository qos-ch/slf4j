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

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfilerTest  extends TestCase {

  Logger logger = LoggerFactory.getLogger(ProfilerTest.class);

  public void setUp() throws Exception {
    super.setUp();
  }
  public void testSmoke() {
    Profiler profiler = new Profiler("SMOKE");
    profiler.stop();
    StopWatch gSW = profiler.globalStopWatch;
    
    // verify
    profiler.sanityCheck();
    assertEquals(TimeInstrumentStatus.STOPPED,  gSW.status);
    assertEquals(0, profiler.childTimeInstrumentList.size());
    assertNull(profiler.getLastTimeInstrument());
  }

  public void testBasicProfiling() {
    Profiler profiler = new Profiler("BAS");

    profiler.start("doX");
    doX(1);

    profiler.start("doY");
    doY(10);

    profiler.start("doZ");
    doZ(2);
    profiler.stop();
    
    // verify
    profiler.sanityCheck();
    StopWatch gSW = profiler.globalStopWatch;
    assertEquals(TimeInstrumentStatus.STOPPED,  gSW.status);
    assertEquals(3, profiler.childTimeInstrumentList.size());
    assertNotNull(profiler.getLastTimeInstrument());
    assertEquals("doZ", profiler.getLastTimeInstrument().getName());
  }

  // + Profiler [BAS]
  // |-- elapsed time                          [doX]     1.272 milliseconds.
  // |-- elapsed time                      [doYYYYY]    25.398 milliseconds.
  // |--+ Profiler [subtask]
  //    |-- elapsed time                           [n1]     1.434 milliseconds.
  //    |-- elapsed time                           [n2]     5.855 milliseconds.
  //    |-- Total elapsed time                [subtask]     7.321 milliseconds.
  // |-- elapsed time                          [doZ]     3.211 milliseconds.
  // |-- Total elapsed time                    [BAS]    30.317 milliseconds.
  public void testNestedProfiling() {
    
    Profiler profiler = new Profiler("BAS");
    profiler.setLogger(logger);
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
    profiler.stop();
    
    // verify
    profiler.sanityCheck();
    StopWatch gSW = profiler.globalStopWatch;
    assertEquals(TimeInstrumentStatus.STOPPED,  gSW.status);
    //assertEquals(3, profiler.stopwatchList.size());
    assertEquals(4, profiler.childTimeInstrumentList.size());
    assertNotNull(profiler.getLastTimeInstrument());
    assertEquals("doZ", profiler.getLastTimeInstrument().getName());
    
  }

  private void doX(int millis) {
    delay(millis);
  }
  private void doY(int millis) {
    delay(millis);
  }
  private void doZ(int millis) {
    delay(millis);
  }

  public void doSubtask(Profiler nested) {
    nested.start("n1");
    doX(1);

    nested.start("n2");
    doX(5);
    nested.stop();
  }


  void delay(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }
}
