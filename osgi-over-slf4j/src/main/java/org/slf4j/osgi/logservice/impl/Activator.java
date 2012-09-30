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

import java.util.Hashtable;

import org.eclipse.equinox.log.ExtendedLogService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceFactory;
import org.osgi.service.log.LogService;
import org.slf4j.osgi.logservice.impl.equinox.ExtendedLogServiceFactory;

/**
 * <code>Activator</code> implements a simple bundle that registers a
 * {@link LogServiceFactory} for the creation of {@link LogService} implementations.
**/
public class Activator implements BundleActivator {
	
	/**
	 * System property indicating whether SLF4J LogService should NOT receive
	 * highest service ranking.
	 */
	public static final String SYSPROP_NOT_DOMINATE = "sfl4j.osgi-over-slf4j.notDominate";
	
	
    /**
     *
	 * Implements <code>BundleActivator.start()</code> to register a
     * LogServiceFactory.
     *
     * @param bundleContext the framework context for the bundle
     * @throws Exception
     */
    @Override
	public void start(BundleContext bundleContext) throws Exception {    
    	// Prepare service properties
    	Hashtable<String, Object> props = new Hashtable<String, Object>();        
    	props.put("description", "An SLF4J implementation.");        

		// To dominate? Highest service ranking property of this implementation
		// increases probability to be selected by service clients    	
    	if (!Boolean.getBoolean(SYSPROP_NOT_DOMINATE)) {
    		props.put(Constants.SERVICE_RANKING, Integer.MAX_VALUE);
    	}
       
        // Register standard OSGi LogService
        bundleContext.registerService(
        		LogService.class.getName(),
        		new LogServiceFactory(),
        		props);
        
        // Register Equinox OSGi ExtendedLogService
        bundleContext.registerService(
	    		ExtendedLogService.class.getName(),
	    		new ExtendedLogServiceFactory(),
	    		props);
    }

    /**
     *
     * Implements <code>BundleActivator.stop()</code>.
     *
     * @param bundleContext the framework context for the bundle
     * @throws Exception
     */
    @Override
	public void stop(BundleContext bundleContext) throws Exception {
        // Note: It is not required that we remove the service here, since
        // the framework will do it automatically anyway.
    }
}
