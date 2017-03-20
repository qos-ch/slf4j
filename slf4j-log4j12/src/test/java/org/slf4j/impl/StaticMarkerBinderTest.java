package org.slf4j.impl;

import org.junit.Test;
import org.slf4j.IMarkerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticMarkerBinderTest {

  @Test
  public void testGetMarkerFactory() throws Exception {
    //setup
    StaticMarkerBinder staticMarkerBinder = StaticMarkerBinder.SINGLETON;

    //when
    IMarkerFactory iMarkerFactory = staticMarkerBinder.getMarkerFactory();

    //then
    assertNotNull("getMarkerFactory() must return an instance", iMarkerFactory);

  }

  @Test
  public void testGetMarkerFactoryClassStr() throws Exception {
    //setup
    StaticMarkerBinder staticMarkerBinder = StaticMarkerBinder.SINGLETON;

    //when
    String classString = staticMarkerBinder.getMarkerFactoryClassStr();

    //then
    assertEquals("org.slf4j.helpers.BasicMarkerFactory", classString);
  }
}
