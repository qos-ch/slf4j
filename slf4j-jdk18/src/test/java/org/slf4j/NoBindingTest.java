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
package org.slf4j;

import java.util.Random;

import org.slf4j.helpers.BasicMarker;
import org.slf4j.helpers.NOPLogger;

import junit.framework.TestCase;

public class NoBindingTest extends TestCase {

  int diff = new Random().nextInt(10000);
  
  public void testLogger() {
    Logger logger = LoggerFactory.getLogger(NoBindingTest.class);
    logger.debug("hello"+diff);
    assertTrue(logger instanceof NOPLogger);
  }

  public void testMDC() {
    MDC.put("k"+diff, "v");
    assertNull(MDC.get("k"));
  }

  public void testMarker() {
    Marker m = MarkerFactory.getMarker("a"+diff);
    assertTrue(m instanceof BasicMarker);
  }
}
