package org.slf4j.spi;

import org.slf4j.event.LoggingEvent;

/**
 * A logger capable of logging from org.slf4j.event.LoggingEvent implements this interface.
 * 
 * @author Ceki Gulcu
 * @since 2.0.0
 */
public interface LoggingEventAware {
    void log(LoggingEvent event);
}
