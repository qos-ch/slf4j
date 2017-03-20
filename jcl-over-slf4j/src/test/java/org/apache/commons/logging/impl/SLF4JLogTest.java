package org.apache.commons.logging.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class SLF4JLogTest {

    private SLF4JLog slf4JLog;

    private LocationAwareLogger logger;

    private String message;

    private Exception ex;

    @Before
    public void setUp() throws Exception {
        message = "A simple message";
        ex = new Exception("An error message");
        logger = mock(LocationAwareLogger.class);
        slf4JLog = new SLF4JLog(logger);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsDebugEnabled() throws Exception {
        //setup
        slf4JLog.isDebugEnabled();

        //then
        verify(logger).isDebugEnabled();
    }

    @Test
    public void testIsErrorEnabled() throws Exception {
        //when
        slf4JLog.isErrorEnabled();

        //then
        verify(logger).isErrorEnabled();
    }

    @Test
    public void testIsFatalEnabled() throws Exception {
        //setup
        slf4JLog.isFatalEnabled();

        //then
        verify(logger).isErrorEnabled();
    }

    @Test
    public void testIsInfoEnabled() throws Exception {
        //setup
        slf4JLog.isInfoEnabled();

        //then
        verify(logger).isInfoEnabled();
    }

    @Test
    public void testIsTraceEnabled() throws Exception {
        //setup
        slf4JLog.isTraceEnabled();

        //then
        verify(logger).isTraceEnabled();
    }

    @Test
    public void testIsWarnEnabled() throws Exception {
        //setup
        slf4JLog.isWarnEnabled();

        //then
        verify(logger).isWarnEnabled();
    }

    @Test
    public void testTrace() throws Exception {
        //when
        slf4JLog.trace(message);

        //then
        verify(logger).trace(String.valueOf(message));
    }

    @Test
    public void testTrace_withException() throws Exception {
        //when
        slf4JLog.trace(message, ex);

        //then
        verify(logger).trace(String.valueOf(message), ex);
    }

    @Test
    public void testDebug() throws Exception {
        //when
        slf4JLog.debug(message);

        //then
        verify(logger).debug(String.valueOf(message));
    }

    @Test
    public void testDebug_withException() throws Exception {
        //when
        slf4JLog.debug(message, ex);

        //then
        verify(logger).debug(String.valueOf(message), ex);
    }

    @Test
    public void testInfo() throws Exception {
        //when
        slf4JLog.info(message);

        //then
        verify(logger).info(String.valueOf(message));
    }

    @Test
    public void testInfo_withException() throws Exception {
        //when
        slf4JLog.info(message, ex);

        //then
        verify(logger).info(String.valueOf(message), ex);
    }

    @Test
    public void testWarn() throws Exception {
        //when
        slf4JLog.warn(message);

        //then
        verify(logger).warn(String.valueOf(message));
    }

    @Test
    public void testWarn_withException() throws Exception {
        //when
        slf4JLog.warn(message, ex);

        //then
        verify(logger).warn(String.valueOf(message), ex);
    }

    @Test
    public void testError() throws Exception {
        //when
        slf4JLog.error(message);

        //then
        verify(logger).error(String.valueOf(message));
    }

    @Test
    public void testError_withException() throws Exception {
        //when
        slf4JLog.error(message, ex);

        //then
        verify(logger).error(String.valueOf(message), ex);
    }

    @Test
    public void testFatal() throws Exception {
        //when
        slf4JLog.fatal(message);

        //then
        verify(logger).error(String.valueOf(message));
    }

    @Test
    public void testFatal_withException() throws Exception {
        //when
        slf4JLog.fatal(message, ex);

        //then
        verify(logger).error(String.valueOf(message), ex);
    }

    @Test
    public void testReadResolve() throws Exception {
        //setup
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.when(LoggerFactory.getLogger(Matchers.anyString())).thenReturn(logger);

        //when
        Object newLogger = slf4JLog.readResolve();

        //then
        assertTrue("it must be an instance of SLF4JLog", newLogger instanceof SLF4JLog);
        assertEquals(logger, getInternalState(newLogger, "logger"));
        assertEquals(logger.getName(), getInternalState(newLogger, "name"));
    }
}
