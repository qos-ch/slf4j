package org.slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LoggerFactoryTest {
    private PrintStream rawSyserr;
    private ByteArrayOutputStream mockedSyserr;

    final ClassLoader classLoaderOfLoggerFactory = LoggerFactory.class.getClassLoader();

    @Before
    public void setUp() {
        rawSyserr = System.err;
        mockedSyserr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(mockedSyserr));
    }

    @After
    public void cleanUp() {
        System.clearProperty(LoggerFactory.PROVIDER_PROPERTY_KEY);
        System.setErr(rawSyserr);
    }

    @Test
    public void testExplicitlySpecified() {
        System.setProperty(LoggerFactory.PROVIDER_PROPERTY_KEY, "org.slf4j.LoggerFactoryTest$TestingProvider");
        SLF4JServiceProvider provider = LoggerFactory.loadExplicitlySpecified(classLoaderOfLoggerFactory);
        assertTrue("provider should be instance of TestingProvider class", provider instanceof  TestingProvider);
        assertTrue(mockedSyserr.toString().contains(" Attempting to load provider \"org.slf4j.LoggerFactoryTest$TestingProvider\" specified via \"slf4j.provider\" system property"));
        System.out.println(mockedSyserr.toString());


    }

    @Test
    public void testExplicitlySpecifiedNull() {
        assertNull(LoggerFactory.loadExplicitlySpecified(classLoaderOfLoggerFactory));
    }

    @Test
    public void testExplicitlySpecifyMissingServiceProvider() {
        System.setProperty(LoggerFactory.PROVIDER_PROPERTY_KEY, "com.example.ServiceProvider");
        SLF4JServiceProvider provider = LoggerFactory.loadExplicitlySpecified(classLoaderOfLoggerFactory);
        assertNull(provider);
        assertTrue(mockedSyserr.toString().contains("Failed to instantiate the specified SLF4JServiceProvider (com.example.ServiceProvider)"));
    }

    @Test
    public void testExplicitlySpecifyNonServiceProvider() {
        System.setProperty(LoggerFactory.PROVIDER_PROPERTY_KEY, "java.lang.String");
        assertNull(LoggerFactory.loadExplicitlySpecified(classLoaderOfLoggerFactory));
        assertTrue(mockedSyserr.toString().contains("Specified SLF4JServiceProvider (java.lang.String) does not implement SLF4JServiceProvider interface"));
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
        public String getRequestedApiVersion() {
            return null;
        }

        @Override
        public void initialize() {

        }
    }
}
