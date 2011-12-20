package org.slf4j;

import junit.framework.TestCase;

public class PropertyBindingTest extends TestCase {
  public void testPropertyBinding() {
    LoggerFactory.reset();
    System.setProperty(LoggerFactory.STATIC_LOGGER_BINDER_CLASS_PROPERTY, "org.slf4j.MyBinding");
    try {
      Logger log = LoggerFactory.getLogger("test");
      assertEquals(log.getClass(), MyBinding.MyLogger.class);
    } finally {
      System.clearProperty(LoggerFactory.STATIC_LOGGER_BINDER_CLASS_PROPERTY);
    }
  }
}
