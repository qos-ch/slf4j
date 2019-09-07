package org.slf4j.event;

import java.util.Queue;

import org.slf4j.Marker;
import org.slf4j.helpers.AbstractLogger;
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
public class EventRecodingLogger extends AbstractLogger  {

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

	public boolean isTraceEnabled(Marker marker) {
		return RECORD_ALL_EVENTS;
	}


	public boolean isDebugEnabled() {
		return RECORD_ALL_EVENTS;
	}

	public boolean isDebugEnabled(Marker marker) {
		return RECORD_ALL_EVENTS;
	}

	public boolean isInfoEnabled() {
		return RECORD_ALL_EVENTS;
	}

	public boolean isInfoEnabled(Marker marker) {
		return RECORD_ALL_EVENTS;
	}

	public boolean isWarnEnabled() {
		return RECORD_ALL_EVENTS;
	}

	public boolean isWarnEnabled(Marker marker) {
		return RECORD_ALL_EVENTS;
	}


	public boolean isErrorEnabled() {
		return RECORD_ALL_EVENTS;
	}

	public boolean isErrorEnabled(Marker marker) {
		return RECORD_ALL_EVENTS;
	}

	// WARNING: this method assumes that any throwable is properly extracted
	protected void handleNormalizedLoggingCall(Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
		SubstituteLoggingEvent loggingEvent = new SubstituteLoggingEvent();
		loggingEvent.setTimeStamp(System.currentTimeMillis());
		loggingEvent.setLevel(level);
		loggingEvent.setLogger(logger);
		loggingEvent.setLoggerName(name);
		loggingEvent.addMarker(marker);
		loggingEvent.setMessage(msg);
		loggingEvent.setThreadName(Thread.currentThread().getName());

		loggingEvent.setArgumentArray(args);
		loggingEvent.setThrowable(throwable);

		eventQueue.add(loggingEvent);
	}
}
