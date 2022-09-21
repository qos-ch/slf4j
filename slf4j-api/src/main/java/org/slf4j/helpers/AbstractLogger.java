/**
 * Copyright (c) 2004-2019 QOS.ch
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

import java.io.ObjectStreamException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * An abstract implementation which delegates actual logging work to the 
 * {@link #handleNormalizedLoggingCall(Level, Marker, String, Object[], Throwable)} method.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0
 */
public abstract class AbstractLogger implements Logger, Serializable {

    private static final long serialVersionUID = -2529255052481744503L;

    protected String name;

    public String getName() {
        return name;
    }

    /**
     * Replace this instance with a homonymous (same name) logger returned 
     * by LoggerFactory. Note that this method is only called during 
     * deserialization.
     * 
     * <p>
     * This approach will work well if the desired ILoggerFactory is the one
     * referenced by {@link org.slf4j.LoggerFactory} However, if the user manages its logger hierarchy
     * through a different (non-static) mechanism, e.g. dependency injection, then
     * this approach would be mostly counterproductive.
     * 
     * @return logger with same name as returned by LoggerFactory
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {
        // using getName() instead of this.name works even for
        // NOPLogger
        return LoggerFactory.getLogger(getName());
    }

    @Override
    public void trace(String msg) {
        if (isTraceEnabled()) {
            handle_0ArgsCall(Level.TRACE, null, msg, null);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled()) {
            handle_1ArgsCall(Level.TRACE, null, format, arg);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled()) {
            handle2ArgsCall(Level.TRACE, null, format, arg1, arg2);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled()) {
            handleArgArrayCall(Level.TRACE, null, format, arguments);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (isTraceEnabled()) {
            handle_0ArgsCall(Level.TRACE, null, msg, t);
        }
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (isTraceEnabled(marker)) {
            handle_0ArgsCall(Level.TRACE, marker, msg, null);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (isTraceEnabled(marker)) {
            handle_1ArgsCall(Level.TRACE, marker, format, arg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (isTraceEnabled(marker)) {
            handle2ArgsCall(Level.TRACE, marker, format, arg1, arg2);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        if (isTraceEnabled(marker)) {
            handleArgArrayCall(Level.TRACE, marker, format, argArray);
        }
    }

    public void trace(Marker marker, String msg, Throwable t) {
        if (isTraceEnabled(marker)) {
            handle_0ArgsCall(Level.TRACE, marker, msg, t);
        }
    }

    public void debug(String msg) {
        if (isDebugEnabled()) {
            handle_0ArgsCall(Level.DEBUG, null, msg, null);
        }
    }

    public void debug(String format, Object arg) {
        if (isDebugEnabled()) {
            handle_1ArgsCall(Level.DEBUG, null, format, arg);
        }
    }

    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled()) {
            handle2ArgsCall(Level.DEBUG, null, format, arg1, arg2);
        }
    }

    public void debug(String format, Object... arguments) {
        if (isDebugEnabled()) {
            handleArgArrayCall(Level.DEBUG, null, format, arguments);
        }
    }

    public void debug(String msg, Throwable t) {
        if (isDebugEnabled()) {
            handle_0ArgsCall(Level.DEBUG, null, msg, t);
        }
    }

    public void debug(Marker marker, String msg) {
        if (isDebugEnabled(marker)) {
            handle_0ArgsCall(Level.DEBUG, marker, msg, null);
        }
    }

    public void debug(Marker marker, String format, Object arg) {
        if (isDebugEnabled(marker)) {
            handle_1ArgsCall(Level.DEBUG, marker, format, arg);
        }
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (isDebugEnabled(marker)) {
            handle2ArgsCall(Level.DEBUG, marker, format, arg1, arg2);
        }
    }

    public void debug(Marker marker, String format, Object... arguments) {
        if (isDebugEnabled(marker)) {
            handleArgArrayCall(Level.DEBUG, marker, format, arguments);
        }
    }

    public void debug(Marker marker, String msg, Throwable t) {
        if (isDebugEnabled(marker)) {
            handle_0ArgsCall(Level.DEBUG, marker, msg, t);
        }
    }

    public void info(String msg) {
        if (isInfoEnabled()) {
            handle_0ArgsCall(Level.INFO, null, msg, null);
        }
    }

    public void info(String format, Object arg) {
        if (isInfoEnabled()) {
            handle_1ArgsCall(Level.INFO, null, format, arg);
        }
    }

    public void info(String format, Object arg1, Object arg2) {
        if (isInfoEnabled()) {
            handle2ArgsCall(Level.INFO, null, format, arg1, arg2);
        }
    }

    public void info(String format, Object... arguments) {
        if (isInfoEnabled()) {
            handleArgArrayCall(Level.INFO, null, format, arguments);
        }
    }

    public void info(String msg, Throwable t) {
        if (isInfoEnabled()) {
            handle_0ArgsCall(Level.INFO, null, msg, t);
        }
    }

    public void info(Marker marker, String msg) {
        if (isInfoEnabled(marker)) {
            handle_0ArgsCall(Level.INFO, marker, msg, null);
        }
    }

    public void info(Marker marker, String format, Object arg) {
        if (isInfoEnabled(marker)) {
            handle_1ArgsCall(Level.INFO, marker, format, arg);
        }
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (isInfoEnabled(marker)) {
            handle2ArgsCall(Level.INFO, marker, format, arg1, arg2);
        }
    }

    public void info(Marker marker, String format, Object... arguments) {
        if (isInfoEnabled(marker)) {
            handleArgArrayCall(Level.INFO, marker, format, arguments);
        }
    }

    public void info(Marker marker, String msg, Throwable t) {
        if (isInfoEnabled(marker)) {
            handle_0ArgsCall(Level.INFO, marker, msg, t);
        }
    }

    public void warn(String msg) {
        if (isWarnEnabled()) {
            handle_0ArgsCall(Level.WARN, null, msg, null);
        }
    }

    public void warn(String format, Object arg) {
        if (isWarnEnabled()) {
            handle_1ArgsCall(Level.WARN, null, format, arg);
        }
    }

    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled()) {
            handle2ArgsCall(Level.WARN, null, format, arg1, arg2);
        }
    }

    public void warn(String format, Object... arguments) {
        if (isWarnEnabled()) {
            handleArgArrayCall(Level.WARN, null, format, arguments);
        }
    }

    public void warn(String msg, Throwable t) {
        if (isWarnEnabled()) {
            handle_0ArgsCall(Level.WARN, null, msg, t);
        }
    }

    public void warn(Marker marker, String msg) {
        if (isWarnEnabled(marker)) {
            handle_0ArgsCall(Level.WARN, marker, msg, null);
        }
    }

    public void warn(Marker marker, String format, Object arg) {
        if (isWarnEnabled(marker)) {
            handle_1ArgsCall(Level.WARN, marker, format, arg);
        }
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (isWarnEnabled(marker)) {
            handle2ArgsCall(Level.WARN, marker, format, arg1, arg2);
        }
    }

    public void warn(Marker marker, String format, Object... arguments) {
        if (isWarnEnabled(marker)) {
            handleArgArrayCall(Level.WARN, marker, format, arguments);
        }
    }

    public void warn(Marker marker, String msg, Throwable t) {
        if (isWarnEnabled(marker)) {
            handle_0ArgsCall(Level.WARN, marker, msg, t);
        }
    }

    public void error(String msg) {
        if (isErrorEnabled()) {
            handle_0ArgsCall(Level.ERROR, null, msg, null);
        }
    }

    public void error(String format, Object arg) {
        if (isErrorEnabled()) {
            handle_1ArgsCall(Level.ERROR, null, format, arg);
        }
    }

    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled()) {
            handle2ArgsCall(Level.ERROR, null, format, arg1, arg2);
        }
    }

    public void error(String format, Object... arguments) {
        if (isErrorEnabled()) {
            handleArgArrayCall(Level.ERROR, null, format, arguments);
        }
    }

    public void error(String msg, Throwable t) {
        if (isErrorEnabled()) {
            handle_0ArgsCall(Level.ERROR, null, msg, t);
        }
    }

    public void error(Marker marker, String msg) {
        if (isErrorEnabled(marker)) {
            handle_0ArgsCall(Level.ERROR, marker, msg, null);
        }
    }

    public void error(Marker marker, String format, Object arg) {
        if (isErrorEnabled(marker)) {
            handle_1ArgsCall(Level.ERROR, marker, format, arg);
        }
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (isErrorEnabled(marker)) {
            handle2ArgsCall(Level.ERROR, marker, format, arg1, arg2);
        }
    }

    public void error(Marker marker, String format, Object... arguments) {
        if (isErrorEnabled(marker)) {
            handleArgArrayCall(Level.ERROR, marker, format, arguments);
        }
    }

    public void error(Marker marker, String msg, Throwable t) {
        if (isErrorEnabled(marker)) {
            handle_0ArgsCall(Level.ERROR, marker, msg, t);
        }
    }

    private void handle_0ArgsCall(Level level, Marker marker, String msg, Throwable t) {
        handleNormalizedLoggingCall(level, marker, msg, null, t);
    }

    private void handle_1ArgsCall(Level level, Marker marker, String msg, Object arg1) {
        handleNormalizedLoggingCall(level, marker, msg, new Object[] { arg1 }, null);
    }

    private void handle2ArgsCall(Level level, Marker marker, String msg, Object arg1, Object arg2) {
        if (arg2 instanceof Throwable) {
            handleNormalizedLoggingCall(level, marker, msg, new Object[] { arg1 }, (Throwable) arg2);
        } else {
            handleNormalizedLoggingCall(level, marker, msg, new Object[] { arg1, arg2 }, null);
        }
    }

    private void handleArgArrayCall(Level level, Marker marker, String msg, Object[] args) {
        Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(args);
        if (throwableCandidate != null) {
            Object[] trimmedCopy = MessageFormatter.trimmedCopy(args);
            handleNormalizedLoggingCall(level, marker, msg, trimmedCopy, throwableCandidate);
        } else {
            handleNormalizedLoggingCall(level, marker, msg, args, null);
        }
    }

    abstract protected String getFullyQualifiedCallerName();

    /**
     * Given various arguments passed as parameters, perform actual logging.
     * 
     * <p>This method assumes that the separation of the args array into actual
     * objects and a throwable has been already operated.
     * 
     * @param level the SLF4J level for this event
     * @param marker  The marker to be used for this event, may be null.
     * @param messagePattern The message pattern which will be parsed and formatted
     * @param arguments  the array of arguments to be formatted, may be null
     * @param throwable  The exception whose stack trace should be logged, may be null
     */
    abstract protected void handleNormalizedLoggingCall(Level level, Marker marker, String messagePattern, Object[] arguments, Throwable throwable);

}
