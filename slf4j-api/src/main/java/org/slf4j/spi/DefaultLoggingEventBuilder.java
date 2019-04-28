package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder {

	Logger logger;
	Level level;
	
	Marker marker;
	Throwable cause;
	Object parameter;
	
	
	public DefaultLoggingEventBuilder(Level level, Logger logger) {
		this.logger = logger;
		this.level = level;
	}
	@Override
	public LoggingEventBuilder addMarker(Marker marker) {
		this.marker = marker;
		return this;
	}

	@Override
	public LoggingEventBuilder setCause(Throwable cause) {
		this.cause = cause;
		return this;
	}

	@Override
	public LoggingEventBuilder addParameter(Object p) {
		this.parameter = p;
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
		return this;
	}
	@Override
	public LoggingEventBuilder addKeyValue(String key, Object value) {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
		// TODO Auto-generated method stub
		return this;
	}

}
