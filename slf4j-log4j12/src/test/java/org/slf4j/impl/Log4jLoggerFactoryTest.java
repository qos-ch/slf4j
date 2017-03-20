package org.slf4j.impl;

import org.apache.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogManager.class, Log4jLoggerAdapter.class, Log4jLoggerFactory.class})
public class Log4jLoggerFactoryTest {

  @Test
  public void testGetLogger() throws Exception {
    //when
    Log4jLoggerFactory log4jLoggerFactory = new Log4jLoggerFactory();
    Logger logger = log4jLoggerFactory.getLogger("TestLogger");

    //then
    assertNotNull("getLogger() must return an instance of Logger ", logger);
  }

  @Test
  public void testGetLogger_WithSameNameAsRootLogger() throws Exception {
    //setup
    mockStatic(LogManager.class);
    mockStatic(Log4jLoggerAdapter.class);
    org.apache.log4j.Logger rootLogger = mock(org.apache.log4j.Logger.class);
    PowerMockito.when(LogManager.class, "getRootLogger").thenReturn(rootLogger);
    Log4jLoggerAdapter expectedLogger = PowerMockito.mock(Log4jLoggerAdapter.class);
    whenNew(Log4jLoggerAdapter.class).withArguments(rootLogger).thenReturn(expectedLogger);

    //when
    Log4jLoggerFactory log4jLoggerFactory = new Log4jLoggerFactory();
    Logger logger = log4jLoggerFactory.getLogger("ROOT");

    //then
    assertEquals(expectedLogger, logger);
  }

  @Test
  public void testGetLogger_WithExistingLoggerInTheLoggerMap() throws Exception {
    //setup
    Log4jLoggerFactory log4jLoggerFactory = new Log4jLoggerFactory();
    Logger mockLogger = mock(Logger.class);
    Map loggerMap = new ConcurrentHashMap();
    loggerMap.put("MyLogger", mockLogger);
    setInternalState(log4jLoggerFactory, "loggerMap", loggerMap);

    //when
    Logger logger = log4jLoggerFactory.getLogger("MyLogger");

    //then
    assertEquals("The return instance of logger must be same as put in the map",
                 mockLogger, logger);

    //when
    logger = log4jLoggerFactory.getLogger("AnotherLogger");

    //then
    assertNotSame("AnotherLogger must be different from the mockLogger",
                  mockLogger, logger);

  }
}
