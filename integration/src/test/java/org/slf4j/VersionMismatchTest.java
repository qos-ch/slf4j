package org.slf4j;

import java.io.PrintStream;
import java.util.Random;

import junit.framework.TestCase;

public class VersionMismatchTest extends TestCase {

  StringPrintStream sps = new StringPrintStream(System.err);
  PrintStream old = System.err;
  int diff = 1024 + new Random().nextInt(10000);

  public VersionMismatchTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    System.setErr(sps);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    System.setErr(old);
  }

  public void test() throws Exception {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    String msg = "hello world " + diff;
    logger.info(msg);
    
    String s0 = (String) sps.stringList.get(0);
    assertTrue(s0.matches("SLF4J: The requested version .* by your slf4j binding is not compatible with.*"));

    String s1 = (String) sps.stringList.get(1);
    assertTrue(s1.contains(LoggerFactory.VERSION_MISMATCH));

    String s2 = (String) sps.stringList.get(2);
    assertTrue(s2.contains(msg));

  }
}
