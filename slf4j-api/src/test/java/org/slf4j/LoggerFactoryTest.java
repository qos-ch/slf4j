package org.slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class LoggerFactoryTest {
    private PrintStream rawSyserr;
    private ByteArrayOutputStream mockedSyserr;

    @Before
    public void setUp() {
        rawSyserr = System.err;
        mockedSyserr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(mockedSyserr));
    }

    @After
    public void cleanUp() {
        System.setErr(rawSyserr);
    }

    @Test
    public void testExplicitlySpecified() {
        assertThat(LoggerFactory.loadExplicitlySpecified("org.slf4j.LoggerFactoryTest$TestingProvider"),
                   is(instanceOf(TestingProvider.class)));
    }

    @Test
    public void testExplicitlySpecifiedNull() {
        assertNull(LoggerFactory.loadExplicitlySpecified(null));
    }

    @Test
    public void testExplicitlySpecifyMissingServiceProvider() {
        assertNull(LoggerFactory.loadExplicitlySpecified("com.example.ServiceProvider"));
        assertThat(mockedSyserr.toString(),
                   containsString("Failed to instantiate the specified SLF4JServiceProvider (com.example.ServiceProvider)"));
    }

    @Test
    public void testExplicitlySpecifyNonServiceProvider() {
        assertNull(LoggerFactory.loadExplicitlySpecified("java.lang.String"));
        assertThat(mockedSyserr.toString(),
                   containsString("Specified SLF4JServiceProvider (java.lang.String) does not implement SLF4JServiceProvider interface"));
    }

    public static class TestingProvider implements SLF4JServiceProvider {
        @Override
        public ILoggerFactory getLoggerFactory() {
            return null;
        }

        @Override
        public IMarkerFactory getMarkerFactory() {
            return null;
        }

        @Override
        public MDCAdapter getMDCAdapter() {
            return null;
        }

        @Override
        public String getRequesteApiVersion() {
            return null;
        }

        @Override
        public void initialize() {

        }
    }
}
