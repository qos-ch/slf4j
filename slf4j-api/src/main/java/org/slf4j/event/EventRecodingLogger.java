package org.slf4j.event;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.SubstituteLogger;

/**
 * 
 * This class is used to record events during the initialization phase of
 * the underlying logging framework. It is called by {@link SubstituteLogger}.
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
        recordEventwithoutMarkerArgArray(Level.TRACE, msg, null, null);
    }

    public void trace(String format, Object arg) {
        recordEventwithoutMarkerArgArray(Level.TRACE, format, new Object[] { arg }, null);
    }

    public void trace(String format, Object arg1, Object arg2) {
        recordEventWithoutMarker2Args(Level.TRACE, format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        recordEventwithoutMarkerArgArray(Level.TRACE, format, arguments, null);
    }

    public void trace(String msg, Throwable t) {
        recordEventwithoutMarkerArgArray(Level.TRACE, msg, null, t);
    }

    public boolean isTraceEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void trace(Marker marker, String msg) {
        recordEvent(Level.TRACE, marker, msg, null, null);

    }

    public void trace(Marker marker, String format, Object arg) {
        recordEvent(Level.TRACE, marker, format, new Object[] { arg }, null);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.TRACE, marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... argArray) {
        recordEvent(Level.TRACE, marker, format, argArray, null);

    }

    public void trace(Marker marker, String msg, Throwable t) {
        recordEvent(Level.TRACE, marker, msg, null, t);
    }

    public boolean isDebugEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void debug(String msg) {
        recordEventwithoutMarkerArgArray(Level.DEBUG, msg, null, null);
    }

    public void debug(String format, Object arg) {
        recordEventwithoutMarkerArgArray(Level.DEBUG, format, new Object[] { arg }, null);

    }

    public void debug(String format, Object arg1, Object arg2) {
        recordEventWithoutMarker2Args(Level.DEBUG, format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        recordEventwithoutMarkerArgArray(Level.DEBUG, format, arguments, null);
    }

    public void debug(String msg, Throwable t) {
        recordEventwithoutMarkerArgArray(Level.DEBUG, msg, null, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void debug(Marker marker, String msg) {
        recordEvent(Level.DEBUG, marker, msg, null, null);
    }

    public void debug(Marker marker, String format, Object arg) {
        recordEvent(Level.DEBUG, marker, format, new Object[] { arg }, null);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.DEBUG, marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... arguments) {
        recordEvent(Level.DEBUG, marker, format, arguments, null);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        recordEvent(Level.DEBUG, marker, msg, null, t);
    }

    public boolean isInfoEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void info(String msg) {
        recordEventwithoutMarkerArgArray(Level.INFO, msg, null, null);
    }

    public void info(String format, Object arg) {
        recordEventwithoutMarkerArgArray(Level.INFO, format, new Object[] { arg }, null);
    }

    public void info(String format, Object arg1, Object arg2) {
        recordEventWithoutMarker2Args(Level.INFO, format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        recordEventwithoutMarkerArgArray(Level.INFO, format, arguments, null);
    }

    public void info(String msg, Throwable t) {
        recordEventwithoutMarkerArgArray(Level.INFO, msg, null, t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void info(Marker marker, String msg) {
        recordEvent(Level.INFO, marker, msg, null, null);
    }

    public void info(Marker marker, String format, Object arg) {
        recordEvent(Level.INFO, marker, format, new Object[] { arg }, null);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.INFO, marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... arguments) {
        recordEvent(Level.INFO, marker, format, arguments, null);
    }

    public void info(Marker marker, String msg, Throwable t) {
        recordEvent(Level.INFO, marker, msg, null, t);

    }

    public boolean isWarnEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void warn(String msg) {
        recordEventwithoutMarkerArgArray(Level.WARN, msg, null, null);
    }

    public void warn(String format, Object arg) {
        recordEventwithoutMarkerArgArray(Level.WARN, format, new Object[] { arg }, null);

    }

    public void warn(String format, Object arg1, Object arg2) {
        recordEventWithoutMarker2Args(Level.WARN, format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        recordEventwithoutMarkerArgArray(Level.WARN, format, arguments, null);
    }

    public void warn(String msg, Throwable t) {
        recordEventwithoutMarkerArgArray(Level.WARN, msg, null, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void warn(Marker marker, String msg) {
        recordEvent(Level.WARN, marker, msg, null, null);
    }

    public void warn(Marker marker, String format, Object arg) {
        recordEvent(Level.WARN, marker, format, new Object[] { arg }, null);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.WARN, marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... arguments) {
        recordEvent(Level.WARN, marker, format, arguments, null);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        recordEvent(Level.WARN, marker, msg, null, t);
    }

    public boolean isErrorEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public void error(String msg) {
        recordEventwithoutMarkerArgArray(Level.ERROR, msg, null, null);
    }

    public void error(String format, Object arg) {
        recordEventwithoutMarkerArgArray(Level.ERROR, format, new Object[] { arg }, null);

    }

    public void error(String format, Object arg1, Object arg2) {
        recordEventWithoutMarker2Args(Level.ERROR, format, arg1, arg2);

    }

    public void error(String format, Object... arguments) {
        recordEventwithoutMarkerArgArray(Level.ERROR, format, arguments, null);

    }

    public void error(String msg, Throwable t) {
        recordEventwithoutMarkerArgArray(Level.ERROR, msg, null, t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return RECORD_ALL_EVENTS;
    }

    public void error(Marker marker, String msg) {
        recordEvent(Level.ERROR, marker, msg, null, null);

    }

    public void error(Marker marker, String format, Object arg) {
        recordEvent(Level.ERROR, marker, format, new Object[] { arg }, null);

    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        recordEvent2Args(Level.ERROR, marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... arguments) {
        recordEvent(Level.ERROR, marker, format, arguments, null);
    }

    public void error(Marker marker, String msg, Throwable t) {
        recordEvent(Level.ERROR, marker, msg, null, t);
    }

    private void recordEventWithoutMarker2Args(Level level, String msg, Object arg1, Object arg2) {
        recordEvent2Args(level, null, msg, arg1, arg2);
    }

    private void recordEvent2Args(Level level, Marker marker, String msg, Object arg1, Object arg2) {
        if (arg2 instanceof Throwable) {
            recordEvent(level, marker, msg, new Object[] { arg1 }, (Throwable) arg2);
        } else {
            recordEvent(level, marker, msg, new Object[] { arg1, arg2 }, null);
        }
    }

    private void recordEventwithoutMarkerArgArray(Level level, String msg, Object[] args, Throwable throwable) {
        recordEvent(level, null, msg, args, throwable);
    }

    private void recordEvent(Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
        // System.out.println("recording logger:"+name+", msg:"+msg);
        SubstituteLoggingEvent loggingEvent = new SubstituteLoggingEvent();
        loggingEvent.setTimeStamp(System.currentTimeMillis());
        loggingEvent.setLevel(level);
        loggingEvent.setLogger(logger);
        loggingEvent.setLoggerName(name);
        loggingEvent.addMarker(marker);
        loggingEvent.setMessage(msg);
        loggingEvent.setThreadName(Thread.currentThread().getName());

        // 1 and 2 args are covered by other methods
        if (throwable == null && args != null && args.length > 2) {
            Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(args);
            if(throwableCandidate != null) {
                loggingEvent.setArgumentArray(MessageFormatter.trimmedCopy(args));
                loggingEvent.setThrowable(throwableCandidate);
            } else {
                loggingEvent.setArgumentArray(args);
                loggingEvent.setThrowable(null);
            }
        } else {
            loggingEvent.setArgumentArray(args);
            loggingEvent.setThrowable(throwable);
        }

        eventQueue.add(loggingEvent);
    }
}
