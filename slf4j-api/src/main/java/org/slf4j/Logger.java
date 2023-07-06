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

package org.slf4j;

import static org.slf4j.event.EventConstants.DEBUG_INT;
import static org.slf4j.event.EventConstants.ERROR_INT;
import static org.slf4j.event.EventConstants.INFO_INT;
import static org.slf4j.event.EventConstants.TRACE_INT;
import static org.slf4j.event.EventConstants.WARN_INT;
import static org.slf4j.event.Level.DEBUG;
import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;
import static org.slf4j.event.Level.TRACE;
import static org.slf4j.event.Level.WARN;

import org.slf4j.event.Level;
import org.slf4j.helpers.CheckReturnValue;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

/**
 * The org.slf4j.Logger interface is the main user entry point of SLF4J API.
 * It is expected that logging takes place through concrete implementations
 * of this interface.
 * 
 * <H3>Typical usage pattern:</H3>
 * <pre>
 * import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory;
 *
 * public class Wombat {
 *
 *   <span style="color:green">final static Logger logger = LoggerFactory.getLogger(Wombat.class);</span>
 *   Integer t;
 *   Integer oldT;
 *
 *   public void setTemperature(Integer temperature) {
 *     oldT = t;
 *     t = temperature;
 *     <span style="color:green">logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);</span>
 *     if (temperature.intValue() &gt; 50) {
 *       <span style="color:green">logger.info("Temperature has risen above 50 degrees.");</span>
 *     }
 *   }
 * }
 * </pre>
 *
 * <p>Note that version 2.0 of the SLF4J API introduces a <a href="../../../manual.html#fluent">fluent api</a>,
 * the most significant API change to occur in the last 20 years.
 *
 * <p>Be sure to read the FAQ entry relating to <a href="../../../faq.html#logging_performance">parameterized
 * logging</a>. Note that logging statements can be parameterized in
 * <a href="../../../faq.html#paramException">presence of an exception/throwable</a>.
 *
 * <p>Once you are comfortable using loggers, i.e. instances of this interface, consider using
 * <a href="MDC.html">MDC</a> as well as <a href="Marker.html">Markers</a>.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
public interface Logger {

    /**
     * Case-insensitive String constant used to retrieve the name of the root logger.
     *
     * @since 1.3
     */
    final public String ROOT_LOGGER_NAME = "ROOT";

    /**
     * Return the name of this <code>Logger</code> instance.
     * @return name of this logger instance 
     */
    public String getName();

    /**
     * <p>Make a new {@link LoggingEventBuilder} instance as appropriate for this logger implementation.
     * This default implementation always returns a new instance of {@link DefaultLoggingEventBuilder}.</p>
     * <p></p>
     * <p>This method is intended to be used by logging systems implementing the SLF4J API and <b>not</b>
     * by end users.</p>
     * <p></p>
     * <p>Also note that a {@link LoggingEventBuilder} instance should be built for all levels,
     * independently of the level argument. In other words, this method is an <b>unconditional</b>
     * constructor for the {@link LoggingEventBuilder} appropriate for this logger implementation.</p>
     * <p></p>
     * @param level desired level for the event builder
     * @return a new {@link LoggingEventBuilder} instance as appropriate for <b>this</b> logger
     * @since 2.0
     */
    default public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        return new DefaultLoggingEventBuilder(this, level);
    }

    /**
     * Make a new {@link LoggingEventBuilder} instance as appropriate for this logger and the
     * desired {@link Level} passed as parameter. If this Logger is disabled for the given Level, then
     * a {@link  NOPLoggingEventBuilder} is returned.
     *
     *
     * @param level desired level for the event builder
     * @return a new {@link LoggingEventBuilder} instance as appropriate for this logger
     * @since 2.0
     */
    @CheckReturnValue
    default public LoggingEventBuilder atLevel(Level level) {
        if (isEnabledForLevel(level)) {
            return makeLoggingEventBuilder(level);
        } else {
            return NOPLoggingEventBuilder.singleton();
        }
    }

    
    
    /**
     * Returns whether this Logger is enabled for a given {@link Level}. 
     * 
     * @param level
     * @return true if enabled, false otherwise.
     */
    default public boolean isEnabledForLevel(Level level) {
        int levelInt = level.toInt();
        switch (levelInt) {
        case (TRACE_INT):
            return isTraceEnabled();
        case (DEBUG_INT):
            return isDebugEnabled();
        case (INFO_INT):
            return isInfoEnabled();
        case (WARN_INT):
            return isWarnEnabled();
        case (ERROR_INT):
            return isErrorEnabled();
        default:
            throw new IllegalArgumentException("Level [" + level + "] not recognized.");
        }
    }

    /**
     * Is the logger instance enabled for the TRACE level?
     *
     * @return True if this Logger is enabled for the TRACE level,
     *         false otherwise.
     * @since 1.4
     */
    public boolean isTraceEnabled();

    /**
     * Log a message at the TRACE level.
     *
     * @param msg the message string to be logged
     * @since 1.4
     */
    public void trace(String msg);

    /**
     * Log a message at the TRACE level according to the specified format
     * and argument.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level. 
     *
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    public void trace(String format, Object arg);

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level. 
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    public void trace(String format, Object arg1, Object arg2);

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the TRACE level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for TRACE. The variants taking {@link #trace(String, Object) one} and
     * {@link #trace(String, Object, Object) two} arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @since 1.4
     */
    public void trace(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the TRACE level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     * @since 1.4
     */
    public void trace(String msg, Throwable t);

    /**
     * Similar to {@link #isTraceEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the TRACE level,
     *         false otherwise.
     *         
     * @since 1.4
     */
    public boolean isTraceEnabled(Marker marker);

    /**
     * Entry point for fluent-logging for {@link org.slf4j.event.Level#TRACE} level. 
     *  
     * @return LoggingEventBuilder instance as appropriate for level TRACE
     * @since 2.0
     */
    @CheckReturnValue
    default public LoggingEventBuilder atTrace() {
        if (isTraceEnabled()) {
            return makeLoggingEventBuilder(TRACE);
        } else {
            return NOPLoggingEventBuilder.singleton();
        }
    }

    /**
     * Log a message with the specific Marker at the TRACE level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     * @since 1.4
     */
    public void trace(Marker marker, String msg);

    /**
     * This method is similar to {@link #trace(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    public void trace(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #trace(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    public void trace(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #trace(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker   the marker data specific to this log statement
     * @param format   the format string
     * @param argArray an array of arguments
     * @since 1.4
     */
    public void trace(Marker marker, String format, Object... argArray);

    /**
     * This method is similar to {@link #trace(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     * @since 1.4
     */
    public void trace(Marker marker, String msg, Throwable t);

    /**
     * Is the logger instance enabled for the DEBUG level?
     *
     * @return True if this Logger is enabled for the DEBUG level,
     *         false otherwise.
     */
    public boolean isDebugEnabled();

    /**
     * Log a message at the DEBUG level.
     *
     * @param msg the message string to be logged
     */
    public void debug(String msg);

    /**
     * Log a message at the DEBUG level according to the specified format
     * and argument.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level. 
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void debug(String format, Object arg);

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level. 
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void debug(String format, Object arg1, Object arg2);

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the DEBUG level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for DEBUG. The variants taking
     * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void debug(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the DEBUG level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void debug(String msg, Throwable t);

    /**
     * Similar to {@link #isDebugEnabled()} method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the DEBUG level,
     *         false otherwise. 
     */
    public boolean isDebugEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the DEBUG level.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message string to be logged
     */
    public void debug(Marker marker, String msg);

    /**
     * This method is similar to {@link #debug(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    public void debug(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #debug(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void debug(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #debug(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void debug(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #debug(String, Throwable)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    public void debug(Marker marker, String msg, Throwable t);

    /**
     * Entry point for fluent-logging for {@link org.slf4j.event.Level#DEBUG} level. 
     *  
     * @return LoggingEventBuilder instance as appropriate for level DEBUG
     * @since 2.0
     */
    @CheckReturnValue
    default public LoggingEventBuilder atDebug() {
        if (isDebugEnabled()) {
            return makeLoggingEventBuilder(DEBUG);
        } else {
            return NOPLoggingEventBuilder.singleton();
        }
    }

    /**
     * Is the logger instance enabled for the INFO level?
     *
     * @return True if this Logger is enabled for the INFO level,
     *         false otherwise.
     */
    public boolean isInfoEnabled();

    /**
     * Log a message at the INFO level.
     *
     * @param msg the message string to be logged
     */
    public void info(String msg);

    /**
     * Log a message at the INFO level according to the specified format
     * and argument.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level. 
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void info(String format, Object arg);

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level. 
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void info(String format, Object arg1, Object arg2);

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void info(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void info(String msg, Throwable t);

    /**
     * Similar to {@link #isInfoEnabled()} method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return true if this Logger is enabled for the INFO level,
     *         false otherwise.
     */
    public boolean isInfoEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the INFO level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    public void info(Marker marker, String msg);

    /**
     * This method is similar to {@link #info(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    public void info(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #info(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void info(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #info(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void info(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #info(String, Throwable)} method
     * except that the marker data is also taken into consideration.
     *
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    public void info(Marker marker, String msg, Throwable t);

    /**
     * Entry point for fluent-logging for {@link org.slf4j.event.Level#INFO} level. 
     *  
     * @return LoggingEventBuilder instance as appropriate for level INFO
     * @since 2.0
     */
    @CheckReturnValue
    default public LoggingEventBuilder atInfo() {
        if (isInfoEnabled()) {
            return makeLoggingEventBuilder(INFO);
        } else {
            return NOPLoggingEventBuilder.singleton();
        }
    }

    /**
     * Is the logger instance enabled for the WARN level?
     *
     * @return True if this Logger is enabled for the WARN level,
     *         false otherwise.
     */
    public boolean isWarnEnabled();

    /**
     * Log a message at the WARN level.
     *
     * @param msg the message string to be logged
     */
    public void warn(String msg);

    /**
     * Log a message at the WARN level according to the specified format
     * and argument.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the WARN level. 
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void warn(String format, Object arg);

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the WARN level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for WARN. The variants taking
     * {@link #warn(String, Object) one} and {@link #warn(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void warn(String format, Object... arguments);

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the WARN level. 
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void warn(String format, Object arg1, Object arg2);

    /**
     * Log an exception (throwable) at the WARN level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void warn(String msg, Throwable t);

    /**
     * Similar to {@link #isWarnEnabled()} method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the WARN level,
     *         false otherwise.
     */
    public boolean isWarnEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the WARN level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    public void warn(Marker marker, String msg);

    /**
     * This method is similar to {@link #warn(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    public void warn(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #warn(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void warn(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #warn(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void warn(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #warn(String, Throwable)} method
     * except that the marker data is also taken into consideration.
     *
     * @param marker the marker data for this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    public void warn(Marker marker, String msg, Throwable t);

    /**
     * Entry point for fluent-logging for {@link org.slf4j.event.Level#WARN} level. 
     *  
     * @return LoggingEventBuilder instance as appropriate for level WARN
     * @since 2.0
     */
    @CheckReturnValue
    default public LoggingEventBuilder atWarn() {
        if (isWarnEnabled()) {
            return makeLoggingEventBuilder(WARN);
        } else {
            return NOPLoggingEventBuilder.singleton();
        }
    }

    /**
     * Is the logger instance enabled for the ERROR level?
     *
     * @return True if this Logger is enabled for the ERROR level,
     *         false otherwise.
     */
    public boolean isErrorEnabled();

    /**
     * Log a message at the ERROR level.
     *
     * @param msg the message string to be logged
     */
    public void error(String msg);

    /**
     * Log a message at the ERROR level according to the specified format
     * and argument.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level. 
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void error(String format, Object arg);

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level. 
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void error(String format, Object arg1, Object arg2);

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     * 
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the ERROR level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for ERROR. The variants taking
     * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void error(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void error(String msg, Throwable t);

    /**
     * Similar to {@link #isErrorEnabled()} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the ERROR level,
     *         false otherwise.
     */
    public boolean isErrorEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the ERROR level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    public void error(Marker marker, String msg);

    /**
     * This method is similar to {@link #error(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    public void error(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #error(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void error(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #error(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void error(Marker marker, String format, Object... arguments);

    /**
     * This method is similar to {@link #error(String, Throwable)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    public void error(Marker marker, String msg, Throwable t);

    /**
     * Entry point for fluent-logging for {@link org.slf4j.event.Level#ERROR} level. 
     *  
     * @return LoggingEventBuilder instance as appropriate for level ERROR
     * @since 2.0
     */
    @CheckReturnValue
    default public LoggingEventBuilder atError() {
        if (isErrorEnabled()) {
            return makeLoggingEventBuilder(ERROR);
        } else {
            return NOPLoggingEventBuilder.singleton();
        }
    }

}
