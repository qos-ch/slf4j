package org.slf4j.event;

import org.slf4j.spi.LocationAwareLogger;

/**
 * Various constants used by {@link Level} and {@link org.slf4j.Logger}.
 *
 *
 */
public class EventConstants {
    public static final int ERROR_INT = LocationAwareLogger.ERROR_INT;
    public static final int WARN_INT = LocationAwareLogger.WARN_INT;
    public static final int INFO_INT = LocationAwareLogger.INFO_INT;
    public static final int DEBUG_INT = LocationAwareLogger.DEBUG_INT;
    public static final int TRACE_INT = LocationAwareLogger.TRACE_INT;
    public static final String NA_SUBST = "NA/SubstituteLogger";

}
