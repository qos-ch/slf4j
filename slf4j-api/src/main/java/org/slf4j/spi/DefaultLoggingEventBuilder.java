/**
 * Copyright (c) 2004-2022 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

/**
 * Default implementation of {@link LoggingEventBuilder}
 */
public class DefaultLoggingEventBuilder implements LoggingEventBuilder, CallerBoundaryAware {

    
    // The caller boundary when the log() methods are invoked, is this class itself.
    
    static String DLEB_FQCN = DefaultLoggingEventBuilder.class.getName();
    
    protected DefaultLoggingEvent loggingEvent;
    protected Logger logger;

    public DefaultLoggingEventBuilder(Logger logger, Level level) {
        this.logger = logger;
        loggingEvent = new DefaultLoggingEvent(level, logger);
    }

    /**
     * Add a marker to the current logging event being built.
     * 
     * It is possible to add multiple markers to the same logging event.
     *
     * @param marker the marker to add
     */
    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        loggingEvent.addMarker(marker);
        return this;
    }

    @Override
    public LoggingEventBuilder setCause(Throwable t) {
        loggingEvent.setThrowable(t);
        return this;
    }

    @Override
    public LoggingEventBuilder addArgument(Object p) {
        loggingEvent.addArgument(p);
        return this;
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> objectSupplier) {
        loggingEvent.addArgument(objectSupplier.get());
        return this;
    }

    @Override
    public void setCallerBoundary(String fqcn) {
        loggingEvent.setCallerBoundary(fqcn);
    }

    @Override
    public void log() {
        log(loggingEvent);
    }

    @Override
    public LoggingEventBuilder setMessage(String message) {
        loggingEvent.setMessage(message);
        return this;
    }
    @Override
    public LoggingEventBuilder setMessage(Supplier<String> messageSupplier) {
        loggingEvent.setMessage(messageSupplier.get());
        return this;
    }

    @Override
    public void log(String message) {
        loggingEvent.setMessage(message);
        log(loggingEvent);
    }

    @Override
    public void log(String message, Object arg) {
        loggingEvent.setMessage(message);
        loggingEvent.addArgument(arg);
        log(loggingEvent);
    }

    @Override
    public void log(String message, Object arg0, Object arg1) {
        loggingEvent.setMessage(message);
        loggingEvent.addArgument(arg0);
        loggingEvent.addArgument(arg1);
        log(loggingEvent);
    }

    @Override
    public void log(String message, Object... args) {
        loggingEvent.setMessage(message);
        loggingEvent.addArguments(args);

        log(loggingEvent);
    }

    @Override
    public void log(Supplier<String> messageSupplier) {
        if (messageSupplier == null) {
            log((String) null);
        } else {
            log(messageSupplier.get());
        }
    }
    
    protected void log(LoggingEvent aLoggingEvent) {
        setCallerBoundary(DLEB_FQCN);
        if (logger instanceof LoggingEventAware) {
            ((LoggingEventAware) logger).log(aLoggingEvent);
        } else {
            logViaPublicSLF4JLoggerAPI(aLoggingEvent);
        }
    }

    private void logViaPublicSLF4JLoggerAPI(LoggingEvent aLoggingEvent) {
        Object[] argArray = aLoggingEvent.getArgumentArray();
        int argLen = argArray == null ? 0 : argArray.length;

        Throwable t = aLoggingEvent.getThrowable();
        int tLen = t == null ? 0 : 1;

        String msg = aLoggingEvent.getMessage();

        Object[] combinedArguments = new Object[argLen + tLen];

        if (argArray != null) {
            System.arraycopy(argArray, 0, combinedArguments, 0, argLen);
        }
        if (t != null) {
            combinedArguments[argLen] = t;
        }

        msg = mergeMarkersAndKeyValuePairs(aLoggingEvent, msg);

        switch (aLoggingEvent.getLevel()) {
        case TRACE:
            logger.trace(msg, combinedArguments);
            break;
        case DEBUG:
            logger.debug(msg, combinedArguments);
            break;
        case INFO:
            logger.info(msg, combinedArguments);
            break;
        case WARN:
            logger.warn(msg, combinedArguments);
            break;
        case ERROR:
            logger.error(msg, combinedArguments);
            break;
        }

    }

    /**
     * Prepend markers and key-value pairs to the message.
     * 
     * @param aLoggingEvent
     * @param msg
     * @return
     */
    private String mergeMarkersAndKeyValuePairs(LoggingEvent aLoggingEvent, String msg) {

        StringBuilder sb = null;

        if (aLoggingEvent.getMarkers() != null) {
            sb = new StringBuilder();
            for (Marker marker : aLoggingEvent.getMarkers()) {
                sb.append(marker);
                sb.append(' ');
            }
        }

        if (aLoggingEvent.getKeyValuePairs() != null) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            for (KeyValuePair kvp : aLoggingEvent.getKeyValuePairs()) {
                sb.append(kvp.key);
                sb.append('=');
                sb.append(kvp.value);
                sb.append(' ');
            }
        }

        if (sb != null) {
            sb.append(msg);
            return sb.toString();
        } else {
            return msg;
        }
    }



    @Override
    public LoggingEventBuilder addKeyValue(String key, Object value) {
        loggingEvent.addKeyValue(key, value);
        return this;
    }

    @Override
    public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
        loggingEvent.addKeyValue(key, value.get());
        return this;
    }

}
