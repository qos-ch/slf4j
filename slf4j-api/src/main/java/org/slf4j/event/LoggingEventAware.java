package org.slf4j.event;

/**
 * 
 * @author Ceki Gulcu
 * @since 1.7.15
 */
public interface LoggingEventAware {
    void log(LoggingEvent event);
}
