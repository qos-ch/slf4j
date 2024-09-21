/**
 * Copyright (c) 2004-2021 QOS.ch
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
package org.slf4j.jdk.platform.logging;

import static java.util.Objects.requireNonNull;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.helpers.Reporter;
import org.slf4j.spi.CallerBoundaryAware;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * Adapts {@link Logger} to {@link System.Logger}.
 * @since 2.0.0
 */
class SLF4JPlatformLogger implements System.Logger {

    static private final String PRESUMED_CALLER_BOUNDARY = System.Logger.class.getName();
                    
    private final Logger slf4jLogger;

    public SLF4JPlatformLogger(Logger logger) {
        this.slf4jLogger = requireNonNull(logger);
    }

    @Override
    public String getName() {
        return slf4jLogger.getName();
    }

    // The fact that non loggable levels (in java.lang.System.Logger.Level)
    // such as ALL and OFF leak into the public interface is quite a pity.

    @Override
    public boolean isLoggable(Level jplLevel) {
        if (jplLevel == Level.ALL)
            return true;
        if (jplLevel == Level.OFF)
            return true;

        org.slf4j.event.Level slf4jLevel = jplLevelToSLF4JLevel(jplLevel);

        return slf4jLogger.isEnabledForLevel(slf4jLevel);
    }


    /**
     * Transform a {@link Level} to {@link org.slf4j.event.Level}.
     * 
     * This method assumes that Level.ALL or Level.OFF never reach this method.
     * 
     * @param jplLevel
     * @return
     */
    private org.slf4j.event.Level jplLevelToSLF4JLevel(Level jplLevel) {
        switch (jplLevel) {
        case TRACE:
            return org.slf4j.event.Level.TRACE;
        case DEBUG:
            return org.slf4j.event.Level.DEBUG;
        case INFO:
            return org.slf4j.event.Level.INFO;
        case WARNING:
            return org.slf4j.event.Level.WARN;
        case ERROR:
            return org.slf4j.event.Level.ERROR;
        default:
            reportUnknownLevel(jplLevel);
            return null;
        }
    }

    @Override
    public void log(Level jplLevel, ResourceBundle bundle, String msg, Throwable thrown) {
        log(jplLevel, bundle, msg, thrown, (Object[]) null);
    }

    @Override
    public void log(Level jplLevel, ResourceBundle bundle, String format, Object... params) {
        log(jplLevel, bundle, format, null, params);
    }

    /**
     * Single point of processing taking all possible parameters.
     * 
     * @param jplLevel 
     * @param bundle
     * @param msg
     * @param thrown
     * @param params
     */
    private void log(final Level jplLevel, final ResourceBundle bundle, final String msg, final Throwable thrown, final Object... params) {

        final Level jplLevelReduced = fixExtremeLevels(jplLevel);

        org.slf4j.event.Level slf4jLevel = jplLevelToSLF4JLevel(jplLevelReduced);
        boolean isEnabled = slf4jLogger.isEnabledForLevel(slf4jLevel);

        if (isEnabled) {
            performLog(slf4jLevel, bundle, msg, thrown, params);
        }
    }

    /**
     * <p>Level.OFF and Level.ALL levels are not supposed to be used when calling log printing methods.
     * </p>
     *
     * <p>We compensate for such incorrect usage by transforming Level.OFF as Level.ERROR and
     * Level.ALL as Level.TRACE.
     * </p>
     *
     * @param jplLevel
     * @return
     */
    private Level fixExtremeLevels(Level jplLevel) {
        if (jplLevel == Level.OFF)
            return  Level.ERROR;

        if (jplLevel == Level.ALL)
            return Level.TRACE;

        return jplLevel;
    }

    private void performLog(org.slf4j.event.Level slf4jLevel, ResourceBundle bundle, String msg, Throwable thrown, Object... params) {
        String message = getResourceStringOrMessage(bundle, msg);
        LoggingEventBuilder leb = slf4jLogger.makeLoggingEventBuilder(slf4jLevel);
        if (thrown != null) {
            leb = leb.setCause(thrown);
        }
        if (params != null && params.length > 0) {
            // add the arguments to the logging event for possible processing by the backend
            for (Object p : params) {
                leb = leb.addArgument(p);
            }
            // The JDK uses a different formatting convention. We must invoke it now.
            message = MessageFormat.format(message, params);
        }
        if (leb instanceof CallerBoundaryAware) {
            CallerBoundaryAware cba = (CallerBoundaryAware) leb;
            cba.setCallerBoundary(PRESUMED_CALLER_BOUNDARY);
        }
        leb.log(message);
    }

    private void reportUnknownLevel(Level jplLevel) {
        String message = "Unknown log level [" + jplLevel + "]";
        IllegalArgumentException iae = new IllegalArgumentException(message);
        Reporter.error("Unsupported log level", iae);
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
