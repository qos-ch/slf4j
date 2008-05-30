package org.slf4j.testBridge;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.ListAppender;
import org.slf4j.bridge.SLF4JBridgeHandler;

import junit.framework.TestCase;

public class SLF4JBridgeHandlerTest extends TestCase {
  
  static String LOGGER_NAME = "yay";
  
  ListAppender listAppender = new ListAppender();
  org.apache.log4j.Logger log4jRoot;
  java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("yay");
  
  public SLF4JBridgeHandlerTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
    listAppender.extractLocationInfo = true;
    log4jRoot = org.apache.log4j.Logger.getRootLogger();
    log4jRoot.addAppender(listAppender);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    log4jRoot.getLoggerRepository().resetConfiguration();
  }
  
  public void testSmoke() {
    SLF4JBridgeHandler.install();
    String msg = "msg";
    julLogger.info(msg);
    assertEquals(1, listAppender.list.size());
    LoggingEvent le = (LoggingEvent) listAppender.list.get(0);
    assertEquals(LOGGER_NAME, le.getLoggerName());
    assertEquals(msg, le.getMessage());
    
    LocationInfo li = le.getLocationInformation();
    System.out.println(li.fullInfo);
    assertEquals("SLF4JBridgeHandlerTest.java", li.getFileName());
    assertEquals("testSmoke", li.getMethodName());
  }
}
