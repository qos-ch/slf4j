/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * <p>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.Logger</code> class (as found in log4j 1.2) 
 * delegating all calls to a {@link org.slf4j.Logger} instance.
 * 
 *
 * @author Ceki G&uuml;lc&uuml; 
 * */
@SuppressWarnings("rawtypes")
public class Logger extends Category {

    private static final String LOGGER_FQCN = Logger.class.getName();

    protected Logger(String name) {
        super(name);
    }

    public static Logger getLogger(String name) {
        return Log4jLoggerFactory.getLogger(name);
    }

    public static Logger getLogger(String name, LoggerFactory loggerFactory) {
        return Log4jLoggerFactory.getLogger(name, loggerFactory);
    }

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * Does the obvious.
     * 
     * @return the root logger
     */
    public static Logger getRootLogger() {
        return Log4jLoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
    }

    /**
     * Delegates to {@link org.slf4j.Logger#isTraceEnabled} 
     * method of SLF4J.
     * 
     * @return whether this logger is enabled for the level TRACE
     */
    public boolean isTraceEnabled() {
        return slf4jLogger.isTraceEnabled();
    }

    /**
     * Delegates to {@link org.slf4j.Logger#trace(String)} method in SLF4J.
     *     
     * @param message the message to log
     *      
     */
    public void trace(Object message) {
        differentiatedLog(null, LOGGER_FQCN, LocationAwareLogger.TRACE_INT, message, null);
    }

    /**
     * Delegates to {@link org.slf4j.Logger#trace(String,Throwable)} 
     * method in SLF4J.
     * 
     * @param message the message to log
     * @param t a Throwable to log
     */
    public void trace(Object message, Throwable t) {
        differentiatedLog(null, LOGGER_FQCN, LocationAwareLogger.TRACE_INT, message, t);
    }

}
