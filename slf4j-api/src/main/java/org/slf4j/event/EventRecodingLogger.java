package org.slf4j.event;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.SubstituteLogger;

/**
 *
 * This class is used to record events during the initialization phase of the
 * underlying logging framework. It is called by {@link SubstituteLogger}.
 *
 *
 * @author Ceki G&uumllc&uuml;
 * @author Wessel van Norel
 *
 */
public class EventRecodingLogger implements Logger {

    String name;
    SubstituteLogger logger;
    Queue<SubstituteLoggingEvent> eventQueue;

    // as an event recording logger we have no choice but to record all events
    final static boolean RECORD_ALL_EVENTS = true;

    public EventRecodingLogger(SubstituteLogger logger, Queue<SubstituteLoggingEvent> eventQueue) {
        this.logger = logger;
        this.name = logger.getName();
        this.eventQueue = eventQueue;
    }

    public String getName() {
        return name;
    }

    public boolean isTraceEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void trace(String msg) {
        recordEvent_0Args(Level.TRACE, null, msg, null);
    }

    public void trace(String format, Object arg) {
        recordEvent_1Args(Level.TRACE, null, format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.TRACE, null, format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        recordEventArgArray(Level.TRACE, null, format, arguments);
    }

    public void trace(String msg, Throwable t) {
        recordEvent_0Args(Level.TRACE, null, msg, t);
    }

    public boolean isTraceEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void trace(Marker marker, String msg) {
        recordEvent_0Args(Level.TRACE, marker, msg, null);
    }

    public void trace(Marker marker, String format, Object arg) {
        recordEvent_1Args(Level.TRACE, marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.TRACE, marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... argArray) {
        recordEventArgArray(Level.TRACE, marker, format, argArray);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        recordEvent_0Args(Level.TRACE, marker, msg, t);
    }

    public boolean isDebugEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void debug(String msg) {
        recordEvent_0Args(Level.DEBUG, null, msg, null);
    }

    public void debug(String format, Object arg) {
        recordEvent_1Args(Level.DEBUG, null, format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.DEBUG, null, format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        recordEventArgArray(Level.DEBUG, null, format, arguments);
    }

    public void debug(String msg, Throwable t) {
        recordEvent_0Args(Level.DEBUG, null, msg, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void debug(Marker marker, String msg) {
        recordEvent_0Args(Level.DEBUG, marker, msg, null);
    }

    public void debug(Marker marker, String format, Object arg) {
        recordEvent_1Args(Level.DEBUG, marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.DEBUG, marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... arguments) {
        recordEventArgArray(Level.DEBUG, marker, format, arguments);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        recordEvent_0Args(Level.DEBUG, marker, msg, t);
    }

    public boolean isInfoEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void info(String msg) {
        recordEvent_0Args(Level.INFO, null, msg, null);
    }

    public void info(String format, Object arg) {
        recordEvent_1Args(Level.INFO, null, format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.INFO, null, format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        recordEventArgArray(Level.INFO, null, format,  arguments);
    }

    public void info(String msg, Throwable t) {
        recordEvent_0Args(Level.INFO, null, msg, t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void info(Marker marker, String msg) {
        recordEvent_0Args(Level.INFO, marker, msg, null);
    }

    public void info(Marker marker, String format, Object arg) {
        recordEvent_1Args(Level.INFO, marker, format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.INFO, marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... arguments) {
        recordEventArgArray(Level.INFO, marker, format, arguments);
    }

    public void info(Marker marker, String msg, Throwable t) {
        recordEvent_0Args(Level.INFO, marker, msg, t);

    }

    public boolean isWarnEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void warn(String msg) {
        recordEvent_0Args(Level.WARN, null, msg, null);
    }

    public void warn(String format, Object arg) {
        recordEvent_1Args(Level.WARN, null, format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.WARN, null, format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        recordEventArgArray(Level.WARN, null, format, arguments);
    }

    public void warn(String msg, Throwable t) {
        recordEvent_0Args(Level.WARN, null, msg, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void warn(Marker marker, String msg) {
        recordEvent_0Args(Level.WARN, marker, msg, null);
    }

    public void warn(Marker marker, String format, Object arg) {
        recordEvent_1Args(Level.WARN, marker, format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.WARN, marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... arguments) {
        recordEventArgArray(Level.WARN, marker, format, arguments);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        recordEvent_0Args(Level.WARN, marker, msg, t);
    }

    public boolean isErrorEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void error(String msg) {
        recordEvent_0Args(Level.ERROR, null, msg, null);
    }

    public void error(String format, Object arg) {
        recordEvent_1Args(Level.ERROR, null, format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.ERROR, null, format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        recordEventArgArray(Level.ERROR, null, format, arguments);
    }

    public void error(String msg, Throwable t) {
        recordEvent_0Args(Level.ERROR, null, msg, t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void error(Marker marker, String msg) {
        recordEvent_0Args(Level.ERROR, marker, msg, null);
    }

    public void error(Marker marker, String format, Object arg) {
        recordEvent_1Args(Level.ERROR, marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.ERROR, marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... arguments) {
        recordEventArgArray(Level.ERROR, marker, format, arguments);
    }

    public void error(Marker marker, String msg, Throwable t) {
        recordEvent_0Args(Level.ERROR, marker, msg, t);
    }

    private void recordEvent_0Args(Level level, Marker marker, String msg, Throwable t) {
        recordEvent(level, marker, msg, null, t);
    }

    private void recordEvent_1Args(Level level, Marker marker, String msg, Object arg1) {
        recordEvent(level, marker, msg, new Object[] { arg1 }, null);
    }

    private void recordEvent2Args(Level level, Marker marker, String msg, Object arg1, Object arg2) {
        if (arg2 instanceof Throwable) {
            recordEvent(level, marker, msg, new Object[] { arg1 }, (Throwable) arg2);
        } else {
            recordEvent(level, marker, msg, new Object[] { arg1, arg2 }, null);
        }
    }

    private void recordEventArgArray(Level level, Marker marker, String msg, Object[] args) {
        Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(args);
        if (throwableCandidate != null) {
            Object[] trimmedCopy = MessageFormatter.trimmedCopy(args);
            recordEvent(level, marker, msg, trimmedCopy, throwableCandidate);
        } else {
            recordEvent(level, marker, msg, args, null);
        }
    }


    // WARNING: this method assumes that any throwable is properly extracted
    private void recordEvent(Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
        SubstituteLoggingEvent loggingEvent = new SubstituteLoggingEvent();
        loggingEvent.setTimeStamp(System.currentTimeMillis());
        loggingEvent.setLevel(level);
        loggingEvent.setLogger(logger);
        loggingEvent.setLoggerName(name);
        loggingEvent.setMarker(marker);
        loggingEvent.setMessage(msg);
        loggingEvent.setThreadName(Thread.currentThread().getName());

        loggingEvent.setArgumentArray(args);
        loggingEvent.setThrowable(throwable);

        eventQueue.add(loggingEvent);
    }
}
