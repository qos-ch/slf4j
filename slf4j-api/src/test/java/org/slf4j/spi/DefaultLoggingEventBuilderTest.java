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
