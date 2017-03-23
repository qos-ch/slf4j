package org.slf4j;

/**
 * All methods in this class are reserved for internal use, for testing purposes.
 * <p/>
 * <p/>They can can be modified, renamed or removed at any time without notice.
 * <p/>
 * You are strongly discouraged calling any of the methods of this class.
 *
 * @since 1.8.0
 *
 *  @author Ceki G&uuml;lc&uuml;
 */
public class LoggerFactoryFriend {

    /*
     * Force LoggerFactory to consider itself uninitialized. <p/>
     */
    static public void reset() {
        LoggerFactory.reset();
    }

    /**
     * Set LoggerFactory.DETECT_LOGGER_NAME_MISMATCH variable.
     * 
     * @param enabled
     */
    public static void setDetectLoggerNameMismatch(boolean enabled) {
        LoggerFactory.DETECT_LOGGER_NAME_MISMATCH = enabled;
    }
}
