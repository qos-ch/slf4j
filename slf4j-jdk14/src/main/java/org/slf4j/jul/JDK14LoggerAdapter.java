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

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.EventConstants;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.Util;
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
public final class JDK14LoggerAdapter extends MarkerIgnoringBase implements LocationAwareLogger {

    private static final long serialVersionUID = -8053026990503422791L;

    transient final java.util.logging.Logger logger;

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
     * Log a message object at level FINEST.
     * 
     * @param msg
     *          - the message object to be logged
     */
    public void trace(String msg) {
        if (logger.isLoggable(Level.FINEST)) {
            log(SELF, Level.FINEST, msg, null);
        }
    }

    /**
     * Log a message object provided by supplier at level FINEST.
     *
     * @param msgSup
     *          - the supplier for message object to be logged
     */
    public void trace(Supplier<String> msgSup) {
        if (logger.isLoggable(Level.FINEST)) {
            log(SELF, Level.FINEST, Util.msgSafeGet(msgSup), null);
        }

    }

    /**
     * Log a message at level FINEST according to the specified format and
     * argument.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for level FINEST.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    public void trace(String format, Object arg) {
        if (logger.isLoggable(Level.FINEST)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level FINEST according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the FINEST level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg1
     *          the first argument
     * @param arg2
     *          the second argument
     */
    public void trace(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.FINEST)) {
            FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
            log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level FINEST according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the FINEST level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    public void trace(String format, Object... argArray) {
        if (logger.isLoggable(Level.FINEST)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at level FINEST with an accompanying message.
     * 
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void trace(String msg, Throwable t) {
        if (logger.isLoggable(Level.FINEST)) {
            log(SELF, Level.FINEST, msg, t);
        }
    }

    /**
     * Log an exception (throwable) at level FINEST with an accompanying message
     * provided by supplier
     *
     * @param msgSup
     *          the supplier for message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void trace(Throwable t, Supplier<String> msgSup) {
        if (logger.isLoggable(Level.FINEST)) {
            log(SELF, Level.FINEST, Util.msgSafeGet(msgSup), t);
        }
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
     * Log a message object at level FINE.
     * 
     * @param msg
     *          - the message object to be logged
     */
    public void debug(String msg) {
        if (logger.isLoggable(Level.FINE)) {
            log(SELF, Level.FINE, msg, null);
        }
    }

    /**
     * Log a message object provided by supplier at level FINE.
     *
     * @param msgSup
     *          - the supplier for message object to be logged
     */
    public void debug(Supplier<String> msgSup) {
        if (logger.isLoggable(Level.FINE)) {
            log(SELF, Level.FINE, Util.msgSafeGet(msgSup), null);
        }
    }

    /**
     * Log a message at level FINE according to the specified format and argument.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for level FINE.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    public void debug(String format, Object arg) {
        if (logger.isLoggable(Level.FINE)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level FINE according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the FINE level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg1
     *          the first argument
     * @param arg2
     *          the second argument
     */
    public void debug(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.FINE)) {
            FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
            log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level FINE according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the FINE level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    public void debug(String format, Object... argArray) {
        if (logger.isLoggable(Level.FINE)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at level FINE with an accompanying message.
     * 
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void debug(String msg, Throwable t) {
        if (logger.isLoggable(Level.FINE)) {
            log(SELF, Level.FINE, msg, t);
        }
    }

    /**
     * Log an exception (throwable) at level FINEST with an accompanying message
     * provided by supplier
     *
     * @param msgSup
     *          the supplier for message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void debug(Throwable t, Supplier<String> msgSup) {
        if (logger.isLoggable(Level.FINE)) {
            log(SELF, Level.FINE, Util.msgSafeGet(msgSup), t);
        }
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
     * Log a message object at the INFO level.
     * 
     * @param msg
     *          - the message object to be logged
     */
    public void info(String msg) {
        if (logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, msg, null);
        }
    }

    /**
     * Log a message object provided by supplier at level INFO.
     *
     * @param msgSup
     *          - the supplier for message object to be logged
     */
    public void info(Supplier<String> msgSup) {
        if (logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, Util.msgSafeGet(msgSup), null);
        }
    }

    /**
     * Log a message at level INFO according to the specified format and argument.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the INFO level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    public void info(String format, Object arg) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at the INFO level according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the INFO level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg1
     *          the first argument
     * @param arg2
     *          the second argument
     */
    public void info(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level INFO according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the INFO level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    public void info(String format, Object... argArray) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at the INFO level with an accompanying
     * message.
     * 
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void info(String msg, Throwable t) {
        if (logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, msg, t);
        }
    }

    /**
     * Log an exception (throwable) at level INFO with an accompanying message
     * provided by supplier
     *
     * @param msgSup
     *          the supplier for message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void info(Throwable t, Supplier<String> msgSup) {
        if (logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, Util.msgSafeGet(msgSup), t);
        }
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
     * Log a message object at the WARNING level.
     * 
     * @param msg
     *          - the message object to be logged
     */
    public void warn(String msg) {
        if (logger.isLoggable(Level.WARNING)) {
            log(SELF, Level.WARNING, msg, null);
        }
    }

    /**
     * Log a message object provided by supplier at level WARNING.
     *
     * @param msgSup
     *          - the supplier for message object to be logged
     */
    public void warn(Supplier<String> msgSup) {
        if (logger.isLoggable(Level.WARNING)) {
            log(SELF, Level.WARNING, Util.msgSafeGet(msgSup), null);
        }
    }

    /**
     * Log a message at the WARNING level according to the specified format and
     * argument.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the WARNING level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    public void warn(String format, Object arg) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at the WARNING level according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the WARNING level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg1
     *          the first argument
     * @param arg2
     *          the second argument
     */
    public void warn(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
            log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level WARNING according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the WARNING level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param argArray
     *          an array of arguments
     */
    public void warn(String format, Object... argArray) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at the WARNING level with an accompanying
     * message.
     * 
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void warn(String msg, Throwable t) {
        if (logger.isLoggable(Level.WARNING)) {
            log(SELF, Level.WARNING, msg, t);
        }
    }

    /**
     * Log an exception (throwable) at level WARNING with an accompanying message
     * provided by supplier
     *
     * @param msgSup
     *          the supplier for message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void warn(Throwable t, Supplier<String> msgSup) {
        if (logger.isLoggable(Level.WARNING)) {
            log(SELF, Level.WARNING, Util.msgSafeGet(msgSup), t);
        }
    }

    /**
     * Is this logger instance enabled for level SEVERE?
     * 
     * @return True if this Logger is enabled for level SEVERE, false otherwise.
     */
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    /**
     * Log a message object at the SEVERE level.
     * 
     * @param msg
     *          - the message object to be logged
     */
    public void error(String msg) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(SELF, Level.SEVERE, msg, null);
        }
    }

    /**
     * Log a message object provided by supplier at level SEVERE.
     *
     * @param msgSup
     *          - the supplier for message object to be logged
     */
    public void error(Supplier<String> msgSup) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(SELF, Level.SEVERE, Util.msgSafeGet(msgSup), null);
        }
    }

    /**
     * Log a message at the SEVERE level according to the specified format and
     * argument.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the SEVERE level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg
     *          the argument
     */
    public void error(String format, Object arg) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at the SEVERE level according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the SEVERE level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arg1
     *          the first argument
     * @param arg2
     *          the second argument
     */
    public void error(String format, Object arg1, Object arg2) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
            log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log a message at level SEVERE according to the specified format and
     * arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled
     * for the SEVERE level.
     * </p>
     * 
     * @param format
     *          the format string
     * @param arguments
     *          an array of arguments
     */
    public void error(String format, Object... arguments) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
        }
    }

    /**
     * Log an exception (throwable) at the SEVERE level with an accompanying
     * message.
     * 
     * @param msg
     *          the message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void error(String msg, Throwable t) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(SELF, Level.SEVERE, msg, t);
        }
    }

    /**
     * Log an exception (throwable) at level SEVERE with an accompanying message
     * provided by supplier
     *
     * @param msgSup
     *          the supplier for message accompanying the exception
     * @param t
     *          the exception (throwable) to log
     */
    public void error(Throwable t, Supplier<String> msgSup) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(SELF, Level.SEVERE, Util.msgSafeGet(msgSup), t);
        }

    }

    /**
     * Log the message at the specified level with the specified throwable if any.
     * This method creates a LogRecord and fills in caller date before calling
     * this instance's JDK14 logger.
     * 
     * See bug report #13 for more details.
     * 
     * @param level
     * @param msg
     * @param t
     */
    private void log(String callerFQCN, Level level, String msg, Throwable t) {
        // millis and thread are filled by the constructor
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(getName());
        record.setThrown(t);
        // Note: parameters in record are not set because SLF4J only
        // supports a single formatting style
        fillCallerData(callerFQCN, record);
        logger.log(record);
    }

    static String SELF = JDK14LoggerAdapter.class.getName();
    static String SUPER = MarkerIgnoringBase.class.getName();

    /**
     * Fill in caller data if possible.
     * 
     * @param record
     *          The record to update
     */
    final private void fillCallerData(String callerFQCN, LogRecord record) {
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        int selfIndex = -1;
        for (int i = 0; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (className.equals(callerFQCN) || className.equals(SUPER)) {
                selfIndex = i;
                break;
            }
        }

        int found = -1;
        for (int i = selfIndex + 1; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (!(className.equals(callerFQCN) || className.equals(SUPER))) {
                found = i;
                break;
            }
        }

        if (found != -1) {
            StackTraceElement ste = steArray[found];
            // setting the class name has the side effect of setting
            // the needToInferCaller variable to false.
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
    }

    public void log(Marker marker, String callerFQCN, int level, String message, Object[] argArray, Throwable t) {
        Level julLevel = slf4jLevelIntToJULLevel(level);
        // the logger.isLoggable check avoids the unconditional
        // construction of location data for disabled log
        // statements. As of 2008-07-31, callers of this method
        // do not perform this check. See also
        // http://jira.qos.ch/browse/SLF4J-81
        if (logger.isLoggable(julLevel)) {
            log(callerFQCN, julLevel, message, t);
        }
    }

    private Level slf4jLevelIntToJULLevel(int slf4jLevelInt) {
        Level julLevel;
        switch (slf4jLevelInt) {
        case LocationAwareLogger.TRACE_INT:
            julLevel = Level.FINEST;
            break;
        case LocationAwareLogger.DEBUG_INT:
            julLevel = Level.FINE;
            break;
        case LocationAwareLogger.INFO_INT:
            julLevel = Level.INFO;
            break;
        case LocationAwareLogger.WARN_INT:
            julLevel = Level.WARNING;
            break;
        case LocationAwareLogger.ERROR_INT:
            julLevel = Level.SEVERE;
            break;
        default:
            throw new IllegalStateException("Level number " + slf4jLevelInt + " is not recognized.");
        }
        return julLevel;
    }

    /**
     * @since 1.7.15
     */
    public void log(LoggingEvent event) {
        Level julLevel = slf4jLevelIntToJULLevel(event.getLevel().toInt());
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
