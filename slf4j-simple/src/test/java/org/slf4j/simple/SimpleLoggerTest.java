/**
 * Copyright (c) 2004-2022 QOS.ch Sarl (Switzerland)
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
package org.slf4j.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleLoggerTest {

    String A_KEY = SimpleLogger.LOG_KEY_PREFIX + "a";
    PrintStream original = System.out;
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    PrintStream replacement = new PrintStream(bout);

    @Before
    public void before() {
        System.setProperty(A_KEY, "info");
    }

    @After
    public void after() {
        System.clearProperty(A_KEY);
        System.clearProperty(SimpleLogger.CACHE_OUTPUT_STREAM_STRING_KEY);
        System.clearProperty(SimpleLogger.SHOW_THREAD_ID_KEY);
        System.clearProperty(SimpleLogger.SHOW_THREAD_NAME_KEY);
        System.setErr(original);
    }

    @Test
    public void emptyLoggerName() {
        SimpleLogger simpleLogger = new SimpleLogger("a");
        assertEquals("info", simpleLogger.recursivelyComputeLevelString());
    }

    @Test
    public void offLevel() {
        System.setProperty(A_KEY, "off");
        SimpleLogger.init();
        SimpleLogger simpleLogger = new SimpleLogger("a");
        assertEquals("off", simpleLogger.recursivelyComputeLevelString());
        assertFalse(simpleLogger.isErrorEnabled());
    }

    @Test
    public void loggerNameWithNoDots_WithLevel() {
        SimpleLogger.init();
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
    public void checkUseOfLastSystemStreamReference() {
        SimpleLogger.init();
        SimpleLogger simpleLogger = new SimpleLogger(this.getClass().getName());

        System.setErr(replacement);
        simpleLogger.info("hello");
        replacement.flush();
        assertTrue(bout.toString().contains("INFO " + this.getClass().getName() + " - hello"));
    }

    @Test
    public void checkUseOfCachedOutputStream() {
        System.setErr(replacement);
        System.setProperty(SimpleLogger.CACHE_OUTPUT_STREAM_STRING_KEY, "true");
        SimpleLogger.init();
        SimpleLogger simpleLogger = new SimpleLogger(this.getClass().getName());
        // change reference to original before logging
        System.setErr(original);

        simpleLogger.info("hello");
        replacement.flush();
        assertTrue(bout.toString().contains("INFO " + this.getClass().getName() + " - hello"));
    }

    @Test
    public void testTheadIdWithoutThreadName() {
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, Boolean.FALSE.toString());
        String patternStr = "^tid=\\d{1,12} INFO org.slf4j.simple.SimpleLoggerTest - hello";
        commonTestThreadId(patternStr);
    }

    @Test
    public void testThreadId() {
        String patternStr = "^\\[.*\\] tid=\\d{1,12} INFO org.slf4j.simple.SimpleLoggerTest - hello";
        commonTestThreadId(patternStr);
    }

    private void commonTestThreadId(String patternStr) {
        System.setErr(replacement);
        System.setProperty(SimpleLogger.SHOW_THREAD_ID_KEY, Boolean.TRUE.toString());
        SimpleLogger.init();
        SimpleLogger simpleLogger = new SimpleLogger(this.getClass().getName());
        simpleLogger.info("hello");
        replacement.flush();
        String output = bout.toString();
        System.out.println(patternStr);
        System.out.println(output);
        assertTrue(Pattern.compile(patternStr).matcher(output).lookingAt());
    }
}
