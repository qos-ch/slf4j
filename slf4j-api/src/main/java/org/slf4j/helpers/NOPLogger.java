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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A direct NOP (no operation) implementation of {@link Logger}.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public class NOPLogger extends NamedLoggerBase implements Logger {

    private static final long serialVersionUID = -517220405410904473L;

    /**
     * The unique instance of NOPLogger.
     */
    public static final NOPLogger NOP_LOGGER = new NOPLogger();

    /**
     * There is no point in creating multiple instances of NOPLogger. 
     * 
     * The present constructor should be "private" but we are leaving it as "protected" for compatibility.
     */
    protected NOPLogger() {
    }

    /**
     * Always returns the string value "NOP".
     */
    @Override
    public String getName() {
        return "NOP";
    }

    /**
     * Always returns false.
     * @return always false
     */
    @Override
    public final boolean isTraceEnabled() {
        return false;
    }

    /** A NOP implementation. */
    @Override
    public final void trace(String msg) {
        // NOP
    }

    /** A NOP implementation.  */
    @Override
    public final void trace(String format, Object arg) {
        // NOP
    }

    /** A NOP implementation.  */
    @Override
    public final void trace(String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation.  */
    @Override
    public final void trace(String format, Object... argArray) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void trace(String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    public final boolean isDebugEnabled() {
        return false;
    }

    /** A NOP implementation. */
    public final void debug(String msg) {
        // NOP
    }

    /** A NOP implementation.  */
    public final void debug(String format, Object arg) {
        // NOP
    }

    /** A NOP implementation.  */
    public final void debug(String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation.  */
    public final void debug(String format, Object... argArray) {
        // NOP
    }

    /** A NOP implementation. */
    public final void debug(String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    public final boolean isInfoEnabled() {
        // NOP
        return false;
    }

    /** A NOP implementation. */
    public final void info(String msg) {
        // NOP
    }

    /** A NOP implementation. */
    public final void info(String format, Object arg1) {
        // NOP
    }

    /** A NOP implementation. */
    public final void info(String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation.  */
    public final void info(String format, Object... argArray) {
        // NOP
    }

    /** A NOP implementation. */
    public final void info(String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    public final boolean isWarnEnabled() {
        return false;
    }

    /** A NOP implementation. */
    public final void warn(String msg) {
        // NOP
    }

    /** A NOP implementation. */
    public final void warn(String format, Object arg1) {
        // NOP
    }

    /** A NOP implementation. */
    public final void warn(String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation.  */
    public final void warn(String format, Object... argArray) {
        // NOP
    }

    /** A NOP implementation. */
    public final void warn(String msg, Throwable t) {
        // NOP
    }

    /** A NOP implementation. */
    public final boolean isErrorEnabled() {
        return false;
    }

    /** A NOP implementation. */
    public final void error(String msg) {
        // NOP
    }

    /** A NOP implementation. */
    public final void error(String format, Object arg1) {
        // NOP
    }

    /** A NOP implementation. */
    public final void error(String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation.  */
    public final void error(String format, Object... argArray) {
        // NOP
    }

    /** A NOP implementation. */
    public final void error(String msg, Throwable t) {
        // NOP
    }

    // ============================================================
    // Added NOP methods since MarkerIgnoringBase is now deprecated
    // ============================================================
    /**
     * Always returns false.
     * @return always false
     */
    public final boolean isTraceEnabled(Marker marker) {
        // NOP
        return false;
    }

    /** A NOP implementation. */
    @Override
    public final void trace(Marker marker, String msg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void trace(Marker marker, String format, Object arg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void trace(Marker marker, String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void trace(Marker marker, String format, Object... argArray) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void trace(Marker marker, String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    public final boolean isDebugEnabled(Marker marker) {
        return false;
    }

    /** A NOP implementation. */
    @Override
    public final void debug(Marker marker, String msg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void debug(Marker marker, String format, Object arg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void debug(Marker marker, String format, Object arg1, Object arg2) {
        // NOP
    }

    @Override
    public final void debug(Marker marker, String format, Object... arguments) {
        // NOP
    }

    @Override
    public final void debug(Marker marker, String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    @Override
    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    /** A NOP implementation. */
    @Override
    public final void info(Marker marker, String msg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void info(Marker marker, String format, Object arg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void info(Marker marker, String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void info(Marker marker, String format, Object... arguments) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void info(Marker marker, String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    @Override
    public final boolean isWarnEnabled(Marker marker) {
        return false;
    }

    /** A NOP implementation. */
    @Override
    public final void warn(Marker marker, String msg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void warn(Marker marker, String format, Object arg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void warn(Marker marker, String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void warn(Marker marker, String format, Object... arguments) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void warn(Marker marker, String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     * @return always false
     */
    @Override
    public final boolean isErrorEnabled(Marker marker) {
        return false;
    }

    /** A NOP implementation. */
    @Override
    public final void error(Marker marker, String msg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void error(Marker marker, String format, Object arg) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void error(Marker marker, String format, Object arg1, Object arg2) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void error(Marker marker, String format, Object... arguments) {
        // NOP
    }

    /** A NOP implementation. */
    @Override
    public final void error(Marker marker, String msg, Throwable t) {
        // NOP
    }
    // ===================================================================
    // End of added NOP methods since MarkerIgnoringBase is now deprecated
    // ===================================================================

}
