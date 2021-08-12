package org.slf4j.spi;


/**
 * Additional interface to {@link LoggingEventBuilder} and {@link LoggingEvent}. 
 * 
 * Implementations of {@link LoggingEventBuilder} and  {@link LoggingEvent} may optionally 
 * implement {@link CallerBoundaryAware} in order to support caller info extraction.
 *
 * This interface is intended for use by logging backends or logging bridges. 
 * 
 * @author Ceki Gulcu
 *
 */
public interface CallerBoundaryAware {

    /**
     * Add a fqcn (fully qualified class name) to this event, presumed to be the caller boundary.
     * 
     * @param fqcn
     */
    void setCallerBoundary(String fqcn);
}
