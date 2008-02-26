package org.slf4j.impl;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfTest extends TestCase {

  public PerfTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void test() {
    Logger logger = LoggerFactory.getLogger(PerfTest.class);
    int len = 1000;
    for (int i = 0; i < len; i++) {
      logger.debug("hello");
    }
    
    long start = System.currentTimeMillis();
    for (int i = 0; i < len; i++) {
      logger.debug("hello");
    }

    long end = System.currentTimeMillis();
    
    System.out.println("Difference "+(end-start));
  }

}
