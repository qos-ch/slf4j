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
package org.slf4j.cal10n_dummy;

import java.util.Locale;

import junit.framework.TestCase;

import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;
import org.slf4j.dummyExt.ListAppender;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;

public class LocLoggerTest extends TestCase {

  ListAppender listAppender;
  org.apache.log4j.Logger log4jRoot;

  IMessageConveyor imc = new MessageConveyor(Locale.UK);
  LocLoggerFactory llFactory_uk = new LocLoggerFactory(imc);

  final static String EXPECTED_FILE_NAME = "LocLoggerTest.java";

  public LocLoggerTest(String name) {
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

  void verify(LoggingEvent le, String expectedMsg) {
    assertEquals(expectedMsg, le.getMessage());
    assertEquals(EXPECTED_FILE_NAME, le.getLocationInformation().getFileName());
  }
  
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSmoke() {
    LocLogger locLogger = llFactory_uk.getLocLogger(this.getClass());
    locLogger.info(Months.JAN);
    verify((LoggingEvent) listAppender.list.get(0), "January");
    
  }
}
