package org.slf4j.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.spi.MDCAdapter;

public class Log4jMDCAdapter implements MDCAdapter {

  public void clear() {
    Map map = org.apache.log4j.MDC.getContext();
    if (map != null) {
      map.clear();
    }
  }

  public String get(String key) {
    return (String) org.apache.log4j.MDC.get(key);
  }

  /**
   * Put a context value (the <code>val</code> parameter) as identified with
   * the <code>key</code> parameter into the current thread's context map. The
   * <code>key</code> parameter cannot be null. Log4j does <em>not</em> 
   * support null for the <code>val</code> parameter.
   * 
   * <p>
   * This method delegates all work to log4j's MDC.
   * 
   * @throws IllegalArgumentException
   *           in case the "key" or <b>"val"</b> parameter is null
   */
  public void put(String key, String val) {
    org.apache.log4j.MDC.put(key, val);
  }

  public void remove(String key) {
    org.apache.log4j.MDC.remove(key);
  }

  public Map getCopyOfContextMap() {
    Map old = org.apache.log4j.MDC.getContext();
    if(old != null) {
      return new HashMap(old);
    } else {
      return null;
    }
  }

  public void setContextMap(Map contextMap) {
    Map old = org.apache.log4j.MDC.getContext();
    if(old == null) {
      Iterator entrySetIterator = contextMap.entrySet().iterator();
      while(entrySetIterator.hasNext()) {
        Map.Entry mapEntry = (Map.Entry) entrySetIterator.next();
        org.apache.log4j.MDC.put((String) mapEntry.getKey(), mapEntry.getValue());
      }
    } else {
      old.clear();
      old.putAll(contextMap);
    }
  }
}
