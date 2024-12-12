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

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventRecordingLogger;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.spi.CallerBoundaryAware;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * A logger implementation which logs via a delegate logger. By default, the delegate is a
 * {@link NOPLogger}. However, a different delegate can be set at any time.
 *
 * <p>See also the <a href="http://www.slf4j.org/codes.html#substituteLogger">relevant
 * error code</a> documentation.
 *
 * @author Chetan Mehrotra
 * @author Ceki Gulcu
 */
public class SubstituteLogger implements LocationAwareLogger {

    private static final String SELF = SubstituteLogger.class.getName();

    private final String name;
    private volatile Logger _delegate;
    private Boolean delegateEventAware;
    private Method logMethodCache;
    private EventRecordingLogger eventRecordingLogger;
    private final Queue<SubstituteLoggingEvent> eventQueue;

    public final boolean createdPostInitialization;

    public SubstituteLogger(String name, Queue<SubstituteLoggingEvent> eventQueue, boolean createdPostInitialization) {
        this.name = name;
        this.eventQueue = eventQueue;
        this.createdPostInitialization = createdPostInitialization;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return delegate().makeLoggingEventBuilder(level);
    }

    @Override
    public LoggingEventBuilder atLevel(Level level) {
        return delegate().atLevel(level); 
    }
    
    @Override
    public boolean isEnabledForLevel(Level level) {
        return delegate().isEnabledForLevel(level);
    }
    
    @Override
    public boolean isTraceEnabled() {
        return delegate().isTraceEnabled();
    }
    
    @Override
    public void trace(String msg) {
        internalEventBuilder(Level.TRACE).log(msg);
    }
    
    @Override
    public void trace(String format, Object arg) {
        internalEventBuilder(Level.TRACE).log(format, arg);
    }
    
    @Override
    public void trace(String format, Object arg1, Object arg2) {
        internalEventBuilder(Level.TRACE).log(format, arg1, arg2);
    }
    
    @Override
    public void trace(String format, Object... arguments) {
        internalEventBuilder(Level.TRACE).log(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        internalEventBuilder(Level.TRACE).setCause(t).log(msg);
    }
    
    @Override
    public boolean isTraceEnabled(Marker marker) {
        return delegate().isTraceEnabled(marker);
    }
    
    @Override
    public void trace(Marker marker, String msg) {
        internalEventBuilder(marker, Level.TRACE).log(msg);
    }
    
    @Override
    public void trace(Marker marker, String format, Object arg) {
        internalEventBuilder(marker, Level.TRACE).log(format, arg);
    }
    
    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        internalEventBuilder(marker, Level.TRACE).log(format, arg1, arg2);
    }
    @Override
    public void trace(Marker marker, String format, Object... arguments) {
        internalEventBuilder(marker, Level.TRACE).log(format, arguments);
    }
    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        internalEventBuilder(marker, Level.TRACE).setCause(t).log(msg);
    }
    
    @Override
    public LoggingEventBuilder atTrace() {
        return delegate().atTrace();
    }
    
    @Override
    public boolean isDebugEnabled() {
        return delegate().isDebugEnabled();
    }
    
    @Override
    public void debug(String msg) {
        internalEventBuilder(Level.DEBUG).log(msg);
    }
    
    @Override
    public void debug(String format, Object arg) {
        internalEventBuilder(Level.DEBUG).log(format, arg);
    }
    
    @Override
    public void debug(String format, Object arg1, Object arg2) {
        internalEventBuilder(Level.DEBUG).log(format, arg1, arg2);
    }
    
    @Override
    public void debug(String format, Object... arguments) {
        internalEventBuilder(Level.DEBUG).log(format, arguments);
    }
    
    @Override
    public void debug(String msg, Throwable t) {
        internalEventBuilder(Level.DEBUG).setCause(t).log(msg);
    }
    
    @Override
    public boolean isDebugEnabled(Marker marker) {
        return delegate().isDebugEnabled(marker);
    }
    
    @Override
    public void debug(Marker marker, String msg) {
        internalEventBuilder(marker, Level.DEBUG).log(msg);
    }
    
    @Override
    public void debug(Marker marker, String format, Object arg) {
        internalEventBuilder(marker, Level.DEBUG).log(format, arg);
    }
    
    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        internalEventBuilder(marker, Level.DEBUG).log(format, arg1, arg2);
    }
    
    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        internalEventBuilder(marker, Level.DEBUG).log(format, arguments);
    }
    
    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        internalEventBuilder(marker, Level.DEBUG).setCause(t).log(msg);
    }
    
    @Override
    public LoggingEventBuilder atDebug() {
        return delegate().atDebug();
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate().isInfoEnabled();
    }


    @Override
    public void info(String msg) {
        internalEventBuilder(Level.INFO).log(msg);
    }

    @Override
    public void info(String format, Object arg) {
        internalEventBuilder(Level.INFO).log(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        internalEventBuilder(Level.INFO).log(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        internalEventBuilder(Level.INFO).log(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        internalEventBuilder(Level.INFO).setCause(t).log(msg);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return delegate().isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        internalEventBuilder(marker, Level.INFO).log(msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        internalEventBuilder(marker, Level.INFO).log(format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        internalEventBuilder(marker, Level.INFO).log(format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        internalEventBuilder(marker, Level.INFO).log(format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        internalEventBuilder(marker, Level.INFO).setCause(t).log(msg);
    }

    @Override
    public LoggingEventBuilder atInfo() {
        return delegate().atInfo();
    }


    @Override
    public boolean isWarnEnabled() {
        return delegate().isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        internalEventBuilder(Level.WARN).log(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        internalEventBuilder(Level.WARN).log(format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        internalEventBuilder(Level.WARN).log(format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        internalEventBuilder(Level.WARN).log(format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        internalEventBuilder(Level.WARN).setCause(t).log(msg);
    }

    public boolean isWarnEnabled(Marker marker) {
        return delegate().isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        internalEventBuilder(marker, Level.WARN).log(msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        internalEventBuilder(marker, Level.WARN).log(format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        internalEventBuilder(marker, Level.WARN).log(format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        internalEventBuilder(marker, Level.WARN).log(format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        internalEventBuilder(marker, Level.WARN).setCause(t).log(msg);
    }

    @Override
    public LoggingEventBuilder atWarn() {
        return delegate().atWarn();
    }



    @Override
    public boolean isErrorEnabled() {
        return delegate().isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        internalEventBuilder(Level.ERROR).log(msg);
    }

    @Override
    public void error(String format, Object arg) {
        internalEventBuilder(Level.ERROR).log(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        internalEventBuilder(Level.ERROR).log(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        internalEventBuilder(Level.ERROR).log(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        internalEventBuilder(Level.ERROR).setCause(t).log(msg);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return delegate().isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        internalEventBuilder(marker, Level.ERROR).log(msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        internalEventBuilder(marker, Level.ERROR).log(format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        internalEventBuilder(marker, Level.ERROR).log(format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        internalEventBuilder(marker, Level.ERROR).log(format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        internalEventBuilder(marker, Level.ERROR).setCause(t).log(msg);
    }

    @Override
    public LoggingEventBuilder atError() {
        return delegate().atError();
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
        if (_delegate != null) {
            return _delegate;
        }
        if (createdPostInitialization) {
            return NOPLogger.NOP_LOGGER;
        } else {
            return getEventRecordingLogger();
        }
    }

    private Logger getEventRecordingLogger() {
        if (eventRecordingLogger == null) {
            eventRecordingLogger = new EventRecordingLogger(this, eventQueue);
        }
        return eventRecordingLogger;
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
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {
            } catch (InvocationTargetException e) {
            }
        }
    }

    public boolean isDelegateNull() {
        return _delegate == null;
    }

    public boolean isDelegateNOP() {
        return _delegate instanceof NOPLogger;
    }

    @Override
    public void log(Marker marker, String fqcn, int level, String message, Object[] argArray, Throwable t) {
        Logger delegate = delegate();
        if (delegate instanceof LocationAwareLogger) {
            ((LocationAwareLogger) delegate).log(marker, fqcn, level, message, argArray, t);
        } else {
            // Unless the delegate implements `LoggingEventAware` the caller boundary information will be lost,
            // but this spares us the trouble of calling the class API.
            LoggingEventBuilder builder =
                    delegate.atLevel(Level.intToLevel(level)).addMarker(marker).setCause(t);
            if (builder instanceof CallerBoundaryAware) {
                ((CallerBoundaryAware) builder).setCallerBoundary(fqcn);
            }
            builder.log(message, argArray);
        }
    }

    /**
     * Creates a {@link LoggingEventBuilder} used within a method of this class.
     */
    private LoggingEventBuilder internalEventBuilder(Level level) {
        LoggingEventBuilder builder = delegate().makeLoggingEventBuilder(level);
        if (builder instanceof CallerBoundaryAware) {
            ((CallerBoundaryAware) builder).setCallerBoundary(SELF);
        }
        return builder;
    }

    /**
     * Creates a {@link LoggingEventBuilder} used within a method of this class.
     */
    private LoggingEventBuilder internalEventBuilder(Marker marker, Level level) {
        LoggingEventBuilder builder = delegate().makeLoggingEventBuilder(level).addMarker(marker);
        if (builder instanceof CallerBoundaryAware) {
            ((CallerBoundaryAware) builder).setCallerBoundary(SELF);
        }
        return builder;
    }

}
