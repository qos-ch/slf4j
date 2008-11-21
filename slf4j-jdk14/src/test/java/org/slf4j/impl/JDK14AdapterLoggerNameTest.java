/*
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.impl;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import junit.framework.TestCase;

public class JDK14AdapterLoggerNameTest extends TestCase {
  private MockHandler mockHandler;

  protected void setUp() throws Exception {
    super.setUp();
    Logger logger = Logger.getLogger("TEST");
    mockHandler = new MockHandler();
    removeHandlers(logger);
    logger.addHandler(mockHandler);
  }

  protected void tearDown() throws Exception {
    removeHandlers(Logger.getLogger("TEST"));
    super.tearDown();
  }

  public void testLoggerNameusingJdkLogging() throws Exception {
    Logger.getLogger("TEST").info("test message");
    assertCorrectLoggerName();

  }

  public void testLoggerNameUsingSlf4j() throws Exception {
    JDK14LoggerFactory factory = new JDK14LoggerFactory();
    org.slf4j.Logger logger = factory.getLogger("TEST");
    logger.info("test message");
    assertCorrectLoggerName();
  }

  private void removeHandlers(Logger logger) {
    logger.setUseParentHandlers(false);
    Handler[] handlers = logger.getHandlers();
    for (int i = 0; i < handlers.length; i++) {
      logger.removeHandler(handlers[i]);
    }
  }

  private void assertCorrectLoggerName() {
    assertNotNull("no log record", mockHandler.record);
    assertNotNull("missing logger name", mockHandler.record.getLoggerName());
  }

  private class MockHandler extends java.util.logging.Handler {
    public LogRecord record;

    public void close() throws SecurityException {
    }

    public void flush() {
    }

    public void publish(LogRecord record) {
      this.record = record;
    }

  }
}