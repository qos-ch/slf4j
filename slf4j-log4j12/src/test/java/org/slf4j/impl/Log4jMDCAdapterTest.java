package org.slf4j.impl;

import org.apache.log4j.MDC;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MDC.class})
public class Log4jMDCAdapterTest {

  private Log4jMDCAdapter log4jMDCAdapter;
  
  @Before
  public void setup(){
    log4jMDCAdapter = new Log4jMDCAdapter();
    PowerMockito.mockStatic(MDC.class);
  }

  @Test
  public void testGetCopyOfContextMap_withContextNotNull() throws Exception {
    //setup
    PowerMockito.mockStatic(MDC.class);
    Hashtable hashtable = new Hashtable();
    hashtable.put("aKey", "aVal");
    PowerMockito.when(MDC.class, "getContext").thenReturn(hashtable);

    //when
    Map map = log4jMDCAdapter.getCopyOfContextMap();

    //then
    assertEquals(new HashMap(hashtable), map);
  }

  @Test
  public void testGetCopyOfContextMap_withContextAsNull() throws Exception {
    //setup
    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when(MDC.class, "getContext").thenReturn(null);

    //when
    Map map = log4jMDCAdapter.getCopyOfContextMap();

    //then
    assertEquals(null, map);
  }

  @Test
  public void testSetContextMap_withOldContextNotNull() throws Exception {
    //setup
    PowerMockito.mockStatic(MDC.class);
    Hashtable<String,String> mdcContext= new Hashtable<String, String>();
    mdcContext.put("aKey", "aVal");
    mdcContext = Mockito.spy(mdcContext);
    PowerMockito.when(MDC.class, "getContext").thenReturn(mdcContext);

    Map<String, String> contextMap = new HashMap<String, String>();
    contextMap.put("newKey", "newVal");

    //when
    log4jMDCAdapter.setContextMap(contextMap);

    //then
    Mockito.verify(mdcContext).clear();
    Mockito.verify(mdcContext).putAll(contextMap);
  }

  @Test
  public void testSetContextMap_withOldContextAsNull() throws Exception {
    //setup
    PowerMockito.mockStatic(MDC.class);
    Hashtable<String,String> mdcContext= null;
    PowerMockito.when(MDC.class, "getContext").thenReturn(mdcContext);

    Map<String, String> contextMap = new HashMap<String, String>();
    contextMap.put("newKey1", "newVal1");
    contextMap.put("newKey2", "newVal2");

    //when
    log4jMDCAdapter.setContextMap(contextMap);

    //then
    PowerMockito.verifyStatic();
    MDC.put("newKey1", "newVal1");
    PowerMockito.verifyStatic();
    MDC.put("newKey2", "newVal2");
  }
}
