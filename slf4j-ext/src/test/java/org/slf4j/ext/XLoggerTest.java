package org.slf4j.ext;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;

public class XLoggerTest extends TestCase {

  ListAppender listAppender;
  org.apache.log4j.Logger log4jRoot;

  public XLoggerTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();

    // start from a clean slate for each test

    listAppender = new ListAppender();
    listAppender.extractLocationInfo = true;
    log4jRoot = org.apache.log4j.Logger.getRootLogger();
    log4jRoot.addAppender(listAppender);
    log4jRoot.setLevel(org.apache.log4j.Level.TRACE);
  }

  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testEntering() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.entry();
    logger.entry(1);
    logger.entry("test");

    assertEquals(3, listAppender.list.size());

    LoggingEvent le0 = (LoggingEvent) listAppender.list.get(0);
    assertEquals("entry", le0.getMessage());
    System.out.println("*********"+le0.getLocationInformation().fullInfo);

    assertEquals("XLoggerTest.java", le0.getLocationInformation().getFileName());
    LoggingEvent le1 = (LoggingEvent) listAppender.list.get(1);
    assertEquals("entry with (1)", le1.getMessage());

    LoggingEvent le2 = (LoggingEvent) listAppender.list.get(2);
    assertEquals("entry with (test)", le2.getMessage());
  }

  public void testExiting() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.exit();
    // assertEquals("exit", logMessage);
    // logger.exit(0);
    // assertEquals("exit 0", logMessage);
    // logger.exit(false);
    // assertEquals("exit false", logMessage);
  }

  public void testThrowing() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.throwing(new UnsupportedOperationException("Test"));
    // assertTrue(logMessage.startsWith("throwing
    // java.lang.UnsupportedOperationException:"));
  }

  public void testCaught() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    long x = 5;
    try {
      @SuppressWarnings("unused")
      long y = x / 0;
    } catch (Exception ex) {
      logger.catching(ex);
    }
    // assertTrue(logMessage.startsWith("caught
    // java.lang.ArithmeticException:"));
  }

  // public void testDump() {
  // XLogger logger = XLoggerFactory.getXLogger("UnitTest");
  // String dumpData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Document>\n
  // <Data>Test</Data>\n</Document>";
  // logger.
  //    
  // dump(logger, dumpData.getBytes());
  // String expected = "3c 3f 78 6d 6c 20 76 65 72 73 69 6f 6e 3d 22 31";
  // assertTrue(logMessage.trim().startsWith(expected));
  // }

  // public void testTimer() {
  // Timer timer = new Timer("TestTimer");
  // LoggerUtil.startTimer(timer);
  // assertEquals("Timer TestTimer started", logMessage);
  // LoggerUtil.pauseTimer(timer);
  // assertEquals("Timer TestTimer paused", logMessage);
  // LoggerUtil.resumeTimer(timer);
  // assertEquals("Timer TestTimer resumed", logMessage);
  // LoggerUtil.stopTimer(timer);
  // String expected = "Timer TestTimer stopped. Elapsed time:";
  // assertTrue("Expected \"" + expected + "\" Result \"" + logMessage + "\"",
  // logMessage.startsWith(expected));
  // }

}
