package org.slf4j.impl;

import org.apache.log4j.MDC;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MDC.class})
public class Log4jMDCAdapterTest {

  private Log4jMDCAdapter log4jMDCAdapter;
  
  @Before
  public void setup(){
    log4jMDCAdapter = new Log4jMDCAdapter();
  }
  
  @Test
  public void testGetCopyOfContextMap() throws Exception {
    //setup
    PowerMockito.mockStatic(MDC.class);
    Hashtable hashtable = new Hashtable();
    hashtable.put("aKey", "aVal");
    PowerMockito.when(MDC.class, "getContext").thenReturn(hashtable);

    //when
    Map map = log4jMDCAdapter.getCopyOfContextMap();

    //then
    Assert.assertEquals(new HashMap(hashtable), map);

    //TODO WIP add both cases--  like null and not null
  }

  @Test
  public void testSetContextMap() throws Exception {

  }
}
