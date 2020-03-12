package org.slf4j.jdk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    public void loggerFinderLoadedAsService() {
        // this method asserts that a `LoggerFinder` of the correct type was loaded
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
        // this fails when test is executed from the module path
        // because the module declaration does not declare
        // `uses System.LoggerFinder`
        return ServiceLoader.load(System.LoggerFinder.class).stream()
                .filter(finderProvider -> SLF4JSystemLoggerFinder.class.isAssignableFrom(finderProvider.type()))
                .findAny()
                .map(ServiceLoader.Provider::get)
                .orElseThrow();
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
