package org.slf4j.eventTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Marker;
import org.slf4j.event.EventRecordingLogger;
import org.slf4j.event.Level;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.SubstituteLogger;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class EventRecordingLoggerTest {
    private Queue<SubstituteLoggingEvent> queue;
    private EventRecordingLogger logger;
    private String message;
    private Object param1;
    private Object param2;
    private Object param3;
    private Object[] oneParam;
    private Object[] twoParams;
    private Object[] threeParams;
    private Throwable exception;
    private Marker marker;

    @Before
    public void setUp() {
        queue = new LinkedBlockingQueue<>();
        logger = new EventRecordingLogger(new SubstituteLogger("testLogger", queue, true), queue);
        message = "Test message with 3 parameters {} {} {} {}";
        param1 = 1;
        param2 = 2;
        param3 = 3;
        oneParam = new Object[] { param1 };
        twoParams = new Object[] { param1, param2 };
        threeParams = new Object[] { param1, param2, param3 };
        exception = new IllegalStateException("We just need an exception");
        marker = new BasicMarkerFactory().getMarker("testMarker");
    }

    @After
    public void tearDown() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void singleMessage() {
        for (Level level : Level.values()) {
            singleMessageCheck(level);
        }
    }

    private void singleMessageCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message);
            break;
        case DEBUG:
            logger.debug(message);
            break;
        case INFO:
            logger.info(message);
            break;
        case WARN:
            logger.warn(message);
            break;
        case ERROR:
            logger.error(message);
            break;
        }
        verifyMessageWithoutMarker(level, null, null);
    }

    @Test
    public void oneParameter() {
        for (Level level : Level.values()) {
            oneParameterCheck(level);
        }
    }

    private void oneParameterCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, param1);
            break;
        case DEBUG:
            logger.debug(message, param1);
            break;
        case INFO:
            logger.info(message, param1);
            break;
        case WARN:
            logger.warn(message, param1);
            break;
        case ERROR:
            logger.error(message, param1);
            break;
        }
        verifyMessageWithoutMarker(level, oneParam, null);
    }

    @Test
    public void messageTwoParameters() {
        for (Level level : Level.values()) {
            messageTwoParametersCheck(level);
        }
    }

    private void messageTwoParametersCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, param1, param2);
            break;
        case DEBUG:
            logger.debug(message, param1, param2);
            break;
        case INFO:
            logger.info(message, param1, param2);
            break;
        case WARN:
            logger.warn(message, param1, param2);
            break;
        case ERROR:
            logger.error(message, param1, param2);
            break;
        }
        verifyMessageWithoutMarker(level, twoParams, null);
    }

    @Test
    public void traceMessageThreeParameters() {
        for (Level level : Level.values()) {
            threeParameterCheck(level);
        }
    }

    private void threeParameterCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, param1, param2, param3);
            break;
        case DEBUG:
            logger.debug(message, param1, param2, param3);
            break;
        case INFO:
            logger.info(message, param1, param2, param3);
            break;
        case WARN:
            logger.warn(message, param1, param2, param3);
            break;
        case ERROR:
            logger.error(message, param1, param2, param3);
            break;
        }
        verifyMessageWithoutMarker(level, threeParams, null);
    }

    @Test
    public void testMessageThrowable() {
        for (Level level : Level.values()) {
            throwableCheck(level);
        }
    }

    private void throwableCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, exception);
            break;
        case DEBUG:
            logger.debug(message, exception);
            break;
        case INFO:
            logger.info(message, exception);
            break;
        case WARN:
            logger.warn(message, exception);
            break;
        case ERROR:
            logger.error(message, exception);
            break;
        }
        verifyMessageWithoutMarker(level, null, exception);
    }

    @Test
    public void traceMessageOneParameterThrowable() {
        for (Level level : Level.values()) {
            oneParamThrowableCheck(level);
        }
    }

    private void oneParamThrowableCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, param1, exception);
            break;
        case DEBUG:
            logger.debug(message, param1, exception);
            break;
        case INFO:
            logger.info(message, param1, exception);
            break;
        case WARN:
            logger.warn(message, param1, exception);
            break;
        case ERROR:
            logger.error(message, param1, exception);
            break;
        }
        verifyMessageWithoutMarker(level, oneParam, exception);
    }

    @Test
    public void traceMessageTwoParametersThrowable() {
        for (Level level : Level.values()) {
            twoParamThrowableCheck(level);
        }
    }

    private void twoParamThrowableCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, param1, param2, exception);
            break;
        case DEBUG:
            logger.debug(message, param1, param2, exception);
            break;
        case INFO:
            logger.info(message, param1, param2, exception);
            break;
        case WARN:
            logger.warn(message, param1, param2, exception);
            break;
        case ERROR:
            logger.error(message, param1, param2, exception);
            break;
        }
        verifyMessageWithoutMarker(level, twoParams, exception);
    }

    @Test
    public void testMessageThreeParametersThrowable() {
        for (Level level : Level.values()) {
            messageWith3ArgsPlusException(level);
        }
    }

    private void messageWith3ArgsPlusException(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(message, param1, param2, param3, exception);
            break;
        case DEBUG:
            logger.debug(message, param1, param2, param3, exception);
            break;
        case INFO:
            logger.info(message, param1, param2, param3, exception);
            break;
        case WARN:
            logger.warn(message, param1, param2, param3, exception);
            break;
        case ERROR:
            logger.error(message, param1, param2, param3, exception);
            break;
        }
        verifyMessageWithoutMarker(level, threeParams, exception);
    }

    @Test
    public void markerMessage() {
        for (Level level : Level.values()) {
            markerMessageCheck(level);
        }
    }

    private void markerMessageCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message);
            break;
        case DEBUG:
            logger.debug(marker, message);
            break;
        case INFO:
            logger.info(marker, message);
            break;
        case WARN:
            logger.warn(marker, message);
            break;
        case ERROR:
            logger.error(marker, message);
            break;
        }
        verifyMessage(level, marker, null, null);
    }

    @Test
    public void markerMessageOneParameter() {
        for (Level level : Level.values()) {
            markerMessageOneParameter(level);
        }
    }

    private void markerMessageOneParameter(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, param1);
            break;
        case DEBUG:
            logger.debug(marker, message, param1);
            break;
        case INFO:
            logger.info(marker, message, param1);
            break;
        case WARN:
            logger.warn(marker, message, param1);
            break;
        case ERROR:
            logger.error(marker, message, param1);
            break;
        }
        verifyMessage(level, marker, oneParam, null);
    }

    @Test
    public void traceMarkerMessageTwoParameters() {
        for (Level level : Level.values()) {
            markerMessageTwoParameters(level);
        }
    }

    private void markerMessageTwoParameters(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, param1, param2);
            break;
        case DEBUG:
            logger.debug(marker, message, param1, param2);
            break;
        case INFO:
            logger.info(marker, message, param1, param2);
            break;
        case WARN:
            logger.warn(marker, message, param1, param2);
            break;
        case ERROR:
            logger.error(marker, message, param1, param2);
            break;
        }
        verifyMessage(level, marker, twoParams, null);
    }

    @Test
    public void traceMarkerMessageThreeParameters() {
        for (Level level : Level.values()) {
            markerMessageThreeParameters(level);
        }
    }

    private void markerMessageThreeParameters(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, param1, param2, param3);
            break;
        case DEBUG:
            logger.debug(marker, message, param1, param2, param3);
            break;
        case INFO:
            logger.info(marker, message, param1, param2, param3);
            break;
        case WARN:
            logger.warn(marker, message, param1, param2, param3);
            break;
        case ERROR:
            logger.error(marker, message, param1, param2, param3);
            break;
        }
        verifyMessage(level, marker, threeParams, null);
    }

    @Test
    public void markerMessageThrowable() {
        for (Level level : Level.values()) {
            markerMessageThrowable(level);
        }
    }

    private void markerMessageThrowable(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, exception);
            break;
        case DEBUG:
            logger.debug(marker, message, exception);
            break;
        case INFO:
            logger.info(marker, message, exception);
            break;
        case WARN:
            logger.warn(marker, message, exception);
            break;
        case ERROR:
            logger.error(marker, message, exception);
            break;
        }
        verifyMessage(level, marker, null, exception);
    }

    @Test
    public void markerMessageOneParameterThrowable() {
        for (Level level : Level.values()) {
            markerMessageOneParameterThrowableCheck(level);
        }
    }

    private void markerMessageOneParameterThrowableCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, param1, exception);
            break;
        case DEBUG:
            logger.debug(marker, message, param1, exception);
            break;
        case INFO:
            logger.info(marker, message, param1, exception);
            break;
        case WARN:
            logger.warn(marker, message, param1, exception);
            break;
        case ERROR:
            logger.error(marker, message, param1, exception);
            break;
        }
        verifyMessage(level, marker, oneParam, exception);
    }

    @Test
    public void traceMarkerMessageTwoParametersThrowable() {
        for (Level level : Level.values()) {
            markerMessageTwoParametersThrowableCheck(level);
        }
    }

    private void markerMessageTwoParametersThrowableCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, param1, param2, exception);
            break;
        case DEBUG:
            logger.debug(marker, message, param1, param2, exception);
            break;
        case INFO:
            logger.info(marker, message, param1, param2, exception);
            break;
        case WARN:
            logger.warn(marker, message, param1, param2, exception);
            break;
        case ERROR:
            logger.error(marker, message, param1, param2, exception);
            break;
        }
        verifyMessage(level, marker, twoParams, exception);
    }

    @Test
    public void traceMarkerMessageThreeParametersThrowable() {
        for (Level level : Level.values()) {
            markerMessageThreeParametersThrowableCheck(level);
        }
    }

    private void markerMessageThreeParametersThrowableCheck(Level level) {
        switch (level) {
        case TRACE:
            logger.trace(marker, message, param1, param2, param3, exception);
            break;
        case DEBUG:
            logger.debug(marker, message, param1, param2, param3, exception);
            break;
        case INFO:
            logger.info(marker, message, param1, param2, param3, exception);
            break;
        case WARN:
            logger.warn(marker, message, param1, param2, param3, exception);
            break;
        case ERROR:
            logger.error(marker, message, param1, param2, param3, exception);
            break;
        }
        verifyMessage(level, marker, threeParams, exception);
    }

    private void verifyMessageWithoutMarker(Level level, Object[] arguments, Throwable exception) {
        verifyMessage(level, null, arguments, exception);
    }

    private void verifyMessage(Level level, Marker marker, Object[] arguments, Throwable exception) {

        assertEquals("missing event: ", 1, queue.size());
        SubstituteLoggingEvent event = queue.poll();
        assertNotNull(event);

        if (marker == null) {
            assertNull(event.getMarkers());
        } else {
            assertEquals(marker, event.getMarkers().get(0));
        }

        assertEquals(message, event.getMessage());

        if (arguments == null) {
            assertNull(event.getArgumentArray());
        } else {
            assertArrayEquals(arguments, event.getArgumentArray());
        }

        assertEquals("wrong level: ", level, event.getLevel());

        if (exception == null) {
            assertNull(event.getThrowable());
        } else {
            assertEquals(exception, event.getThrowable());
        }
    }
}
