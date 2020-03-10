package org.slf4j.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

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
                INTERNAL_LOGGER.error(
                        "SLF4J internal error: unknown log level {} passed to `isLoggable` (likely by the JDK).", level);
                return true;
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        String message = bundle == null ? msg : bundle.getString(msg);
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
        String message = bundle == null ? format : bundle.getString(format);
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

}
