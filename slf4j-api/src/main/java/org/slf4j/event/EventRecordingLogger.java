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

package org.slf4j.event;

import java.util.Queue;

import org.slf4j.Marker;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.SubstituteLogger;

/**
 * 
 * This class is used to record events during the initialization phase of the
 * underlying logging framework. It is called by {@link SubstituteLogger}.
 * 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Wessel van Norel
 *
 */
public class EventRecordingLogger extends LegacyAbstractLogger {

    private static final long serialVersionUID = -176083308134819629L;

    String name;
    SubstituteLogger logger;
    Queue<SubstituteLoggingEvent> eventQueue;

    // as an event recording logger we have no choice but to record all events
    final static boolean RECORD_ALL_EVENTS = true;

    public EventRecordingLogger(SubstituteLogger logger, Queue<SubstituteLoggingEvent> eventQueue) {
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

    public boolean isDebugEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public boolean isInfoEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public boolean isWarnEnabled() {
        return RECORD_ALL_EVENTS;
    }

    public boolean isErrorEnabled() {
        return RECORD_ALL_EVENTS;
    }

    // WARNING: this method assumes that any throwable is properly extracted
    protected void handleNormalizedLoggingCall(Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
        SubstituteLoggingEvent loggingEvent = new SubstituteLoggingEvent();
        loggingEvent.setTimeStamp(System.currentTimeMillis());
        loggingEvent.setLevel(level);
        loggingEvent.setLogger(logger);
        loggingEvent.setLoggerName(name);
        if (marker != null) {
            loggingEvent.addMarker(marker);
        }
        loggingEvent.setMessage(msg);
        loggingEvent.setThreadName(Thread.currentThread().getName());

        loggingEvent.setArgumentArray(args);
        loggingEvent.setThrowable(throwable);

        eventQueue.add(loggingEvent);

    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return null;
    }
}
