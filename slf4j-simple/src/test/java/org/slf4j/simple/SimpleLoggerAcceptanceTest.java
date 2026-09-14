package org.slf4j.simple;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Acceptance tests for the slf4j-simple SimpleLogger implementation.
 *
 * <p>This concrete JUnit test class exercises logging at different levels
 * (TRACE, DEBUG, INFO, WARN, ERROR), verifies message formatting, and
 * checks exception logging behavior. The class contains helper methods to
 * create a logger that writes into a supplied {@link ListAppendingOutputStream}
 * and to extract message and exception details from produced log lines.</p>
 *
 * <p>Important: these tests are predicated on the fact that {@code SimpleLogger}
 * invokes {@code flush()} on the provided output stream after writing each
 * log entry and that each logging event is accumulated into a single entry of
 * the backing {@code List<String>} fiels in {@link ListAppendingOutputStream}.
 * </p>
 *
 * <p>Each logging event — including events that contain a {@code Throwable}
 * and produce stacktrace lines — is accumulated into the {@code targetList}
 * field of {@link ListAppendingOutputStream} as a single entry representing
 * the entire event. Tests expect one list element per logging event.</p>
 *
 */
public class SimpleLoggerAcceptanceTest {

    /**
     * Create a {@link ListAppendingOutputStream} backed by the supplied list.
     *
     * <p>This helper returns an output stream that accumulates written text
     * lines into the provided list. Tests rely on the {@code SimpleLogger}
     * implementation to invoke {@code flush()} on the provided stream after
     * writing each log entry so that bytes are converted into list entries and
     * visible to assertions. </p>
     *
     * <p>Each logging event (including a {@code Throwable} and its stacktrace)
     * is collected by {@code ListAppendingOutputStream} into a single element
     * in its {@code targetList} field; tests expect one string element per event.</p>
     *
     * @param outputList the list which will receive captured log lines
     * @return a new ListAppendingOutputStream writing into the supplied list
     */
    private ListAppendingOutputStream prepareSink(List<String> outputList) {
        return new ListAppendingOutputStream(outputList);
    }

    /**
     * Extracts only the part of the log string that should represent the `message` string.
     *
     * @param message the full log message
     * @return only the supplied message
     */
    public String extractMessage(String message) {
        // Example log line:
        // [main] INFO TestSuiteLogger - An exception occurred: 99
        return message
                .split("\n")[0]
                .split("- ")[1];
    }

    /**
     * Extracts only the part of the log string that should represent the supplied exception message, if any.
     *
     * @param message the full log message
     * @return only the supplied exception message
     */
    public String extractExceptionMessage(String message) {
        // Example log lines:
        // [main] INFO TestSuiteLogger - An exception occurred: 99
        // java.lang.IllegalArgumentException: Invalid argument
        //  at org.slf4j.simple.SimpleLoggerAcceptanceTest.testExceptionParameterFormatting(SimpleLoggerAcceptanceTest.java:274)

        String[] logLines = message.split("\n");

        if(logLines.length < 2) {
            return null;
        }
        String exceptionLine = logLines[1];
        return exceptionLine.split(": ")[1];
    }

    /**
     * Extracts only the part of the log string that should represent the supplied exception type.
     *
     * @param message the full log message
     * @return only the supplied exception type name
     */
    public String extractExceptionType(String message) {
        String[] logLines = message.split("\n");

        if(logLines.length < 2) {
            return null;
        }
        String exceptionLine = logLines[1];
        return exceptionLine.split(": ")[0];
    }

    /**
     * Configures the logger for running the tests.
     *
     * @param outputStream The output stream for logs to be written to
     * @param level        The expected level the tests will run for this logger
     * @return a configured logger able to run the tests
     */
    public Logger createLogger(ListAppendingOutputStream outputStream, Level level) {
        SimpleLogger.CONFIG_PARAMS.outputChoice = new OutputChoice(new PrintStream(outputStream));

        SimpleLogger logger = new SimpleLogger("TestSuiteLogger");
        logger.currentLogLevel = SimpleLoggerConfiguration.stringToLevel(level.toString());
        return logger;
    }

    @Test
    public void testTrace() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.TRACE);

        assertTrue("Trace level should be enabled for this test", configuredLogger.isTraceEnabled());
        configuredLogger.trace("Simple trace message");

        assertEquals("Trace message should've been captured", 1, outputList.size());
        assertTrue("Message should be logged in trace level", isTraceMessage(outputList.get(0)));
        assertEquals("Supplied trace message wasn't found in the log",
                     "Simple trace message",
                     extractMessage(outputList.get(0)));

        outputList.clear();

        configuredLogger.debug("Simple debug message");
        configuredLogger.info("Simple info message");
        configuredLogger.warn("Simple warn message");
        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 4, outputList.size());

    }

    @Test
    public void testDebug() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.DEBUG);

        configuredLogger.trace("Simple trace message");
        assertEquals("Lower levels should have been ignored", 0, outputList.size());

        assertTrue("Debug level should be enabled for this test", configuredLogger.isDebugEnabled());
        configuredLogger.debug("Simple debug message");

        assertEquals("Debug message should've been captured", 1, outputList.size());
        assertTrue("Message should be logged in debug level", isDebugMessage(outputList.get(0)));
        assertEquals("Supplied debug message wasn't found in the log",
                     "Simple debug message",
                     extractMessage(outputList.get(0)));

        outputList.clear();

        configuredLogger.info("Simple info message");
        configuredLogger.warn("Simple warn message");
        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 3, outputList.size());
    }


    @Test
    public void testInfo() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.INFO);

        configuredLogger.trace("Simple trace message");
        configuredLogger.debug("Simple debug message");
        assertEquals("Lower levels should have been ignored", 0, outputList.size());

        assertTrue("Info level should be enabled for this test", configuredLogger.isInfoEnabled());
        configuredLogger.info("Simple info message");

        assertEquals("Info message should've been captured", 1, outputList.size());
        assertTrue("Message should be logged in debug level", isInfoMessage(outputList.get(0)));
        assertEquals("Supplied info message wasn't found in the log",
                     "Simple info message",
                     extractMessage(outputList.get(0)));

        outputList.clear();

        configuredLogger.warn("Simple warn message");
        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 2, outputList.size());
    }

    @Test
    public void testWarn() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.WARN);

        configuredLogger.trace("Simple trace message");
        configuredLogger.debug("Simple debug message");
        configuredLogger.info("Simple info message");
        assertEquals("Lower levels should have been ignored", 0, outputList.size());

        assertTrue("Warn level should be enabled for this test", configuredLogger.isWarnEnabled());
        configuredLogger.warn("Simple warn message");

        assertEquals("Warn message should've been captured", 1, outputList.size());
        assertTrue("Message should be logged in warn level", isWarnMessage(outputList.get(0)));
        assertEquals("Supplied warn message wasn't found in the log",
                     "Simple warn message",
                     extractMessage(outputList.get(0)));

        outputList.clear();

        configuredLogger.error("Simple error message");
        assertEquals("The other levels should have been captured", 1, outputList.size());
    }

    @Test
    public void testError() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.ERROR);

        configuredLogger.trace("Simple trace message");
        configuredLogger.debug("Simple debug message");
        configuredLogger.info("Simple info message");
        configuredLogger.warn("Simple warn message");
        assertEquals("Lower levels should have been ignored", 0, outputList.size());

        assertTrue("Error level should be enabled for this test", configuredLogger.isErrorEnabled());
        configuredLogger.error("Simple error message");

        assertEquals("Error message should've been captured", 1, outputList.size());
        assertTrue("Message should be logged in error level", isErrorMessage(outputList.get(0)));
        assertEquals("Supplied error message wasn't found in the log",
                     "Simple error message",
                     extractMessage(outputList.get(0)));
    }

    @Test
    public void testFormatting() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.INFO);

        configuredLogger.info("Some {} string", "formatted");
        assertEquals("The formatted message should've been captured", 1, outputList.size());
        assertEquals("Message should've been formatted", "Some formatted string", extractMessage(outputList.get(0)));
    }

    @Test
    public void testException() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.INFO);

        Exception exception = new RuntimeException("My error");

        configuredLogger.info("Logging with an exception", exception);
        assertEquals("The formatted message should've been captured", 1, outputList.size());
        assertEquals("Message should've been formatted",
                     "My error",
                     extractExceptionMessage(outputList.get(0)));

        assertEquals("Message should've been formatted",
                     "java.lang.RuntimeException",
                     extractExceptionType(outputList.get(0)));
    }


    @Test
    public void testExceptionParameterFormatting() {
        ArrayList<String> outputList = new ArrayList<>();
        Logger configuredLogger = createLogger(prepareSink(outputList), Level.INFO);

        Exception exception = new IllegalArgumentException("Invalid argument");

        configuredLogger.info("An exception occurred: {}", 99, exception);
        assertEquals("The formatted message should've been captured", 1, outputList.size());
        assertEquals("Message should've been formatted",
                     "An exception occurred: 99",
                     extractMessage(outputList.get(0)));

        assertEquals("Exception type should've been captured",
                     "java.lang.IllegalArgumentException",
                     extractExceptionType(outputList.get(0)));


        configuredLogger.info("Another exception occurred: {} {}", exception, 99);
        assertEquals("The formatted message should've been captured", 2, outputList.size());
        assertEquals("Message should've been formatted",
                     "Another exception occurred: java.lang.IllegalArgumentException: Invalid argument 99",
                     extractMessage(outputList.get(1)));

        AtomicInteger atomicInteger = new AtomicInteger();
        outputList.forEach(s -> System.out.println("x Log line " + (atomicInteger.getAndIncrement()) + ": " + s));
    }

    /**
     * Allows tests to check whether the log message contains a trace message.
     * Override if needed.
     *
     * @param message String containing the full log message
     * @return whether it is a trace message or not
     */
    protected boolean isTraceMessage(String message) {
        return message.toLowerCase().contains("trace");
    }

    /**
     * Allows tests to check whether the log message contains a debug message.
     * Override if needed.
     *
     * @param message String containing the full log message
     * @return whether it is a debug message or not
     */
    protected boolean isDebugMessage(String message) {
        return message.toLowerCase().contains("debug");
    }

    /**
     * Allows tests to check whether the log message contains an info message.
     * Override if needed.
     *
     * @param message String containing the full log message
     * @return whether it is an info message or not
     */
    protected boolean isInfoMessage(String message) {
        return message.toLowerCase().contains("info");
    }

    /**
     * Allows tests to check whether the log message contains a warn message.
     * Override if needed.
     *
     * @param message String containing the full log message
     * @return whether it is a warn message or not
     */
    protected boolean isWarnMessage(String message) {
        return message.toLowerCase().contains("warn");
    }

    /**
     * Allows tests to check whether the log message contains an error message.
     * Override if needed.
     *
     * @param message String containing the full log message
     * @return whether it is an error message or not
     */
    protected boolean isErrorMessage(String message) {
        return message.toLowerCase().contains("error");
    }

}
