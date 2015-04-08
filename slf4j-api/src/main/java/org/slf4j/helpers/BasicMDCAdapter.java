/**
 * Copyright (c) 2004-2011 QOS.ch
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
 *
 */
package org.slf4j.helpers;

import org.slf4j.spi.MDCAdapter;

import java.util.*;
import java.util.Map;

/**
 * Basic MDC implementation, which can be used with logging systems that lack
 * out-of-the-box MDC support.
 * 
 * This code was initially inspired by  logback's LogbackMDCAdapter. However,
 * LogbackMDCAdapter has evolved and is now considerably more sophisticated.
 *
 * @author Ceki Gulcu
 * @author Maarten Bosteels
 * 
 * @since 1.5.0
 */
public class BasicMDCAdapter implements MDCAdapter {

    private InheritableThreadLocal<Map<String, String>> inheritableThreadLocal = new InheritableThreadLocal<Map<String, String>>();

    static boolean isJDK14() {
        try {
            String javaVersion = System.getProperty("java.version");
            return javaVersion.startsWith("1.4");
        } catch (SecurityException se) {
            // punt and assume JDK 1.5 or later
            return false;
        }
    }

    static boolean IS_JDK14 = isJDK14();

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
        Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
        if (map == null) {
            map = Collections.<String, String> synchronizedMap(new HashMap<String, String>());
            inheritableThreadLocal.set(map);
        }
        map.put(key, val);
    }

    /**
     * Get the context identified by the <code>key</code> parameter.
     */
    public String get(String key) {
        Map<String, String> Map = (Map<String, String>) inheritableThreadLocal.get();
        if ((Map != null) && (key != null)) {
            return (String) Map.get(key);
        } else {
            return null;
        }
    }

    /**
     * Remove the the context identified by the <code>key</code> parameter.
     */
    public void remove(String key) {
        Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
        if (map != null) {
            map.remove(key);
        }
    }

    /**
     * Clear all entries in the MDC.
     */
    public void clear() {
        Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
        if (map != null) {
            map.clear();
            // the InheritableThreadLocal.remove method was introduced in JDK 1.5
            // Thus, invoking clear() on previous JDK 1.4 will fail
            if (isJDK14()) {
                inheritableThreadLocal.set(null);
            } else {
                inheritableThreadLocal.remove();
            }
        }
    }

    /**
     * Returns the keys in the MDC as a {@link Set} of {@link String}s The
     * returned value can be null.
     * 
     * @return the keys in the MDC
     */
    public Set<String> getKeys() {
        Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
        if (map != null) {
            return map.keySet();
        } else {
            return null;
        }
    }

    /**
     * Return a copy of the current thread's context map. 
     * Returned value may be null.
     * 
     */
    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> oldMap = (Map<String, String>) inheritableThreadLocal.get();
        if (oldMap != null) {
            Map<String, String> newMap = Collections.<String, String> synchronizedMap(new HashMap<String, String>());
            synchronized (oldMap) {
                newMap.putAll(oldMap);
            }
            return newMap;
        } else {
            return null;
        }
    }

    public void setContextMap(Map<String, String> contextMap) {
        Map<String, String> map = Collections.<String, String> synchronizedMap(new HashMap<String, String>(contextMap));
        inheritableThreadLocal.set(map);
    }

}
