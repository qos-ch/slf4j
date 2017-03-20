package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class SimpleServiceProvider implements SLF4JServiceProvider {

    /**
     * Declare the version of the SLF4J API this implementation is compiled against. 
     * The value of this field is modified with each major release. 
     */
    // to avoid constant folding by the compiler, this field must *not* be final
    public static String REQUESTED_API_VERSION = "1.8.99"; // !final

    public ILoggerFactory getLoggerFactory() {
        return new SimpleLoggerFactory();
    }

    public IMarkerFactory getMarkerFactory() {
        return new BasicMarkerFactory();
    }

    public MDCAdapter getMDCAdapter() {
        return new NOPMDCAdapter();
    }

    public String getRequesteApiVersion() {
        return REQUESTED_API_VERSION;
    }

}
