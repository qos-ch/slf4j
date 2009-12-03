package org.slf4j.helpers;

import java.text.MessageFormat;

import junit.framework.TestCase;

public class MessageFormatterPerfTest extends TestCase {

  Integer i1 = new Integer(1);
  static long RUN_LENGTH = 100000;
  static long REFERENCE_BIPS = 9000;
  
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
    System.out.println("jdk duration = "+duration+" nanos");
  }
  
  public void testSLF4JPerf() {
    slf4jMessageFormatter(RUN_LENGTH);
    double duration = slf4jMessageFormatter(RUN_LENGTH);
    long referencePerf = 140;
    BogoPerf.assertDuration(duration, referencePerf, REFERENCE_BIPS);
  }

  public double slf4jMessageFormatter(long len) {
    String s = ""; 
    s += ""; // keep compiler happy
    long start = System.currentTimeMillis();
    for (int i = 0; i < len; i++) {
      s = MessageFormatter.format("This is some rather short message {} ", i1);
    }
    long end = System.currentTimeMillis();
    return (1.0*end - start);
  }  
  public double jdkMessageFormatter(long len) {
    String s = ""; 
    s += ""; // keep compiler happy
    long start = System.currentTimeMillis();
    Object[] oa = new Object[] {i1};
    for (int i = 0; i < len; i++) {
      s = MessageFormat.format("This is some rather short message {0}", oa);
    }
    long end = System.currentTimeMillis();
    return (1.0*end - start);
  }

}
