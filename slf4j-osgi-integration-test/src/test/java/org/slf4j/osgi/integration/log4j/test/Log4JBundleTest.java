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

package org.slf4j.osgi.integration.log4j.test;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.osgi.integration.IntegrationTestConstants;
import org.slf4j.osgi.test.service.Probe;
import org.springframework.osgi.test.ConfigurableBundleCreatorTests;

/**
 * 
 * <code>Log4JBundleTest</code> starts up an OSGi environment (equinox,
 * knopflerfish, or felix according to the profile selected) and installs the
 * slf4j.osgi.test.bundle, the slf4j.log4j12 bundle and the bundles they depend
 * on.
 * 
 * The slf4j.log4j12 will use the log4j bundle associated with the Spring-OSGi
 * testing framework.
 * 
 * The test classes in this project will be turned into a virtual bundle which
 * is also installed and the tests are then run inside the OSGi runtime.
 * 
 * The tests have access to a BundleContext, which we use to test that all
 * bundles have been started.
 * 
 * 
 * @author John Conlon
 */
public class Log4JBundleTest extends ConfigurableBundleCreatorTests implements IntegrationTestConstants {

	

	
	

	/**
	 * The manifest to use for the "virtual bundle" created out of the test
	 * classes and resources in this project
	 * 
	 * This is actually the boilerplate manifest with one additional
	 * import-package added. We should provide a simpler customization point for
	 * such use cases that doesn't require duplication of the entire manifest...
	 */
	protected String getManifestLocation() {
		return "classpath:org/slf4j/osgi/integration/log4j/test/MANIFEST.MF";
	}

	/**
	 * The location of the packaged OSGi bundles to be installed for this test.
	 * Values are Spring resource paths. The bundles we want to use are part of
	 * the same multi-project maven build as this project is. Hence we use the
	 * localMavenArtifact helper method to find the bundles produced by the
	 * package phase of the maven build (these tests will run after the
	 * packaging phase, in the integration-test phase).
	 * 
	 * JUnit, commons-logging, spring-core and the spring OSGi test bundle are
	 * automatically included so they do not need to be specified here.
	 * 
	 * Except in the case of slf4j.log4j12 using the log4j bundle associated
	 * with the Spring-OSGi testing framework our test bundles are using package
	 * import and export versions to keep these other logging bundles from
	 * getting mixed up with our test bundles.
	 */
	protected String[] getBundleLocations() {
		return new String[] {
				localMavenArtifact(SPRINGFRAMEWORK_OSGI_GROUP_NAME,
						"aopalliance.osgi", "1.0-SNAPSHOT"),
				localMavenArtifact(SPRINGFRAMEWORK_OSGI_GROUP_NAME,
						"spring-context", "2.1-SNAPSHOT"),
				localMavenArtifact(SPRINGFRAMEWORK_OSGI_GROUP_NAME, "spring-beans",
						"2.1-SNAPSHOT"),
				localMavenArtifact(SPRINGFRAMEWORK_OSGI_GROUP_NAME,
						"spring-osgi-core", "1.0-SNAPSHOT"),
				localMavenArtifact(SPRINGFRAMEWORK_OSGI_GROUP_NAME, "spring-aop",
						"2.1-SNAPSHOT"),
				localMavenArtifact(SLF4J_GROUP_ID, API_BUNDLE_ARTIFACT_ID, SLF4J_VERSION_UNDER_TEST),
				localMavenArtifact(SLF4J_GROUP_ID, LOG4J12_BINDING_BUNDLE_ARTIFACT_ID, SLF4J_VERSION_UNDER_TEST),
				localMavenArtifact(SLF4J_GROUP_ID, TEST_BUNDLE_ARTIFACT_ID,
						SLF4J_VERSION_UNDER_TEST) };
	}

	/**
	 * The superclass provides us access to the root bundle context via the
	 * 'getBundleContext' operation. Make sure it is not null.
	 */
	public void testOSGiStartedOk() {
		BundleContext bundleContext = getBundleContext();
		assertNotNull(bundleContext);

	}

	/**
	 * Makes sure our bundles are in the OSGi runtime and their state is Active.
	 * 
	 */
	public void testSlf4jLog4j12BundleIntegration() {
		Logger log = LoggerFactory.getLogger(Log4JBundleTest.class);
		assertNotNull(log);
		BundleContext context = getBundleContext();
		List symNames = new ArrayList();

		Bundle[] bundles = context.getBundles();
		log.info("Loaded bundles:");
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundle = bundles[i];
			assertEquals("Bundle " + bundle.getSymbolicName()
					+ " is not active.", Bundle.ACTIVE, bundle.getState());
			symNames.add(bundle.getSymbolicName());
			if (bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR) != null) {
				log.info("Symbolic Name:" + bundle.getSymbolicName()
						+ ", Activator:"
						+ bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR));
			} else {
				log.info("Symbolic Name:" + bundle.getSymbolicName());
			}
		}

		assertTrue(symNames.contains(LOG4J_BINDING_BUNDLE_SYM_NAME));
		assertTrue(symNames.contains(TEST_BUNDLE_SYM_NAME));

	}

	public void testProbeService() {
		BundleContext context = getBundleContext();
		ServiceReference ref = context.getServiceReference(Probe.class
				.getName());
		assertNotNull("Service Reference is null", ref);
		Probe probe = null;

		probe = (Probe) context.getService(ref);
		assertNotNull("Cannot find the probe service", probe);

		try {
			probe.testCommonslogging();
			fail("The commons logging package should not be found. Instead an exception should be thrown.");
		} catch (Throwable t) {
			// "Expect.",t);
		}

		context.ungetService(ref);

	}
}
