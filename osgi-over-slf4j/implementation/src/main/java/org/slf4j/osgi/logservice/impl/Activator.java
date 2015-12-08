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
 *
 */

package org.slf4j.osgi.logservice.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <code>Activator</code> implements a simple bundle that registers a
 * {@link LogServiceFactory} for the creation of {@link LogService} implementations.
 *
 * @author John Conlon
 * @author Matt Bishop
 */
public class Activator implements BundleActivator {

  private final LogEntryStore logEntryStore = new LogEntryStore();
  private final Log log = new Log(logEntryStore, new LogListeners());

  /**
   * Implements <code>BundleActivator.start()</code> to register the
   * LogServiceFactory and LogReaderService.
   *
   * @param bundleContext the framework context for the bundle
   * @throws Exception
   */
  public void start(BundleContext bundleContext) throws Exception {
    registerLogEventStoreAsManagedService(bundleContext);
    startFrameworkEventLogger(bundleContext);
    startEventAdminSender(bundleContext);
    registerLogService(bundleContext);
    registerLogReaderService(bundleContext);
  }

  private void startEventAdminSender(BundleContext bundleContext) {
    //EventAdmin is an optional Service, so use a ServiceTracker to manage access to it.
    ServiceTracker tracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
    tracker.open();
    EventAdminSender sender = new EventAdminSender(tracker);
    log.addLogListener(sender);
  }

  private void startFrameworkEventLogger(BundleContext bundleContext) {
    OSGiEventLogger logger = new OSGiEventLogger(log);
    bundleContext.addFrameworkListener(logger);
    bundleContext.addBundleListener(logger);
    bundleContext.addServiceListener(logger);
  }

  private void registerLogService(BundleContext bundleContext) {
    ServiceReference packageAdminReference = bundleContext.getServiceReference(PackageAdmin.class.getName());
    //PackageAdmin is part of the framework so it will be available and won't be removed later
    PackageAdmin packageAdmin = (PackageAdmin) bundleContext.getService(packageAdminReference);
    Dictionary<String, String> properties = new Hashtable<String, String>();
    properties.put("description", "An SLF4J LogService implementation.");
    bundleContext.registerService(
            LogService.class.getName(),
            new LogServiceFactory(log, packageAdmin),
            properties);
  }

  private void registerLogReaderService(BundleContext bundleContext) {
    Dictionary<String, String> properties = new Hashtable<String, String>();
    properties.put("description", "An SLF4J LogReaderService implementation.");
    bundleContext.registerService(
            LogReaderService.class.getName(),
            new LogReaderServiceFactory(log),
            properties);
  }

  private void registerLogEventStoreAsManagedService(BundleContext bundleContext) {
    Dictionary<String, String> properties = new Hashtable<String, String>();
    properties.put("description", "An SLF4J LogReaderService configuration.");
    properties.put("service.pid", "slf4j.logservice.LogReaderService");
    bundleContext.registerService(
            ManagedService.class.getName(),
            logEntryStore,
            properties);
  }

  /**
   * Implements <code>BundleActivator.stop()</code>.
   *
   * @param bundleContext the framework context for the bundle
   * @throws Exception
   */
  public void stop(BundleContext bundleContext) throws Exception {
    log.stop();
  }
}
