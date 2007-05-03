package org.slf4j.spi;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * An <b>optional</b> interface helping integration with logging systems capable of 
 * extracting location information. This interface is mainly used by SLF4J bridges 
 * such as jcl104-over-slf4j which need to provide hints so that the underlying logging
 * system can extract the correct locatin information (method name, line number, etc.).
 * 
 * 
 * @author Ceki Gulcu
 * @since 1.3
 */
public interface LocationAwareLogger extends Logger {

  final public int TRACE_INT = 00;
  final public int DEBUG_INT = 10;
  final public int INFO_INT = 20;
  final public int WARN_INT = 30;
  final public int ERROR_INT = 40;
  
  
  /**
   * Printing method which support for location information. 
   * 
   * @param marker
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level
   * @param message
   * @param t
   */  
  public void log(Marker marker, String fqcn, int level, String message, Throwable t);
  
}
