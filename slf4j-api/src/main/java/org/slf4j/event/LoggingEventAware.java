package org.slf4j.event;

/**
 * 
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 */
public interface LoggingEventAware {

  void log(LoggingEvent event);
	
}
