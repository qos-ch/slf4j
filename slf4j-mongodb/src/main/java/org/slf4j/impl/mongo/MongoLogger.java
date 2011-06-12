/* 
 * Copyright (c) 2011 Christian Trutz
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
 */
package org.slf4j.impl.mongo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.helpers.MarkerIgnoringBase;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 
 */
public class MongoLogger extends MarkerIgnoringBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5313857708266728847L;

	/**
	 * 
	 */
	private final String name;

	/**
	 * 
	 */
	private final DBCollection traceCollection;

	/**
	 * 
	 */
	private final DBCollection debugCollection;

	/**
	 * 
	 */
	private final DBCollection infoCollection;

	/**
	 * 
	 */
	private final DBCollection warnCollection;

	/**
	 * 
	 */
	private final DBCollection errorCollection;

	/**
	 * 
	 * @param name
	 * @param traceCollection
	 * @param debugCollection
	 * @param infoCollection
	 * @param warnCollection
	 * @param errorCollection
	 */
	protected MongoLogger(final String name,
			final DBCollection traceCollection,
			final DBCollection debugCollection,
			final DBCollection infoCollection,
			final DBCollection warnCollection,
			final DBCollection errorCollection) {
		this.name = name;
		this.traceCollection = traceCollection;
		this.debugCollection = debugCollection;
		this.infoCollection = infoCollection;
		this.warnCollection = warnCollection;
		this.errorCollection = errorCollection;
	}

	private void log(final DBCollection collection, final Throwable throwable,
			final String message, final Object... args) {
		Map<String, Object> attributes = new HashMap<String, Object>(4);

		// logger name
		attributes.put("name", name);

		// throwable
		if (throwable != null) {
			StringWriter writer = new StringWriter();
			throwable.printStackTrace(new PrintWriter(writer));
			attributes.put("throwable", writer.toString());
		}

		// message
		attributes.put("message", message);
		if (args != null)
			if (args.length > 0)
				attributes.put("args", args);

		DBObject dbObject = new BasicDBObject(attributes);
		collection.insert(dbObject);
	}

	public boolean isTraceEnabled() {
		return true;
	}

	public void trace(String msg) {
		log(traceCollection, null, msg);
	}

	public void trace(String format, Object arg) {
		log(traceCollection, null, format, arg);
	}

	public void trace(String format, Object arg1, Object arg2) {
		log(traceCollection, null, format, arg1, arg2);
	}

	public void trace(String format, Object[] argArray) {
		log(traceCollection, null, format, argArray);
	}

	public void trace(String msg, Throwable throwable) {
		log(traceCollection, throwable, msg);
	}

	public boolean isDebugEnabled() {
		return true;
	}

	public void debug(String msg) {
		log(debugCollection, null, msg);
	}

	public void debug(String format, Object arg) {
		log(debugCollection, null, format);
	}

	public void debug(String format, Object arg1, Object arg2) {
		log(debugCollection, null, format, arg1, arg2);
	}

	public void debug(String format, Object[] argArray) {
		log(debugCollection, null, format, argArray);
	}

	public void debug(String msg, Throwable throwable) {
		log(debugCollection, throwable, msg);
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public void info(String msg) {
		log(infoCollection, null, msg);
	}

	public void info(String format, Object arg) {
		log(infoCollection, null, format, arg);
	}

	public void info(String format, Object arg1, Object arg2) {
		log(infoCollection, null, format, arg1, arg2);
	}

	public void info(String format, Object[] argArray) {
		log(infoCollection, null, format, argArray);
	}

	public void info(String msg, Throwable throwable) {
		log(infoCollection, throwable, msg);
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public void warn(String msg) {
		log(warnCollection, null, msg);
	}

	public void warn(String format, Object arg) {
		log(warnCollection, null, format, arg);

	}

	public void warn(String format, Object arg1, Object arg2) {
		log(warnCollection, null, format, arg1, arg2);

	}

	public void warn(String format, Object[] argArray) {
		log(warnCollection, null, format, argArray);

	}

	public void warn(String msg, Throwable throwable) {
		log(warnCollection, throwable, msg);

	}

	public boolean isErrorEnabled() {
		return true;
	}

	public void error(String msg) {
		log(errorCollection, null, msg);
	}

	public void error(String format, Object arg) {
		log(errorCollection, null, format, arg);
	}

	public void error(String format, Object arg1, Object arg2) {
		log(errorCollection, null, format, arg1, arg2);
	}

	public void error(String format, Object[] argArray) {
		log(errorCollection, null, format, argArray);
	}

	public void error(String msg, Throwable throwable) {
		log(errorCollection, throwable, msg);
	}

}
