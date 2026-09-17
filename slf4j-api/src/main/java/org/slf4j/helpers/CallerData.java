/**
 * Copyright (c) 2004-2026 QOS.ch
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
package org.slf4j.helpers;

import java.util.List;

/**
 * This class computes caller data returning the result in the form of a
 * StackTraceElement array.
 *
 * <p>Adapted with permission from {@code ch.qos.logback.classic.spi.CallerData}.</p>
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 3.0.0
 */
public class CallerData {

    /**
     * Used when a name or other string field is not available.
     */
    public static final String NA = "?";

    // All logger calls in log4j-over-slf4j use the Category class
    private static final String LOG4J_CATEGORY = "org.apache.log4j.Category";
    private static final String SLF4J_BOUNDARY = "org.slf4j.Logger";
    private static final String DLEB = "org.slf4j.spi.DefaultLoggingEventBuilder";
    private static final String LEB = "org.slf4j.spi.LoggingEventBuilder";

    /**
     * When caller information is not available this constant is used for the line
     * number.
     */
    public static final int LINE_NA = -1;

    public static final String CALLER_DATA_NA = "?#?:?" + System.lineSeparator();

    /**
     * This value is returned in case no caller data could be extracted.
     */
    public static final StackTraceElement[] EMPTY_CALLER_DATA_ARRAY = new StackTraceElement[0];

    /**
     * Extract caller data information as an array based on a Throwable passed as
     * parameter.
     *
     * @param t throwable whose stack is used as the source of caller data
     * @param fqnOfInvokingClass fully qualified name of the invoking class, typically
     *                           the caller boundary
     * @param maxDepth maximum number of stack frames to return
     * @param frameworkPackageList additional class/package prefixes considered part
     *                             of the logging framework, may be null
     * @return extracted caller data, never null except when {@code t} is null
     */
    public static StackTraceElement[] extract(Throwable t, String fqnOfInvokingClass, final int maxDepth,
            List<String> frameworkPackageList) {
        if (t == null) {
            return null;
        }

        StackTraceElement[] steArray = t.getStackTrace();
        StackTraceElement[] callerDataArray;

        int found = LINE_NA;
        for (int i = 0; i < steArray.length; i++) {
            if (isInFrameworkSpace(steArray[i].getClassName(), fqnOfInvokingClass, frameworkPackageList)) {
                // the caller is assumed to be the next stack frame, hence the +1.
                found = i + 1;
            } else {
                if (found != LINE_NA) {
                    break;
                }
            }
        }

        // we failed to extract caller data
        if (found == LINE_NA) {
            return EMPTY_CALLER_DATA_ARRAY;
        }

        int availableDepth = steArray.length - found;
        int desiredDepth = maxDepth < (availableDepth) ? maxDepth : availableDepth;
        if (desiredDepth < 0) {
            desiredDepth = 0;
        }

        callerDataArray = new StackTraceElement[desiredDepth];
        for (int i = 0; i < desiredDepth; i++) {
            callerDataArray[i] = steArray[found + i];
        }
        return callerDataArray;
    }

    static boolean isInFrameworkSpace(String currentClass, String fqnOfInvokingClass,
            List<String> frameworkPackageList) {
        // the check for org.apache.log4j.Category class is intended to support
        // log4j-over-slf4j. it solves http://bugzilla.slf4j.org/show_bug.cgi?id=66
        if (currentClass.equals(fqnOfInvokingClass) || currentClass.equals(LOG4J_CATEGORY)
                || currentClass.equals(DLEB) || currentClass.equals(LEB)
                || currentClass.startsWith(SLF4J_BOUNDARY)
                || isInFrameworkSpaceList(currentClass, frameworkPackageList)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Is currentClass present in the list of packages considered part of the
     * logging framework?
     */
    private static boolean isInFrameworkSpaceList(String currentClass, List<String> frameworkPackageList) {
        if (frameworkPackageList == null)
            return false;

        for (String s : frameworkPackageList) {
            if (currentClass.startsWith(s))
                return true;
        }
        return false;
    }

    /**
     * Returns a StackTraceElement where all string fields are set to {@link #NA}
     * and line number is set to {@link #LINE_NA}.
     *
     * @return StackTraceElement with values set to NA constants.
     */
    public static StackTraceElement naInstance() {
        return new StackTraceElement(NA, NA, NA, LINE_NA);
    }

}
