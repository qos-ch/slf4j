package org.slf4j.bridge;

import java.util.logging.Level;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.bridge.SLF4JBridgeHandler;

import junit.framework.TestCase;

public class SLF4JBridgeHandlerTest extends TestCase {

  static String LOGGER_NAME = "yay";

  ListAppender listAppender = new ListAppender();
  org.apache.log4j.Logger log4jRoot;
  java.util.logging.Logger julLogger = java.util.logging.Logger
      .getLogger("yay");

  public SLF4JBridgeHandlerTest(String arg0) {
    super(arg0);
  }

  protected void setUp() throws Exception {
    super.setUp();
    listAppender.extractLocationInfo = true;
    log4jRoot = org.apache.log4j.Logger.getRootLogger();
    log4jRoot.addAppender(listAppender);
    log4jRoot.setLevel(org.apache.log4j.Level.TRACE);
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

    // get the location info in the event.
    // Note that this must have been computed previously
    // within an appender for the following assertion to
    // work properly
    LocationInfo li = le.getLocationInformation();
    System.out.println(li.fullInfo);
    assertEquals("SLF4JBridgeHandlerTest.java", li.getFileName());
    assertEquals("testSmoke", li.getMethodName());
  }

  public void testLevels() {
    SLF4JBridgeHandler.install();
    String msg = "msg";
    julLogger.setLevel(Level.ALL);

    julLogger.finest(msg);
    julLogger.finer(msg);
    julLogger.fine(msg);
    julLogger.info(msg);
    julLogger.warning(msg);
    julLogger.severe(msg);

    assertEquals(6, listAppender.list.size());
    int i = 0;
    assertLevel(i++, org.apache.log4j.Level.TRACE);
    assertLevel(i++, org.apache.log4j.Level.DEBUG);
    assertLevel(i++, org.apache.log4j.Level.DEBUG);
    assertLevel(i++, org.apache.log4j.Level.INFO);
    assertLevel(i++, org.apache.log4j.Level.WARN);
    assertLevel(i++, org.apache.log4j.Level.ERROR);
  }

  void assertLevel(int index, org.apache.log4j.Level expectedLevel) {
    LoggingEvent le = (LoggingEvent) listAppender.list.get(index);
    assertEquals(expectedLevel, le.getLevel());
  }
}
