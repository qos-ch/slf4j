package org.slf4j.impl;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.BogoPerf;

public class PerfTest extends TestCase {

  static long REFERENCE_BIPS = 9000;

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
    
    int LEN = 1000*1000*10;
    debugLoop(LEN); // warm up
    double avg = debugLoop(LEN);
    long referencePerf = 93;
    BogoPerf.assertDuration(avg, referencePerf, REFERENCE_BIPS);

    // when the code is guarded by a logger.isLoggable condition,
    // duration is about 16 *micro*seconds for 1000 iterations
    // when it is not guarded the figure is 90 milliseconds,
    // i.e a ration of 1 to 5000
  }

  double debugLoop(int len) {
    Logger logger = LoggerFactory.getLogger(PerfTest.class);
    long start = System.currentTimeMillis();
    for (int i = 0; i < len; i++) {
      logger.debug("hello");
    }

    long end = System.currentTimeMillis();

    long duration = end - start;
    return duration;
  }

}
