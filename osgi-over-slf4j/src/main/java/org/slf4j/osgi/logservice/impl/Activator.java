/*
 * Copyright (c) 2004-2005 QOS.ch
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

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.service.log.LogService;

/**
 * <code>Activator</code> implements a simple bundle that registers a
 * {@link LogServiceFactory} for the creation of {@link LogService} implementations.
 *
 * @author John Conlon
 * @author Matt Bishop
 **/
public class Activator implements BundleActivator {
    /**
     *
	 * Implements <code>BundleActivator.start()</code> to register a
     * LogServiceFactory.
     *
     * @param bundleContext the framework context for the bundle
     * @throws Exception
     */
    public void start(BundleContext bundleContext) throws Exception {

        Properties props = new Properties();
        props.put("description", "An SLF4J LogService implementation.");
        ServiceFactory factory = new LogServiceFactory();
        bundleContext.registerService(LogService.class.getName(), factory, props);
    }

    /**
     *
     * Implements <code>BundleActivator.stop()</code>.
     *
     * @param bundleContext the framework context for the bundle
     * @throws Exception
     */
    public void stop(BundleContext bundleContext) throws Exception {

        // Note: It is not required that we remove the service here, since
        // the framework will do it automatically.
    }
}
