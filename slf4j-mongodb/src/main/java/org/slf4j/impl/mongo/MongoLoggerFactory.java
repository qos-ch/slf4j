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

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * 
 */
public class MongoLoggerFactory implements ILoggerFactory {

	/**
	 *
	 */
	private final Map<String, Logger> loggerMap;

	/**
	 * mongoDB trace collection.
	 */
	private final DBCollection traceCollection;

	/**
	 * mongoDB debug collection.
	 */
	private final DBCollection debugCollection;

	/**
	 * mongoDB info collection.
	 */
	private final DBCollection infoCollection;

	/**
	 * mongoDB warn collection.
	 */
	private final DBCollection warnCollection;

	/**
	 * mongoDB error collection.
	 */
	private final DBCollection errorCollection;

	/**
	 * 
	 */
	public MongoLoggerFactory() {
		loggerMap = new HashMap<String, Logger>();
		Mongo mongo;
		try {
			mongo = new Mongo();
			DB db = mongo.getDB("logger");
			traceCollection = db.getCollection("trace");
			debugCollection = db.getCollection("debug");
			infoCollection = db.getCollection("info");
			warnCollection = db.getCollection("warn");
			errorCollection = db.getCollection("error");
		} catch (UnknownHostException unknownHostException) {
			throw new IllegalStateException(unknownHostException);
		} catch (MongoException mongoException) {
			throw new IllegalStateException(mongoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Logger getLogger(final String name) {
		Logger logger = null;
		// protect against concurrent access of the loggerMap
		synchronized (this) {
			logger = loggerMap.get(name);
			if (logger == null) {
				logger = new MongoLogger(name, traceCollection,
						debugCollection, infoCollection, warnCollection,
						errorCollection);
				loggerMap.put(name, logger);
			}
		}
		return logger;
	}

}
