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
package org.slf4j.log4j12;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.MDCFriend;
import org.slf4j.helpers.ThreadLocalMapOfStacks;
import org.slf4j.spi.MDCAdapter;

public class Log4jMDCAdapter implements MDCAdapter {

    private final ThreadLocalMapOfStacks threadLocalMapOfStacks = new ThreadLocalMapOfStacks();
    
    static {
        if (VersionUtil.getJavaMajorVersion() >= 9) {
            MDCFriend.fixForJava9();
        }
    }

    @Override
    public void clear() {
        @SuppressWarnings("rawtypes")
        Map map = org.apache.log4j.MDC.getContext();
        if (map != null) {
            map.clear();
        }
    }

    @Override
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
     *             in case the "key" or <b>"val"</b> parameter is null
     */
    @Override
    public void put(String key, String val) {
        org.apache.log4j.MDC.put(key, val);
    }

    @Override
    public void remove(String key) {
        org.apache.log4j.MDC.remove(key);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getCopyOfContextMap() {
        Map old = org.apache.log4j.MDC.getContext();
        if (old != null) {
            return new HashMap(old);
        } else {
            return null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setContextMap(Map<String, String> contextMap) {
        Map old = org.apache.log4j.MDC.getContext();
        
        // we must cater for the case where the contextMap argument is null 
        if (contextMap == null) {
            if (old != null) {
                old.clear();
            }
            return;
        }
        
        if (old == null) {
            for (Map.Entry<String, String> mapEntry : contextMap.entrySet()) {
                org.apache.log4j.MDC.put(mapEntry.getKey(), mapEntry);
            }
        } else {
            old.clear();
            old.putAll(contextMap);
        }
    }

    @Override
    public void pushByKey(String key, String value) {
        threadLocalMapOfStacks.pushByKey(key, value);
    }

    @Override
    public String popByKey(String key) {
        return threadLocalMapOfStacks.popByKey(key);    
     }

    @Override
    public Deque<String> getCopyOfStackByKey(String key) {
        return threadLocalMapOfStacks.getCopyOfStackByKey(key);
    }
 
}
