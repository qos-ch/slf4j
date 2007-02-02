/* 
 * Copyright (c) 2004-2005 SLF4J.ORG
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

package org.slf4j.osgi.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * <code>Activator</code> implements a simple bundle to test OSGi slf4j
 * logging components.
 * 
 * Upon startup, shutdown, and receiving a service event, it logs event
 * details.
 * 
 * On startup it logs a series of messages.
 * 
 * @author John Conlon
 */
public class Activator implements BundleActivator, ServiceListener {

	private final Logger log = LoggerFactory.getLogger(Activator.class);

	/**
	 * Implements <code>BundleActivator.start()</code>. Logs a message adds
	 * itself to the bundle context as a service listener, and exercises the
	 * logger.
	 * 
	 * @param bundleContext
	 *            the framework context for the bundle
	 * @throws Exception
	 */
	public void start(BundleContext bundleContext) throws Exception {
		log.info("Starting to listen for service events.");
		bundleContext.addServiceListener(this);

		test1();
		test2();
		testNull();
		testMarker();
	}

	/**
	 * Implements <code>BundleActivator.stop()</code>. Prints a message and
	 * removes itself from the bundle context as a service listener.
	 * 
	 * @param bundleContext
	 *            the framework context for the bundle
	 * @throws Exception
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		bundleContext.removeServiceListener(this);
		log.info("Stopped listening for service events.");

		// Note: It is not required that we remove the listener here, since
		// the framework will do it automatically anyway.
	}

	/**
	 * Implements <code>ServiceListener.serviceChanges()</code>. Logs the
	 * details of any service event from the framework.
	 * 
	 * @param event
	 *            the fired service event
	 */
	public void serviceChanged(ServiceEvent event) {
		String[] objectClass = (String[]) event.getServiceReference()
				.getProperty("objectClass");
		if (event.getType() == ServiceEvent.REGISTERED) {
			log.info("SimpleBundle: Service of type {} registered.",
					objectClass[0]);
		} else if (event.getType() == ServiceEvent.UNREGISTERING) {
			log.info("SimpleBundle: Service of type {} unregistered.",
					objectClass[0]);
		} else if (event.getType() == ServiceEvent.MODIFIED) {
			log.info("SimpleBundle: Service of type {} modified.",
					objectClass[0]);
		}
	}

	public void test1() {
		Logger logger = LoggerFactory.getLogger("test1");
		logger.debug("Hello world.");
	}

	public void test2() {
		Integer i1 = new Integer(1);
		Integer i2 = new Integer(2);
		Integer i3 = new Integer(3);
		Exception e = new Exception("This is a test exception.");
		Logger logger = LoggerFactory.getLogger("test2");

		logger.debug("Hello world 1.");
		logger.debug("Hello world {}", i1);
		logger.debug("val={} val={}", i1, i2);
		logger.debug("val={} val={} val={}", new Object[] { i1, i2, i3 });

		logger.debug("Hello world 2", e);
		logger.info("Hello world 2.");

		logger.warn("Hello world 3.");
		logger.warn("Hello world 3", e);

		logger.error("Hello world 4.");
		logger.error("Hello world {}", new Integer(3));
		logger.error("Hello world 4.", e);
	}

	public void testNull() {
		Logger logger = LoggerFactory.getLogger("testNull");
		logger.debug(null);
		logger.info(null);
		logger.warn(null);
		logger.error(null);

		Exception e = new Exception("This is a test exception.");
		logger.debug(null, e);
		logger.info(null, e);
		logger.warn(null, e);
		logger.error(null, e);
	}

	public void testMarker() {
		Logger logger = LoggerFactory.getLogger("testMarker");
		Marker blue = MarkerFactory.getMarker("BLUE");
		logger.debug(blue, "hello");
		logger.info(blue, "hello");
		logger.warn(blue, "hello");
		logger.error(blue, "hello");

		logger.debug(blue, "hello {}", "world");
		logger.info(blue, "hello {}", "world");
		logger.warn(blue, "hello {}", "world");
		logger.error(blue, "hello {}", "world");

		logger.debug(blue, "hello {} and {} ", "world", "universe");
		logger.info(blue, "hello {} and {} ", "world", "universe");
		logger.warn(blue, "hello {} and {} ", "world", "universe");
		logger.error(blue, "hello {} and {} ", "world", "universe");
	}
}
