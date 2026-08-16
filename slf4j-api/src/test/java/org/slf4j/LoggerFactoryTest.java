package org.slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
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

    @Test
    public void initializeIsInvokedBeforeGetMDCAdapter() {
        DelayedMdcInitProvider.reset();
        System.setProperty(LoggerFactory.PROVIDER_PROPERTY_KEY, DelayedMdcInitProvider.class.getName());
        LoggerFactory.reset();
        MDC.MDC_ADAPTER = null;
        try {
            ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
            assertTrue(loggerFactory instanceof NOPLoggerFactory);
            assertTrue(DelayedMdcInitProvider.initialized);
            assertFalse(DelayedMdcInitProvider.getMdcAdapterCalledBeforeInitialize);
            assertSame(DelayedMdcInitProvider.mdcAdapter, MDC.getMDCAdapter());
        } finally {
            LoggerFactory.reset();
            MDC.MDC_ADAPTER = null;
            DelayedMdcInitProvider.reset();
        }
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

    /**
     * Provider that creates its MDC adapter in {@code initialize()}, matching
     * implementations that throw if {@code getMDCAdapter()} is called first.
     */
    public static class DelayedMdcInitProvider implements SLF4JServiceProvider {
        static volatile boolean initialized;
        static volatile boolean getMdcAdapterCalledBeforeInitialize;
        static volatile MDCAdapter mdcAdapter;

        static void reset() {
            initialized = false;
            getMdcAdapterCalledBeforeInitialize = false;
            mdcAdapter = null;
        }

        private ILoggerFactory loggerFactory;
        private IMarkerFactory markerFactory;

        @Override
        public ILoggerFactory getLoggerFactory() {
            return loggerFactory;
        }

        @Override
        public IMarkerFactory getMarkerFactory() {
            return markerFactory;
        }

        @Override
        public MDCAdapter getMDCAdapter() {
            if (!initialized) {
                getMdcAdapterCalledBeforeInitialize = true;
                throw new IllegalStateException("getMDCAdapter() called before initialize()");
            }
            return mdcAdapter;
        }

        @Override
        public String getRequestedApiVersion() {
            return "2.0.99";
        }

        @Override
        public void initialize() {
            loggerFactory = new NOPLoggerFactory();
            markerFactory = new BasicMarkerFactory();
            mdcAdapter = new NOPMDCAdapter();
            initialized = true;
        }
    }
}
