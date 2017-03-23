package org.slf4j.helpers;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class NOPServiceProvider implements SLF4JServiceProvider {

	/**
	 * Declare the version of the SLF4J API this implementation is compiled
	 * against. The value of this field is modified with each major release.
	 */
	// to avoid constant folding by the compiler, this field must *not* be final
	public static String REQUESTED_API_VERSION = "1.8.99"; // !final

	private ILoggerFactory loggerFactory = new NOPLoggerFactory();
	private IMarkerFactory markerFactory =  new BasicMarkerFactory();
	private MDCAdapter mdcAdapter =  new NOPMDCAdapter();

	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	public IMarkerFactory getMarkerFactory() {
		return markerFactory;
	}

	public MDCAdapter getMDCAdapter() {
		return mdcAdapter;
	}

	public String getRequesteApiVersion() {
		return REQUESTED_API_VERSION;
	}

	@Override
	public void initialize() {
		// already initialized
	}
}
