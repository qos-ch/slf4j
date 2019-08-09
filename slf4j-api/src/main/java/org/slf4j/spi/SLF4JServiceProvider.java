package org.slf4j.spi;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.LoggerFactory;

/**
 * This interface based on {@link java.util.ServiceLoader} paradigm. 
 * 
 * <p>It replaces the old static-binding mechanism used in SLF4J versions 1.0.x to 1.7.x.
 *
 * @author Ceki G&uml;lc&uml;
 * @since 1.8
 */
public interface SLF4JServiceProvider {

    
    /**
     * Return the instance of {@link ILoggerFactory} that 
     * {@link org.slf4j.LoggerFactory} class should bind to.
     * 
     * @return instance of {@link ILoggerFactory} 
     */
    public ILoggerFactory getLoggerFactory();
    
    /**
     * Return the instance of {@link IMarkerFactory} that 
     * {@link org.slf4j.MarkerFactory} class should bind to.
     * 
     * @return instance of {@link IMarkerFactory} 
     */
    public IMarkerFactory getMarkerFactory();
    
    /**
     * Return the instnace of {@link MDCAdapter} that
     * {@link MDC} should bind to.
     * 
     * @return instance of {@link MDCAdapter} 
     */
    public MDCAdapter getMDCAdapter();
    
    public String getRequesteApiVersion();
    
    /**
     * Initialize the logging back-end.
     * 
     * <p><b>WARNING:</b> This method is intended to be called once by 
     * {@link LoggerFactory} class and from nowhere else. 
     * 
     */
    public void initialize();
}
