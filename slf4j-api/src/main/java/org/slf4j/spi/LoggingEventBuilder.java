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
	LoggingEventBuilder addArgument(Object p);
	LoggingEventBuilder addArgument(Supplier<Object> objectSupplier);
	LoggingEventBuilder addKeyValue(String key, Object value);
	LoggingEventBuilder addKeyValue(String key, Supplier<Object> value);
	
	void log(String message);

	void log(String message, Object arg);
	void log(String message, Object arg0, Object arg1);
	void log(String message, Object... args);
	
	void log(Supplier<String> messageSupplier);

	
	
}
