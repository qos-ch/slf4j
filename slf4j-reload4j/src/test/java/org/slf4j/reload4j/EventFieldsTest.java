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
