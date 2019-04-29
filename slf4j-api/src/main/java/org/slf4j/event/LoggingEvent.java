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
    String getMessage();
    List<Object> getArguments();
    Object[] getArgumentArray();
    
    List<Marker> getMarkers();
    List<KeyValuePair> getKeyValuePairs();
    Throwable getThrowable();
    long getTimeStamp();
    String getThreadName();
}
