/**
 * Copyright (c) 2004-2014 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.ILoggerFactory;

/**
 * @author C&eacute;drik LIME
 */
public class SimpleLoggerAccess {

//	final static SimpleLoggerFactory INSTANCE = SimpleLoggerFactory.INSTANCE;
	final static SimpleLoggerFactory INSTANCE = (SimpleLoggerFactory) StaticLoggerBinder.getSingleton().getLoggerFactory();

	private SimpleLoggerAccess() {
	}

	/**
	 * Return an appropriate {@link SimpleLogger} instance by name, or
	 * {@code null} if none exists.
	 *
	 * @see ILoggerFactory#getLogger(String)
	 */
	public static SimpleLogger getLogger(String name) {
		SimpleLogger slogger = INSTANCE.loggerMap.get(name);
		return slogger;
	}

	public static Collection<SimpleLogger> getAllLoggers() {
		return new ArrayList<SimpleLogger>(INSTANCE.loggerMap.values());
	}


	public static void changeLoggerLevel(String loggerName, String newLevel) throws IllegalArgumentException {
		changeLoggerLevel(getLogger(loggerName), levelNameToInt(newLevel));
	}

	public static void changeLoggerLevel(SimpleLogger logger, String newLevel) throws IllegalArgumentException {
		changeLoggerLevel(logger, levelNameToInt(newLevel));
	}

	public static void changeLoggerLevel(SimpleLogger logger, int newLevel) throws IllegalArgumentException {
		if (logger == null) {
			return;
		}
		// Sanity check; will throw IllegalArgumentException
		getLevelName(newLevel);
		logger.currentLogLevel = newLevel;
	}


	public static int levelNameToInt(String level) throws IllegalArgumentException {
		int result = SimpleLogger.stringToLevel(level);
		// SimpleLogger#stringToLevel() defaults to INFO
		if (result == SimpleLogger.LOG_LEVEL_INFO && ! "info".equalsIgnoreCase(level)) {
			throw new IllegalArgumentException(level);
		}
		return result;
	}

	public static String getLevelName(SimpleLogger logger) throws IllegalArgumentException {
		return getLevelName(logger.currentLogLevel);
	}
	public static String getLevelName(int level) throws IllegalArgumentException {
		return SimpleLogger.levelToString(level);
	}
}
