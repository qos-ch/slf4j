/**
 * Copyright (c) 2004-2021 QOS.ch
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
package org.slf4j.jdk.platform.logging.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.lang.System.LoggerFinder;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * The present test is fragile in the sense that it sets up SimpleLogger
 * with a StringPrintStream and reverts to the old stream when done.
 * 
 * Any tests running simultaneously (and using SimpleLogger) will be affected 
 * by this. Moreover, since SimpleLogger is initialized by the call to LoggerFactory
 * and tests also using LoggerFactory will also be affected.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
public class SLF4JPlatformLoggingTest {

    static final String PREFIX = "org.slf4j.simpleLogger.";
    static final String SIMPLE_LOGGER_FILE_PROPERTY = PREFIX + "logFile";
    static final String SIMPLE_LOGGER_THREAD_NAME_PROPERTY = PREFIX + "showThreadName";
    
    static final String EXPECTED_FINDER_CLASS = "org.slf4j.jdk.platform.logging.SLF4JSystemLoggerFinder";
    
    static int diff = new Random().nextInt(100*1000*1000);
   
    static final PrintStream oldErr = System.err;
    static StringPrintStream SPS = new StringPrintStream(oldErr, false);
    
    @BeforeClass
    static public void beforeClass() throws Exception {
        System.setErr(SPS);
        //System.setProperty(SIMPLE_LOGGER_FILE_PROPERTY, targetFile);
        System.setProperty(SIMPLE_LOGGER_THREAD_NAME_PROPERTY, "false");
    }

    @AfterClass
    static public void afterClass() {
        System.setErr(oldErr);
        System.clearProperty(SIMPLE_LOGGER_THREAD_NAME_PROPERTY);
    }

    @After
    public void tearDown() {
        SPS.stringList.clear();
    }
    
    @Test
    public void smoke() throws IOException {
        LoggerFinder finder = System.LoggerFinder.getLoggerFinder();
        assertEquals(EXPECTED_FINDER_CLASS, finder.getClass().getName());
        Logger systemLogger = finder.getLogger("smoke", null);
        systemLogger.log(Level.INFO, "hello");
        systemLogger.log(Level.INFO, "hello {0}", "world");
        
        List<String> results = SPS.stringList;
        assertEquals(2, results.size());
        assertEquals("INFO smoke - hello", results.get(0));
        assertEquals("INFO smoke - hello world", results.get(1));
    }

    @Test
    public void throwTest() throws IOException {
        LoggerFinder finder = System.LoggerFinder.getLoggerFinder();
        assertEquals(EXPECTED_FINDER_CLASS, finder.getClass().getName());

        Logger systemLogger = finder.getLogger("throwTest", null);
        systemLogger.log(Level.INFO, "we have a problem", new Exception());
        
        List<String> results = SPS.stringList;
        //INFO throwTest - a problem
        //java.lang.Exception
        //        at org.slf4j.jdk.platform.logging/org.slf4j.jdk.platform.logging.SLF4JPlatformLoggingTest.throwTest(SLF4JPlatformLoggingTest.java:92)

        int line = 0;
        //assertTrue(results.get(0).startsWith("SLF4J(I): Connected with provider of type ["));
        assertEquals("INFO throwTest - we have a problem", results.get(line++));
        assertEquals(Exception.class.getName(), results.get(line++));
        assertTrue(results.get(line).contains("at "));
        assertTrue(results.get(line++).contains(this.getClass().getName()));
    }

    @Test
    public void extremeLevels() throws IOException {
        LoggerFinder finder = System.LoggerFinder.getLoggerFinder();
        assertEquals(EXPECTED_FINDER_CLASS, finder.getClass().getName());
        Logger systemLogger = finder.getLogger("extremeLevels", null);
        systemLogger.log(Level.OFF, "hello");
        systemLogger.log(Level.ALL, "world");

        List<String> results = SPS.stringList;
        assertEquals(1, results.size());
        assertEquals("ERROR extremeLevels - hello", results.get(0));

    }

}
