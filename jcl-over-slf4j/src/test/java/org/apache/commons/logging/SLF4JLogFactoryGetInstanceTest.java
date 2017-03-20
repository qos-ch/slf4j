package org.apache.commons.logging;

import org.apache.commons.logging.impl.SLF4JLocationAwareLog;
import org.apache.commons.logging.impl.SLF4JLog;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class, SLF4JLocationAwareLog.class, SLF4JLog.class, Logger.class, SLF4JLogFactory.class})
public class SLF4JLogFactoryGetInstanceTest {

  private SLF4JLogFactory slf4JLogFactory;

  private Logger logger;

  private SLF4JLog slf4JLog;

  private SLF4JLocationAwareLog slf4JLocationAwareLog;

  class Dummy {

    String name;

    public Dummy(String name) {
      this.name = name;
    }
  }

  @Before
  public void setup() throws Exception {
    slf4JLogFactory = PowerMockito.spy(new SLF4JLogFactory());
    mockStatic(LoggerFactory.class);
    mockStatic(SLF4JLog.class);
    mockStatic(SLF4JLocationAwareLog.class);

    slf4JLocationAwareLog = PowerMockito.mock(SLF4JLocationAwareLog.class);
    slf4JLog = PowerMockito.mock(SLF4JLog.class);

    whenNew(SLF4JLocationAwareLog.class).withAnyArguments().thenReturn(slf4JLocationAwareLog);
    whenNew(SLF4JLog.class).withAnyArguments().thenReturn(slf4JLog);
  }

  @Test
  public void testGetInstance_ForSLF4JLog() throws Exception {
    //setup
    logger = PowerMockito.mock(Logger.class);
    PowerMockito.when(LoggerFactory.getLogger(Matchers.anyString())).thenReturn(logger);

    //when
    Log log = slf4JLogFactory.getInstance(Dummy.class);

    //then
    assertEquals(slf4JLog, log);
  }

  @Test
  public void testGetInstance_ForSLF4JLocationAwareLog() throws Exception {
    //setup
    logger = PowerMockito.mock(LocationAwareLogger.class);
    PowerMockito.when(LoggerFactory.getLogger(Matchers.anyString())).thenReturn(logger);

    //when
    Log log = slf4JLogFactory.getInstance(Dummy.class);

    //then
    assertEquals(slf4JLocationAwareLog, log);
  }


  @Test
  public void testGetInstance_ForExistingInstance() throws Exception {
    //setup
    ConcurrentMap<String, Log> loggerMap = new ConcurrentHashMap<String, Log>();
    loggerMap.put(Dummy.class.toString(), slf4JLocationAwareLog);
    setInternalState(slf4JLogFactory, "loggerMap", loggerMap);
    logger = PowerMockito.mock(LocationAwareLogger.class);

    //when
    Log log = slf4JLogFactory.getInstance(Dummy.class);

    //then
    assertEquals(slf4JLog, log);
  }

  @Test
  public void testRelease() throws Exception {
    slf4JLogFactory.release();
    //It is a no op method so nothing can be verified or tested.
  }
}
