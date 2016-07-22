package org.slf4j.event;

/**
 * 
 * @author ceki
 * @since 1.7.15
 */
public final class Level {

    public static final Level ERROR = new Level(EventConstants.ERROR_INT, "ERROR");
    public static final Level WARN = new Level(EventConstants.WARN_INT, "WARN");
    public static final Level INFO = new Level(EventConstants.INFO_INT, "INFO");
    public static final Level DEBUG = new Level(EventConstants.DEBUG_INT, "DEBUG");
    public static final Level TRACE = new Level(EventConstants.TRACE_INT, "TRACE");

    private int levelInt;
    private String levelStr;

    private Level(int i, String s) {
        levelInt = i;
        levelStr = s;
    }

    public int toInt() {
        return levelInt;
    }

    /**
     * Returns the string representation of this Level.
     */
    public String toString() {
        return levelStr;
    }

    public boolean equals(Object o) {
        return this.levelInt == ((Level)o).levelInt;
    }
}
