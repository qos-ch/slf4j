/**
 * Copyright (c) 2015 QOS.ch
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
 */

package org.slf4j.osgi.logservice.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.util.tracker.ServiceTracker;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Listens for LogEntries and posts them to the Event Admin, if available.
 * <p/>
 * Reference: Section 101.6 Log Events in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author Matt Bishop
 */
class EventAdminSender implements LogListener {

    static final String LOG_TOPIC_BASE = "org/osgi/service/log/LogEntry/";
    private static final String[] LOG_TOPICS = {
        LOG_TOPIC_BASE + "LOG_OTHER",
        LOG_TOPIC_BASE + "LOG_ERROR",
        LOG_TOPIC_BASE + "LOG_WARNING",
        LOG_TOPIC_BASE + "LOG_INFO",
        LOG_TOPIC_BASE + "LOG_DEBUG"
    };

    private final ServiceTracker eventAdminTracker;

    EventAdminSender(ServiceTracker tracker) {
        this.eventAdminTracker = tracker;
}

    public void logged(LogEntry logEntry) {
        EventAdmin eventAdmin = (EventAdmin) eventAdminTracker.getService();
        if (eventAdmin == null) {
            return;
        }

        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        Bundle bundle = logEntry.getBundle();
        properties.put("bundle.id", bundle.getBundleId());
        if (bundle.getSymbolicName() != null) {
            properties.put("bundle.symbolicName", bundle.getSymbolicName());
        }
        properties.put("bundle", bundle);
        properties.put("log.level", logEntry.getLevel());
        properties.put("message", logEntry.getMessage());
        properties.put("timestamp", logEntry.getTime());
        properties.put("log.entry", logEntry);

        Throwable exception = logEntry.getException();
        if (exception != null) {
            String name = (exception instanceof LogEntryException)
                    ? ((LogEntryException) exception).getOriginalClassName()
                    : exception.getClass().getName();
            properties.put("exception.class", name);
            properties.put("exception.message", exception.getMessage());
            properties.put("exception", exception);
        }

        ServiceReference reference = logEntry.getServiceReference();
        if (reference != null) {
            properties.put("service", reference);
            properties.put("service.id", reference.getProperty("service.id"));
            Object pid = reference.getProperty("service.pid");
            if (pid != null) {
                properties.put("service.pid", reference.getProperty("service.pid"));
            }
            properties.put("service.objectClass", reference.getProperty("service.objectClass"));
        }

        int level = logEntry.getLevel();
        if (level >= LOG_TOPICS.length) {
            level = 0;
        }
        Event logEvent = new Event(LOG_TOPICS[level], properties);
        eventAdmin.postEvent(logEvent);
    }
}
