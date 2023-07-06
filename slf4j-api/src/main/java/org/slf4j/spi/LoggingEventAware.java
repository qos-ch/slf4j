package org.slf4j.spi;

import org.slf4j.event.LoggingEvent;

/**
 * A logger capable of logging from org.slf4j.event.LoggingEvent implements this interface.
 *
 * <p>Please note that when the {@link #log(LoggingEvent)} method assumes that
 * the event was filtered beforehand and no further filtering needs to occur by the method itself.
 * <p>
 *
 * <p>Implementations of this interface <b>may</b> apply further filtering but they are not
 * required to do so. In other words, {@link #log(LoggingEvent)} method is free to assume that
 * the event was filtered beforehand and no further filtering needs to occur in the method itself.</p>
 *
 * See also https://jira.qos.ch/browse/SLF4J-575
 *
 * @author Ceki Gulcu
 * @since 2.0.0
 */
public interface LoggingEventAware {
    void log(LoggingEvent event);
}
