/* 
 * Copyright (c) 2004-2007 QOS.ch
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

package org.slf4j;

import java.util.Map;

import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMDCBinder;
import org.slf4j.spi.MDCAdapter;

/**
 * This class hides and serves as a substitute for the underlying logging
 * system's MDC implementation.
 * 
 * <p>
 * If the underlying logging system offers MDC functionality, then SLF4J's MDC,
 * i.e. this class, will delegate to the underlying system's MDC. Note that at
 * this time, only two logging systems, namely log4j and logback, offer MDC
 * functionality. If the underlying system does not support MDC, e.g. java.util.logging, 
 * then SLF4J will use a {@link BasicMDCAdapter}. 
 * 
 * <p>
 * Thus, as a SLF4J user, you can take advantage of MDC in the presence of log4j
 * logback, or java.util.logging, but without forcing these systems as dependencies 
 * upon your users.
 * 
 * <p>
 * For more information on MDC please see the <a
 * href="http://logback.qos.ch/manual/mdc.html">chapter on MDC</a> in the
 * logback manual.
 * 
 * <p>
 * Please note that all methods in this class are static.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.4.1
 */
public class MDC {

  static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
  static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
  static MDCAdapter mdcAdapter;

  private MDC() {
  }

  static {
    try {
      mdcAdapter = StaticMDCBinder.SINGLETON.getMDCA();
    } catch (NoClassDefFoundError ncde) {
      String msg = ncde.getMessage();
      if (msg != null && msg.indexOf("org/slf4j/impl/StaticMDCBinder") != -1) {
        Util
            .reportFailure("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
        Util.reportFailure("See " + NO_STATIC_MDC_BINDER_URL
            + " for further details.");

      }
      throw ncde;
    } catch (Exception e) {
      // we should never get here
      Util.reportFailure("Could not bind with an instance of class ["
          + StaticMDCBinder.SINGLETON.getMDCAdapterClassStr() + "]", e);
    }
  }

  /**
   * Put a context value (the <code>val</code> parameter) as identified with
   * the <code>key</code> parameter into the current thread's context map.
   * The <code>key</code> parameter cannot be null. The code>val</code> parameter 
   * can be null only if the underlying implementation supports it.
   * 
   * <p>
   * This method delegates all work to the MDC of the underlying logging system.
   * 
   * @throws IllegalArgumentException in case the "key" parameter is null
   */
  public static void put(String key, String val) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("key parameter cannot be null");
    }
    if (mdcAdapter == null) {
      throw new IllegalStateException("MDCAdapter cannot be null. See also "
          + NULL_MDCA_URL);
    }
    mdcAdapter.put(key, val);
  }

  /**
   * Get the context identified by the <code>key</code> parameter. The 
   * <code>key</code> parameter cannot be null.
   * 
   * <p>This method delegates all work to the MDC of the underlying logging system. 
   * 
   * @return the string value identified by the <code>key</code> parameter.
   * @throws IllegalArgumentException in case the "key" parameter is null
   */
  public static String get(String key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("key parameter cannot be null");
    }
    
    if (mdcAdapter == null) {
      throw new IllegalStateException("MDCAdapter cannot be null. See also "
          + NULL_MDCA_URL);
    }
    return mdcAdapter.get(key);
  }

  /**
   * Remove the the context identified by the <code>key</code> parameter using
   * the underlying system's MDC implementation. The  <code>key</code> parameter 
   * cannot be null. This method does nothing if there is no previous value 
   * associated with <code>key</code>.
   * 
   * @throws IllegalArgumentException in case the "key" parameter is null
   */
  public static void remove(String key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("key parameter cannot be null");
    }
    
    if (mdcAdapter == null) {
      throw new IllegalStateException("MDCAdapter cannot be null. See also "
          + NULL_MDCA_URL);
    }
    mdcAdapter.remove(key);
  }

  /** 
   * Clear all entries in the MDC of the underlying implementation.
   */
  public static void clear() {
    if (mdcAdapter == null) {
      throw new IllegalStateException("MDCAdapter cannot be null. See also "
          + NULL_MDCA_URL);
    }
    mdcAdapter.clear();
  }
  
  /**
   * Return a copy of the current thread's context map, with keys and 
   * values of type String. Returned value may be null.
   * 
   * @return A copy of the current thread's context map. May be null.
   * @since 1.5.1
   */
  public static Map getCopyOfContextMap() {
    if (mdcAdapter == null) {
      throw new IllegalStateException("MDCAdapter cannot be null. See also "
          + NULL_MDCA_URL);
    }
    return mdcAdapter.getCopyOfContextMap();
  }

  /**
   * Set the current thread's context map by first clearing any existing 
   * map and then copying the map passed as parameter. The context map passed
   * as parameter must only contain keys and values of type String.
   * 
   * @param contextMap must contain only keys and values of type String
   * @since 1.5.1
   */
  public static void setContextMap(Map contextMap) {
    if (mdcAdapter == null) {
      throw new IllegalStateException("MDCAdapter cannot be null. See also "
          + NULL_MDCA_URL);
    }
    mdcAdapter.setContextMap(contextMap);
  }
  
  
  /**
   * Returns the MDCAdapter instance currently in use.
   * 
   * @return the MDcAdapter instance currently in use.
   * @since 1.4.2
   */
  public static MDCAdapter getMDCAdapter() {
    return mdcAdapter;
  }
  
  
}