/* 
 * Copyright (c) 2004-2008 QOS.ch
 * All rights reserved.
 * 
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * 
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.slf4j.helpers;

import org.slf4j.spi.MDCAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic MDC implementation, which can be used with logging systems that lack
 * out-of-the-box MDC support.
 * 
 * This code is largely based on logback's <a
 * href="http://svn.qos.ch/viewvc/logback/trunk/logback-classic/src/main/java/org/slf4j/impl/LogbackMDCAdapter.java">
 * LogbackMDCAdapter</a>.
 * 
 * @author Ceki Gulcu
 * @author Maarten Bosteels
 * 
 * @since 1.5.0
 */
public class BasicMDCAdapter implements MDCAdapter {

  private InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();

  /**
   * Put a context value (the <code>val</code> parameter) as identified with
   * the <code>key</code> parameter into the current thread's context map.
   * Note that contrary to log4j, the <code>val</code> parameter can be null.
   * 
   * <p>
   * If the current thread does not have a context map it is created as a side
   * effect of this call.
   * 
   * @throws IllegalArgumentException
   *                 in case the "key" parameter is null
   */
  public void put(String key, String val) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    HashMap map = (HashMap) inheritableThreadLocal.get();
    if (map == null) {
      map = new HashMap();
      inheritableThreadLocal.set(map);
    }
    map.put(key, val);
  }

  /**
   * Get the context identified by the <code>key</code> parameter.
   */
  public String get(String key) {
    HashMap hashMap = (HashMap) inheritableThreadLocal.get();
    if ((hashMap != null) && (key != null)) {
      return (String) hashMap.get(key);
    } else {
      return null;
    }
  }

  /**
   * Remove the the context identified by the <code>key</code> parameter.
   */
  public void remove(String key) {
    HashMap map = (HashMap) inheritableThreadLocal.get();
    if (map != null) {
      map.remove(key);
    }
  }

  /**
   * Clear all entries in the MDC.
   */
  public void clear() {
    HashMap hashMap = (HashMap) inheritableThreadLocal.get();
    if (hashMap != null) {
      hashMap.clear();
      inheritableThreadLocal.remove();
    }
  }

  /**
   * Returns the keys in the MDC as a {@link Set} of {@link String}s The
   * returned value can be null.
   * 
   * @return the keys in the MDC
   */
  public Set getKeys() {
    HashMap hashMap = (HashMap) inheritableThreadLocal.get();
    if (hashMap != null) {
      return hashMap.keySet();
    } else {
      return null;
    }
  }
  /**
   * Return a copy of the current thread's context map. 
   * Returned value may be null.
   * 
   */
  public Map getCopyOfContextMap() {
    HashMap hashMap = (HashMap) inheritableThreadLocal.get();
    if (hashMap != null) {
      return new HashMap(hashMap);
    } else {
      return null;
    }
  }

  public void setContextMap(Map contextMap) {
    HashMap hashMap = (HashMap) inheritableThreadLocal.get();
    if (hashMap != null) {
      hashMap.clear();
      hashMap.putAll(contextMap);
    } else {
      hashMap = new HashMap(contextMap);
      inheritableThreadLocal.set(hashMap);
    }
  }

}
