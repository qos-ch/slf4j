package org.slf4j.helpers;

import java.util.Map;

import org.slf4j.spi.MDCAdapter;

/**
 * This adapter is an empty implementation of the {@link MDCAdapter} interface.
 * It is used for all logging systems which do not support mapped
 * diagnostic contexts such as JDK14, simple and NOP. 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * 
 * @since 1.4.1
 */
public class NOPMakerAdapter implements MDCAdapter {

  public void clear() {
  }

  public String get(String key) {
    return null;
  }

  public void put(String key, String val) {
  }

  public void remove(String key) {
  }

  public Map getCopyOfContextMap() {
    return null;
  }

  public void setContextMap(Map contextMap) {
    // NOP
  }

}
