/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.slf4j;

public class NDC {
    public final static String PREFIX = "NDC";

    private static int size() {
        int i = 0;
        while(true) {
            String val = MDC.get(PREFIX + i);
            if(val != null) {
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    /**
     * Adds a new value to the current thread's nested diagnostic context. The
     * <code>context</code> parameter cannot be <code>null</code>.
     *
     * @param val a non-null value to add to the nested diagnostic context
     * @throws IllegalArgumentException in case the <code>val</code> parameter is null
     */
    public static void push(String val) {
        int next = size();
        MDC.put(PREFIX + next, val);
    }

    /**
     * Removes the last added value from the current thread's nested diagnostic context and returns it.
     * If the nested diagnostic context is empty, an empty String will be returned.
     *
     * @return the nested diagnostic context value removed, or an empty String if the nested diagnostic context is
     * empty.
     */
    public static String pop() {
        int next = size();
        if(next == 0) {
            return "";
        }
        int last = next - 1;
        String key = PREFIX + last;
        String val = MDC.get(key);
        MDC.remove(key);
        return val;
    }

    /**
     * Returns whether or not the provided context value is already included in the current thread's
     * nested diagnostic context. A <code>null</code> value will always return <code>false</code>.
     *
     * @param context the context to check for in the nested diagnostic context.
     * @return <code>true</code> if the provided context exists within the nested diagnostic context,
     * <code>false</code> if it does not exist or the provided context value was <code>null</code>.
     */
    public static boolean contains(String context) {
        if(context == null) {
            return false;
        }

        int i = 0;
        String val;
        while((val = MDC.get(PREFIX + i)) != null) {
            if(val.equals(context)) {
                return true;
            }
            i++;
        }
        return false;
    }

}
