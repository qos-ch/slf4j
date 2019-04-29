package org.slf4j.event;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A default implementation of {@link LoggingEvent}.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 * @since 2.0.0
 */
public class DefaultLoggingEvent implements LoggingEvent {

	Logger logger;
	Level level;

	String message;
	List<Marker> markers;
	List<Object> arguments;
	List<KeyValuePair> keyValuePairs;

	Throwable cause;
	String threadName;
	long timeStamp;
	

	public DefaultLoggingEvent(Level level, Logger logger) {
		this.logger = logger;
		this.level = level;
	}

	public void addMarker(Marker marker) {
		getMarkers().add(marker);
	}

	@Override
	public List<Marker> getMarkers() {
		if (markers == null) {
			markers = new ArrayList<>(5);
		}
		return markers;
	}

	public void addArgument(Object p) {
		getNonNullArguments().add(p);
	}

	private List<Object> getNonNullArguments() {
		if (arguments == null) {
			arguments = new ArrayList<>(5);
		}
		return arguments;
	}

	@Override
	public List<Object> getArguments() {
		return arguments;
	}

	@Override
	public Object[] getArgumentArray() {
		if (arguments == null)
			return null;
		return arguments.toArray();
	}

	public void addKeyValue(String key, Object value) {
		getNonnullKeyValuePairs().add(new KeyValuePair(key, value));
	}

	private List<KeyValuePair> getNonnullKeyValuePairs() {
		if (keyValuePairs == null) {
			keyValuePairs = new ArrayList<>(4);
		}
		return keyValuePairs;
	}

	@Override
	public List<KeyValuePair> getKeyValuePairs() {
		return keyValuePairs;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public String getLoggerName() {
		return logger.getName();
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Throwable getThrowable() {
		return cause;
	}
	public String getThreadName() {
		return threadName;
	}

	public long getTimeStamp() {
		return timeStamp;
	}
}
