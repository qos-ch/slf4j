/**
 * Copyright (c) 2004-2011 QOS.ch
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
package org.slf4j.jul;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventConstants;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.AbstractLogger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NormalizedParameters;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventAware;

/**
 * A wrapper over {@link java.util.logging.Logger java.util.logging.Logger} in
 * conformity with the {@link Logger} interface. Note that the logging levels
 * mentioned in this class refer to those defined in the java.util.logging
 * package.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Peter Royal
 */
public final class JDK14LoggerAdapter extends LegacyAbstractLogger implements LocationAwareLogger, LoggingEventAware {

    private static final long serialVersionUID = -8053026990503422791L;

    transient final java.util.logging.Logger logger;

    static int NOT_FOUND = -1;

    // WARN: JDK14LoggerAdapter constructor should have only package access so
    // that only JDK14LoggerFactory be able to create one.
    JDK14LoggerAdapter(java.util.logging.Logger logger) {
        this.logger = logger;
        this.name = logger.getName();
    }

    /**
     * Is this logger instance enabled for the FINEST level?
     * 
     * @return True if this Logger is enabled for level FINEST, false otherwise.
     */
    public boolean isTraceEnabled() {
        return logger.isLoggable(Level.FINEST);
    }

    /**
     * Is this logger instance enabled for the FINE level?
     * 
     * @return True if this Logger is enabled for level FINE, false otherwise.
     */
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    /**
     * Is this logger instance enabled for the INFO level?
     * 
     * @return True if this Logger is enabled for the INFO level, false otherwise.
     */
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    /**
     * Is this logger instance enabled for the WARNING level?
     * 
     * @return True if this Logger is enabled for the WARNING level, false
     *         otherwise.
     */
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    /**
     * Is this logger instance enabled for level SEVERE?
     * 
     * @return True if this Logger is enabled for level SEVERE, false otherwise.
     */
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    // /**
    // * Log the message at the specified level with the specified throwable if any.
    // * This method creates a LogRecord and fills in caller date before calling
    // * this instance's JDK14 logger.
    // *
    // * See bug report #13 for more details.
    // *
    // * @param level
    // * @param msg
    // * @param t
    // */
    // private void log(String callerFQCN, Level level, String msg, Throwable t) {
    // // millis and thread are filled by the constructor
    // LogRecord record = new LogRecord(level, msg);
    // record.setLoggerName(getName());
    // record.setThrown(t);
    // // Note: parameters in record are not set because SLF4J only
    // // supports a single formatting style
    // fillCallerData(callerFQCN, record);
    // logger.log(record);
    // }

    /**
     * Log the message at the specified level with the specified throwable if any.
     * This method creates a LogRecord and fills in caller date before calling this
     * instance's JDK14 logger.
     */
    @Override
    protected void handleNormalizedLoggingCall(org.slf4j.event.Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
        // AbstractLogger is the entry point of all classic API calls
        innerNormalizedLoggingCallHandler(SUPER_OF_SUPER, level, marker, msg, args, throwable);
    }

    private void innerNormalizedLoggingCallHandler(String fqcn, org.slf4j.event.Level level, Marker marker, String msg, Object[] args, Throwable throwable) {
        // millis and thread are filled by the constructor
        Level julLevel = slf4jLevelToJULLevel(level);
        String formattedMessage = MessageFormatter.basicArrayFormat(msg, args);
        LogRecord record = new LogRecord(julLevel, formattedMessage);

        // https://jira.qos.ch/browse/SLF4J-13
        record.setLoggerName(getName());
        record.setThrown(throwable);
        // Note: parameters in record are not set because SLF4J only
        // supports a single formatting style
        // See also https://jira.qos.ch/browse/SLF4J-10
        fillCallerData(fqcn, record);
        logger.log(record);
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return SELF;
    }

    @Override
    public void log(Marker marker, String callerFQCN, int slf4jLevelInt, String message, Object[] arguments, Throwable throwable) {

        org.slf4j.event.Level slf4jLevel = org.slf4j.event.Level.intToLevel(slf4jLevelInt);
        Level julLevel = slf4jLevelIntToJULLevel(slf4jLevelInt);

        if (logger.isLoggable(julLevel)) {
            NormalizedParameters np = NormalizedParameters.normalize(message, arguments, throwable);
            innerNormalizedLoggingCallHandler(callerFQCN, slf4jLevel, marker, np.getMessage(), np.getArguments(), np.getThrowable());
        }
    }

    /**
     * Fill in caller data if possible.
     * 
     * @param record The record to update
     */
    private void fillCallerData(String callerFQCN, LogRecord record) {
        StackTraceElement[] steArray = new Throwable().getStackTrace();
        // Find the first stack trace element matching the caller boundary
        int selfIndex = NOT_FOUND;
        for (int i = 0; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (className.equals(callerFQCN)) {
                selfIndex = i;
                break;
            }
        }
        // Find the first stack trace element after the caller boundary
        int found = NOT_FOUND;
        for (int i = selfIndex + 1; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (!(className.equals(callerFQCN))) {
                found = i;
                break;
            }
        }

        if (found != NOT_FOUND) {
            StackTraceElement ste = steArray[found];
            // setting the class name has the side effect of setting
            // the needToInferCaller variable to false.
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
    }

    static String SELF = JDK14LoggerAdapter.class.getName();

    static String SUPER_OF_SUPER = AbstractLogger.class.getName();

    private static Level slf4jLevelIntToJULLevel(int levelInt) {
        org.slf4j.event.Level slf4jLevel = org.slf4j.event.Level.intToLevel(levelInt);
        return slf4jLevelToJULLevel(slf4jLevel);
    }

    private static Level slf4jLevelToJULLevel(org.slf4j.event.Level slf4jLevel) {
        Level julLevel;
        switch (slf4jLevel) {
        case TRACE:
            julLevel = Level.FINEST;
            break;
        case DEBUG:
            julLevel = Level.FINE;
            break;
        case INFO:
            julLevel = Level.INFO;
            break;
        case WARN:
            julLevel = Level.WARNING;
            break;
        case ERROR:
            julLevel = Level.SEVERE;
            break;
        default:
            throw new IllegalStateException("Level " + slf4jLevel + " is not recognized.");
        }
        return julLevel;
    }

    /**
     * @since 1.7.15
     */
    @Override
    public void log(LoggingEvent event) {
        // assumes that the invocation is made from a substitute logger
        // this assumption might change in the future with the advent of a fluent API
        Level julLevel = slf4jLevelToJULLevel(event.getLevel());
        if (logger.isLoggable(julLevel)) {
            LogRecord record = eventToRecord(event, julLevel);
            String callerBoundary = event.getCallerBoundary();
            fillCallerData(callerBoundary != null ? callerBoundary : SELF, record);
            logger.log(record);
        }
    }

    private LogRecord eventToRecord(LoggingEvent event, Level julLevel) {
        String format = event.getMessage();
        Object[] arguments = event.getArgumentArray();
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        if (ft.getThrowable() != null && event.getThrowable() != null) {
            throw new IllegalArgumentException(
                    "both last element in argument array and last argument are of type Throwable");
        }

        Throwable t = event.getThrowable();
        if (t == null && ft.getThrowable() != null) {
            t = ft.getThrowable();
        }

        LogRecord record = new LogRecord(
                julLevel,
                prependMarkersAndKeyValuePairs(event.getMarkers(), event.getKeyValuePairs(), ft.getMessage()));
        record.setLoggerName(event.getLoggerName());
        record.setMillis(event.getTimeStamp());
        record.setSourceClassName(EventConstants.NA_SUBST);
        record.setSourceMethodName(EventConstants.NA_SUBST);

        record.setThrown(t);
        return record;
    }

    private String prependMarkersAndKeyValuePairs(
            List<Marker> markers, List<KeyValuePair> KeyValuePairs, String message) {
        boolean hasMarkers = isNotEmpty(markers);
        boolean hasKeyValuePairs = isNotEmpty(KeyValuePairs);
        if (!hasMarkers && !hasKeyValuePairs) {
            return message;
        }
        StringBuilder sb = new StringBuilder(message.length());
        if (hasMarkers) {
            for (Marker marker : markers) {
                sb.append(marker).append(' ');
            }
        }
        if (hasKeyValuePairs) {
            for (KeyValuePair keyValuePair : KeyValuePairs) {
                sb.append(keyValuePair.key)
                        .append('=')
                        .append(keyValuePair.value)
                        .append(' ');
            }
        }
        return sb.append(message).toString();
    }

    private boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }
}
