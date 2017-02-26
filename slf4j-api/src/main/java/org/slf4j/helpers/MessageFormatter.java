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

import java.text.MessageFormat;
import java.util.IdentityHashMap;
import java.util.Map;

// contributors: lizongbo: proposed special treatment of array parameter values
// Joern Huxhorn: pointed out double[] omission, suggested deep array copy
/**
 * Formats messages according to very simple substitution rules. Substitutions
 * can be made 1, 2 or more arguments.
 *
 * <p>
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;)
 * </pre>
 *
 * will return the string "Hi there.".
 * <p>
 * The {} pair is called the <em>formatting anchor</em>. It serves to designate
 * the location where arguments need to be substituted within the message
 * pattern.
 * <p>
 * In case your message contains the '{' or the '}' character, you do not have
 * to do anything special unless the '}' character immediately follows '{'. For
 * example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set {1,2,3} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 *
 * will return the string "Set {1,2,3} is not equal to 1,2.".
 *
 * <p>
 * If for whatever reason you need to place the string "{}" in the message
 * without its <em>formatting anchor</em> meaning, then you need to escape the
 * '{' character with '\', that is the backslash character. Only the '{'
 * character should be escaped. There is no need to escape the '}' character.
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set \\{} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 *
 * will return the string "Set {} is not equal to 1,2.".
 *
 * <p>
 * The escaping behavior just described can be overridden by escaping the escape
 * character '\'. Calling
 *
 * <pre>
 * MessageFormatter.format(&quot;File name is C:\\\\{}.&quot;, &quot;file.zip&quot;);
 * </pre>
 *
 * will return the string "File name is C:\file.zip".
 *
 * <p>
 * The formatting conventions are different than those of {@link MessageFormat}
 * which ships with the Java platform. This is justified by the fact that
 * SLF4J's implementation is 10 times faster than that of {@link MessageFormat}.
 * This local performance difference is both measurable and significant in the
 * larger context of the complete logging processing chain.
 *
 * <p>
 * See also {@link #format(String, Object)},
 * {@link #format(String, Object, Object)} and
 * {@link #arrayFormat(String, Object[])} methods for more details.
 *
 * @author Ceki G&uuml;lc&uuml;
 * @author Joern Huxhorn
 */
final public class MessageFormatter {
    private static final char DELIM_START = '{';
    private static final char DELIM_END = '}';
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';
    private static final char[] ARRAY_SEPARATOR = ", ".toCharArray();
    private static final char[] NULL = "null".toCharArray();
    private static final Object[] EMPTY_ARRAY = {};

    /**
     * Performs single argument substitution for the 'messagePattern' passed as
     * parameter.
     * <p>
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;);
     * </pre>
     *
     * will return the string "Hi there.".
     * <p>
     *
     * @param messagePattern
     *          The message pattern which will be parsed and formatted
     * @param arg
     *          The argument to be substituted in place of the formatting anchor
     * @return The formatted message
     */
    final public static FormattingTuple format(String messagePattern, Object arg) {
        if (arg instanceof Throwable) {
            return arrayFormat(messagePattern, EMPTY_ARRAY, (Throwable) arg);
        }
        return arrayFormat(messagePattern, new Object[]{arg}, null);
    }

    /**
     *
     * Performs a two argument substitution for the 'messagePattern' passed as
     * parameter.
     * <p>
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}. My name is {}.&quot;, &quot;Alice&quot;, &quot;Bob&quot;);
     * </pre>
     *
     * will return the string "Hi Alice. My name is Bob.".
     *
     * @param messagePattern
     *          The message pattern which will be parsed and formatted
     * @param arg1
     *          The argument to be substituted in place of the first formatting
     *          anchor
     * @param arg2
     *          The argument to be substituted in place of the second formatting
     *          anchor
     * @return The formatted message
     */
    final public static FormattingTuple format(final String messagePattern, Object arg1, Object arg2) {
        if (arg2 instanceof Throwable) {
            return arrayFormat(messagePattern, new Object[]{arg1}, (Throwable) arg2);
        }
        return arrayFormat(messagePattern, new Object[]{arg1, arg2}, null);
    }

    final public static FormattingTuple arrayFormat(final String messagePattern, Object[] argArray) {
        if (argArray != null) {
            int cutLen = argArray.length - 1;
            Object throwableCandidate = cutLen >= 0 ? argArray[cutLen] : null;
            if (throwableCandidate instanceof Throwable) {
                Object[] cutArgs = new Object[cutLen];
                System.arraycopy(argArray, 0, cutArgs, 0, cutLen);
                return arrayFormat(messagePattern, cutArgs, (Throwable) throwableCandidate);
            }
        }
        return arrayFormat(messagePattern, argArray, null);
    }

    final public static FormattingTuple arrayFormat(final String msg, final Object[] argArray, Throwable throwable) {
        if (msg == null || argArray == null || argArray.length == 0) {
            return new FormattingTuple(msg, argArray, throwable);
        }
        int len = msg.length();
        StringBuilder sbuf = new StringBuilder(len + 50);

        for (int i = 0, argIdx = 0, escaped = 0; i < len; ) {
            char c = msg.charAt(i++);
            if (c != DELIM_START || i == len || msg.charAt(i) != DELIM_END) {
                sbuf.append(c);
                escaped = c != ESCAPE_CHAR ? 0 : escaped + 1;
                continue;
            }

            i++;
            if (escaped > 0) {
                // need to rewrite the previously added backslash
                sbuf.setLength(sbuf.length() - 1);
                if (--escaped == 0) {
                    // single backslash: delimeter escaped - insert delimeter "as is"
                    sbuf.append(DELIM_STR);
                    continue;
                }
                escaped = 0;
            }
            // normal case 'abc {}' or double escaped 'abc \\{}'
            deeplyAppendParameter(sbuf, argArray[argIdx++], null);
            if (argIdx == argArray.length) {
                // append the characters following the last delimeter
                sbuf.append(msg, i, len);
                break;
            }
        }

        return new FormattingTuple(sbuf.toString(), argArray, throwable);
    }

    // special treatment of array values was suggested by 'lizongbo'
    private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Map<Object[], Object> seenMap) {
        if (o == null) {
            sbuf.append(NULL);
            return;
        }
        Class<?> aClass = o.getClass();
        if (!aClass.isArray()) {
            safeObjectAppend(sbuf, o);
        } else {
            // check for primitive array types because they
            // unfortunately cannot be cast to Object[]
            sbuf.append('[');
            if (aClass == boolean[].class) {
                booleanArrayAppend(sbuf, (boolean[]) o);
            } else if (aClass == byte[].class) {
                byteArrayAppend(sbuf, (byte[]) o);
            } else if (aClass == char[].class) {
                charArrayAppend(sbuf, (char[]) o);
            } else if (aClass == short[].class) {
                shortArrayAppend(sbuf, (short[]) o);
            } else if (aClass == int[].class) {
                intArrayAppend(sbuf, (int[]) o);
            } else if (aClass == long[].class) {
                longArrayAppend(sbuf, (long[]) o);
            } else if (aClass == float[].class) {
                floatArrayAppend(sbuf, (float[]) o);
            } else if (aClass == double[].class) {
                doubleArrayAppend(sbuf, (double[]) o);
            } else {
                objectArrayAppend(sbuf, (Object[]) o, seenMap);
            }
            sbuf.append(']');
        }
    }

    private static void safeObjectAppend(StringBuilder sbuf, Object o) {
        try {
            String oAsString = o.toString();
            sbuf.append(oAsString);
        } catch (Throwable t) {
            Util.report("SLF4J: Failed toString() invocation on an object of type [" + o.getClass().getName() + ']', t);
            sbuf.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Map<Object[], Object> seenMap) {
        final int len = a.length;
        if (len > 0) {
            if (seenMap == null) {
                seenMap = new IdentityHashMap<Object[], Object>(len);
            }
            if (seenMap.put(a, a) == null) {
                deeplyAppendParameter(sbuf, a[0], seenMap);
                for (int i = 1; i < len; i++) {
                    sbuf.append(ARRAY_SEPARATOR);
                    deeplyAppendParameter(sbuf, a[i], seenMap);
                }
                // allow repeats in siblings
                seenMap.remove(a);
            } else {
                // avoid a circular reference
                sbuf.append("...");
            }
        }
    }

    private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void charArrayAppend(StringBuilder sbuf, char[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void intArrayAppend(StringBuilder sbuf, int[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void longArrayAppend(StringBuilder sbuf, long[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

    private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
        final int len = a.length;
        if (len > 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < len; i++) {
                sbuf.append(ARRAY_SEPARATOR);
                sbuf.append(a[i]);
            }
        }
    }

}
