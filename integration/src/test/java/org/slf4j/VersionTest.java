package org.slf4j;

import junit.framework.TestCase;

public class VersionTest extends TestCase {

  public VersionTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  
  public void test() throws Exception  {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    logger.info("hello world");
  }
}
