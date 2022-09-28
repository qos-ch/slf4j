package org.slf4j.reload4j;

import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class EventFieldsTest {

    // value of LogManager.DEFAULT_CONFIGURATION_KEY;
    static String CONFIG_FILE_KEY = "log4j.configuration";

    @After
    public void tearDown() throws Exception {
        System.clearProperty(CONFIG_FILE_KEY);
    }

    @Test
    public void testWhetherEventsFieldsAreSet() {
        System.setProperty(CONFIG_FILE_KEY, "eventFields.properties");
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("hello");
        logger.atInfo().setMessage("hello").log();

        org.slf4j.reload4j.Reload4jLoggerAdapter rootReload4j = (org.slf4j.reload4j.Reload4jLoggerAdapter) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);


        ListAppender listAppender = (ListAppender) rootReload4j.logger.getAppender("LIST");

        assertNotNull(listAppender);
        assertNotNull(listAppender.list);

        List<LoggingEvent> eventList = listAppender.list;

        assertEquals(2, eventList.size());

        LoggingEvent loggingEvent0 = eventList.get(0);
        long timeStamp0 = loggingEvent0.getTimeStamp();
        String threadName0 = loggingEvent0.getThreadName();
        assertTrue(timeStamp0 != 0);
        assertNotNull(threadName0);
        assertFalse(threadName0.isEmpty());

        LoggingEvent loggingEvent1 = eventList.get(1);
        long timeStamp1 = loggingEvent1.getTimeStamp();
        String threadName1 = loggingEvent1.getThreadName();
        assertTrue(timeStamp1 != 0);
        assertTrue(timeStamp1 >= timeStamp0);
        assertNotNull(threadName1);
        assertFalse(threadName1.isEmpty());
        assertEquals(threadName0, threadName1);

    }


}
