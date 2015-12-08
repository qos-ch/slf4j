/*
 * Copyright (c) 2004-2015 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 */

package org.slf4j.osgi.logservice.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;

/**
 * <code>LogServiceImpl</code> is an OSGi LogService implementation that delegates to a slf4j
 * Logger.
 * <p/>
 * Reference: Section 101.2 Log Service in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author John Conlon
 * @author Matt Bishop
 */
class LogServiceImpl implements LogService {

    private static final String UNKNOWN = "Unknown";

    private final Log log;
    private final Logger delegate;
    private final Bundle bundle;
    private final PackageAdmin packageAdmin;

    /**
     * Creates a new instance of LogServiceImpl.
     *  @param bundle   The bundle to create a new LogService for.
     * @param log      The Log to provide service to
     * @param delegate the Logger to delegate log messages to
     * @param packageAdmin The Package Admin service
     */
    LogServiceImpl(Bundle bundle, Log log, Logger delegate, PackageAdmin packageAdmin) {
        this.bundle = bundle;
        this.log = log;
        this.delegate = delegate;
        this.packageAdmin = packageAdmin;
    }

    public void log(int level, String message) {

        switch (level) {
            case LOG_ERROR:
                delegate.error(message);
                break;
            case LOG_WARNING:
                delegate.warn(message);
                break;
            case LOG_INFO:
                delegate.info(message);
                break;
            case LOG_DEBUG:
                delegate.debug(message);
                break;
            default:
                delegate.trace(message);
                break;
        }

        if (shouldAddLogEntry(level)) {
            LogEntry logEntry = new ImmutableLogEntry(bundle, level, message);
            log.addLogEntry(logEntry);
        }
    }

    public void log(int level, String message, Throwable originalException) {

        Throwable exception = LogEntryException.from(originalException, packageAdmin);

        switch (level) {
            case LOG_ERROR:
                delegate.error(message, exception);
                break;
            case LOG_WARNING:
                delegate.warn(message, exception);
                break;
            case LOG_INFO:
                delegate.info(message, exception);
                break;
            case LOG_DEBUG:
                delegate.debug(message, exception);
                break;
            default:
                delegate.trace(message);
                break;
        }

        if (shouldAddLogEntry(level)) {
            LogEntry logEntry = new ImmutableLogEntry(bundle, level, message, exception);
            log.addLogEntry(logEntry);
        }
    }

    public void log(ServiceReference reference, int level, String message) {

        switch (level) {
            case LOG_ERROR:
                if (delegate.isErrorEnabled()) {
                    delegate.error(createMessage(reference, message));
                }
                break;
            case LOG_WARNING:
                if (delegate.isWarnEnabled()) {
                    delegate.warn(createMessage(reference, message));
                }
                break;
            case LOG_INFO:
                if (delegate.isInfoEnabled()) {
                    delegate.info(createMessage(reference, message));
                }
                break;
            case LOG_DEBUG:
                if (delegate.isDebugEnabled()) {
                    delegate.debug(createMessage(reference, message));
                }
                break;
            default:
                if (delegate.isTraceEnabled()) {
                    delegate.trace(createMessage(reference, message));
                }
                break;
        }

        if (shouldAddLogEntry(level)) {
            LogEntry logEntry = new ImmutableLogEntry(getBundle(reference), reference, level, message);
            log.addLogEntry(logEntry);
        }
    }

    public void log(ServiceReference reference, int level, String message, Throwable originalException) {

        Throwable exception = LogEntryException.from(originalException, packageAdmin);

        switch (level) {
            case LOG_ERROR:
                if (delegate.isErrorEnabled()) {
                    delegate.error(createMessage(reference, message), exception);
                }
                break;
            case LOG_WARNING:
                if (delegate.isWarnEnabled()) {
                    delegate.warn(createMessage(reference, message), exception);
                }
                break;
            case LOG_INFO:
                if (delegate.isInfoEnabled()) {
                    delegate.info(createMessage(reference, message), exception);
                }
                break;
            case LOG_DEBUG:
                if (delegate.isDebugEnabled()) {
                    delegate.debug(createMessage(reference, message), exception);
                }
                break;
            default:
                if (delegate.isTraceEnabled()) {
                    delegate.trace(createMessage(reference, message));
                }
                break;
        }

        if (shouldAddLogEntry(level)) {
            LogEntry logEntry = new ImmutableLogEntry(getBundle(reference), reference, level, message, exception);
            log.addLogEntry(logEntry);
        }
    }

    /**
     * Formats the log message to indicate the service sending it, if known.
     *
     * @param reference the ServiceReference sending the message.
     * @param message   The message to log.
     * @return The formatted log message.
     */
    private String createMessage(ServiceReference reference, String message) {
        String referenceString = reference == null
                ? UNKNOWN
                : reference.toString();

        return "[" + referenceString + ']' + message;
    }

    private boolean shouldAddLogEntry(int level) {
        return LogService.LOG_DEBUG != level || log.debugEnabled();
    }

    private Bundle getBundle(ServiceReference reference) {
        return reference == null
                ? bundle
                : reference.getBundle();
    }
}
