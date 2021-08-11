package org.slf4j.jdk;

import static java.util.Objects.requireNonNull;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;

/**
 * Adapts {@link Logger} to {@link System.Logger}.
 */
class SLF4JSystemLogger implements System.Logger {

    private final Logger slf4jLogger;

    public SLF4JSystemLogger(Logger logger) {
        this.slf4jLogger = requireNonNull(logger);
    }

    @Override
    public String getName() {
        return slf4jLogger.getName();
    }

    @Override
    public boolean isLoggable(Level jplLevel) {
        switch (jplLevel) {
        case ALL:
            return true;
        case TRACE:
            return slf4jLogger.isTraceEnabled();
        case DEBUG:
            return slf4jLogger.isDebugEnabled();
        case INFO:
            return slf4jLogger.isInfoEnabled();
        case WARNING:
            return slf4jLogger.isWarnEnabled();
        case ERROR:
            return slf4jLogger.isErrorEnabled();
        case OFF:
            return false;
        default:
            reportUnknownLevel(jplLevel);
            return true;
        }
    }

    @Override
    public void log(Level jplLevel, ResourceBundle bundle, String msg, Throwable thrown) {
        String message = getResourceStringOrMessage(bundle, msg);
        switch (jplLevel) {
        case ALL:
            // fall-through intended because a message is visible on all log levels
            // if it is logged on the lowest level
        case TRACE:
            slf4jLogger.trace(message, thrown);
            break;
        case DEBUG:
            slf4jLogger.debug(message, thrown);
            break;
        case INFO:
            slf4jLogger.info(message, thrown);
            break;
        case WARNING:
            slf4jLogger.warn(message, thrown);
            break;
        case ERROR:
            slf4jLogger.error(message, thrown);
            break;
        case OFF:
            // don't do anything for a message on level `OFF`
            break;
        default:
            reportUnknownLevel(jplLevel);

        }
    }

    @Override
    public void log(Level jplLevel, ResourceBundle bundle, String format, Object... params) {
        String message = getResourceStringOrMessage(bundle, format);
        switch (jplLevel) {
        case ALL:
            // fall-through intended because a message is visible on all log levels
            // if it is logged on the lowest level
        case TRACE:
            slf4jLogger.trace(message, params);
            break;
        case DEBUG:
            slf4jLogger.debug(message, params);
            break;
        case INFO:
            slf4jLogger.info(message, params);
            break;
        case WARNING:
            slf4jLogger.warn(message, params);
            break;
        case ERROR:
            slf4jLogger.error(message, params);
            break;
        case OFF:
            // don't do anything for a message on level `OFF`
            break;
        default:
            reportUnknownLevel(jplLevel);
        }
    }

    private void reportUnknownLevel(Level jplLevel) {
        String message = "Unknown log level [" + jplLevel + "]";
        IllegalArgumentException iae = new IllegalArgumentException(message);
        org.slf4j.helpers.Util.report("Unsupported log level", iae);
    }
    
    private static String getResourceStringOrMessage(ResourceBundle bundle, String msg) {
        if (bundle == null || msg == null)
            return msg;
        // ResourceBundle::getString throws:
        //
        // * NullPointerException for null keys
        // * ClassCastException if the message is no string
        // * MissingResourceException if there is no message for the key
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
