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

import java.text.MessageFormat;

import junit.framework.TestCase;

public class MessageFormatterPerfTest extends TestCase {

  Integer i1 = new Integer(1);
  Integer i2 = new Integer(2);
  static long RUN_LENGTH = 100 * 1000;
  // 
  static long REFERENCE_BIPS = 48416;

  public MessageFormatterPerfTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public void XtestJDKFormatterPerf() {
    jdkMessageFormatter(RUN_LENGTH);
    double duration = jdkMessageFormatter(RUN_LENGTH);
    System.out.println("jdk duration = " + duration + " nanos");
  }

  public void testSLF4JPerf_OneArg() {
    slf4jMessageFormatter_OneArg(RUN_LENGTH);
    double duration = slf4jMessageFormatter_OneArg(RUN_LENGTH);
    System.out.println("duration=" + duration);
    long referencePerf = 36;
    BogoPerf.assertDuration(duration, referencePerf, REFERENCE_BIPS);
  }

  public void testSLF4JPerf_TwoArg() {
    slf4jMessageFormatter_TwoArg(RUN_LENGTH);
    double duration = slf4jMessageFormatter_TwoArg(RUN_LENGTH);
    long referencePerf = 60;
    BogoPerf.assertDuration(duration, referencePerf, REFERENCE_BIPS);
  }

  
  public double slf4jMessageFormatter_OneArg(long len) {
    long start = System.nanoTime();
    for (int i = 0; i < len; i++) {
      final FormattingTuple tp = MessageFormatter.format("This is some rather short message {} ", i1);
      tp.getMessage();
      tp.getArgArray();
      tp.getThrowable();
      
      MessageFormatter.format("This is some rather short message {} ", i1);
    }
    long end = System.nanoTime();
    return (end - start)/(1000*1000.0);
  }
  
  public double slf4jMessageFormatter_TwoArg(long len) {
    long start = System.nanoTime();
    for (int i = 0; i < len; i++) {
      final FormattingTuple tp = MessageFormatter.format(
          "This is some {} short message {} ", i1, i2);
      tp.getMessage();
      tp.getArgArray();
      tp.getThrowable();
    }
    long end = System.nanoTime();
    return (end - start)/(1000*1000.0);
  }



  public double jdkMessageFormatter(long len) {
    @SuppressWarnings("unused")
    String s = "";
    s += ""; // keep compiler happy
    long start = System.currentTimeMillis();
    Object[] oa = new Object[] { i1 };
    for (int i = 0; i < len; i++) {
      s = MessageFormat.format("This is some rather short message {0}", oa);
    }
    long end = System.currentTimeMillis();
    return (1.0 * end - start);
  }

}
