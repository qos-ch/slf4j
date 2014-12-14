/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j;

import java.util.Hashtable;
import java.util.Map;

public class MDC {

  public static void put(String key, String value) {
    org.slf4j.MDC.put(key, value);
  }
  
  public static void put(String key, Object value) {
    if (value != null) {
      put(key, value.toString());
    } else {
      put(key, null);
    }
  }
  
  public static Object get(String key) {
    return org.slf4j.MDC.get(key);
  }
  
  public static void remove(String key) {
    org.slf4j.MDC.remove(key);
  }
  
  public static void clear() {
    org.slf4j.MDC.clear();
  }
  
  /** 
   * This method is not part of the Log4J public API. However it 
   * has been called by other projects. This method is here temporarily  
   * until projects who are depending on this method release fixes. 
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Deprecated
  public static Hashtable getContext() {
    Map map = org.slf4j.MDC.getCopyOfContextMap();

    if (map != null) {
      return new Hashtable(map);
    }
    else {
      return new Hashtable();
    }
  }
}
