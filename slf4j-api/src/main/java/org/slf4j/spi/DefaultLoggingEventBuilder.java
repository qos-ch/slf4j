package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEventAware;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder {

	DefaultLoggingEvent logggingEvent;
	Logger logger;

	public DefaultLoggingEventBuilder(Logger logger, Level level) {
		this.logger = logger;
		logggingEvent = new DefaultLoggingEvent(level, logger);
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
	public LoggingEventBuilder setCause(Throwable t) {
		logggingEvent.setThrowable(t);
		return this;
	}

	@Override
	public LoggingEventBuilder addArgument(Object p) {
		logggingEvent.addArgument(p);
		return this;
	}

	@Override
	public LoggingEventBuilder addArgument(Supplier<Object> objectSupplier) {
		logggingEvent.addArgument(objectSupplier.get());
		return this;
	}

	@Override
	public void log(String message) {
		logggingEvent.setMessage(message);

		if (logger instanceof LoggingEventAware) {
			((LoggingEventAware) logger).log(logggingEvent);
		} else {
			logViaPublicLoggerAPI();
		}

	}

	private void logViaPublicLoggerAPI() {
		Object[] argArray = logggingEvent.getArgumentArray();
		int argLen = argArray == null ? 0 : argArray.length;

		Throwable t = logggingEvent.getThrowable();
		int tLen = t == null ? 0 : 1;

		String msg = logggingEvent.getMessage();

		Object[] combinedArguments = new Object[argLen + tLen];

		if (argArray != null) {
			System.arraycopy(argArray, 0, combinedArguments, 0, argLen);
		}
		if (t != null) {
			combinedArguments[argLen] = t;
		}

		switch (logggingEvent.getLevel()) {
		case TRACE:
			logger.trace(msg, combinedArguments);
			break;
		case DEBUG:
			logger.debug(msg, combinedArguments);
			break;
		case INFO:
			logger.info(msg, combinedArguments);
			break;
		case WARN:
			logger.warn(msg, combinedArguments);
			break;
		case ERROR:
			logger.error(msg, combinedArguments);
			break;
		}

	}

	@Override
	public void log(Supplier<String> messageSupplier) {
		if (messageSupplier == null) {
			log((String) null);
		} else {
			log(messageSupplier.get());
		}
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
