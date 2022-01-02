package org.slf4j.helpers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalMapOfStacks {

    final ThreadLocal<Map<String, Deque<String>>> tlMapOfStacks = new ThreadLocal<>();

    public void pushByKey(String key, String value) {
        if (key == null)
            return;

        Map<String, Deque<String>> map = tlMapOfStacks.get();

        if (map == null) {
            map = new HashMap<>();
            tlMapOfStacks.set(map);
        }

        Deque<String> stack = map.get(key);
        if (stack == null) {
            stack = new ArrayDeque<>();
        }
        stack.push(value);
        map.put(key, stack);
    }

    public String popByKey(String key) {
        if (key == null)
            return null;

        Map<String, Deque<String>> map = tlMapOfStacks.get();
        if (map == null)
            return null;
        Deque<String> stack = map.get(key);
        if (stack == null)
            return null;
        return stack.pop();
    }

    public Deque<String> getCopyOfStackByKey(String key) {
        if (key == null)
            return null;

        Map<String, Deque<String>> map = tlMapOfStacks.get();
        if (map == null)
            return null;
        Deque<String> stack = map.get(key);
        if (stack == null)
            return null;

        return new ArrayDeque<String>(stack);
    }

}
