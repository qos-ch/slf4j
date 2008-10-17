package org.slf4j;

import java.io.PrintStream;
import java.util.Random;

import junit.framework.TestCase;

public class Pre155VersionTest extends TestCase {

  StringPrintStream sps = new StringPrintStream(System.err);
  PrintStream old = System.err;
  int diff = 1024 + new Random().nextInt(10000);

  public Pre155VersionTest(String name) {
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
    assertTrue(s0.contains(msg));
  }
}
