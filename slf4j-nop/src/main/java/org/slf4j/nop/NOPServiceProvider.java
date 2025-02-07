package org.slf4j.nop;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class NOPServiceProvider implements SLF4JServiceProvider {

    /**
     * Declare the version of the SLF4J API this implementation is compiled against. 
     * The value of this field is modified with each major release. 
     */
    // to avoid constant folding by the compiler, this field must *not* be final
    public static String REQUESTED_API_VERSION = "2.0.99"; // !final

    private final ILoggerFactory loggerFactory = new NOPLoggerFactory();

    // LoggerFactory expects providers to initialize markerFactory as early as possible.
    private final IMarkerFactory markerFactory;
    
    // LoggerFactory expects providers to initialize their MDCAdapter field
    // as early as possible, preferably at construction time.
    private final MDCAdapter mdcAdapter;

    public NOPServiceProvider() {
        markerFactory = new BasicMarkerFactory();
        mdcAdapter = new NOPMDCAdapter();
    }
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    public MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }

    @Override
    public String getRequestedApiVersion() {
        return REQUESTED_API_VERSION;
    }

    public void initialize() {
    }

   
}
