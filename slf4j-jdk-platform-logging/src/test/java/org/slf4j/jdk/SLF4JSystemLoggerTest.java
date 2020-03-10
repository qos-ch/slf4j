package org.slf4j.jdk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.ServiceLoader;

import static org.junit.Assert.*;

public class SLF4JSystemLoggerTest {

    private static final PrintStream ERROR_OUT = System.err;
    private static final ByteArrayOutputStream OUTPUT = new ByteArrayOutputStream();
    private static final PrintStream ERROR_OUT_REPLACEMENT = new PrintStream(OUTPUT);

    @Before
    public void setUp() {
        System.setErr(ERROR_OUT_REPLACEMENT);
    }

    @After
    public void tearDown() {
        System.setErr(ERROR_OUT);
    }

    @Test
    public void loggerFinderLoadedAsOnlyService() {
        // this method asserts that there is exactly one `LoggerFinder` and its of the correct type
        getSLF4JSystemLoggerFinder();
    }

    @Test
    public void loggerFinderReturnsLogger() {
        var logger = getSLF4JSystemLogger();
        assertNotNull("LoggerFinder must return logger", logger);
    }

    @Test
    public void loggerLogsMessage() {
        var logger = getSLF4JSystemLogger();
        var message = "Test system logging!";
        logger.log(System.Logger.Level.INFO, message);

        String output = getOutput();

        assertTrue("Captured output should contain logged message.", output.contains(message));
    }

    private static System.LoggerFinder getSLF4JSystemLoggerFinder() {
        var loggerFinders = new ArrayList<System.LoggerFinder>();
        // this fails when test is executed from the module path
        // because the module declaration does not declare
        // `uses System.LoggerFinder`
        ServiceLoader.load(System.LoggerFinder.class).forEach(loggerFinders::add);
        assertEquals("There should be exactly one `LoggerFinder`.", 1, loggerFinders.size());
        var loggerFinder = loggerFinders.get(0);
        assertEquals("The `LoggerFinder` should be of type `SLF4JSystemLoggerFinder`.",
                     SLF4JSystemLoggerFinder.class,
                     loggerFinder.getClass());
        return loggerFinder;
    }

    private static System.Logger getSLF4JSystemLogger() {
        var loggerFinder = getSLF4JSystemLoggerFinder();
        return loggerFinder.getLogger("TestLogger", SLF4JSystemLoggerTest.class.getModule());
    }

    private static String getOutput() {
        ERROR_OUT_REPLACEMENT.flush();
        return OUTPUT.toString();
    }

}
