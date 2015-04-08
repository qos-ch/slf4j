/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.logging.impl;

import java.io.Serializable;
import org.apache.commons.logging.Log;

/**
 * <p>
 * Trivial implementation of Log that throws away all messages. No configurable
 * system properties are supported.
 * </p>
 * 
 * @author <a href="mailto:sanders@apache.org">Scott Sanders</a>
 * @author Rod Waldhoff
 * @version $Id: NoOpLog.java,v 1.8 2004/06/06 21:13:12 rdonkin Exp $
 */
public class NoOpLog implements Log, Serializable {
    private static final long serialVersionUID = 561423906191706148L;

    /** Convenience constructor */
    public NoOpLog() {
    }

    /** Base constructor */
    public NoOpLog(String name) {
    }

    /** Do nothing */
    public void trace(Object message) {
    }

    /** Do nothing */
    public void trace(Object message, Throwable t) {
    }

    /** Do nothing */
    public void debug(Object message) {
    }

    /** Do nothing */
    public void debug(Object message, Throwable t) {
    }

    /** Do nothing */
    public void info(Object message) {
    }

    /** Do nothing */
    public void info(Object message, Throwable t) {
    }

    /** Do nothing */
    public void warn(Object message) {
    }

    /** Do nothing */
    public void warn(Object message, Throwable t) {
    }

    /** Do nothing */
    public void error(Object message) {
    }

    /** Do nothing */
    public void error(Object message, Throwable t) {
    }

    /** Do nothing */
    public void fatal(Object message) {
    }

    /** Do nothing */
    public void fatal(Object message, Throwable t) {
    }

    /**
     * Debug is never enabled.
     * 
     * @return false
     */
    public final boolean isDebugEnabled() {
        return false;
    }

    /**
     * Error is never enabled.
     * 
     * @return false
     */
    public final boolean isErrorEnabled() {
        return false;
    }

    /**
     * Fatal is never enabled.
     * 
     * @return false
     */
    public final boolean isFatalEnabled() {
        return false;
    }

    /**
     * Info is never enabled.
     * 
     * @return false
     */
    public final boolean isInfoEnabled() {
        return false;
    }

    /**
     * Trace is never enabled.
     * 
     * @return false
     */
    public final boolean isTraceEnabled() {
        return false;
    }

    /**
     * Warn is never enabled.
     * 
     * @return false
     */
    public final boolean isWarnEnabled() {
        return false;
    }

}
