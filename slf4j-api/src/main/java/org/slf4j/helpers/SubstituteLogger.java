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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Queue;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventRecodingLogger;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.SubstituteLoggingEvent;

/**
 * A logger implementation which logs via a delegate logger. By default, the delegate is a
 * {@link NOPLogger}. However, a different delegate can be set at any time.
 * <p/>
 * See also the <a href="http://www.slf4j.org/codes.html#substituteLogger">relevant
 * error code</a> documentation.
 *
 * @author Chetan Mehrotra
 * @author Ceki Gulcu
 */
public class SubstituteLogger implements Logger {

    private final String name;
    private volatile Logger _delegate;
    private Boolean delegateEventAware;
    private Method logMethodCache;
    private EventRecodingLogger eventRecodingLogger;
    private Queue<SubstituteLoggingEvent> eventQueue;

    private final boolean createdPostInitialization;
    
    public SubstituteLogger(String name, Queue<SubstituteLoggingEvent> eventQueue, boolean createdPostInitialization) {
        this.name = name;
        this.eventQueue = eventQueue;
        this.createdPostInitialization = createdPostInitialization;
    }

    public String getName() {
        return name;
    }

    public boolean isTraceEnabled() {
        return delegate().isTraceEnabled();
    }

    public void trace(String msg) {
        delegate().trace(msg);
    }

    @Override
    public void trace(Supplier<String> msgSup) {
        delegate().trace(msgSup);
    }

    public void trace(String format, Object arg) {
        delegate().trace(format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        delegate().trace(format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        delegate().trace(format, arguments);
    }

    public void trace(String msg, Throwable t) {
        delegate().trace(msg, t);
    }

    @Override
    public void trace(Throwable t, Supplier<String> msgSup) {
        delegate().trace(t, msgSup);
    }

    public boolean isTraceEnabled(Marker marker) {
        return delegate().isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg) {
        delegate().trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, Supplier<String> msgSup) {
        delegate().trace(marker, msgSup);
    }

    public void trace(Marker marker, String format, Object arg) {
        delegate().trace(marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        delegate().trace(marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... arguments) {
        delegate().trace(marker, format, arguments);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        delegate().trace(marker, msg, t);
    }

    @Override
    public void trace(Marker marker, Throwable t, Supplier<String> msgSup) {
        delegate().trace(marker, t, msgSup);
    }

    public boolean isDebugEnabled() {
        return delegate().isDebugEnabled();
    }

    public void debug(String msg) {
        delegate().debug(msg);
    }

    @Override
    public void debug(Supplier<String> msgSup) {
        delegate().debug(msgSup);
    }

    public void debug(String format, Object arg) {
        delegate().debug(format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        delegate().debug(format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        delegate().debug(format, arguments);
    }

    public void debug(String msg, Throwable t) {
        delegate().debug(msg, t);
    }

    @Override
    public void debug(Throwable t, Supplier<String> msgSup) {
        delegate().debug(t, msgSup);
    }

    public boolean isDebugEnabled(Marker marker) {
        return delegate().isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        delegate().debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, Supplier<String> msgSup) {
        delegate().debug(marker, msgSup);
    }

    public void debug(Marker marker, String format, Object arg) {
        delegate().debug(marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        delegate().debug(marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... arguments) {
        delegate().debug(marker, format, arguments);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        delegate().debug(marker, msg, t);
    }

    @Override
    public void debug(Marker marker, Throwable t, Supplier<String> msgSup) {
        delegate().debug(marker, t, msgSup);
    }

    public boolean isInfoEnabled() {
        return delegate().isInfoEnabled();
    }

    public void info(String msg) {
        delegate().info(msg);
    }

    @Override
    public void info(Supplier<String> msgSup) {
        delegate().info(msgSup);
    }

    public void info(String format, Object arg) {
        delegate().info(format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        delegate().info(format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        delegate().info(format, arguments);
    }

    public void info(String msg, Throwable t) {
        delegate().info(msg, t);
    }

    @Override
    public void info(Throwable t, Supplier<String> msgSup) {
        delegate().info(t, msgSup);
    }

    public boolean isInfoEnabled(Marker marker) {
        return delegate().isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        delegate().info(marker, msg);
    }

    @Override
    public void info(Marker marker, Supplier<String> msgSup) {
        delegate().info(marker, msgSup);
    }

    public void info(Marker marker, String format, Object arg) {
        delegate().info(marker, format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        delegate().info(marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... arguments) {
        delegate().info(marker, format, arguments);
    }

    public void info(Marker marker, String msg, Throwable t) {
        delegate().info(marker, msg, t);
    }

    @Override
    public void info(Marker marker, Throwable t, Supplier<String> msgSup) {
        delegate().info(marker, t, msgSup);
    }

    public boolean isWarnEnabled() {
        return delegate().isWarnEnabled();
    }

    public void warn(String msg) {
        delegate().warn(msg);
    }

    @Override
    public void warn(Supplier<String> msgSup) {
        delegate().warn(msgSup);
    }

    public void warn(String format, Object arg) {
        delegate().warn(format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        delegate().warn(format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        delegate().warn(format, arguments);
    }

    public void warn(String msg, Throwable t) {
        delegate().warn(msg, t);
    }

    @Override
    public void warn(Throwable t, Supplier<String> msgSup) {
        delegate().warn(t, msgSup);
    }

    public boolean isWarnEnabled(Marker marker) {
        return delegate().isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        delegate().warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, Supplier<String> msgSup) {
        delegate().warn(marker, msgSup);
    }

    public void warn(Marker marker, String format, Object arg) {
        delegate().warn(marker, format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        delegate().warn(marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... arguments) {
        delegate().warn(marker, format, arguments);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        delegate().warn(marker, msg, t);
    }

    @Override
    public void warn(Marker marker, Throwable t, Supplier<String> msgSup) {
        delegate().warn(marker, t, msgSup);
    }

    public boolean isErrorEnabled() {
        return delegate().isErrorEnabled();
    }

    public void error(String msg) {
        delegate().error(msg);
    }

    @Override
    public void error(Supplier<String> msgSup) {
        delegate().error(msgSup);
    }

    public void error(String format, Object arg) {
        delegate().error(format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        delegate().error(format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        delegate().error(format, arguments);
    }

    public void error(String msg, Throwable t) {
        delegate().error(msg, t);
    }

    @Override
    public void error(Throwable t, Supplier<String> msgSup) {
        delegate().error(t, msgSup);
    }

    public boolean isErrorEnabled(Marker marker) {
        return delegate().isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        delegate().error(marker, msg);
    }

    @Override
    public void error(Marker marker, Supplier<String> msgSup) {
        delegate().error(marker, msgSup);
    }

    public void error(Marker marker, String format, Object arg) {
        delegate().error(marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        delegate().error(marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... arguments) {
        delegate().error(marker, format, arguments);
    }

    public void error(Marker marker, String msg, Throwable t) {
        delegate().error(marker, msg, t);
    }

    @Override
    public void error(Marker marker, Throwable t, Supplier<String> msgSup) {
        delegate().error(marker, t, msgSup);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SubstituteLogger that = (SubstituteLogger) o;

        if (!name.equals(that.name))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Return the delegate logger instance if set. Otherwise, return a {@link NOPLogger}
     * instance.
     */
    public Logger delegate() {
        if(_delegate != null) {
            return _delegate;
        }
        if(createdPostInitialization) {
            return NOPLogger.NOP_LOGGER;
        } else {
            return getEventRecordingLogger();
        }
    }

    private Logger getEventRecordingLogger() {
        if (eventRecodingLogger == null) {
            eventRecodingLogger = new EventRecodingLogger(this, eventQueue);
        }
        return eventRecodingLogger;
    }

    /**
     * Typically called after the {@link org.slf4j.LoggerFactory} initialization phase is completed.
     * @param delegate
     */
    public void setDelegate(Logger delegate) {
        this._delegate = delegate;
    }

    public boolean isDelegateEventAware() {
        if (delegateEventAware != null)
            return delegateEventAware;

        try {
            logMethodCache = _delegate.getClass().getMethod("log", LoggingEvent.class);
            delegateEventAware = Boolean.TRUE;
        } catch (NoSuchMethodException e) {
            delegateEventAware = Boolean.FALSE;
        }
        return delegateEventAware;
    }

    public void log(LoggingEvent event) {
        if (isDelegateEventAware()) {
            try {
                logMethodCache.invoke(_delegate, event);
            } catch (IllegalAccessException
                    | IllegalArgumentException
                    | InvocationTargetException e) {
            } 
        }
    }


    public boolean isDelegateNull() {
        return _delegate == null;
    }

    public boolean isDelegateNOP() {
        return _delegate instanceof NOPLogger;
    }
}
