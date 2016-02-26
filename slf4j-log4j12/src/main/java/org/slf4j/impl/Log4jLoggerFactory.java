/**
 * Copyright (c) 2004-2011 QOS.ch
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.LogManager;
import org.slf4j.helpers.Util;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Log4jLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriate named {@link Log4jLoggerAdapter} instance.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class Log4jLoggerFactory implements ILoggerFactory {

    private static final String LOG4J_DELEGATION_LOOP_URL = "http://www.slf4j.org/codes.html#log4jDelegationLoop";

    // check for delegation loops
    static {
        try {
            Class.forName("org.apache.log4j.Log4jLoggerFactory");
            String part1 = "Detected both log4j-over-slf4j.jar AND bound slf4j-log4j12.jar on the class path, preempting StackOverflowError. ";
            String part2 = "See also " + LOG4J_DELEGATION_LOOP_URL + " for more details.";

            Util.report(part1);
            Util.report(part2);
            throw new IllegalStateException(part1 + part2);
        } catch (ClassNotFoundException e) {
            // this is the good case
        }
    }

    // key: name (String), value: a Log4jLoggerAdapter;
    ConcurrentMap<String, Logger> loggerMap;

    public Log4jLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, Logger>();
        // force log4j to initialize
        org.apache.log4j.LogManager.getRootLogger();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.slf4j.ILoggerFactory#getLogger(java.lang.String)
     */
    public Logger getLogger(String name) {
        Logger slf4jLogger = loggerMap.get(name);
        if (slf4jLogger != null) {
            return slf4jLogger;
        } else {
            org.apache.log4j.Logger log4jLogger;
            if (name.equalsIgnoreCase(Logger.ROOT_LOGGER_NAME))
                log4jLogger = LogManager.getRootLogger();
            else
                log4jLogger = LogManager.getLogger(name);

            Logger newInstance = new Log4jLoggerAdapter(log4jLogger);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
