package org.slf4j.event;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class DefaultLoggingEvent implements LoggingEvent {

	Logger logger;
	Level level;
	
	List<Marker> markers;
	List<Object> parameters;
	List<KeyValuePair> keyValuePairs;
	
	Throwable cause;
	String threadName;
	
	public DefaultLoggingEvent(Level level, Logger logger) {
		this.logger = logger;
		this.level = level;
	}
	
	public void addMarker(Marker marker) {
		getMarkers().add(marker);
	}

	@Override
	public List<Marker> getMarkers() {
		if(markers == null) {
			markers = new ArrayList<>(5);
		}
		return markers;
	}
	
	
	public void addParameter(Object p) {
		getParameters().add(p);
	}

	private List<Object> getParameters() {
		
		if(parameters == null) {
			parameters = new ArrayList<>(5);
		}
		return parameters;
	}

	public void addKeyValue(String key, Object value) {
		getKeyValuePairs().add(new KeyValuePair(key, value));
	}

	
	private List<KeyValuePair> getKeyValuePairs() {
		if(keyValuePairs == null) {
			keyValuePairs = new ArrayList<>(4);
		}
		return keyValuePairs;
	}

	
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	@Override
	public Level getLevel() {
		return level;
	}


	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	
	@Override
	public String getThreadName() {
		return threadName;
	}
	
	@Override
	public String getLoggerName() {
		return logger.getName();
	}

	
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object[] getArgumentArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTimeStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Throwable getThrowable() {
		// TODO Auto-generated method stub
		return null;
	}

}
