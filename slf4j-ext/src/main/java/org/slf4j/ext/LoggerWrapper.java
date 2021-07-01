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
package org.slf4j.ext;

import org.slf4j.Logger;
import org.slf4j.Marker;
//import org.slf4j.helpers.FormattingTuple;
//import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A helper class wrapping an {@link org.slf4j.Logger} instance preserving
 * location information if the wrapped instance supports it.
 * 
 * @author Ralph Goers
 * @author Ceki G&uuml;lc&uuml;
 */
public class LoggerWrapper implements Logger {

    // To ensure consistency between two instances sharing the same name
    // (homonyms) a LoggerWrapper should not contain any state beyond
    // the Logger instance it wraps.
    // Note that 'instanceofLAL' directly depends on Logger.
    // fqcn depend on the caller, but its value would not be different
    // between successive invocations of a factory class

    protected final Logger logger;
    final String fqcn;
    // is this logger instance a LocationAwareLogger
    protected final boolean instanceofLAL;

    public LoggerWrapper(Logger logger, String fqcn) {
        this.logger = logger;
        this.fqcn = fqcn;
        if (logger instanceof LocationAwareLogger) {
            instanceofLAL = true;
        } else {
            instanceofLAL = false;
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled(marker);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(String msg) {
        if (!logger.isTraceEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, null, null);
        } else {
            logger.trace(msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(String format, Object arg) {
        if (!logger.isTraceEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.TRACE_INT, format, new Object[] { arg }, null);
        } else {
            logger.trace(format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(String format, Object arg1, Object arg2) {
        if (!logger.isTraceEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.TRACE_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.trace(format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(String format, Object... args) {
        if (!logger.isTraceEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.TRACE_INT, format, args, null);
        } else {
            logger.trace(format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(String msg, Throwable t) {
        if (!logger.isTraceEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.TRACE_INT, msg, null, t);
        } else {
            logger.trace(msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(Marker marker, String msg) {
        if (!logger.isTraceEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.TRACE_INT, msg, null, null);
        } else {
            logger.trace(marker, msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(Marker marker, String format, Object arg) {
        if (!logger.isTraceEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.TRACE_INT, format, new Object[] { arg }, null);
        } else {
            logger.trace(marker, format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (!logger.isTraceEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.TRACE_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.trace(marker, format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(Marker marker, String format, Object... args) {
        if (!logger.isTraceEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.TRACE_INT, format, args, null);
        } else {
            logger.trace(marker, format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void trace(Marker marker, String msg, Throwable t) {
        if (!logger.isTraceEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.TRACE_INT, msg, null, t);
        } else {
            logger.trace(marker, msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(String msg) {
        if (!logger.isDebugEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, null);
        } else {
            logger.debug(msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(String format, Object arg) {
        if (!logger.isDebugEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.DEBUG_INT, format, new Object[] { arg }, null);
        } else {
            logger.debug(format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(String format, Object arg1, Object arg2) {
        if (!logger.isDebugEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.DEBUG_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.debug(format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(String format, Object... argArray) {
        if (!logger.isDebugEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.DEBUG_INT, format, argArray, null);
        } else {
            logger.debug(format, argArray);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(String msg, Throwable t) {
        if (!logger.isDebugEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, t);
        } else {
            logger.debug(msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(Marker marker, String msg) {
        if (!logger.isDebugEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, null);
        } else {
            logger.debug(marker, msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(Marker marker, String format, Object arg) {
        if (!logger.isDebugEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.DEBUG_INT, format, new Object[] { arg }, null);
        } else {
            logger.debug(marker, format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (!logger.isDebugEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.DEBUG_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.debug(marker, format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(Marker marker, String format, Object... argArray) {
        if (!logger.isDebugEnabled(marker))
            return;
        if (instanceofLAL) {

            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.DEBUG_INT, format, argArray, null);
        } else {
            logger.debug(marker, format, argArray);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void debug(Marker marker, String msg, Throwable t) {
        if (!logger.isDebugEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.DEBUG_INT, msg, null, t);
        } else {
            logger.debug(marker, msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(String msg) {
        if (!logger.isInfoEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.INFO_INT, msg, null, null);
        } else {
            logger.info(msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(String format, Object arg) {
        if (!logger.isInfoEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.INFO_INT, format, new Object[] { arg }, null);
        } else {
            logger.info(format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(String format, Object arg1, Object arg2) {
        if (!logger.isInfoEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.INFO_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.info(format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(String format, Object... args) {
        if (!logger.isInfoEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.INFO_INT, format, args, null);
        } else {
            logger.info(format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(String msg, Throwable t) {
        if (!logger.isInfoEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.INFO_INT, msg, null, t);
        } else {
            logger.info(msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(Marker marker, String msg) {
        if (!logger.isInfoEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.INFO_INT, msg, null, null);
        } else {
            logger.info(marker, msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(Marker marker, String format, Object arg) {
        if (!logger.isInfoEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.INFO_INT, format, new Object[] { arg }, null);
        } else {
            logger.info(marker, format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (!logger.isInfoEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.INFO_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.info(marker, format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(Marker marker, String format, Object... args) {
        if (!logger.isInfoEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.INFO_INT, format, args, null);
        } else {
            logger.info(marker, format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void info(Marker marker, String msg, Throwable t) {
        if (!logger.isInfoEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.INFO_INT, msg, null, t);
        } else {
            logger.info(marker, msg, t);
        }
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(String msg) {
        if (!logger.isWarnEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.WARN_INT, msg, null, null);
        } else {
            logger.warn(msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(String format, Object arg) {
        if (!logger.isWarnEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.WARN_INT, format, new Object[] { arg }, null);
        } else {
            logger.warn(format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(String format, Object arg1, Object arg2) {
        if (!logger.isWarnEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.WARN_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.warn(format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(String format, Object... args) {
        if (!logger.isWarnEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.WARN_INT, format, args, null);
        } else {
            logger.warn(format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(String msg, Throwable t) {
        if (!logger.isWarnEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.WARN_INT, msg, null, t);
        } else {
            logger.warn(msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(Marker marker, String msg) {
        if (!logger.isWarnEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.WARN_INT, msg, null, null);
        } else {
            logger.warn(marker, msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(Marker marker, String format, Object arg) {
        if (!logger.isWarnEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.WARN_INT, format, new Object[] { arg }, null);
        } else {
            logger.warn(marker, format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (!logger.isWarnEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.WARN_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.warn(marker, format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(Marker marker, String format, Object... args) {
        if (!logger.isWarnEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.WARN_INT, format, args, null);
        } else {
            logger.warn(marker, format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void warn(Marker marker, String msg, Throwable t) {
        if (!logger.isWarnEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.WARN_INT, msg, null, t);
        } else {
            logger.warn(marker, msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(String msg) {
        if (!logger.isErrorEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, null, null);
        } else {
            logger.error(msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(String format, Object arg) {
        if (!logger.isErrorEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, format, new Object[] { arg }, null);
        } else {
            logger.error(format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(String format, Object arg1, Object arg2) {
        if (!logger.isErrorEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.error(format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(String format, Object... args) {
        if (!logger.isErrorEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, format, args, null);
        } else {
            logger.error(format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(String msg, Throwable t) {
        if (!logger.isErrorEnabled())
            return;

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, msg, null, t);
        } else {
            logger.error(msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(Marker marker, String msg) {
        if (!logger.isErrorEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.ERROR_INT, msg, null, null);
        } else {
            logger.error(marker, msg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(Marker marker, String format, Object arg) {
        if (!logger.isErrorEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.ERROR_INT, format, new Object[] { arg }, null);
        } else {
            logger.error(marker, format, arg);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (!logger.isErrorEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.ERROR_INT, format, new Object[] { arg1, arg2 }, null);
        } else {
            logger.error(marker, format, arg1, arg2);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(Marker marker, String format, Object... args) {
        if (!logger.isErrorEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.ERROR_INT, format, args, null);
        } else {
            logger.error(marker, format, args);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void error(Marker marker, String msg, Throwable t) {
        if (!logger.isErrorEnabled(marker))
            return;
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(marker, fqcn, LocationAwareLogger.ERROR_INT, msg, null, t);
        } else {
            logger.error(marker, msg, t);
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public String getName() {
        return logger.getName();
    }
}
