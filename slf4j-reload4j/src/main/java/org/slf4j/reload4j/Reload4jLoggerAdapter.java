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
package org.slf4j.reload4j;

import static org.slf4j.event.EventConstants.NA_SUBST;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.ThrowableInformation;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NormalizedParameters;
import org.slf4j.helpers.SubstituteLogger;
import org.slf4j.spi.LocationAwareLogger;
import org.slf4j.spi.LoggingEventAware;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * A wrapper over {@link org.apache.log4j.Logger org.apache.log4j.Logger} 
 * conforming to the {@link Logger} interface.
 * 
 * <p>
 * Note that the logging levels mentioned in this class refer to those defined
 * in the <a href=
 * "http://logging.apache.org/log4j/docs/api/org/apache/log4j/Level.html">
 * <code>org.apache.log4j.Level</code></a> class.
 * 
 * <p>This class is a copy-and-paste of Log4j12LoggerAdapter from the
 * slf4j-log4j12 module.</p>
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0-alpha6
 */
public final class Reload4jLoggerAdapter extends LegacyAbstractLogger implements LocationAwareLogger, LoggingEventAware, Serializable {

    private static final long serialVersionUID = 6989384227325275811L;

    final transient org.apache.log4j.Logger logger;

    final static String FQCN_NOMINAL = org.slf4j.helpers.AbstractLogger.class.getName();
    final static String FQCN_SUBSTITUE = FQCN_NOMINAL;
    final static String FQCN_FLUENT = org.slf4j.spi.DefaultLoggingEventBuilder.class.getName();

    // WARN: Reload4jLoggerAdapter constructor should have only package access so
    // that only Reload4jLoggerFactory be able to create one.
    Reload4jLoggerAdapter(org.apache.log4j.Logger logger) {
        this.logger = logger;
        this.name = logger.getName();
    }

    /**
     * Is this logger instance enabled for the TRACE level?
     * 
     * @return True if this Logger is enabled for level TRACE, false otherwise.
     */
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /**
     * Is this logger instance enabled for the DEBUG level?
     * 
     * @return True if this Logger is enabled for level DEBUG, false otherwise.
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Is this logger instance enabled for the INFO level?
     * 
     * @return True if this Logger is enabled for the INFO level, false otherwise.
     */
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    /**
     * Is this logger instance enabled for the WARN level?
     * 
     * @return True if this Logger is enabled for the WARN level, false otherwise.
     */
    public boolean isWarnEnabled() {
        return logger.isEnabledFor(Level.WARN);
    }

    /**
     * Is this logger instance enabled for level ERROR?
     * 
     * @return True if this Logger is enabled for level ERROR, false otherwise.
     */
    public boolean isErrorEnabled() {
        return logger.isEnabledFor(Level.ERROR);
    }

    @Override
    public void log(Marker marker, String callerFQCN, int level, String msg, Object[] arguments, Throwable t) {
        Level log4jLevel = toLog4jLevel(level);
        NormalizedParameters np = NormalizedParameters.normalize(msg, arguments, t);
        String formattedMessage = MessageFormatter.basicArrayFormat(np.getMessage(), np.getArguments());
        logger.log(callerFQCN, log4jLevel, formattedMessage, np.getThrowable());
    }

    @Override
    protected void handleNormalizedLoggingCall(org.slf4j.event.Level level, Marker marker, String msg, Object[] arguments, Throwable throwable) {
        Level log4jLevel = toLog4jLevel(level.toInt());
        String formattedMessage = MessageFormatter.basicArrayFormat(msg, arguments);
        logger.log(getFullyQualifiedCallerName(), log4jLevel, formattedMessage, throwable);
    }

    /**
     * Called by {@link SubstituteLogger} or by {@link LoggingEventBuilder} instances
     * @param event
     */
    public void log(LoggingEvent event) {
        Level log4jLevel = toLog4jLevel(event.getLevel().toInt());
        if (!logger.isEnabledFor(log4jLevel))
            return;

        org.apache.log4j.spi.LoggingEvent log4jevent = event2Log4jEvent(event, log4jLevel);
        logger.callAppenders(log4jevent);

    }

    private org.apache.log4j.spi.LoggingEvent event2Log4jEvent(LoggingEvent event, Level log4jLevel) {

        String formattedMessage = MessageFormatter.basicArrayFormat(event.getMessage(), event.getArgumentArray());

        LocationInfo locationInfo = null;
        String fqcn = null;

        if (event instanceof SubstituteLoggingEvent) {
            locationInfo = new LocationInfo(NA_SUBST, NA_SUBST, NA_SUBST, "0");
            fqcn = FQCN_SUBSTITUE;
        } else {
            fqcn = FQCN_FLUENT;
        }

        ThrowableInformation ti = null;
        Throwable t = event.getThrowable();
        if (t != null)
            ti = new ThrowableInformation(t);

        if(event instanceof DefaultLoggingEvent) {
            DefaultLoggingEvent defaultLoggingEvent = (DefaultLoggingEvent) event;
            defaultLoggingEvent.setTimeStamp(System.currentTimeMillis());
        }

        org.apache.log4j.spi.LoggingEvent log4jEvent = new org.apache.log4j.spi.LoggingEvent(fqcn, logger, event.getTimeStamp(), log4jLevel, formattedMessage,
                        event.getThreadName(), ti, null, locationInfo, null);

        return log4jEvent;
    }

    private Level toLog4jLevel(int slf4jLevelInt) {
        Level log4jLevel;
        switch (slf4jLevelInt) {
        case LocationAwareLogger.TRACE_INT:
            log4jLevel = Level.TRACE;
            break;
        case LocationAwareLogger.DEBUG_INT:
            log4jLevel = Level.DEBUG;
            break;
        case LocationAwareLogger.INFO_INT:
            log4jLevel = Level.INFO;
            break;
        case LocationAwareLogger.WARN_INT:
            log4jLevel = Level.WARN;
            break;
        case LocationAwareLogger.ERROR_INT:
            log4jLevel = Level.ERROR;
            break;
        default:
            throw new IllegalStateException("Level number " + slf4jLevelInt + " is not recognized.");
        }
        return log4jLevel;
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return FQCN_NOMINAL;
    }

}
