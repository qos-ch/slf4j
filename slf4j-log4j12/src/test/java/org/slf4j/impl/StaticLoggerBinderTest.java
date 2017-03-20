package org.slf4j.impl;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.ILoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Level.class})
public class StaticLoggerBinderTest {

  private StaticLoggerBinder staticLoggerBinder;

  @Before
  public void setUp() throws Exception {
    staticLoggerBinder = StaticLoggerBinder.getSingleton();
  }

  @Test
  public void testGetSingleton() throws Exception {
    assertNotNull("getSingleton() must return an object", staticLoggerBinder);
  }

  @Test
  public void testGetSingleton_ShouldReturnSameInstanceEveryTime() throws Exception {
    //when
    StaticLoggerBinder firstResult = StaticLoggerBinder.getSingleton();
    StaticLoggerBinder secondResult = StaticLoggerBinder.getSingleton();

    //then
    assertEquals("getSingleton() must return same object every time", firstResult, secondResult);
  }

  @Test
  public void testGetLoggerFactory() throws Exception {
    //when
    ILoggerFactory iLoggerFactory = staticLoggerBinder.getLoggerFactory();

    //then
    assertNotNull("staticLoggerBinder.getLoggerFactory() must return a factory object", iLoggerFactory);
  }

  @Test
  public void testGetLoggerFactoryClassStr() throws Exception {
    //when
    String factoryClassStr = staticLoggerBinder.getLoggerFactoryClassStr();
    String expectedClassName = Log4jLoggerFactory.class.getName();

    //then
    assertEquals("factoryClass must be Log4jLoggerFactory", expectedClassName, factoryClassStr);
  }
}
