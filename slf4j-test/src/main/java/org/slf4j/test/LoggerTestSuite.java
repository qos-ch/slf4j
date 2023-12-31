package org.slf4j.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public abstract class LoggerTestSuite {

    public static class ListAppendingOutputStream extends OutputStream {
        private final StringBuilder word = new StringBuilder();
        private final List<String> list;

        private ListAppendingOutputStream(List<String> list) {this.list = list;}


        @Override
        public void write(int b) throws IOException {
            word.append((char) b);
        }

        @Override
        public void flush() {
            list.add(word.toString());
            // Resets the string builder for a next log message
            word.delete(0, word.length());
        }
    }

    private ListAppendingOutputStream prepareSink(List<String> source) {
        return new ListAppendingOutputStream(source);

    }

    @Test
    public void testTrace() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.TRACE);

        assertTrue("Trace level should be enabled for this test", configuredLogger.isTraceEnabled());
        configuredLogger.trace("Simple trace message");

        assertEquals("Trace message should've been captured", 1, loggingEvents.size());
        assertTrue("Message should be logged in trace level", isLevel(loggingEvents.get(0), Level.TRACE));
        assertEquals("Supplied trace message wasn't found in the log",
                     "Simple trace message",
                     extractMessage(loggingEvents.get(0)));

        loggingEvents.clear();

        configuredLogger.debug("Simple debug message");
        configuredLogger.info("Simple info message");
        configuredLogger.warn("Simple warn message");
        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 4, loggingEvents.size());

    }

    @Test
    public void testDebug() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.DEBUG);

        configuredLogger.trace("Simple trace message");
        assertEquals("Lower levels should have been ignored", 0, loggingEvents.size());

        assertTrue("Debug level should be enabled for this test", configuredLogger.isDebugEnabled());
        configuredLogger.debug("Simple debug message");

        assertEquals("Debug message should've been captured", 1, loggingEvents.size());
        assertTrue("Message should be logged in debug level", isLevel(loggingEvents.get(0), Level.DEBUG));
        assertEquals("Supplied debug message wasn't found in the log",
                     "Simple debug message",
                     extractMessage(loggingEvents.get(0)));

        loggingEvents.clear();

        configuredLogger.info("Simple info message");
        configuredLogger.warn("Simple warn message");
        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 3, loggingEvents.size());
    }


    @Test
    public void testInfo() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.INFO);

        configuredLogger.trace("Simple trace message");
        configuredLogger.debug("Simple debug message");
        assertEquals("Lower levels should have been ignored", 0, loggingEvents.size());

        assertTrue("Info level should be enabled for this test", configuredLogger.isInfoEnabled());
        configuredLogger.info("Simple info message");

        assertEquals("Info message should've been captured", 1, loggingEvents.size());
        assertTrue("Message should be logged in debug level", isLevel(loggingEvents.get(0), Level.INFO));
        assertEquals("Supplied info message wasn't found in the log",
                     "Simple info message",
                     extractMessage(loggingEvents.get(0)));

        loggingEvents.clear();

        configuredLogger.warn("Simple warn message");
        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 2, loggingEvents.size());
    }

    @Test
    public void testWarn() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.WARN);

        configuredLogger.trace("Simple trace message");
        configuredLogger.debug("Simple debug message");
        configuredLogger.info("Simple info message");
        assertEquals("Lower levels should have been ignored", 0, loggingEvents.size());

        assertTrue("Warn level should be enabled for this test", configuredLogger.isWarnEnabled());
        configuredLogger.warn("Simple warn message");

        assertEquals("Warn message should've been captured", 1, loggingEvents.size());
        assertTrue("Message should be logged in warn level", isLevel(loggingEvents.get(0), Level.WARN));
        assertEquals("Supplied warn message wasn't found in the log",
                     "Simple warn message",
                     extractMessage(loggingEvents.get(0)));

        loggingEvents.clear();

        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 1, loggingEvents.size());
    }

    @Test
    public void testError() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.ERROR);

        configuredLogger.trace("Simple trace message");
        configuredLogger.debug("Simple debug message");
        configuredLogger.info("Simple info message");
        configuredLogger.warn("Simple warn message");
        assertEquals("Lower levels should have been ignored", 0, loggingEvents.size());

        assertTrue("Error level should be enabled for this test", configuredLogger.isErrorEnabled());
        configuredLogger.error("Simple error message");

        assertEquals("Error message should've been captured", 1, loggingEvents.size());
        assertTrue("Message should be logged in error level", isLevel(loggingEvents.get(0), Level.ERROR));
        assertEquals("Supplied error message wasn't found in the log",
                     "Simple error message",
                     extractMessage(loggingEvents.get(0)));
    }

    @Test
    public void testFormatting() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.INFO);

        configuredLogger.info("Some {} string", "formatted");
        assertEquals("The formatted message should've been captured", 1, loggingEvents.size());
        assertEquals("Message should've been formatted", "Some formatted string", extractMessage(loggingEvents.get(0)));
    }

    @Test
    public void testException() {
        ArrayList<String> loggingEvents = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(loggingEvents), Level.INFO);

        Exception exception = new RuntimeException("My error");

        configuredLogger.info("Logging with an exception", exception);
        assertEquals("The formatted message should've been captured", 1, loggingEvents.size());
        assertEquals("Message should've been formatted",
                     "My error",
                     extractExceptionMessage(loggingEvents.get(0)));

        assertEquals("Message should've been formatted",
                     "java.lang.RuntimeException",
                     extractExceptionType(loggingEvents.get(0)));
    }

    /**
     * Allows tests to check whether the logged message corresponds to the supplied level.
     * Override if needed.
     * @param message String containing the full log message
     * @param level {@link Level} the message is expected to be in
     * @return returns true if the level matches
     */
    protected boolean isLevel(String message, Level level) {
        return message.toUpperCase().contains(level.toString());
    }

    /**
     * Extracts only the part of the log string that should represent the `message` string.
     * @param message the full log message
     * @return only the supplied message
     */
    public abstract String extractMessage(String message);

    /**
     * Extracts only the part of the log string that should represent the supplied exception message, if any.
     * @param message the full log message
     * @return only the supplied exception message
     */
    public abstract String extractExceptionMessage(String message);

    /**
     * Extracts only the part of the log string that should represent the supplied exception type.
     * @param message the full log message
     * @return only the supplied exception type name
     */
    public abstract String extractExceptionType(String message);

    /**
     * Configures the logger for running the tests.
     * @param outputStream The output stream for logs to be written to
     * @param level The expected level the tests will run for this logger
     * @return a configured logger able to run the tests
     */
    public abstract Logger createLogger(ListAppendingOutputStream outputStream, Level level);

}