/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j.helpers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple implementation of ThreadLocal backed Map containing values of type 
 * Deque<String>.
 * 
 * @author Ceki Guuml;c&uuml;
 * @since 2.0.0
 */
public class ThreadLocalMapOfStacks {

    // BEWARE: Keys or values placed in a ThreadLocal should not be of a type/class
    // not included in the JDK. See also https://jira.qos.ch/browse/LOGBACK-450

    final ThreadLocal<Map<String, Deque<String>>> tlMapOfStacks = new ThreadLocal<>();

    public void pushByKey(String key, String value) {
        if (key == null)
            return;

        Map<String, Deque<String>> map = tlMapOfStacks.get();

        if (map == null) {
            map = new HashMap<>();
            tlMapOfStacks.set(map);
        }

        Deque<String> deque = map.get(key);
        if (deque == null) {
            deque = new ArrayDeque<>();
        }
        deque.push(value);
        map.put(key, deque);
    }

    public String popByKey(String key) {
        if (key == null)
            return null;

        Map<String, Deque<String>> map = tlMapOfStacks.get();
        if (map == null)
            return null;
        Deque<String> deque = map.get(key);
        if (deque == null)
            return null;
        return deque.pop();
    }

    public Deque<String> getCopyOfDequeByKey(String key) {
        if (key == null)
            return null;

        Map<String, Deque<String>> map = tlMapOfStacks.get();
        if (map == null)
            return null;
        Deque<String> deque = map.get(key);
        if (deque == null)
            return null;

        return new ArrayDeque<String>(deque);
    }
    
    /**
     * Clear the deque(stack) referenced by 'key'. 
     * 
     * @param key identifies the  stack
     * 
     * @since 2.0.0
     */
    public void clearDequeByKey(String key) {
        if (key == null)
            return;

        Map<String, Deque<String>> map = tlMapOfStacks.get();
        if (map == null)
            return;
        Deque<String> deque = map.get(key);
        if (deque == null)
            return;
        deque.clear();
    }

}
