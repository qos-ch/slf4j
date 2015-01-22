/**
 * Copyright (c) 2004-2012 QOS.ch
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
package org.slf4j.impl;

import mockit.Deencapsulation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.slf4j.impl.SimpleLogger.DATE_TIME_FORMAT_KEY;
import static org.slf4j.impl.SimpleLogger.LOG_KEY_PREFIX;
import static org.slf4j.impl.SimpleLogger.SHOW_DATE_TIME_KEY;

public class SimpleLoggerTest {

  String A_KEY = LOG_KEY_PREFIX + "a";

  @Before public void before() {
    System.setProperty(A_KEY, "info");
    Deencapsulation.setField(SimpleLogger.class, "INITIALIZED", false);
  }

  @After public void after() {
    System.clearProperty(A_KEY);
    System.clearProperty(SHOW_DATE_TIME_KEY);
    Deencapsulation.setField(SimpleLogger.class, "SHOW_DATE_TIME", false);
    System.clearProperty(DATE_TIME_FORMAT_KEY);
    Deencapsulation.setField(SimpleLogger.class, "DATE_TIME_FORMAT_STR", null);

    final ThreadLocal<DateFormat> formatThreadLocal = Deencapsulation.getField(SimpleLogger.class, "DATE_FORMATTER");
    formatThreadLocal.remove();
  }

  @Test
  public void emptyLoggerName() {
    SimpleLogger simpleLogger = new SimpleLogger("");
    assertNull(simpleLogger.recursivelyComputeLevelString());
  }

  @Test
  public void loggerNameWithNoDots_WithLevel() {
    SimpleLogger simpleLogger = new SimpleLogger("a");
    assertEquals("info", simpleLogger.recursivelyComputeLevelString());
  }

  @Test
  public void loggerNameWithOneDotShouldInheritFromParent() {
    SimpleLogger simpleLogger = new SimpleLogger("a.b");
    assertEquals("info", simpleLogger.recursivelyComputeLevelString());
  }


  @Test
  public void loggerNameWithNoDots_WithNoSetLevel() {
    SimpleLogger simpleLogger = new SimpleLogger("x");
    assertNull(simpleLogger.recursivelyComputeLevelString());
  }

  @Test
  public void loggerNameWithOneDot_NoSetLevel() {
    SimpleLogger simpleLogger = new SimpleLogger("x.y");
    assertNull(simpleLogger.recursivelyComputeLevelString());
  }

  @Test
  public void loggerDateFormatString_IsNull() {
    new SimpleLogger("a");
    ThreadLocal<DateFormat> formatThreadLocal = Deencapsulation.getField(SimpleLogger.class, "DATE_FORMATTER");
    assertFalse(formatThreadLocal.get() instanceof SimpleDateFormat);
  }

  @Test
  public void loggerDateFormatString_IsNotNull() {
    System.setProperty(DATE_TIME_FORMAT_KEY, "");
    new SimpleLogger("a");
    ThreadLocal<DateFormat> formatThreadLocal = Deencapsulation.getField(SimpleLogger.class, "DATE_FORMATTER");
    assertTrue(formatThreadLocal.get() instanceof SimpleDateFormat);
  }

  @Test
  public void loggerDateFormatString_IsWrong() {
    System.setProperty(DATE_TIME_FORMAT_KEY, "f00b4r!#&^$*(&!@^#($");
    new SimpleLogger("a");
    ThreadLocal<DateFormat> formatThreadLocal = Deencapsulation.getField(SimpleLogger.class, "DATE_FORMATTER");
    assertFalse(formatThreadLocal.get() instanceof SimpleDateFormat);
  }
}
