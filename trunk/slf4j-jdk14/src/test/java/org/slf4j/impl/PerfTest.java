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

  public void testBug72() {
    Logger logger = LoggerFactory.getLogger(PerfTest.class);
    int len = 2000;
    for (int i = 0; i < len; i++) {
      logger.debug("hello");
    }
    
    long start = System.currentTimeMillis();
    for (int i = 0; i < len; i++) {
      logger.debug("hello");
    }

    long end = System.currentTimeMillis();
    
    long duration = end-start;
    // when the code is guarded by a logger.isLoggable condition, 
    // duration is about 16 *micro*seconds for 1000 iterations
    // when it is not guarded the figure is 90 milliseconds,
    // i.e a ration of 1 to 5000
    assertTrue(duration <= 5);
  }

}
