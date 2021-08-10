package org.slf4j.jdk;

import static java.util.Objects.requireNonNull;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapts {@link Logger} to {@link System.Logger}.
 */
class SLF4JSystemLogger implements System.Logger {

    private static final Logger INTERNAL_LOGGER = LoggerFactory.getLogger(SLF4JSystemLogger.class);

    private final Logger logger;

    public SLF4JSystemLogger(Logger logger) {
        this.logger = requireNonNull(logger);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isLoggable(Level level) {
        switch(level) {
            case ALL:
                // fall-through intended because `ALL` is loggable if the
                // lowest level is enabled
            case TRACE:
                return logger.isTraceEnabled();
            case DEBUG:
                return logger.isDebugEnabled();
            case INFO:
                return logger.isInfoEnabled();
            case WARNING:
                return logger.isWarnEnabled();
            case ERROR:
                return logger.isErrorEnabled();
            case OFF:
                // all logging is disabled if the highest level is disabled
                return !logger.isErrorEnabled();
            default:
                org.slf4j.helpers.Util.report(
                        "unknown log level ["+level+"] passed to `isLoggable` (likely by the JDK).");
                return true;
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        String message = getResourceStringOrMessage(bundle, msg);
        switch(level) {
            case ALL:
                // fall-through intended because a message is visible on all log levels
                // if it is logged on the lowest level
            case TRACE:
                logger.trace(message, thrown);
                break;
            case DEBUG:
                logger.debug(message, thrown);
                break;
            case INFO:
                logger.info(message, thrown);
                break;
            case WARNING:
                logger.warn(message, thrown);
                break;
            case ERROR:
                logger.error(message, thrown);
                break;
            case OFF:
                // don't do anything for a message on level `OFF`
                break;
            default:
                INTERNAL_LOGGER.error(
                        "SLF4J internal error: unknown log level {} passed to `log` (likely by the JDK).", level);
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        String message = getResourceStringOrMessage(bundle, format);
        switch(level) {
            case ALL:
                // fall-through intended because a message is visible on all log levels
                // if it is logged on the lowest level
            case TRACE:
                logger.trace(message, params);
                break;
            case DEBUG:
                logger.debug(message, params);
                break;
            case INFO:
                logger.info(message, params);
                break;
            case WARNING:
                logger.warn(message, params);
                break;
            case ERROR:
                logger.error(message, params);
                break;
            case OFF:
                // don't do anything for a message on level `OFF`
                break;
            default:
                INTERNAL_LOGGER.error(
                        "SLF4J internal error: unknown log level {} passed to `log` (likely by the JDK).", level);
        }
    }

    private static String getResourceStringOrMessage(ResourceBundle bundle, String msg) {
        if (bundle == null || msg == null)
            return msg;
        // ResourceBundle::getString throws:
        //
        //  * NullPointerException for null keys
        //  * ClassCastException if the message is no string
        //  * MissingResourceException if there is no message for the key
        //
        // Handle all of these cases here to avoid log-related exceptions from crashing the JVM.
        try {
            return bundle.getString(msg);
        } catch (MissingResourceException ex) {
            return msg;
        } catch (ClassCastException ex) {
            return bundle.getObject(msg).toString();
        }
    }

}
