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

import org.slf4j.MDC;

import java.util.Stack;

/**
 * A log4j's NDC implemented in terms of SLF4J MDC primitives.
 *
 * @since SLF4J 1.6.0
 */

public class NDC {

    public final static String PREFIX = "NDC";

    public static void clear() {
        int depth = getDepth();
        for (int i = 0; i < depth; i++) {
            String key = PREFIX + i;
            MDC.remove(key);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Stack cloneStack() {
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static void inherit(Stack stack) {
    }

    static public String get() {
        return null;
    }

    public static int getDepth() {
        int i = 0;
        while (true) {
            String val = MDC.get(PREFIX + i);
            if (val != null) {
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    public static String pop() {
        int next = getDepth();
        if (next == 0) {
            return "";
        }
        int last = next - 1;
        String key = PREFIX + last;
        String val = MDC.get(key);
        MDC.remove(key);
        return val;
    }

    public static String peek() {
        int next = getDepth();
        if (next == 0) {
            return "";
        }
        int last = next - 1;
        String key = PREFIX + last;
        String val = MDC.get(key);
        return val;
    }

    public static void push(String message) {
        int next = getDepth();
        MDC.put(PREFIX + next, message);
    }

    static public void remove() {
        clear();
    }

    static public void setMaxDepth(int maxDepth) {
    }

}
