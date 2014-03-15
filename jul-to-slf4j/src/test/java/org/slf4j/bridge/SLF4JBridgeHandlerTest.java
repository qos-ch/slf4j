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
package org.slf4j.bridge;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;

import junit.framework.TestCase;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

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
    SLF4JBridgeHandler.uninstall();
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

  public void testLogWithResourceBundle() {
    SLF4JBridgeHandler.install();

    String resourceBundleName = "org.slf4j.bridge.testLogStrings";
    ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName);
    String resourceKey = "resource_key";
    String expectedMsg = bundle.getString(resourceKey);
    String msg = resourceKey;

    java.util.logging.Logger julResourceBundleLogger = java.util.logging.Logger
        .getLogger("yay", resourceBundleName);

    julResourceBundleLogger.info(msg);
    assertEquals(1, listAppender.list.size());
    LoggingEvent le = (LoggingEvent) listAppender.list.get(0);
    assertEquals(LOGGER_NAME, le.getLoggerName());
    assertEquals(expectedMsg, le.getMessage());
  }

  public void testLogWithResourceBundleWithParameters() {
    SLF4JBridgeHandler.install();

    String resourceBundleName = "org.slf4j.bridge.testLogStrings";
    ResourceBundle bundle = ResourceBundle.getBundle(resourceBundleName);

    java.util.logging.Logger julResourceBundleLogger = java.util.logging.Logger
        .getLogger("foo", resourceBundleName);

    String resourceKey1 = "resource_key_1";
    String expectedMsg1 = bundle.getString(resourceKey1);
    julResourceBundleLogger.info(resourceKey1); // 1st log

    String resourceKey2 = "resource_key_2";
    Object[] params2 = new Object[] { "foo", "bar" };
    String expectedMsg2 = MessageFormat.format(bundle.getString(resourceKey2),
        params2);
    julResourceBundleLogger.log(Level.INFO, resourceKey2, params2); // 2nd log

    
    String resourceKey3 = "invalidKey {0}";
    Object[] params3 = new Object[] { "John" };
    String expectedMsg3 = MessageFormat.format(resourceKey3, params3);
    julResourceBundleLogger.log(Level.INFO, resourceKey3, params3); // 3rd log

    julLogger.log(Level.INFO, resourceKey3, params3);  // 4th log

    assertEquals(4, listAppender.list.size());

    LoggingEvent le = null;

    le = (LoggingEvent) listAppender.list.get(0);
    assertEquals("foo", le.getLoggerName());
    assertEquals(expectedMsg1, le.getMessage());

    le = (LoggingEvent) listAppender.list.get(1);
    assertEquals("foo", le.getLoggerName());
    assertEquals(expectedMsg2, le.getMessage());

    le = (LoggingEvent) listAppender.list.get(2);
    assertEquals("foo", le.getLoggerName());
    assertEquals(expectedMsg3, le.getMessage());

    le = (LoggingEvent) listAppender.list.get(3);
    assertEquals("yay", le.getLoggerName());
    assertEquals(expectedMsg3, le.getMessage());
  }

  public void testLogWithPlaceholderNoParameters() {
    SLF4JBridgeHandler.install();
    String msg = "msg {non-number-string}";
    julLogger.logp(Level.INFO, "SLF4JBridgeHandlerTest", "testLogWithPlaceholderNoParameters", msg, new Object[0]);

    assertEquals(1, listAppender.list.size());
    LoggingEvent le = (LoggingEvent) listAppender.list.get(0);
    assertEquals(LOGGER_NAME, le.getLoggerName());
    assertEquals(msg, le.getMessage());
  }

  void assertLevel(int index, org.apache.log4j.Level expectedLevel) {
    LoggingEvent le = (LoggingEvent) listAppender.list.get(index);
    assertEquals(expectedLevel, le.getLevel());
  }
}
