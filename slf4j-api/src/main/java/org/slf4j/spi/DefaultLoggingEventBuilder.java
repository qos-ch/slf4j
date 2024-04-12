/**
 * Copyright (c) 2004-2022 QOS.ch
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.slf4j.spi;

import java.util.List;
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
     * <p>
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
        this.loggingEvent.addArgument(p);
        return this;
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> objectSupplier) {
        this.loggingEvent.addArgument(objectSupplier.get());
        return this;
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

    @Override
    public void setCallerBoundary(String fqcn) {
        this.loggingEvent.setCallerBoundary(fqcn);
    }

    @Override
    public void log() {
        log(this.loggingEvent);
    }

    @Override
    public LoggingEventBuilder setMessage(String message) {
        this.loggingEvent.setMessage(message);
        return this;
    }

    @Override
    public LoggingEventBuilder setMessage(Supplier<String> messageSupplier) {
        this.loggingEvent.setMessage(messageSupplier.get());
        return this;
    }

    @Override
    public void log(String message) {
        this.loggingEvent.setMessage(message);
        log(this.loggingEvent);
    }

    @Override
    public void log(String message, Object arg) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArgument(arg);
        log(this.loggingEvent);
    }

    @Override
    public void log(String message, Object arg0, Object arg1) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArgument(arg0);
        this.loggingEvent.addArgument(arg1);
        log(this.loggingEvent);
    }

    @Override
    public void log(String message, Object... args) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArguments(args);

        log(this.loggingEvent);
    }

    @Override
    public void log(Supplier<String> messageSupplier) {
        if(messageSupplier == null) {
            log((String) null);
        } else {
            log(messageSupplier.get());
        }
    }

    protected void log(LoggingEvent aLoggingEvent) {
        if(aLoggingEvent.getCallerBoundary() == null) {
            setCallerBoundary(DLEB_FQCN);
        }

        if(logger instanceof LoggingEventAware) {
            ((LoggingEventAware) logger).log(aLoggingEvent);
        } else if(logger instanceof LocationAwareLogger) {
            logViaLocationAwareLoggerAPI((LocationAwareLogger) logger, aLoggingEvent);
        } else {
            logViaPublicSLF4JLoggerAPI(aLoggingEvent);
        }
    }

    private void logViaLocationAwareLoggerAPI(LocationAwareLogger locationAwareLogger, LoggingEvent aLoggingEvent) {
        String msg = aLoggingEvent.getMessage();
        List<Marker> markerList = aLoggingEvent.getMarkers();
        String mergedMessage = mergeMarkersAndKeyValuePairsAndMessage(aLoggingEvent);
        locationAwareLogger.log(null, aLoggingEvent.getCallerBoundary(), aLoggingEvent.getLevel().toInt(),
                                mergedMessage,
                                aLoggingEvent.getArgumentArray(), aLoggingEvent.getThrowable());
    }

    private void logViaPublicSLF4JLoggerAPI(LoggingEvent aLoggingEvent) {
        Object[] argArray = aLoggingEvent.getArgumentArray();
        int argLen = argArray == null ? 0 : argArray.length;

        Throwable t = aLoggingEvent.getThrowable();
        int tLen = t == null ? 0 : 1;

        Object[] combinedArguments = new Object[argLen + tLen];

        if(argArray != null) {
            System.arraycopy(argArray, 0, combinedArguments, 0, argLen);
        }
        if(t != null) {
            combinedArguments[argLen] = t;
        }

        String mergedMessage = mergeMarkersAndKeyValuePairsAndMessage(aLoggingEvent);


        switch(aLoggingEvent.getLevel()) {
            case TRACE:
                logger.trace(mergedMessage, combinedArguments);
                break;
            case DEBUG:
                logger.debug(mergedMessage, combinedArguments);
                break;
            case INFO:
                logger.info(mergedMessage, combinedArguments);
                break;
            case WARN:
                logger.warn(mergedMessage, combinedArguments);
                break;
            case ERROR:
                logger.error(mergedMessage, combinedArguments);
                break;
        }
    }


    /**
     * Prepend markers and key-value pairs to the message.
     *
     * @param aLoggingEvent
     *
     * @return
     */
    private String mergeMarkersAndKeyValuePairsAndMessage(LoggingEvent aLoggingEvent) {
        StringBuilder sb = mergeMarkers(aLoggingEvent.getMarkers(), null);
        sb = mergeKeyValuePairs(aLoggingEvent.getKeyValuePairs(), sb);
        final String mergedMessage = mergeMessage(aLoggingEvent.getMessage(), sb);
        return mergedMessage;
    }

    private StringBuilder mergeMarkers(List<Marker> markerList, StringBuilder sb) {
        if(markerList == null || markerList.isEmpty())
            return sb;

        if(sb == null)
            sb = new StringBuilder();

        for(Marker marker : markerList) {
            sb.append(marker);
            sb.append(' ');
        }
        return sb;
    }

    private StringBuilder mergeKeyValuePairs(List<KeyValuePair> keyValuePairList, StringBuilder sb) {
        if(keyValuePairList == null || keyValuePairList.isEmpty())
            return sb;

        if(sb == null)
            sb = new StringBuilder();

        for(KeyValuePair kvp : keyValuePairList) {
            sb.append(kvp.key);
            sb.append('=');
            sb.append(kvp.value);
            sb.append(' ');
        }
        return sb;
    }

    private String mergeMessage(String msg, StringBuilder sb) {
        if(sb != null) {
            sb.append(msg);
            return sb.toString();
        } else {
            return msg;
        }
    }






}
