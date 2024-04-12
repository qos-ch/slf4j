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
