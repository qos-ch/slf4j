package org.slf4j.spi;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;

public interface SLF4JServiceProvider {

    
    /**
     * Return the instance of {@link ILoggerFactory} that 
     * {@link org.slf4j.LoggerFactory} class should bind to.
     * 
     * @return the instance of {@link ILoggerFactory} that 
     * {@link org.slf4j.LoggerFactory} class should bind to.
     */
    public ILoggerFactory getLoggerFactory();
    
    /**
     * Return the instance of {@link IMarkerFactory} that 
     * {@link org.slf4j.MarkerFactory} class should bind to.
     * 
     * @return the instance of {@link IMarkerFactory} that 
     * {@link org.slf4j.MarkerFactory} class should bind to.
     */
    public IMarkerFactory getMarkerFactory();
    
    
    public MDCAdapter getMDCAdapter();
    
    public String getRequesteApiVersion();
    
    public void initialize();
}
