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
package org.slf4j.impl;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;

import org.slf4j.helpers.MultithreadedInitializationTest;

public class Log4j12MultithreadedInitializationTest extends MultithreadedInitializationTest {
    static int NUM_LINES_BY_RECURSIVE_APPENDER = 3;
    
    // value of LogManager.DEFAULT_CONFIGURATION_KEY;
    static String CONFIG_FILE_KEY = "log4j.configuration";
    final String loggerName = this.getClass().getName();

    @Before
    public void setup() {
        System.setProperty(CONFIG_FILE_KEY, "recursiveInitWithActivationDelay.properties");
        System.out.println("THREAD_COUNT=" + THREAD_COUNT);
    }

    @After
    public void tearDown() throws Exception {
        System.clearProperty(CONFIG_FILE_KEY);
    }

    protected long getRecordedEventCount() {
        List<LoggingEvent> eventList = getRecordedEvents();
        assertNotNull(eventList);
        return eventList.size();
    }

    protected int extraLogEvents() {
        return NUM_LINES_BY_RECURSIVE_APPENDER;
    }
    
    private List<LoggingEvent> getRecordedEvents() {
        org.apache.log4j.Logger root = LogManager.getRootLogger();

        RecursiveAppender ra = (RecursiveAppender) root.getAppender("RECURSIVE");
        assertNotNull(ra);
        return ra.events;
    }

}
