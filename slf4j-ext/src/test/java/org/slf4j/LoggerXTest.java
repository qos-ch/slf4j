package org.slf4j;

import junit.framework.TestCase;

public class LoggerXTest extends TestCase {

  Logger logger = LoggerFactory.getLogger(this.getClass());
  
  public void testSmoke() {
    XLogger xLogger = new XLogger(logger);
    xLogger.entry(logger);
  }
}
