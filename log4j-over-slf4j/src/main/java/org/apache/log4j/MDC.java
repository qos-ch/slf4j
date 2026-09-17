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
     * 
     * @return a copy of the underlying map returned as a Hashtable
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Deprecated
    public static Hashtable getContext() {
        Map map = org.slf4j.MDC.getCopyOfContextMap();

        if (map != null) {
            return new Hashtable(map);
        } else {
            return new Hashtable();
        }
    }
}
