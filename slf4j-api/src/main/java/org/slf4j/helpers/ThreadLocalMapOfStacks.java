package org.slf4j.helpers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple implementation of ThreadLocal backed Map containing values of type 
 * Deque<Object>.
 * 
 * @author Ceki Guuml;c&uuml;
 * @since 2.0.0
 */
public class ThreadLocalMapOfStacks {

    // BEWARE: Keys or values placed in a ThreadLocal should not be of a type/class
    // not included in the JDK. See also https://jira.qos.ch/browse/LOGBACK-450

    final ThreadLocal<Map<String, Deque<Object>>> tlMapOfStacks = new ThreadLocal<>();

    public void pushByKey(String key, Object value) {
        if (key == null)
            return;

        Map<String, Deque<Object>> map = tlMapOfStacks.get();

        if (map == null) {
            map = new HashMap<>();
            tlMapOfStacks.set(map);
        }

        Deque<Object> deque = map.get(key);
        if (deque == null) {
            deque = new ArrayDeque<>();
        }
        deque.push(value);
        map.put(key, deque);
    }

    public Object popByKey(String key) {
        if (key == null)
            return null;

        Map<String, Deque<Object>> map = tlMapOfStacks.get();
        if (map == null)
            return null;
        Deque<Object> deque = map.get(key);
        if (deque == null)
            return null;
        return deque.pop();
    }

    public Deque<Object> getCopyOfDequeByKey(String key) {
        if (key == null)
            return null;

        Map<String, Deque<Object>> map = tlMapOfStacks.get();
        if (map == null)
            return null;
        Deque<Object> deque = map.get(key);
        if (deque == null)
            return null;

        return new ArrayDeque<Object>(deque);
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

        Map<String, Deque<Object>> map = tlMapOfStacks.get();
        if (map == null)
            return;
        Deque<Object> deque = map.get(key);
        if (deque == null)
            return;
        deque.clear();
    }

}
