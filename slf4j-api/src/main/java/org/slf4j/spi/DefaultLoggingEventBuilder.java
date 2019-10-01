package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.LoggingEventAware;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder {

	DefaultLoggingEvent loggingEvent;
	Logger logger;

	public DefaultLoggingEventBuilder(Logger logger, Level level) {
		this.logger = logger;
		loggingEvent = new DefaultLoggingEvent(level, logger);
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
		loggingEvent.addMarker(marker);
		return this;
	}

	@Override
	public LoggingEventBuilder setCause(Throwable t) {
		loggingEvent.setThrowable(t);
		return this;
	}

	@Override
	public LoggingEventBuilder addArgument(Object p) {
		loggingEvent.addArgument(p);
		return this;
	}

	@Override
	public LoggingEventBuilder addArgument(Supplier<Object> objectSupplier) {
		loggingEvent.addArgument(objectSupplier.get());
		return this;
	}

	@Override
	public void log(String message) {
		loggingEvent.setMessage(message);
		innerLog(loggingEvent);
	}

	
	@Override
	public void log(String message, Object arg) {
		loggingEvent.setMessage(message);
		loggingEvent.addArgument(arg);
		innerLog(loggingEvent);
	}

	@Override
	public void log(String message, Object arg0, Object arg1) {
		loggingEvent.setMessage(message);
		loggingEvent.addArgument(arg0);
		loggingEvent.addArgument(arg1);
		innerLog(loggingEvent);
	}

	@Override
	public void log(String message, Object... args) {
		loggingEvent.setMessage(message);
		loggingEvent.addArguments(args);
		
		innerLog(loggingEvent);
	}

	private void innerLog(LoggingEvent logggingEvent) {
		if (logger instanceof LoggingEventAware) {
			((LoggingEventAware) logger).log(logggingEvent);
		} else {
			logViaPublicLoggerAPI(logggingEvent);
		}
	}
	
	private void logViaPublicLoggerAPI(LoggingEvent logggingEvent) {
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

		msg = mergeMarkersAndKeyValuePairs(logggingEvent, msg);
		
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

	/**
	 * Prepend markers and key-value pairs to the message.
	 * 
	 * @param logggingEvent
	 * @param msg
	 * @return
	 */
	private String mergeMarkersAndKeyValuePairs(LoggingEvent logggingEvent, String msg) {
		
		StringBuilder sb = null;
		
		if(loggingEvent.getMarkers() != null) {
			sb = new StringBuilder();
			for(Marker marker: logggingEvent.getMarkers()) {
				sb.append(marker);
				sb.append(' ');
			}
		}

		if(logggingEvent.getKeyValuePairs() != null) {
			if(sb == null) {
				sb = new StringBuilder();
			}
			for(KeyValuePair kvp: logggingEvent.getKeyValuePairs()) {
				sb.append(kvp.key);
				sb.append('=');
				sb.append(kvp.value);
				sb.append(' ');
			}
		} 
		
		if(sb != null) {
			sb.append(msg);
			return sb.toString();
		} else {
			return msg;
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
		loggingEvent.addKeyValue(key, value);
		return this;
	}

	@Override
	public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
		loggingEvent.addKeyValue(key, value.get());
		return this;
	}

}
