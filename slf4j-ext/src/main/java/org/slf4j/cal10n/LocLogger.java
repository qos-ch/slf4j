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
package org.slf4j.cal10n;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.ext.LoggerWrapper;
import org.slf4j.spi.LocationAwareLogger;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageParameterObj;

/**
 * A logger specialized in localized logging. Localization is based in the <a
 * href="http://cal10n.qos.ch">CAL10N project</a>.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class LocLogger extends LoggerWrapper implements Logger {

    private static final String FQCN = LocLogger.class.getName();

    /**
     * Every localized message logged by a LocLogger will bear this marker. It
     * allows marker-aware implementations to perform additional processing on
     * localized messages.
     */
    static Marker LOCALIZED = MarkerFactory.getMarker("LOCALIZED");

    final IMessageConveyor imc;

    public LocLogger(Logger logger, IMessageConveyor imc) {
        super(logger, LoggerWrapper.class.getName());
        if (imc == null) {
            throw new IllegalArgumentException("IMessageConveyor cannot be null");
        }
        this.imc = imc;
    }

    /**
     * Log a localized message at the TRACE level.
     * 
     * @param key
     *          the key used for localization
     * @param args
     *          optional arguments
     */
    public void trace(Enum<?> key, Object... args) {
        if (!logger.isTraceEnabled()) {
            return;
        }
        String translatedMsg = imc.getMessage(key, args);
        MessageParameterObj mpo = new MessageParameterObj(key, args);

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(LOCALIZED, FQCN, LocationAwareLogger.TRACE_INT, translatedMsg, args, null);
        } else {
            logger.trace(LOCALIZED, translatedMsg, mpo);
        }
    }

    /**
     * Log a localized message at the DEBUG level.
     * 
     * @param key
     *          the key used for localization
     * @param args
     *          optional arguments
     */
    public void debug(Enum<?> key, Object... args) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        String translatedMsg = imc.getMessage(key, args);
        MessageParameterObj mpo = new MessageParameterObj(key, args);

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(LOCALIZED, FQCN, LocationAwareLogger.DEBUG_INT, translatedMsg, args, null);
        } else {
            logger.debug(LOCALIZED, translatedMsg, mpo);
        }
    }

    /**
     * Log a localized message at the INFO level.
     * 
     * @param key
     *          the key used for localization
     * @param args
     *          optional arguments
     */
    public void info(Enum<?> key, Object... args) {
        if (!logger.isInfoEnabled()) {
            return;
        }
        String translatedMsg = imc.getMessage(key, args);
        MessageParameterObj mpo = new MessageParameterObj(key, args);

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(LOCALIZED, FQCN, LocationAwareLogger.INFO_INT, translatedMsg, args, null);
        } else {
            logger.info(LOCALIZED, translatedMsg, mpo);
        }
    }

    /**
     * Log a localized message at the WARN level.
     * 
     * @param key
     *          the key used for localization
     * @param args
     *          optional arguments
     */
    public void warn(Enum<?> key, Object... args) {
        if (!logger.isWarnEnabled()) {
            return;
        }
        String translatedMsg = imc.getMessage(key, args);
        MessageParameterObj mpo = new MessageParameterObj(key, args);

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(LOCALIZED, FQCN, LocationAwareLogger.WARN_INT, translatedMsg, args, null);
        } else {
            logger.warn(LOCALIZED, translatedMsg, mpo);
        }
    }

    /**
     * Log a localized message at the ERROR level.
     * 
     * @param key
     *          the key used for localization
     * @param args
     *          optional arguments
     */
    public void error(Enum<?> key, Object... args) {
        if (!logger.isErrorEnabled()) {
            return;
        }
        String translatedMsg = imc.getMessage(key, args);
        MessageParameterObj mpo = new MessageParameterObj(key, args);

        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(LOCALIZED, FQCN, LocationAwareLogger.ERROR_INT, translatedMsg, args, null);
        } else {
            logger.error(LOCALIZED, translatedMsg, mpo);
        }
    }

}
