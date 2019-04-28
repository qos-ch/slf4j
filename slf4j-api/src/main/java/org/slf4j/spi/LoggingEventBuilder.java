package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Marker;

/**
 * A fluent API for creating logging events.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 *
 */
public interface LoggingEventBuilder {


	LoggingEventBuilder setCause(Throwable cause);
	
	LoggingEventBuilder addMarker(Marker marker);
	LoggingEventBuilder addParameter(Object p);
	LoggingEventBuilder addParameter(Supplier<Object> objectSupplier);
	LoggingEventBuilder addKeyValue(String key, Object value);
	LoggingEventBuilder addKeyValue(String key, Supplier<Object> value);
	
	void log(String message);
	void log(Supplier<String> messageSupplier);

	
	
}
