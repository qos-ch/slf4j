package org.slf4j.event;

import java.util.List;

import org.slf4j.Marker;

/**
 * The minimal interface sufficient for the restitution of data passed
 * by the user to the SLF4J API.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.7.15
 */
public interface LoggingEvent {

    Level getLevel();

    String getLoggerName();

    /** Log message, might be null. */
    String getMessage();

    /** List of arguments in the event, might be null. */
    List<Object> getArguments();

    /** Array of arguments in the event, might be null. */
    Object[] getArgumentArray();

    /** List of markers in the event, might be null. */
    List<Marker> getMarkers();

    /** List of key-value pairs in the event, might be null. */
    List<KeyValuePair> getKeyValuePairs();

    /** Cause exception, might be null. */
    Throwable getThrowable();

    long getTimeStamp();

    /** Name of thread that made the log, might be null. */
    String getThreadName();
 
    /**
     * Returns the presumed caller boundary provided by the logging library (not the user of the library). 
     * Null by default.
     *  
     * @return presumed caller, null by default.
     */
    default String getCallerBoundary() {
        return null;
    }
}
