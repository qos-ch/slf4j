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

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import junit.framework.TestCase;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.MDC;
import org.slf4j.ext.EventData;
import org.slf4j.ext.EventLogger;

public class EventLoggerTest extends TestCase {

  ListAppender listAppender;
  org.apache.log4j.Logger log4;

  final static String EXPECTED_FILE_NAME = "EventLoggerTest.java";

  public EventLoggerTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();

    // start from a clean slate for each test

    listAppender = new ListAppender();
    listAppender.extractLocationInfo = true;
    org.apache.log4j.Logger eventLogger =
        org.apache.log4j.Logger.getLogger("EventLogger");
    eventLogger.addAppender(listAppender);
    eventLogger.setLevel(org.apache.log4j.Level.TRACE);
    eventLogger.setAdditivity(false);
    // Items that apply to any activity
    MDC.put("ipAddress", "192.168.1.110");
    MDC.put("login", "TestUSer");
    MDC.put("hostname", "localhost");
    MDC.put("productName", "SLF4J");
    MDC.put("locale", Locale.getDefault().getDisplayName());
    MDC.put("timezone", TimeZone.getDefault().getDisplayName());

  }

  public void tearDown() throws Exception {
    super.tearDown();
    MDC.clear();
  }

  void verify(LoggingEvent le, String expectedMsg) {
    assertEquals(expectedMsg, le.getMessage());
    assertEquals(EXPECTED_FILE_NAME, le.getLocationInformation().getFileName());
  }


  public void testEventLogger() {
    EventData data[] = new EventData[2];
    data[0] = new EventData();
    data[0].setEventType("Login");
    data[0].setEventId("1");
    data[0].setEventDateTime(new Date());
    data[0].put("Userid", "TestUser");
    EventLogger.logEvent(data[0]);

    data[1] = new EventData();
    data[1].setEventType("Update");
    data[1].setEventId("2");
    data[1].setEventDateTime(new Date());
    data[1].put("FileName", "/etc/hosts");
    EventLogger.logEvent(data[1]);

    assertEquals(2, listAppender.list.size());
    for (int i=0; i < 2; ++i) {
      LoggingEvent event = listAppender.list.get(i);
      verify(event, data[i].toXML());
      LocationInfo li = event.getLocationInformation();
      assertEquals(this.getClass().getName(), li.getClassName());
      assertEquals(event.getMDC("hostname"), "localhost");
    }
  }
}