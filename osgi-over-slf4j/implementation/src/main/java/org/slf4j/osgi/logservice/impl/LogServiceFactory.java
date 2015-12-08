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
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.Version;
import org.osgi.service.packageadmin.PackageAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>LogServiceFactory</code> creates {@link org.osgi.service.log.LogService} implementations. ServiceFactory
 * is necessary to capture the bundle that is using the LogService for LogEntry instances.
 *
 * @author John Conlon
 * @author Matt Bishop
 */
class LogServiceFactory implements ServiceFactory {

  private final Log log;
  private final PackageAdmin packageAdmin;

  LogServiceFactory(Log log, PackageAdmin packageAdmin) {
    this.log = log;
    this.packageAdmin = packageAdmin;
  }

  public Object getService(Bundle bundle, ServiceRegistration registration) {
    Logger delegate = getDelegate(bundle);
    return new LogServiceImpl(bundle, log, delegate, packageAdmin);
  }

  public void ungetService(Bundle bundle, ServiceRegistration registration, Object service) {
    // nothing to do.
  }

  private Logger getDelegate(Bundle bundle) {
    String name = bundle.getSymbolicName();
    Version version = bundle.getVersion();
    if (version == null) {
      version = Version.emptyVersion;
    }
    return LoggerFactory.getLogger(name + '.' + version);
  }
}
