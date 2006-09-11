/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * 
 * Copyright (C) 1999-2006, QOS.ch
 * 
 * This library is free software, you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation.
 */

package org.apache.log4j;

import java.util.Hashtable;

/**
 * This class is a factory that creates and maintains org.apache.log4j.Loggers
 * warpping org.slf4j.Loggers.
 * 
 * It keeps a hashtable of all created org.apache.log4j.Logger instances so that
 * all newly created instances are not dulpicates of existing loggers.
 * 
 * @author S&eacute;bastien Pennec
 */
public class Log4jLoggerFactory {

	private static Hashtable log4jLoggers = new Hashtable();

	public static Logger getLogger(String name) {
		if (log4jLoggers.containsKey(name)) {
			return (org.apache.log4j.Logger) log4jLoggers.get(name);
		} else {
			Logger log4jLogger = new Logger(name);
			log4jLoggers.put(name, log4jLogger);
			return log4jLogger;
		}
	}

}
