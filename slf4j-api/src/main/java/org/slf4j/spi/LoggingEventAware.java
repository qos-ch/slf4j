package org.slf4j.spi;

import org.slf4j.event.LoggingEvent;

public interface LoggingEventAware {

	void log(LoggingEvent event);
	
}
