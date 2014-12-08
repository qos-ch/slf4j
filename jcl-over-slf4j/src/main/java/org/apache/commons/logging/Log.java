/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.logging;

/**
 * A simple logging interface abstracting logging APIs.  In order to be
 * instantiated successfully by {@link LogFactory}, classes that implement
 * this interface must have a constructor that takes a single String
 * parameter representing the "name" of this Log.
 * <p>
 * The six logging levels used by <code>Log</code> are (in order):
 * <ol>
 * <li>trace (the least serious)</li>
 * <li>debug</li>
 * <li>info</li>
 * <li>warn</li>
 * <li>error</li>
 * <li>fatal (the most serious)</li>
 * </ol>
 * The mapping of these log levels to the concepts used by the underlying
 * logging system is implementation dependent.
 * The implementation should ensure, though, that this ordering behaves
 * as expected.
 * <p>
 * Performance is often a logging concern.
 * By examining the appropriate property,
 * a component can avoid expensive operations (producing information
 * to be logged).
 * <p>
 * For example,
 * <code><pre>
 *    if (log.isDebugEnabled()) {
 *        ... do something expensive ...
 *        log.debug(theResult);
 *    }
 * </pre></code>
 * <p>
 * Configuration of the underlying logging system will generally be done
 * external to the Logging APIs, through whatever mechanism is supported by
 * that system.
 *
 * @version $Id$
 */
public interface Log {

    // ----------------------------------------------------- Logging Properties

    /**
     * Is debug logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug.
     *
     * @return true if debug is enabled in the underlying logger.
     */
    public boolean isDebugEnabled();

    /**
     * Is error logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error.
     *
     * @return true if error is enabled in the underlying logger.
     */
    public boolean isErrorEnabled();

    /**
     * Is fatal logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than fatal.
     *
     * @return true if fatal is enabled in the underlying logger.
     */
    public boolean isFatalEnabled();

    /**
     * Is info logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info.
     *
     * @return true if info is enabled in the underlying logger.
     */
    public boolean isInfoEnabled();

    /**
     * Is trace logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace.
     *
     * @return true if trace is enabled in the underlying logger.
     */
    public boolean isTraceEnabled();

    /**
     * Is warn logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than warn.
     *
     * @return true if warn is enabled in the underlying logger.
     */
    public boolean isWarnEnabled();

    // -------------------------------------------------------- Logging Methods

    /**
     * Log a message with trace log level.
     *
     * @param message log this message
     */
    public void trace(Object message);

    /**
     * Log an error with trace log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void trace(Object message, Throwable t);

    /**
     * Log a message with debug log level.
     *
     * @param message log this message
     */
    public void debug(Object message);

    /**
     * Log an error with debug log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void debug(Object message, Throwable t);

    /**
     * Log a message with info log level.
     *
     * @param message log this message
     */
    public void info(Object message);

    /**
     * Log an error with info log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void info(Object message, Throwable t);

    /**
     * Log a message with warn log level.
     *
     * @param message log this message
     */
    public void warn(Object message);

    /**
     * Log an error with warn log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void warn(Object message, Throwable t);

    /**
     * Log a message with error log level.
     *
     * @param message log this message
     */
    public void error(Object message);

    /**
     * Log an error with error log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void error(Object message, Throwable t);

    /**
     * Log a message with fatal log level.
     *
     * @param message log this message
     */
    public void fatal(Object message);

    /**
     * Log an error with fatal log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void fatal(Object message, Throwable t);
}
