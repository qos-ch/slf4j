package org.slf4j.impl;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class Log4JLoggerAdapterTraceTest extends Log4jLoggerAdapterTestSupport {

  @Before
  public void setup() {
    super.setup();
  }

  private void setTraceEnabled() {
    Mockito.when(logger.isTraceEnabled()).thenReturn(true);
  }

  private void setTraceDisabled() {
    Mockito.when(logger.isTraceEnabled()).thenReturn(false);
  }

  private void setInfoEnabled() {
    Mockito.when(logger.isInfoEnabled()).thenReturn(true);
  }

  private void setInfoDisabled() {
    Mockito.when(logger.isInfoEnabled()).thenReturn(false);
  }

  private void setEnabled(Level level) {
    Mockito.when(logger.isEnabledFor(level)).thenReturn(true);
  }

  private void setDisabled(Level level) {
    Mockito.when(logger.isEnabledFor(level)).thenReturn(false);
  }

  @Test
  public void testIsTraceEnabled() throws Exception {
    //when
    log4jLoggerAdapter.isTraceEnabled();

    //then
    Mockito.verify(logger, Mockito.times(2)).isTraceEnabled();
  }

  @Test
  public void testIsDebugEnabled() throws Exception {
    //when
    log4jLoggerAdapter.isDebugEnabled();

    //then
    Mockito.verify(logger).isDebugEnabled();
  }

  @Test
  public void testIsInfoEnabled() throws Exception {
    //when
    log4jLoggerAdapter.isInfoEnabled();

    //then
    Mockito.verify(logger).isInfoEnabled();
  }

  @Test
  public void testIsWarnEnabled() throws Exception {
    //when
    log4jLoggerAdapter.isWarnEnabled();

    //then
    Mockito.verify(logger).isEnabledFor(Level.WARN);
  }

  @Test
  public void testIsErrorEnabled() throws Exception {
    //when
    log4jLoggerAdapter.isErrorEnabled();

    //then
    Mockito.verify(logger).isEnabledFor(Level.ERROR);
  }

  @Test
  public void testTrace() throws Exception {
    //when
    log4jLoggerAdapter.trace(message);

    //then
    Mockito.verify(logger).log(FQCN, Level.TRACE, message, null);
  }

  @Test
  public void testTrace_WithObject_TraceEnabled() throws Exception {
    //setup
    setTraceEnabled();

    //when
    log4jLoggerAdapter.trace(message, dummy);

    //then
    Mockito.verify(logger).log(FQCN, Level.TRACE, ft.getMessage(), ft.getThrowable());
  }

  @Test
  public void testTrace_WithObject_TraceDisabled() throws Exception {
    //setup
    setTraceDisabled();

    //when
    log4jLoggerAdapter.trace(message, dummy);

    //then
    Mockito.verify(logger, Mockito.times(2)).isTraceEnabled();
    Mockito.verifyNoMoreInteractions(logger);
  }

  @Test
  public void testTrace_WithTwoObjects_TraceEnabled() throws Exception {
    //setup
    setTraceEnabled();

    //when
    log4jLoggerAdapter.trace(message, dummy, secondObject);

    //then
    Mockito.verify(logger).log(FQCN, Level.TRACE, ft2.getMessage(), ft2.getThrowable());
  }

  @Test
  public void testTrace_WithTwoObjects_TraceDisabled() throws Exception {
    //setup
    setTraceDisabled();

    //when
    log4jLoggerAdapter.trace(message, dummy, secondObject);

    //then
    Mockito.verify(logger, Mockito.times(2)).isTraceEnabled();
    Mockito.verifyNoMoreInteractions(logger);
  }

  @Test
  public void testTrace_WithNObjects_TraceEnabled() throws Exception {
    //setup
    setTraceEnabled();

    //when
    log4jLoggerAdapter.trace(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).log(FQCN, Level.TRACE, ftN.getMessage(), ftN.getThrowable());
  }

  @Test
  public void testTrace_WithNObjects_TraceDisabled() throws Exception {
    //setup
    setTraceDisabled();

    //when
    log4jLoggerAdapter.trace(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger, Mockito.times(2)).isTraceEnabled();
    Mockito.verifyNoMoreInteractions(logger);
  }


  @Test
  public void testWarn_WithNObjects_WarnEnabled() throws Exception {
    //setup
    setEnabled(Level.WARN);

    //when
    log4jLoggerAdapter.warn(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).log(FQCN, Level.WARN, ftN.getMessage(), ftN.getThrowable());
  }

  @Test
  public void testWarn_WithNObjects_WarnDisabled() throws Exception {
    //setup
    setDisabled(Level.WARN);

    //when
    log4jLoggerAdapter.warn(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).isTraceEnabled();
    Mockito.verify(logger).isEnabledFor(Level.WARN);
    Mockito.verifyNoMoreInteractions(logger);
  }

  @Test
  public void testError_WithNObjects_ErrorEnabled() throws Exception {
    //setup
    setEnabled(Level.ERROR);

    //when
    log4jLoggerAdapter.error(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).log(FQCN, Level.ERROR, ftN.getMessage(), ftN.getThrowable());
  }

  @Test
  public void testError_WithNObjects_ErrorDisabled() throws Exception {
    //setup
    setDisabled(Level.ERROR);

    //when
    log4jLoggerAdapter.error(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).isTraceEnabled();
    Mockito.verify(logger).isEnabledFor(Level.ERROR);
    Mockito.verifyNoMoreInteractions(logger);
  }

  @Test
  public void testInfo_WithNObjects_InfoEnabled() throws Exception {
    //setup
    setInfoEnabled();

    //when
    log4jLoggerAdapter.info(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).log(FQCN, Level.INFO, ftN.getMessage(), ftN.getThrowable());
  }

  @Test
  public void testInfo_WithNObjects_InfoDisabled() throws Exception {
    //setup
    setInfoDisabled();

    //when
    log4jLoggerAdapter.info(message, dummy, secondObject, thirdObject, fourthObject);

    //then
    Mockito.verify(logger).isTraceEnabled();
    Mockito.verify(logger).isInfoEnabled();
    Mockito.verifyNoMoreInteractions(logger);
  }

  @Test
  public void testTrace_withThrowable() throws Exception {
    //when
    log4jLoggerAdapter.trace(message, th);

    //then
    Mockito.verify(logger).log(FQCN, Level.TRACE, message, th);
  }

  @Test
  public void testLog_Trace() throws Exception {
    //setup
    Marker marker = Mockito.mock(Marker.class);
    String callerFQCN = "callerFQCN";
    int priority = LocationAwareLogger.TRACE_INT;

    //when
    log4jLoggerAdapter.log(marker, callerFQCN, priority, message, new Object[]{dummy, secondObject, thirdObject, fourthObject}, th);

    //then
    Mockito.verify(logger).log(callerFQCN, Level.TRACE, message, th);

    //when
    priority = LocationAwareLogger.DEBUG_INT;
    log4jLoggerAdapter.log(marker, callerFQCN, priority, message, new Object[]{dummy, secondObject, thirdObject, fourthObject}, th);

    //then
    Mockito.verify(logger).log(callerFQCN, Level.DEBUG, message, th);

    //when
    priority = LocationAwareLogger.WARN_INT;
    log4jLoggerAdapter.log(marker, callerFQCN, priority, message, new Object[]{dummy, secondObject, thirdObject, fourthObject}, th);

    //then
    Mockito.verify(logger).log(callerFQCN, Level.WARN, message, th);

    //when
    priority = LocationAwareLogger.ERROR_INT;
    log4jLoggerAdapter.log(marker, callerFQCN, priority, message, new Object[]{dummy, secondObject, thirdObject, fourthObject}, th);

    //then
    Mockito.verify(logger).log(callerFQCN, Level.ERROR, message, th);

    //when
    priority = LocationAwareLogger.INFO_INT;
    log4jLoggerAdapter.log(marker, callerFQCN, priority, message, new Object[]{dummy, secondObject, thirdObject, fourthObject}, th);

    //then
    Mockito.verify(logger).log(callerFQCN, Level.INFO, message, th);

    //when
    priority = 100;
    try {
      log4jLoggerAdapter.log(marker, callerFQCN, priority, message, new Object[]{dummy, secondObject, thirdObject, fourthObject}, th);
      fail("An exception must have been thrown as we passed invalid level");
    } catch (Throwable ex) {
      //then
      assertTrue("Exception must be of type IllegalStateException", ex instanceof IllegalStateException);
    }
  }
}
