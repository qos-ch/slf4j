package org.slf4j.event;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.SubstituteLogger;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

public class EventRecodingLoggerTest {
    private Queue<SubstituteLoggingEvent> queue;
    private EventRecodingLogger logger;
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
        logger = new EventRecodingLogger(new SubstituteLogger("testLogger", queue, true), queue);
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
    public void traceMessage() {
        logger.trace(message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, null, null);
    }

    @Test
    public void traceMessageOneParameter() {
        logger.trace(message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, oneParam, null);
    }

    @Test
    public void traceMessageTwoParameters() {
        logger.trace(message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, twoParams, null);
    }

    @Test
    public void traceMessageThreeParameters() {
        logger.trace(message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, threeParams, null);
    }

    @Test
    public void traceMessageThrowable() {
        logger.trace(message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, null, exception);
    }

    @Test
    public void traceMessageOneParameterThrowable() {
        logger.trace(message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, oneParam, exception);
    }

    @Test
    public void traceMessageTwoParametersThrowable() {
        logger.trace(message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, twoParams, exception);
    }

    @Test
    public void traceMessageThreeParametersThrowable() {
        logger.trace(message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, threeParams, exception);
    }

    @Test
    public void traceMarkerMessage() {
        logger.trace(marker, message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, null, null);
    }

    @Test
    public void traceMarkerMessageOneParameter() {
        logger.trace(marker, message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, oneParam, null);
    }

    @Test
    public void traceMarkerMessageTwoParameters() {
        logger.trace(marker, message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, twoParams, null);
    }

    @Test
    public void traceMarkerMessageThreeParameters() {
        logger.trace(marker, message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, threeParams, null);
    }

    @Test
    public void traceMarkerMessageThrowable() {
        logger.trace(marker, message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, null, exception);
    }

    @Test
    public void traceMarkerMessageOneParameterThrowable() {
        logger.trace(marker, message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, oneParam, exception);
    }

    @Test
    public void traceMarkerMessageTwoParametersThrowable() {
        logger.trace(marker, message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, twoParams, exception);
    }

    @Test
    public void traceMarkerMessageThreeParametersThrowable() {
        logger.trace(marker, message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.TRACE, marker, threeParams, exception);
    }

    @Test
    public void debugMessage() {
        logger.debug(message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, null, null);
    }

    @Test
    public void debugMessageOneParameter() {
        logger.debug(message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, oneParam, null);
    }

    @Test
    public void debugMessageTwoParameters() {
        logger.debug(message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, twoParams, null);
    }

    @Test
    public void debugMessageThreeParameters() {
        logger.debug(message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, threeParams, null);
    }

    @Test
    public void debugMessageThrowable() {
        logger.debug(message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, null, exception);
    }

    @Test
    public void debugMessageOneParameterThrowable() {
        logger.debug(message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, oneParam, exception);
    }

    @Test
    public void debugMessageTwoParametersThrowable() {
        logger.debug(message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, twoParams, exception);
    }

    @Test
    public void debugMessageThreeParametersThrowable() {
        logger.debug(message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, threeParams, exception);
    }

    @Test
    public void debugMarkerMessage() {
        logger.debug(marker, message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, null, null);
    }

    @Test
    public void debugMarkerMessageOneParameter() {
        logger.debug(marker, message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, oneParam, null);
    }

    @Test
    public void debugMarkerMessageTwoParameters() {
        logger.debug(marker, message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, twoParams, null);
    }

    @Test
    public void debugMarkerMessageThreeParameters() {
        logger.debug(marker, message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, threeParams, null);
    }

    @Test
    public void debugMarkerMessageThrowable() {
        logger.debug(marker, message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, null, exception);
    }

    @Test
    public void debugMarkerMessageOneParameterThrowable() {
        logger.debug(marker, message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, oneParam, exception);
    }

    @Test
    public void debugMarkerMessageTwoParametersThrowable() {
        logger.debug(marker, message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, twoParams, exception);
    }

    @Test
    public void debugMarkerMessageThreeParametersThrowable() {
        logger.debug(marker, message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.DEBUG, marker, threeParams, exception);
    }

    @Test
    public void infoMessage() {
        logger.info(message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, null, null);
    }

    @Test
    public void infoMessageOneParameter() {
        logger.info(message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, oneParam, null);
    }

    @Test
    public void infoMessageTwoParameters() {
        logger.info(message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, twoParams, null);
    }

    @Test
    public void infoMessageThreeParameters() {
        logger.info(message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, threeParams, null);
    }

    @Test
    public void infoMessageThrowable() {
        logger.info(message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, null, exception);
    }

    @Test
    public void infoMessageOneParameterThrowable() {
        logger.info(message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, oneParam, exception);
    }

    @Test
    public void infoMessageTwoParametersThrowable() {
        logger.info(message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, twoParams, exception);
    }

    @Test
    public void infoMessageThreeParametersThrowable() {
        logger.info(message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, threeParams, exception);
    }

    @Test
    public void infoMarkerMessage() {
        logger.info(marker, message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, null, null);
    }

    @Test
    public void infoMarkerMessageOneParameter() {
        logger.info(marker, message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, oneParam, null);
    }

    @Test
    public void infoMarkerMessageTwoParameters() {
        logger.info(marker, message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, twoParams, null);
    }

    @Test
    public void infoMarkerMessageThreeParameters() {
        logger.info(marker, message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, threeParams, null);
    }

    @Test
    public void infoMarkerMessageThrowable() {
        logger.info(marker, message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, null, exception);
    }

    @Test
    public void infoMarkerMessageOneParameterThrowable() {
        logger.info(marker, message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, oneParam, exception);
    }

    @Test
    public void infoMarkerMessageTwoParametersThrowable() {
        logger.info(marker, message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, twoParams, exception);
    }

    @Test
    public void infoMarkerMessageThreeParametersThrowable() {
        logger.info(marker, message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.INFO, marker, threeParams, exception);
    }

    @Test
    public void warnMessage() {
        logger.warn(message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, null, null);
    }

    @Test
    public void warnMessageOneParameter() {
        logger.warn(message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, oneParam, null);
    }

    @Test
    public void warnMessageTwoParameters() {
        logger.warn(message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, twoParams, null);
    }

    @Test
    public void warnMessageThreeParameters() {
        logger.warn(message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, threeParams, null);
    }

    @Test
    public void warnMessageThrowable() {
        logger.warn(message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, null, exception);
    }

    @Test
    public void warnMessageOneParameterThrowable() {
        logger.warn(message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, oneParam, exception);
    }

    @Test
    public void warnMessageTwoParametersThrowable() {
        logger.warn(message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, twoParams, exception);
    }

    @Test
    public void warnMessageThreeParametersThrowable() {
        logger.warn(message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, threeParams, exception);
    }

    @Test
    public void warnMarkerMessage() {
        logger.warn(marker, message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, null, null);
    }

    @Test
    public void warnMarkerMessageOneParameter() {
        logger.warn(marker, message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, oneParam, null);
    }

    @Test
    public void warnMarkerMessageTwoParameters() {
        logger.warn(marker, message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, twoParams, null);
    }

    @Test
    public void warnMarkerMessageThreeParameters() {
        logger.warn(marker, message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, threeParams, null);
    }

    @Test
    public void warnMarkerMessageThrowable() {
        logger.warn(marker, message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, null, exception);
    }

    @Test
    public void warnMarkerMessageOneParameterThrowable() {
        logger.warn(marker, message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, oneParam, exception);
    }

    @Test
    public void warnMarkerMessageTwoParametersThrowable() {
        logger.warn(marker, message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, twoParams, exception);
    }

    @Test
    public void warnMarkerMessageThreeParametersThrowable() {
        logger.warn(marker, message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.WARN, marker, threeParams, exception);
    }

    @Test
    public void errorMessage() {
        logger.error(message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, null, null);
    }

    @Test
    public void errorMessageOneParameter() {
        logger.error(message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, oneParam, null);
    }

    @Test
    public void errorMessageTwoParameters() {
        logger.error(message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, twoParams, null);
    }

    @Test
    public void errorMessageThreeParameters() {
        logger.error(message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, threeParams, null);
    }

    @Test
    public void errorMessageThrowable() {
        logger.error(message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, null, exception);
    }

    @Test
    public void errorMessageOneParameterThrowable() {
        logger.error(message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, oneParam, exception);
    }

    @Test
    public void errorMessageTwoParametersThrowable() {
        logger.error(message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, twoParams, exception);
    }

    @Test
    public void errorMessageThreeParametersThrowable() {
        logger.error(message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, threeParams, exception);
    }

    @Test
    public void errorMarkerMessage() {
        logger.error(marker, message);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, null, null);
    }

    @Test
    public void errorMarkerMessageOneParameter() {
        logger.error(marker, message, param1);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, oneParam, null);
    }

    @Test
    public void errorMarkerMessageTwoParameters() {
        logger.error(marker, message, param1, param2);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, twoParams, null);
    }

    @Test
    public void errorMarkerMessageThreeParameters() {
        logger.error(marker, message, param1, param2, param3);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, threeParams, null);
    }

    @Test
    public void errorMarkerMessageThrowable() {
        logger.error(marker, message, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, null, exception);
    }

    @Test
    public void errorMarkerMessageOneParameterThrowable() {
        logger.error(marker, message, param1, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, oneParam, exception);
    }

    @Test
    public void errorMarkerMessageTwoParametersThrowable() {
        logger.error(marker, message, param1, param2, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, twoParams, exception);
    }

    @Test
    public void errorMarkerMessageThreeParametersThrowable() {
        logger.error(marker, message, param1, param2, param3, exception);

        SubstituteLoggingEvent event = queue.poll();

        verifyMessage(event, Level.ERROR, marker, threeParams, exception);
    }

    private void verifyMessage(SubstituteLoggingEvent event, Level level, Object[] arguments, Throwable exception) {
        verifyMessage(event, level, null, arguments, exception);
    }

    private void verifyMessage(SubstituteLoggingEvent event, Level level, Marker marker, Object[] arguments, Throwable exception) {
        assertNotNull(event);

        if (marker == null) {
            assertNull(event.getMarkers().get(0));
        } else {
            assertEquals(marker, event.getMarkers().get(0));
        }

        assertEquals(message, event.getMessage());

        if (arguments == null) {
            assertNull(event.getArgumentArray());
        } else {
            assertArrayEquals(arguments, event.getArgumentArray());
        }

        assertEquals(level, event.getLevel());

        if (exception == null) {
            assertNull(event.getThrowable());
        } else {
            assertEquals(exception, event.getThrowable());
        }
    }
}
