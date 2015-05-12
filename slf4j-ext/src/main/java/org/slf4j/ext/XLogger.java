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
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A utility that provides standard mechanisms for logging certain kinds of
 * activities.
 * 
 * @author Ralph Goers
 * @author Ceki G&uuml;lc&uuml;
 */
public class XLogger extends LoggerWrapper implements Logger {

    private static final String FQCN = XLogger.class.getName();
    static Marker FLOW_MARKER = MarkerFactory.getMarker("FLOW");
    static Marker ENTRY_MARKER = MarkerFactory.getMarker("ENTRY");
    static Marker EXIT_MARKER = MarkerFactory.getMarker("EXIT");

    static Marker EXCEPTION_MARKER = MarkerFactory.getMarker("EXCEPTION");
    static Marker THROWING_MARKER = MarkerFactory.getMarker("THROWING");
    static Marker CATCHING_MARKER = MarkerFactory.getMarker("CATCHING");

    static String EXIT_MESSAGE_0 = "exit";
    static String EXIT_MESSAGE_1 = "exit with ({})";

    static String ENTRY_MESSAGE_0 = "entry";
    static String ENTRY_MESSAGE_1 = "entry with ({})";
    static String ENTRY_MESSAGE_2 = "entry with ({}, {})";
    static String ENTRY_MESSAGE_3 = "entry with ({}, {}, {})";
    static String ENTRY_MESSAGE_4 = "entry with ({}, {}, {}, {})";
    static int ENTRY_MESSAGE_ARRAY_LEN = 5;
    static String[] ENTRY_MESSAGE_ARRAY = new String[ENTRY_MESSAGE_ARRAY_LEN];
    static {
        ENTRY_MARKER.add(FLOW_MARKER);
        EXIT_MARKER.add(FLOW_MARKER);
        THROWING_MARKER.add(EXCEPTION_MARKER);
        CATCHING_MARKER.add(EXCEPTION_MARKER);

        ENTRY_MESSAGE_ARRAY[0] = ENTRY_MESSAGE_0;
        ENTRY_MESSAGE_ARRAY[1] = ENTRY_MESSAGE_1;
        ENTRY_MESSAGE_ARRAY[2] = ENTRY_MESSAGE_2;
        ENTRY_MESSAGE_ARRAY[3] = ENTRY_MESSAGE_3;
        ENTRY_MESSAGE_ARRAY[4] = ENTRY_MESSAGE_4;
    }

    public enum Level {
        TRACE("TRACE", LocationAwareLogger.TRACE_INT), DEBUG("DEBUG", LocationAwareLogger.DEBUG_INT), INFO("INFO", LocationAwareLogger.INFO_INT), WARN("WARN",
                        LocationAwareLogger.WARN_INT), ERROR("ERROR", LocationAwareLogger.ERROR_INT);

        private final String name;
        private final int level;

        public String toString() {
            return this.name;
        }

        public int intValue() {
            return this.level;
        }

        private Level(String name, int level) {
            this.name = name;
            this.level = level;
        }
    }

    /**
     * Given an underlying logger, construct an XLogger
     * 
     * @param logger
     *          underlying logger
     */
    public XLogger(Logger logger) {
        // If class B extends A, assuming B does not override method x(), the caller
        // of new B().x() is A and not B, see also
        // http://jira.qos.ch/browse/SLF4J-105
        super(logger, LoggerWrapper.class.getName());
    }

    /**
     * Log method entry.
     * 
     * @param argArray
     *          supplied parameters
     */
    public void entry(Object... argArray) {
        if (instanceofLAL && logger.isTraceEnabled(ENTRY_MARKER)) {
            String messagePattern = null;
            if (argArray.length < ENTRY_MESSAGE_ARRAY_LEN) {
                messagePattern = ENTRY_MESSAGE_ARRAY[argArray.length];
            } else {
                messagePattern = buildMessagePattern(argArray.length);
            }
            FormattingTuple tp = MessageFormatter.arrayFormat(messagePattern, argArray);
            ((LocationAwareLogger) logger).log(ENTRY_MARKER, FQCN, LocationAwareLogger.TRACE_INT, tp.getMessage(), argArray, tp.getThrowable());
        }
    }

    /**
     * Log method exit
     */
    public void exit() {
        if (instanceofLAL && logger.isTraceEnabled(ENTRY_MARKER)) {
            ((LocationAwareLogger) logger).log(EXIT_MARKER, FQCN, LocationAwareLogger.TRACE_INT, EXIT_MESSAGE_0, null, null);
        }
    }

    /**
     * Log method exit
     * 
     * @param result
     *          The result of the method being exited
     */
    public <T> T exit(T result) {
        if (instanceofLAL && logger.isTraceEnabled(ENTRY_MARKER)) {
            FormattingTuple tp = MessageFormatter.format(EXIT_MESSAGE_1, result);
            ((LocationAwareLogger) logger).log(EXIT_MARKER, FQCN, LocationAwareLogger.TRACE_INT, tp.getMessage(), new Object[] { result }, tp.getThrowable());
        }
        return result;
    }

    /**
     * Log an exception being thrown. The generated log event uses Level ERROR.
     * 
     * @param throwable
     *          the exception being caught.
     */
    public <T extends Throwable> T throwing(T throwable) {
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(THROWING_MARKER, FQCN, LocationAwareLogger.ERROR_INT, "throwing", null, throwable);
        }
        return throwable;
    }

    /**
     * Log an exception being thrown allowing the log level to be specified.
     * 
     * @param level
     *          the logging level to use.
     * @param throwable
     *          the exception being caught.
     */
    public <T extends Throwable> T throwing(Level level, T throwable) {
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(THROWING_MARKER, FQCN, level.level, "throwing", null, throwable);
        }
        return throwable;
    }

    /**
     * Log an exception being caught. The generated log event uses Level ERROR.
     * 
     * @param throwable
     *          the exception being caught.
     */
    public void catching(Throwable throwable) {
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(CATCHING_MARKER, FQCN, LocationAwareLogger.ERROR_INT, "catching", null, throwable);
        }
    }

    /**
     * Log an exception being caught allowing the log level to be specified.
     * 
     * @param level
     *          the logging level to use.
     * @param throwable
     *          the exception being caught.
     */
    public void catching(Level level, Throwable throwable) {
        if (instanceofLAL) {
            ((LocationAwareLogger) logger).log(CATCHING_MARKER, FQCN, level.level, "catching", null, throwable);
        }
    }

    private static String buildMessagePattern(int len) {
        StringBuilder sb = new StringBuilder();
        sb.append(" entry with (");
        for (int i = 0; i < len; i++) {
            sb.append("{}");
            if (i != len - 1)
                sb.append(", ");
        }
        sb.append(')');
        return sb.toString();
    }
}
