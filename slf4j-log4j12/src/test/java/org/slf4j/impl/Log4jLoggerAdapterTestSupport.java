package org.slf4j.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class Log4jLoggerAdapterTestSupport {

  protected Log4jLoggerAdapter log4jLoggerAdapter;

  protected Logger logger;

  protected String message = "A test message";

  protected Dummy dummy = new Dummy("A dummy object");

  protected Dummy secondObject = new Dummy("Second dummy object");

  protected Dummy thirdObject = new Dummy("Third dummy object");

  protected Dummy fourthObject = new Dummy("Fourth dummy object");

  protected FormattingTuple ft = MessageFormatter.format(message, dummy);

  protected FormattingTuple ft2 = MessageFormatter.format(message, dummy, secondObject);

  protected FormattingTuple ftN = MessageFormatter.format(message, dummy, new Object[]{secondObject, thirdObject, fourthObject});

  protected Throwable th = new Throwable("A throwable object");

  public static final String FQCN = Log4jLoggerAdapter.class.getName();

  class Dummy {

    String name;

    public Dummy(String name) {
      this.name = name;
    }
  }

  @Before
  public void setup() {
    logger = Mockito.mock(Logger.class);
    log4jLoggerAdapter = new Log4jLoggerAdapter(logger);
  }

  @Test
  public void testIsTraceEnabled() throws Exception {
    //when
    log4jLoggerAdapter.isTraceEnabled();

    //then
    verify(logger, times(2)).isTraceEnabled();
  }

  @Test
  public void testTrace() throws Exception {
    //when
    log4jLoggerAdapter.trace(message);
    when(logger.isTraceEnabled()).thenReturn(true);

    //then
    verify(logger).log(FQCN, Level.TRACE, message, null);
  }

  @Test
  public void testTrace_withTraceEnabled_Object() throws Exception {
    //setup
    when(logger.isTraceEnabled()).thenReturn(true);

    //when
    log4jLoggerAdapter.trace(message, dummy);

    //then
    verify(logger).log(FQCN, Level.TRACE, ft.getMessage(), ft.getThrowable());
  }
}
