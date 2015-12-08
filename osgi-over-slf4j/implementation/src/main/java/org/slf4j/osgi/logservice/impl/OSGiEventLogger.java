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

import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;

import java.util.HashMap;
import java.util.Map;

/**
 * Listens for Framework events and logs them to LogService.
 * <p/>
 * Reference: Section 101.6 Mapping of Events in <a href="https://osgi.org/download/r4v42/r4.cmpn.pdf">OSGi R4.2 Compendium</a>
 *
 * @author Matt Bishop
 */
class OSGiEventLogger implements FrameworkListener, BundleListener, ServiceListener {

    private static final Map<Integer, String> FRAMEWORK_EVENT_MESSAGES = new HashMap<Integer, String>();
    private static final Map<Integer, String> BUNDLE_EVENT_MESSAGES = new HashMap<Integer, String>();
    private static final Map<Integer, String> SERVICE_EVENT_MESSAGES = new HashMap<Integer, String>();

    static {
        FRAMEWORK_EVENT_MESSAGES.put(FrameworkEvent.STARTED, "FrameworkEvent STARTED");
        FRAMEWORK_EVENT_MESSAGES.put(FrameworkEvent.ERROR, "FrameworkEvent ERROR");
        FRAMEWORK_EVENT_MESSAGES.put(FrameworkEvent.PACKAGES_REFRESHED, "FrameworkEvent PACKAGES REFRESHED");
        FRAMEWORK_EVENT_MESSAGES.put(FrameworkEvent.STARTLEVEL_CHANGED, "FrameworkEvent STARTLEVEL CHANGED");
        FRAMEWORK_EVENT_MESSAGES.put(FrameworkEvent.WARNING, "FrameworkEvent WARNING");
        FRAMEWORK_EVENT_MESSAGES.put(FrameworkEvent.INFO, "FrameworkEvent INFO");

        BUNDLE_EVENT_MESSAGES.put(BundleEvent.INSTALLED, "BundleEvent INSTALLED");
        BUNDLE_EVENT_MESSAGES.put(BundleEvent.STARTED, "BundleEvent STARTED");
        BUNDLE_EVENT_MESSAGES.put(BundleEvent.UPDATED, "BundleEvent UPDATED");
        BUNDLE_EVENT_MESSAGES.put(BundleEvent.UNINSTALLED, "BundleEvent UNINSTALLED");
        BUNDLE_EVENT_MESSAGES.put(BundleEvent.RESOLVED, "BundleEvent RESOLVED");
        BUNDLE_EVENT_MESSAGES.put(BundleEvent.UNRESOLVED, "BundleEvent UNRESOLVED");

        SERVICE_EVENT_MESSAGES.put(ServiceEvent.REGISTERED, "ServiceEvent REGISTERED");
        SERVICE_EVENT_MESSAGES.put(ServiceEvent.MODIFIED, "ServiceEvent MODIFIED");
        SERVICE_EVENT_MESSAGES.put(ServiceEvent.UNREGISTERING, "ServiceEvent UNREGISTERING");
    }


    private final Log log;

    OSGiEventLogger(Log log) {
        this.log = log;
    }

    public void frameworkEvent(FrameworkEvent event) {
        String message = FRAMEWORK_EVENT_MESSAGES.get(event.getType());
        if (message != null) {
            int level = event.getType() == FrameworkEvent.ERROR
                    ? LogService.LOG_ERROR
                    : LogService.LOG_INFO;
            LogEntry entry = new ImmutableLogEntry(event.getBundle(), level, message, event.getThrowable());
            log.addLogEntry(entry);
        }
    }

    public void bundleChanged(BundleEvent event) {
        String message = BUNDLE_EVENT_MESSAGES.get(event.getType());
        if (message != null) {
            LogEntry entry = new ImmutableLogEntry(event.getBundle(), LogService.LOG_INFO, message);
            log.addLogEntry(entry);
        }
    }

    public void serviceChanged(ServiceEvent event) {
        String message = SERVICE_EVENT_MESSAGES.get(event.getType());
        if (message != null) {
            int level = event.getType() == ServiceEvent.MODIFIED
                   ? LogService.LOG_DEBUG
                    : LogService.LOG_INFO;
            ServiceReference reference = event.getServiceReference();
            LogEntry entry = new ImmutableLogEntry(reference.getBundle(), reference, level, message);
            log.addLogEntry(entry);
        }
    }
}
