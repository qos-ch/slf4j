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
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class SLF4JLocationAwareLogTest {

    private SLF4JLocationAwareLog slf4JLocationAwareLog;

    private LocationAwareLogger logger;

    private static final String FQCN = SLF4JLocationAwareLog.class.getName();

    private String message;

    private Exception ex;

    @Before
    public void setUp() throws Exception {
        message = "A simple message";
        ex = new Exception("An error message");
        logger = mock(LocationAwareLogger.class);
        slf4JLocationAwareLog = new SLF4JLocationAwareLog(logger);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsTraceEnabled() throws Exception {
        //when
        slf4JLocationAwareLog.isTraceEnabled();

        //then
        verify(logger).isTraceEnabled();
    }

    @Test
    public void testIsDebugEnabled() throws Exception {
        //when
        slf4JLocationAwareLog.isDebugEnabled();

        //then
        verify(logger).isDebugEnabled();
    }

    @Test
    public void testIsInfoEnabled() throws Exception {
        //when
        slf4JLocationAwareLog.isInfoEnabled();

        //then
        verify(logger).isInfoEnabled();
    }

    @Test
    public void testIsWarnEnabled() throws Exception {
        //when
        slf4JLocationAwareLog.isWarnEnabled();

        //then
        verify(logger).isWarnEnabled();
    }

    @Test
    public void testIsErrorEnabled() throws Exception {
        //when
        slf4JLocationAwareLog.isErrorEnabled();

        //then
        verify(logger).isErrorEnabled();
    }

    @Test
    public void testIsFatalEnabled() throws Exception {
        //when
        slf4JLocationAwareLog.isFatalEnabled();

        //then
        verify(logger).isErrorEnabled();
    }

    @Test
    public void testTrace() throws Exception {
        //when
        slf4JLocationAwareLog.trace(message);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), null, null);
    }

    @Test
    public void testTrace_WithMessageAndException() throws Exception {
        //when
        slf4JLocationAwareLog.trace(message, ex);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), null, ex);
    }

    @Test
    public void testDebug() throws Exception {
        //when
        slf4JLocationAwareLog.debug(message);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), null, null);
    }

    @Test
    public void testDebug_WithMessageAndException() throws Exception {
        //when
        slf4JLocationAwareLog.debug(message, ex);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), null, ex);
    }

    @Test
    public void testInfo() throws Exception {
        //when
        slf4JLocationAwareLog.info(message);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), null, null);
    }

    @Test
    public void testInfo_WithMessageAndException() throws Exception {
        //when
        slf4JLocationAwareLog.info(message, ex);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), null, ex);
    }

    @Test
    public void testWarn() throws Exception {
        //when
        slf4JLocationAwareLog.warn(message);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), null, null);
    }

    @Test
    public void testWarn_WithMessageAndException() throws Exception {
        //when
        slf4JLocationAwareLog.warn(message, ex);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), null, ex);
    }

    @Test
    public void testError() throws Exception {
        //when
        slf4JLocationAwareLog.error(message);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null, null);
    }

    @Test
    public void testError_WithMessageAndException() throws Exception {
        //when
        slf4JLocationAwareLog.error(message, ex);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null, ex);
    }

    @Test
    public void testFatal() throws Exception {
        //when
        slf4JLocationAwareLog.fatal(message);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null, null);
    }

    @Test
    public void testFatal_WithMessageAndException() throws Exception {
        //when
        slf4JLocationAwareLog.fatal(message, ex);

        //then
        verify(logger).log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), null, ex);
    }

    @Test
    public void testReadResolve() throws Exception {
        //setup
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.when(LoggerFactory.getLogger(Matchers.anyString())).thenReturn(logger);

        //when
        Object newObject = slf4JLocationAwareLog.readResolve();

        //then
        assertTrue("it must be an instance of Slf4JLocationAwareLog", newObject instanceof SLF4JLocationAwareLog);
        assertEquals(logger, getInternalState(newObject, "logger"));
        assertEquals(logger.getName(), getInternalState(newObject, "name"));
    }
}
