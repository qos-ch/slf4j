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

package org.slf4j.osgi.logservice.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>LogServiceImpl</code> is a simple OSGi LogService implementation that delegates to a slf4j
 * Logger.
 *
 * @author John Conlon
 * @author Matt Bishop
 */
public class LogServiceImpl implements LogService {

    private static final String UNKNOWN = "[Unknown]";

    private final Logger delegate;

    /**
     * Creates a new instance of LogServiceImpl.
     *
     * @param bundle The bundle to create a new LogService for.
     */
    public LogServiceImpl(Bundle bundle) {

        String name = bundle.getSymbolicName();
        Version version = bundle.getVersion();
        if (version == null) {
            version = Version.emptyVersion;
        }
        delegate = LoggerFactory.getLogger(name + '.' + version);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.service.log.LogService#log(int, java.lang.String)
     */
    public void log(int level, String message) {

        switch (level) {
        case LOG_DEBUG:
            delegate.debug(message);
            break;
        case LOG_ERROR:
            delegate.error(message);
            break;
        case LOG_INFO:
            delegate.info(message);
            break;
        case LOG_WARNING:
            delegate.warn(message);
            break;
        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.service.log.LogService#log(int, java.lang.String, java.lang.Throwable)
     */
    public void log(int level, String message, Throwable exception) {

        switch (level) {
        case LOG_DEBUG:
            delegate.debug(message, exception);
            break;
        case LOG_ERROR:
            delegate.error(message, exception);
            break;
        case LOG_INFO:
            delegate.info(message, exception);
            break;
        case LOG_WARNING:
            delegate.warn(message, exception);
            break;
        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.service.log.LogService#log(org.osgi.framework.ServiceReference, int, java.lang.String)
     */
    public void log(ServiceReference sr, int level, String message) {

        switch (level) {
        case LOG_DEBUG:
            if (delegate.isDebugEnabled()) {
                delegate.debug(createMessage(sr, message));
            }
            break;
        case LOG_ERROR:
            if (delegate.isErrorEnabled()) {
                delegate.error(createMessage(sr, message));
            }
            break;
        case LOG_INFO:
            if (delegate.isInfoEnabled()) {
                delegate.info(createMessage(sr, message));
            }
            break;
        case LOG_WARNING:
            if (delegate.isWarnEnabled()) {
                delegate.warn(createMessage(sr, message));
            }
            break;
        default:
            break;
        }
    }

    /**
     * Formats the log message to indicate the service sending it, if known.
     *
     * @param sr the ServiceReference sending the message.
     * @param message The message to log.
     * @return The formatted log message.
     */
    private String createMessage(ServiceReference sr, String message) {

        StringBuilder output = new StringBuilder();
        if (sr != null) {
            output.append('[').append(sr.toString()).append(']');
        } else {
            output.append(UNKNOWN);
        }
        output.append(message);

        return output.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.service.log.LogService#log(org.osgi.framework.ServiceReference, int, java.lang.String,
     * java.lang.Throwable)
     */
    public void log(ServiceReference sr, int level, String message, Throwable exception) {

        switch (level) {
        case LOG_DEBUG:
            if (delegate.isDebugEnabled()) {
                delegate.debug(createMessage(sr, message), exception);
            }
            break;
        case LOG_ERROR:
            if (delegate.isErrorEnabled()) {
                delegate.error(createMessage(sr, message), exception);
            }
            break;
        case LOG_INFO:
            if (delegate.isInfoEnabled()) {
                delegate.info(createMessage(sr, message), exception);
            }
            break;
        case LOG_WARNING:
            if (delegate.isWarnEnabled()) {
                delegate.warn(createMessage(sr, message), exception);
            }
            break;
        default:
            break;
        }
    }
}
