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

import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class is a re-implementation of the org.apache.log4j.Logger class. It
 * uses a org.slf4j.Logger object to delegate the actual logging to a
 * user-chosen implementation. 
 * </p>
 * <p>
 * Its printing methods that are shared with the
 * org.slf4j.Logger interface redirect the logging requests to the
 * org.slf4j.Logger. Those methods are debug, info, warn and error. However, the
 * methods that are now present in the org.slf4j.Logger interface are not
 * implemented. Those are the trace and fatal methods.
 * </p>
 * 
 * @author S&eacute;bastien Pennec
 */
public class Logger {

	private String name;

	private org.slf4j.Logger lbLogger;

	protected Logger(String name) {
		this.name = name;
		lbLogger = LoggerFactory.getLogger(name);
	}

	public static Logger getLogger(String name) {
		return Log4jLoggerFactory.getLogger(name);
	}

	public static Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}

	public static Logger getRootLogger() {
		return getLogger("root");
	}

	public String getName() {
		return name;
	}

	public boolean isDebugEnabled() {
		return lbLogger.isDebugEnabled();
	}

	public void debug(Object message) {
		/**
		 * In the debug(Object message) method, as well as other printing methods,
		 * we consider that the message passed as a parameter is a String. Object
		 * that usually need an ObjectRenderer cannot be sent to these methods.
		 */
		lbLogger.debug((String) message);
	}

	public void debug(Object message, Throwable t) {
		lbLogger.debug((String) message, t);
	}

	public void debug(Object messagePattern, Object arg) {
		lbLogger.debug((String) messagePattern, arg);
	}

	public void debug(Object messagePattern, Object arg1, Object arg2) {
		lbLogger.debug((String) messagePattern, arg1, arg2);
	}

	public boolean isInfoEnabled() {
		return lbLogger.isInfoEnabled();
	}

	public void info(Object message) {
		lbLogger.info((String) message);
	}

	public void info(Object message, Throwable t) {
		lbLogger.info((String) message, t);
	}

	public void info(Object messagePattern, Object arg) {
		lbLogger.info((String) messagePattern, arg);
	}

	public void info(Object messagePattern, Object arg1, Object arg2) {
		lbLogger.info((String) messagePattern, arg1, arg2);
	}

	public boolean isWarnEnabled() {
		return lbLogger.isWarnEnabled();
	}

	public void warn(Object message) {
		lbLogger.warn((String) message);
	}

	public void warn(Object message, Throwable t) {
		lbLogger.warn((String) message, t);
	}

	public void warn(Object messagePattern, Object arg) {
		lbLogger.warn((String) messagePattern, arg);
	}

	public void warn(Object messagePattern, Object arg1, Object arg2) {
		lbLogger.warn((String) messagePattern, arg1, arg2);
	}

	public boolean isErrorEnabled() {
		return lbLogger.isErrorEnabled();
	}

	public void error(Object message) {
		lbLogger.error((String) message);
	}

	public void error(Object message, Throwable t) {
		lbLogger.error((String) message, t);
	}

	public void error(Object messagePattern, Object arg) {
		lbLogger.error((String) messagePattern, arg);
	}

	public void error(Object messagePattern, Object arg1, Object arg2) {
		lbLogger.error((String) messagePattern, arg1, arg2);
	}

	// public void log(String fqcn, Level level, String message, Throwable t) {
	// //FIXME improve + complete impl.
	// Logger logger = getLogger(fqcn);
	// if (Level.DEBUG.equals(level)) {
	// logger.debug(message, t);
	// } else if (Level.INFO.equals(level)) {
	// logger.info(message, t);
	// } else if (Level.WARN.equals(level)) {
	// logger.info(message, t);
	// } else if (Level.ERROR.equals(level)) {
	// logger.info(message, t);
	// }
	// }
	//  
	// public boolean isEnabledFor(Level level) {
	// //FIXME improve + complete impl.
	// if(Level.DEBUG.equals(level) && lbLogger.isDebugEnabled()) {
	// return true;
	// }
	// if(Level.INFO.equals(level) && lbLogger.isInfoEnabled()) {
	// return true;
	// }
	// if(Level.WARN.equals(level) && lbLogger.isWarnEnabled()) {
	// return true;
	// }
	// if(Level.ERROR.equals(level) && lbLogger.isErrorEnabled()) {
	// return true;
	// }
	// return false;
	// }

}