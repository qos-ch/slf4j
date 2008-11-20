package org.slf4j.dummyExt;

import junit.framework.TestCase;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

public class XLoggerTest extends TestCase {

  ListAppender listAppender;
  org.apache.log4j.Logger log4jRoot;

  final static String EXPECTED_FILE_NAME = "XLoggerTest.java";

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

  void verify(LoggingEvent le, String expectedMsg) {
    assertEquals(expectedMsg, le.getMessage());
    assertEquals(EXPECTED_FILE_NAME, le.getLocationInformation().getFileName());
  }

  void verifyWithException(LoggingEvent le, String expectedMsg, Throwable t) {
    verify(le, expectedMsg);
    assertEquals(t.toString(), le.getThrowableStrRep()[0]);
  }

  public void testEntering() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.entry();
    logger.entry(1);
    logger.entry("test");

    assertEquals(3, listAppender.list.size());
    verify((LoggingEvent) listAppender.list.get(0), "entry");
    verify((LoggingEvent) listAppender.list.get(1), "entry with (1)");
    verify((LoggingEvent) listAppender.list.get(2), "entry with (test)");
  }

  public void testExiting() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.exit();
    logger.exit(0);
    logger.exit(false);

    assertEquals(3, listAppender.list.size());
    verify((LoggingEvent) listAppender.list.get(0), "exit");
    verify((LoggingEvent) listAppender.list.get(1), "exit with (0)");
    verify((LoggingEvent) listAppender.list.get(2), "exit with (false)");
  }

  public void testThrowing() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    Throwable t = new UnsupportedOperationException("Test");
    logger.throwing(t);
    assertEquals(1, listAppender.list.size());
    verifyWithException((LoggingEvent) listAppender.list.get(0), "throwing", t);
  }

  public void testCaught() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    long x = 5;
    Throwable t = null;
    try {
      @SuppressWarnings("unused")
      long y = x / 0;
    } catch (Exception ex) {
      t = ex;
      logger.catching(ex);
    }
    verifyWithException((LoggingEvent) listAppender.list.get(0), "catching", t);
  }

  // See http://bugzilla.slf4j.org/show_bug.cgi?id=114
  public void testLocationExtraction_Bug114() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.exit();
    logger.debug("hello");

    assertEquals(2, listAppender.list.size());

    {
      LoggingEvent e = listAppender.list.get(0);
      LocationInfo li = e.getLocationInformation();
      assertEquals(this.getClass().getName(), li.getClassName());
      assertEquals("95", li.getLineNumber());
    }
    
    {
      LoggingEvent e = listAppender.list.get(1);
      LocationInfo li = e.getLocationInformation();
      assertEquals(this.getClass().getName(), li.getClassName());
      assertEquals("96", li.getLineNumber());
    }

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

}
