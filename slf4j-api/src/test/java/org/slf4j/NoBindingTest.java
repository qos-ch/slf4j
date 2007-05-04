package org.slf4j;

import junit.framework.TestCase;

public class NoBindingTest extends TestCase {

  public void test() {
    try {
    Logger logger = LoggerFactory.getLogger(NoBindingTest.class);
    logger.debug("hello");
    fail("slf4j-api does not ship with a binding");
    } catch(NoClassDefFoundError e) {
      
    }
  }
}
