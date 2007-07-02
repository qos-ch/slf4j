package org.slf4j.impl;

import java.util.Map;

import org.slf4j.spi.MDCAdapter;

public class Log4jMDCAdapter implements MDCAdapter {

  public void clear() {
    Map map = org.apache.log4j.MDC.getContext();
    if(map != null) {
      map.clear();
    }
  }

  public String get(String key) {
    return (String) org.apache.log4j.MDC.get(key);
  }

  public void put(String key, String val) {
    org.apache.log4j.MDC.put(key, val);
  }

  public void remove(String key) {
    org.apache.log4j.MDC.remove(key);
  }

}
