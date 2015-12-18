package org.slf4j.impl;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.spi.MDCAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StaticMDCBinderTest {

  private StaticMDCBinder staticMDCBinder;

  @Before
  public void setUp() throws Exception {
    staticMDCBinder = StaticMDCBinder.SINGLETON;
  }

  @Test
  public void testGetMDCA() throws Exception {
    //when
    MDCAdapter mdcAdapter = staticMDCBinder.getMDCA();

    //then
    assertNotNull("getMDCA() must return an MDCAdapter instance", mdcAdapter);
  }

  @Test
  public void testGetMDCAdapterClassStr() throws Exception {
    //when
    String classStr = staticMDCBinder.getMDCAdapterClassStr();
    String exepctedClassStr = "org.slf4j.impl.Log4jMDCAdapter";

    //then
    assertEquals(exepctedClassStr, classStr);
  }
}
