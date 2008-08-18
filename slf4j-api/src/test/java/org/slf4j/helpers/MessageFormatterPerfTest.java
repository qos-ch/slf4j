package org.slf4j.helpers;

import java.text.MessageFormat;

import junit.framework.TestCase;

public class MessageFormatterPerfTest extends TestCase {

  Integer i1 = new Integer(1);
  static long RUN_LENGTH = 100000;
  static long REFERENCE_BIPS = 9629;
  
  public MessageFormatterPerfTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
  }

  protected void tearDown() throws Exception {
  }

  public void XtestJDKFormatterPerf() {
    jdkMessageFormatter(RUN_LENGTH);
    double avg = jdkMessageFormatter(RUN_LENGTH);
    System.out.println("jdk avg = "+avg+" nanos");
  }
  
  public void testSLF4JPerf() {
    slf4jMessageFormatter(RUN_LENGTH);
    double avgDuration = slf4jMessageFormatter(RUN_LENGTH);
    
    long referencePerf = 1700;
    BogoPerf.assertDuration(avgDuration, referencePerf, REFERENCE_BIPS);
  }

  public double slf4jMessageFormatter(long len) {
    String s = ""; 
    s += ""; // keep compiler happy
    long start = System.nanoTime();
    for (int i = 0; i < len; i++) {
      s = MessageFormatter.format("This is some rather short message {} ", i1);
    }
    long end = System.nanoTime();
    return (1.0*end - start) / len;
  }  
  public double jdkMessageFormatter(long len) {
    String s = ""; 
    s += ""; // keep compiler happy
    long start = System.nanoTime();
    Object[] oa = new Object[] {i1};
    for (int i = 0; i < len; i++) {
      s = MessageFormat.format("This is some rather short message {0}", oa);
    }
    long end = System.nanoTime();
    return (1.0*end - start) / len;
  }

}
