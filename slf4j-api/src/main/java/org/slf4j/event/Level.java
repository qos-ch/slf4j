package org.slf4j.event;

import static org.slf4j.event.EventConstants.DEBUG_INT;
import static org.slf4j.event.EventConstants.ERROR_INT;
import static org.slf4j.event.EventConstants.INFO_INT;
import static org.slf4j.event.EventConstants.TRACE_INT;
import static org.slf4j.event.EventConstants.WARN_INT;

/**
 * SLF4J's internal representation of Level.
 * 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.7.15
 */
public enum Level {

    ERROR(ERROR_INT, "ERROR"), WARN(WARN_INT, "WARN"), INFO(INFO_INT, "INFO"), DEBUG(DEBUG_INT, "DEBUG"), TRACE(TRACE_INT, "TRACE");

    private final int levelInt;
    private final String levelStr;

    Level(int i, String s) {
        levelInt = i;
        levelStr = s;
    }

    public int toInt() {
        return levelInt;
    }

    public static Level intToLevel(int levelInt) {
        switch (levelInt) {
        case (TRACE_INT):
            return TRACE;
        case (DEBUG_INT):
            return DEBUG;
        case (INFO_INT):
            return INFO;
        case (WARN_INT):
            return WARN;
        case (ERROR_INT):
            return ERROR;
        default:
            throw new IllegalArgumentException("Level integer [" + levelInt + "] not recognized.");
        }
    }

    /**
     * Returns the string representation of this Level.
     */
    public String toString() {
        return levelStr;
    }

}
