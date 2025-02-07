package org.slf4j.reload4j;

import org.apache.log4j.Level;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Reporter;
import org.slf4j.helpers.Util;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class Reload4jServiceProvider implements SLF4JServiceProvider {

    /**
     * Declare the version of the SLF4J API this implementation is compiled against. 
     * The value of this field is modified with each major release. 
     */
    // to avoid constant folding by the compiler, this field must *not* be final
    public static String REQUESTED_API_VERSION = "2.0.99"; // !final

    private ILoggerFactory loggerFactory;

    // LoggerFactory expects providers to initialize markerFactory as early as possible.
    private final IMarkerFactory markerFactory;

    // LoggerFactory expects providers to have a valid MDCAdapter field
    // as early as possible, preferably at construction time.
    private final MDCAdapter mdcAdapter;

    public Reload4jServiceProvider() {
        markerFactory = new BasicMarkerFactory();
        mdcAdapter = new Reload4jMDCAdapter();
        try {
            @SuppressWarnings("unused")
            Level level = Level.TRACE;
        } catch (NoSuchFieldError nsfe) {
            Reporter.error("This version of SLF4J requires log4j version 1.2.12 or later. See also http://www.slf4j.org/codes.html#log4j_version");
        }
    }

    @Override
    public void initialize() {
        loggerFactory = new Reload4jLoggerFactory();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }


    @Override
    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }


    @Override
    public MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }

    @Override
    public String getRequestedApiVersion() {
        return REQUESTED_API_VERSION;
    }
}
