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

package org.slf4j.spi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;
import org.slf4j.helpers.NOPLogger;

public class DefaultLoggingEventBuilderTest {

    @Test
    public void withCallerDataCapturesThisClass() {
        InspectableBuilder builder = new InspectableBuilder();
        builder.withCallerData().log("hello");

        StackTraceElement[] callerData = builder.event().getCallerData();
        assertNotNull(callerData);
        assertTrue(callerData.length > 0);
        assertEquals(DefaultLoggingEventBuilderTest.class.getName(), callerData[0].getClassName());
        assertEquals("withCallerDataCapturesThisClass", callerData[0].getMethodName());
    }

    @Test
    public void withCallerDataRespectsDepth() {
        InspectableBuilder builder = new InspectableBuilder();
        builder.withCallerData(1).log("hello");

        StackTraceElement[] callerData = builder.event().getCallerData();
        assertNotNull(callerData);
        assertEquals(1, callerData.length);
        assertEquals(DefaultLoggingEventBuilderTest.class.getName(), callerData[0].getClassName());
    }

    @Test
    public void withoutWithCallerDataCallerDataRemainsNull() {
        InspectableBuilder builder = new InspectableBuilder();
        builder.log("hello");
        assertNull(builder.event().getCallerData());
    }

    @Test
    public void withCallerDataHonoursCallerBoundary() {
        InspectableBuilder builder = new InspectableBuilder();
        new LoggingWrapper(builder).logWithCallerData("hello");

        StackTraceElement[] callerData = builder.event().getCallerData();
        assertNotNull(callerData);
        assertTrue(callerData.length > 0);
        assertEquals(DefaultLoggingEventBuilderTest.class.getName(), callerData[0].getClassName());
        assertEquals("withCallerDataHonoursCallerBoundary", callerData[0].getMethodName());
    }

    static class InspectableBuilder extends DefaultLoggingEventBuilder {
        InspectableBuilder() {
            super(NOPLogger.NOP_LOGGER, Level.INFO);
        }

        DefaultLoggingEvent event() {
            return loggingEvent;
        }
    }

    static class LoggingWrapper {
        final LoggingEventBuilder builder;

        LoggingWrapper(LoggingEventBuilder builder) {
            this.builder = builder;
        }

        void logWithCallerData(String msg) {
            if (builder instanceof CallerBoundaryAware) {
                ((CallerBoundaryAware) builder).setCallerBoundary(LoggingWrapper.class.getName());
            }
            builder.withCallerData().log(msg);
        }
    }
}
