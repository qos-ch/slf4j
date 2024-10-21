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

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventConstants;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.AbstractLogger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NormalizedParameters;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A wrapper over {@link java.util.logging.Logger java.util.logging.Logger} in
 * conformity with the {@link Logger} interface. Note that the logging levels
 * mentioned in this class refer to those defined in the java.util.logging
 * package.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Peter Royal
 */
public final class JDK14LoggerAdapter extends LegacyAbstractLogger implements LocationAwareLogger {

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
        innerNormalizedLoggingCallHandler(getFullyQualifiedCallerName(), level, marker, msg, args, throwable);
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
    final private void fillCallerData(String callerFQCN, LogRecord record) {
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        int furthestIndex = findFurthestIndex(callerFQCN, steArray);

        if (furthestIndex != NOT_FOUND) {
            int found = furthestIndex+1;
            StackTraceElement ste = steArray[found];
            // setting the class name has the side effect of setting
            // the needToInferCaller variable to false.
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
    }

    // find the furthest index which matches any of the barrier classes
    // We assume that the actual caller is at most MAX_SEARCH_DEPTH calls away
    private int findFurthestIndex(String callerFQCN, StackTraceElement[] steArray) {

        final int maxIndex = Math.min(MAX_SEARCH_DEPTH, steArray.length);
        int furthestIndex = NOT_FOUND;

        for (int i = 0; i < maxIndex; i++) {
            final String className = steArray[i].getClassName();
            if (barrierMatch(callerFQCN, className)) {
                furthestIndex = i;
            }
        }
        return furthestIndex;
    }

   static final int MAX_SEARCH_DEPTH = 12;
   static String SELF = JDK14LoggerAdapter.class.getName();

    static String SUPER = LegacyAbstractLogger.class.getName();
    static String SUPER_OF_SUPER = AbstractLogger.class.getName();
    static String SUBSTITUE = SubstituteLogger.class.getName();
    static String FLUENT = DefaultLoggingEventBuilder.class.getName();

    static String[] BARRIER_CLASSES = new String[] { SUPER_OF_SUPER, SUPER, SELF, SUBSTITUE, FLUENT };

    private boolean barrierMatch(String callerFQCN, String candidateClassName) {
        if (candidateClassName.equals(callerFQCN))
            return true;
        for (String barrierClassName : BARRIER_CLASSES) {
            if (barrierClassName.equals(candidateClassName)) {
                return true;
            }
        }
        return false;
    }

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
    public void log(LoggingEvent event) {
        // assumes that the invocation is made from a substitute logger
        // this assumption might change in the future with the advent of a fluent API
        Level julLevel = slf4jLevelToJULLevel(event.getLevel());
        if (logger.isLoggable(julLevel)) {
            LogRecord record = eventToRecord(event, julLevel);
            logger.log(record);
        }
    }

    private LogRecord eventToRecord(LoggingEvent event, Level julLevel) {
        String format = event.getMessage();
        Object[] arguments = event.getArgumentArray();
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        if (ft.getThrowable() != null && event.getThrowable() != null) {
            throw new IllegalArgumentException("both last element in argument array and last argument are of type Throwable");
        }

        Throwable t = event.getThrowable();
        if (ft.getThrowable() != null) {
            t = ft.getThrowable();
            throw new IllegalStateException("fix above code");
        }

        LogRecord record = new LogRecord(julLevel, ft.getMessage());
        record.setLoggerName(event.getLoggerName());
        record.setMillis(event.getTimeStamp());
        record.setSourceClassName(EventConstants.NA_SUBST);
        record.setSourceMethodName(EventConstants.NA_SUBST);

        record.setThrown(t);
        return record;
    }

}
