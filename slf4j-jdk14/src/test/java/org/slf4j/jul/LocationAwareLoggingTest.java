package org.slf4j.jul;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.EventConstants;
import org.slf4j.spi.CallerBoundaryAware;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.Assert.assertEquals;

public class LocationAwareLoggingTest {
    ListHandler listHandler = new ListHandler();
    java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
    Level oldLevel;

    LoggerWrapper logger = new LoggerWrapper(getClass());

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
        for(Handler h : handlers) {
            if(h instanceof ListHandler)
                logger.removeHandler(h);
        }
    }

    @Test
    public void simpleMessage() {
        String msg = "Hello world.";
        logger.info(msg);
        assertLogMessage("Hello world.", 0);
        assertSourceClassName(LocationAwareLoggingTest.class.getName(), 0);
    }

    @Test
    public void messageViaEvent() {
        String msg = "Hello world.";
        logger.infoWithEvent(msg);
        assertLogMessage("Hello world.", 0);
        assertSourceClassName(LocationAwareLoggingTest.class.getName(), 0);
    }

    private void assertSourceClassName(String expected, int index) {
        LogRecord logRecord = listHandler.recordList.get(index);
        Assert.assertNotNull(logRecord);
        assertEquals(expected, logRecord.getSourceClassName());
    }

    private void assertLogMessage(String expected, int index) {
        LogRecord logRecord = listHandler.recordList.get(index);
        Assert.assertNotNull(logRecord);
        assertEquals(expected, logRecord.getMessage());
    }
}

class LoggerWrapper {
    static final String FQCN_WRAPPER = LoggerWrapper.class.getName();
    final Logger logger;

    LoggerWrapper(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String msg) {
        if(logger instanceof LocationAwareLogger)
            ((LocationAwareLogger) logger).log(null, FQCN_WRAPPER, EventConstants.INFO_INT, msg, new Object[0], null);
        else
            logger.info(msg);
    }

    public void infoWithEvent(String msg) {
        LoggingEventBuilder builder = logger.atInfo();
        if(builder instanceof CallerBoundaryAware)
            ((CallerBoundaryAware) builder).setCallerBoundary(FQCN_WRAPPER);

        builder.log(msg);
    }
}