package org.slf4j.spi;

import org.slf4j.Marker;

public interface LoggingEventBuilder {


	LoggingEventBuilder withMarker(Marker marker);
	LoggingEventBuilder withCause(Throwable cause);
	LoggingEventBuilder withParameter(Object p);
	void log(String message);
	
}
