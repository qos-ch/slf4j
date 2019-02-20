package org.slf4j.spi;

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
	public LoggingEventBuilder withMarker(Marker marker) {
		this.marker = marker;
		return this;
	}

	@Override
	public LoggingEventBuilder withCause(Throwable cause) {
		this.cause = cause;
		return this;
	}

	@Override
	public LoggingEventBuilder withParameter(Object p) {
		this.parameter = p;
		return this;
	}

	@Override
	public void log(String message) {
		
		//logger.log(marker, message, parameter, cause);
	}

}
