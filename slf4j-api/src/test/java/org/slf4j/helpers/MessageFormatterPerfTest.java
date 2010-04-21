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

  public double slf4jMessageFormatter_OneArg(long len) {
    String s = "";
    s += ""; // keep compiler happy
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
  
  public void testSLF4JPerf_TwoArg() {
    slf4jMessageFormatter_TwoArg(RUN_LENGTH);
    double duration = slf4jMessageFormatter_TwoArg(RUN_LENGTH);
    System.out.println("duration2=" + duration);
    long referencePerf = 60;
    BogoPerf.assertDuration(duration, referencePerf, REFERENCE_BIPS);
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
