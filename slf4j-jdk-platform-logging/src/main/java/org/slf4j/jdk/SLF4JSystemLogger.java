package org.slf4j.jdk;

import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

class SLF4JSystemLogger implements System.Logger {

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
                throw new UnsupportedOperationException();
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
                throw new UnsupportedOperationException();
        }
        // TODO
        return true;
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        switch(level) {
            case ALL:
                throw new UnsupportedOperationException();
            case TRACE:
                logger.trace(msg, thrown);
                break;
            case DEBUG:
                logger.debug(msg, thrown);
                break;
            case INFO:
                logger.info(msg, thrown);
                break;
            case WARNING:
                logger.warn(msg, thrown);
                break;
            case ERROR:
                logger.error(msg, thrown);
                break;
            case OFF:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        switch(level) {
            case ALL:
                throw new UnsupportedOperationException();
            case TRACE:
                logger.trace(MessageFormat.format(format, params));
                break;
            case DEBUG:
                logger.debug(MessageFormat.format(format, params));
                break;
            case INFO:
                logger.info(MessageFormat.format(format, params));
                break;
            case WARNING:
                logger.warn(MessageFormat.format(format, params));
                break;
            case ERROR:
                logger.error(MessageFormat.format(format, params));
                break;
            case OFF:
                throw new UnsupportedOperationException();
        }
    }

}
