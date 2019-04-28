package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Marker;

/**
 * A no-operation implementation of {@link LoggingEventBuilder}. 
 * As the name indicates, this implementation does nothing.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 *
 */
public class NOPLoggingEventBuilder implements LoggingEventBuilder {

	static final NOPLoggingEventBuilder SINGLETON = new NOPLoggingEventBuilder();
	
	public static LoggingEventBuilder singleton() {
		return SINGLETON;
	}

	@Override
	public LoggingEventBuilder addMarker(Marker marker) {
		return singleton();
	}

	@Override
	public LoggingEventBuilder addParameter(Object p) {
		return singleton();
	}

	@Override
	public LoggingEventBuilder addParameter(Supplier<Object> objectSupplier) {
		return singleton();
	}

	@Override
	public LoggingEventBuilder addKeyValue(String key, Object value) {
		return singleton();
	}

	@Override
	public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
		return singleton();
	}

	@Override
	public LoggingEventBuilder setCause(Throwable cause) {
		return singleton();
	}

	@Override
	public void log(String message) {
		
	}

	@Override
	public void log(Supplier<String> messageSupplier) {
	}

}
