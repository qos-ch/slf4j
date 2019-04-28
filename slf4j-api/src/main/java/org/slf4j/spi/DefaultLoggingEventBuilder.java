package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder {

	DefaultLoggingEvent logggingEvent;
	
	
	public DefaultLoggingEventBuilder(Level level, Logger logger) {
		logggingEvent = new DefaultLoggingEvent(level, logger);
		logggingEvent.setThreadName(Thread.currentThread().getName());
	}
	
	/**
	 * Add a marker to the current logging event being built.
	 * 
	 * It is possible to add multiple markers to the same logging event.
	 *
	 * @param marker the marker to add
	 */
	@Override
	public LoggingEventBuilder addMarker(Marker marker) {
		logggingEvent.addMarker(marker);
		return this;
	}
	
	
	@Override
	public LoggingEventBuilder setCause(Throwable cause) {
		logggingEvent.setCause(cause);
		return this;
	}

	@Override
	public LoggingEventBuilder addParameter(Object p) {
		logggingEvent.addParameter(p);
		return this;
	}

	@Override
	public void log(String message) {
		
	}

	@Override
	public void log(Supplier<String> messageSupplier) {
	}
	
	@Override
	public LoggingEventBuilder addParameter(Supplier<Object> objectSupplier) {
		logggingEvent.addParameter(objectSupplier.get());
		return this;
	}
	
	@Override
	public LoggingEventBuilder addKeyValue(String key, Object value) {
		logggingEvent.addKeyValue(key, value);
		return this;
	}

	@Override
	public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
		logggingEvent.addKeyValue(key, value.get());
		return this;
	}

}
