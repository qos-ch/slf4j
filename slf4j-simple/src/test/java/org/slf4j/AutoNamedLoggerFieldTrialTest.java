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
package org.slf4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests that the automatically named logger field trial works and
 * doesn't cause problems or trigger if disabled.
 * <p>
 * This test can't live inside slf4j-api because the NOP Logger doesn't
 * remember its name.
 */
public class AutoNamedLoggerFieldTrialTest {

  private static final String MISMATCH_STRING = "Auto-named logger field trial: mismatch detected";

  private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
  private final PrintStream oldErr = System.err;

  @Before
  public void setUp() {
    System.setErr(new PrintStream(byteArrayOutputStream));
  }

  @After
  public void tearDown() {
    setTrialEnabled(false);
    System.setErr(oldErr);
  }

  /*
   * Pass in the wrong class to the Logger with the field trial disabled, and
   * make sure there are no errors.
   */
  @Test
  public void testNoTriggerWithoutProperty() {
    setTrialEnabled(false);
    Logger logger = LoggerFactory.getLogger(String.class);
    assertEquals("java.lang.String", logger.getName());
    assertIsMismatch(false);
  }

  /*
   * Pass in the wrong class to the Logger with the field trial enabled, and
   * make sure there ARE errors.
   */
  @Test
  public void testTriggerWithProperty() {
    setTrialEnabled(true);
    LoggerFactory.getLogger(String.class);
    assertIsMismatch(true);
  }

  /*
   * Checks the whole error message to ensure all the names show up correctly.
   */
  @Test
  public void testTriggerWholeMessage() {
    setTrialEnabled(true);
    LoggerFactory.getLogger(String.class);
    assertTrue(String.valueOf(byteArrayOutputStream).contains(
      "Auto-named logger field trial: mismatch detected between given logger " +
      "name and automatic logger name. Given name: \"java.lang.String\"; " +
      "automatic name: \"org.slf4j.AutoNamedLoggerFieldTrialTest\". If this " +
      "is unexpected, please file a bug against slf4j. Set property " +
      "org.slf4j.LoggerFactory.autoNamedLoggerFieldTrial to \"false\" to " +
      "disable this check."));
  }

  /*
   * Checks that there are no errors with the trial enabled if the
   * class matches.
   */
  @Test
  public void testPassIfMatch() {
    setTrialEnabled(true);
    Logger logger = LoggerFactory.getLogger(AutoNamedLoggerFieldTrialTest.class);
    assertEquals("org.slf4j.AutoNamedLoggerFieldTrialTest", logger.getName());
    assertIsMismatch(false);
  }

  private void assertIsMismatch(boolean isMismatch) {
    assertEquals(
        isMismatch,
        String.valueOf(byteArrayOutputStream).contains(MISMATCH_STRING));
  }

  private static void setTrialEnabled(boolean enabled) {
    // The system property is read into a static variable at initialization time
    // so we cannot just reset the system property to test this feature.
    // Therefore we set the variable directly.
    LoggerFactory.AUTO_NAMED_LOGGER_FIELD_TRIAL = enabled;
  }
}
