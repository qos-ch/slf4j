package org.slf4j.jdk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.System.LoggerFinder;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
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
        System.Logger logger = getSLF4JSystemLogger();
        assertNotNull("LoggerFinder must return logger", logger);
    }

    @Test
    public void loggerLogsMessage() {
        System.Logger logger = getSLF4JSystemLogger();
        String message = "Test system logging!";
        logger.log(System.Logger.Level.INFO, message);

        String output = getOutput();

        assertTrue("Captured output should contain logged message.", output.contains(message));
    }

    private static System.LoggerFinder getSLF4JSystemLoggerFinder() {
        // this fails when test is executed from the module path
        // because the module declaration does not declare
        // `uses System.LoggerFinder`
        
        ServiceLoader<System.LoggerFinder> loader = ServiceLoader.load(System.LoggerFinder.class);
        Optional<Provider<LoggerFinder>> optional = loader.stream()
               .filter( provider -> SLF4JSystemLoggerFinder.class.isAssignableFrom(provider.type()))
               .findAny();
        
        if(optional.isPresent()) {
            Provider<LoggerFinder> p = optional.get();
            return p.get();
        } else {
            throw new ServiceConfigurationError("Could not find SLF4JSystemLoggerFinder");
        }
//        
//        return .stream()
//                .filter(finderProvider -> SLF4JSystemLoggerFinder.class.isAssignableFrom(finderProvider.type()))
//                .findAny()
//                .map(ServiceLoader.Provider::get)
//                .orElseThrow();
    }

    private static System.Logger getSLF4JSystemLogger() {
        LoggerFinder loggerFinder = getSLF4JSystemLoggerFinder();
        return loggerFinder.getLogger("TestLogger", SLF4JSystemLoggerTest.class.getModule());
    }

    private static String getOutput() {
        ERROR_OUT_REPLACEMENT.flush();
        return OUTPUT.toString();
    }

}
