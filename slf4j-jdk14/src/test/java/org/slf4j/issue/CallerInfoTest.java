/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j.issue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventConstants;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.helpers.SubstituteServiceProvider;
import org.slf4j.jul.JDK14LoggerAdapter;
import org.slf4j.jul.ListHandler;
import org.slf4j.spi.CallerBoundaryAware;
import org.slf4j.spi.LoggingEventBuilder;

public class CallerInfoTest {
    Level oldLevel;
    java.util.logging.Logger root = java.util.logging.Logger.getLogger("");

    ListHandler listHandler = new ListHandler();

    @Before
    public void setUp() throws Exception {
        oldLevel = root.getLevel();
        root.setLevel(Level.FINE);
        // removeAllHandlers(root);
        root.addHandler(listHandler);
    }

    @After
    public void tearDown() throws Exception {
        root.setLevel(oldLevel);
        removeListHandlers(root);
    }

    void removeListHandlers(java.util.logging.Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler h : handlers) {
            if (h instanceof ListHandler)
                logger.removeHandler(h);
        }
    }

    @Test
    public void testCallerInfo() {
        Logger logger = LoggerFactory.getLogger("bla");
        logger.debug("hello");

        List<LogRecord> recordList = listHandler.recordList;

        assertEquals(1, recordList.size());

        LogRecord logRecod = recordList.get(0);
        assertEquals(this.getClass().getName(), logRecod.getSourceClassName());
    }

    // Do we preserve location info using fluent API?
    // See https://jira.qos.ch/browse/SLF4J-511

    @Test
    public void testCallerInfoWithFluentAPI() {
        Logger logger = LoggerFactory.getLogger("bla");
        logger.atDebug().log("hello");

        List<LogRecord> recordList = listHandler.recordList;

        assertEquals(1, recordList.size());

        LogRecord logRecod = recordList.get(0);
        assertEquals(this.getClass().getName(), logRecod.getSourceClassName());
        assertEquals("testCallerInfoWithFluentAPI", logRecod.getSourceMethodName());
    }

    @Test
    public void testCallerInfoWithFluentAPIAndAWrapper() {
        Logger logger = LoggerFactory.getLogger("bla");
        LoggingWrapper wrappedLogger = new LoggingWrapper(logger);

        wrappedLogger.logWithEvent("hello");

        List<LogRecord> recordList = listHandler.recordList;

        assertEquals(1, recordList.size());

        LogRecord logRecod = recordList.get(0);
        assertEquals(this.getClass().getName(), logRecod.getSourceClassName());
    }


    @Test
    public void testPostInitializationCallerInfoWithSubstituteLogger() {
        Logger logger = LoggerFactory.getLogger("bla");
        SubstituteLogger substituteLogger = new SubstituteLogger("bla", null, false);
        substituteLogger.setDelegate(logger);
        substituteLogger.debug("hello");

        List<LogRecord> recordList = listHandler.recordList;

        assertEquals(1, recordList.size());

        LogRecord logRecod = recordList.get(0);
        assertEquals(CallerInfoTest.class.getName(), logRecod.getSourceClassName());
    }

    // In this case we KNOW that we CANNOT KNOW the caller
    @Test
    public void testIntraInitializationCallerInfoWithSubstituteLogger() throws InterruptedException {
        SubstituteServiceProvider substituteServiceProvider = new SubstituteServiceProvider();
        String loggerName = "bkla";
        substituteServiceProvider.getLoggerFactory().getLogger(loggerName);
        SubstituteLogger substituteLogger = substituteServiceProvider.getSubstituteLoggerFactory().getLoggers().get(0);
        assertEquals(loggerName, substituteLogger.getName());

        substituteLogger.debug("jello");
        Logger logger = LoggerFactory.getLogger(loggerName);
        assertTrue(logger instanceof JDK14LoggerAdapter);
        substituteLogger.setDelegate(logger);

        final LinkedBlockingQueue<SubstituteLoggingEvent> queue = substituteServiceProvider.getSubstituteLoggerFactory().getEventQueue();

        SubstituteLoggingEvent substituteLoggingEvent = queue.take();
        assertTrue(substituteLogger.isDelegateEventAware());
        substituteLogger.log(substituteLoggingEvent);

        List<LogRecord> recordList = listHandler.recordList;

        assertEquals(1, recordList.size());

        LogRecord logRecod = recordList.get(0);
        assertEquals(EventConstants.NA_SUBST, logRecod.getSourceClassName());
    }

    static class LoggingWrapper {

        Logger underlyingLogger;

        LoggingWrapper(Logger aLogger) {
            this.underlyingLogger = aLogger;
        }
        public void logWithEvent(String msg) {
            LoggingEventBuilder lev = underlyingLogger.atInfo();
            // setting the caller boundary to LoggingWrapper
            if(lev instanceof CallerBoundaryAware) {
                // builder is CallerBoundaryAware
                ((CallerBoundaryAware) lev).setCallerBoundary(LoggingWrapper.class.getName());
            }
            lev.log(msg);
        }
    }

}
