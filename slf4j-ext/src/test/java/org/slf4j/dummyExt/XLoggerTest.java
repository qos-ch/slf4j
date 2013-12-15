/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
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

  void verifyWithLevelAndException(LoggingEvent le, XLogger.Level level, String expectedMsg, Throwable t) {
    verify(le, expectedMsg);
    assertEquals(t.toString(), le.getThrowableStrRep()[0]);
    assertEquals(le.getLevel().toString(), level.toString());
  }

  public void testEntering() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.entry();
    logger.entry(1);
    logger.entry("test");
    logger.entry("a", "b", "c", "d");
    logger.entry("a", "b", "c", "d", "e");
    logger.entry("a", "b", "c", "d", "e", "f");

    assertEquals(6, listAppender.list.size());
    verify((LoggingEvent) listAppender.list.get(0), "entry");
    verify((LoggingEvent) listAppender.list.get(1), "entry with (1)");
    verify((LoggingEvent) listAppender.list.get(2), "entry with (test)");
  }

  public void testExiting() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    logger.exit();
    assertEquals(Integer.valueOf(0), logger.exit(0));
    assertEquals(Boolean.FALSE, logger.exit(false));

    assertEquals(3, listAppender.list.size());
    verify((LoggingEvent) listAppender.list.get(0), "exit");
    verify((LoggingEvent) listAppender.list.get(1), "exit with (0)");
    verify((LoggingEvent) listAppender.list.get(2), "exit with (false)");
  }

  public void testThrowing() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    Throwable t = new UnsupportedOperationException("Test");
    assertEquals(t, logger.throwing(t));
    assertEquals(t, logger.throwing(XLogger.Level.DEBUG,t));
    assertEquals(2, listAppender.list.size());
    verifyWithException((LoggingEvent) listAppender.list.get(0), "throwing", t);
    LoggingEvent event = (LoggingEvent)listAppender.list.get(1);
    verifyWithLevelAndException(event, XLogger.Level.DEBUG,
        "throwing", t);
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
      logger.catching(XLogger.Level.DEBUG, ex);
    }
    verifyWithException((LoggingEvent) listAppender.list.get(0), "catching", t);
    verifyWithLevelAndException((LoggingEvent) listAppender.list.get(1), XLogger.Level.DEBUG,
        "catching", t);
  }

  // See http://bugzilla.slf4j.org/show_bug.cgi?id=114
  public void testLocationExtraction_Bug114() {
    XLogger logger = XLoggerFactory.getXLogger("UnitTest");
    int line = 137; // next line is line number 134
    logger.exit(); 
    logger.debug("hello");

    assertEquals(2, listAppender.list.size());

    {
      LoggingEvent e = listAppender.list.get(0);
      LocationInfo li = e.getLocationInformation();
      assertEquals(this.getClass().getName(), li.getClassName());
      assertEquals(""+line, li.getLineNumber());
    }
    
    {
      LoggingEvent e = listAppender.list.get(1);
      LocationInfo li = e.getLocationInformation();
      assertEquals(this.getClass().getName(), li.getClassName());
      assertEquals(""+(line+1), li.getLineNumber());
    }

  }
}
