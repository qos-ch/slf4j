package org.slf4j.spi;

public class NOPLoggingEventBuilder implements LoggingEventBuilder {

	static NOPLoggingEventBuilder SINGLETON = new NOPLoggingEventBuilder();
	
	public static LoggingEventBuilder singleton() {
		return SINGLETON;
	}

}
