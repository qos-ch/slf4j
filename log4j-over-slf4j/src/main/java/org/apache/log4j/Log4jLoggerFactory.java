/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This class is a factory that creates and maintains org.apache.log4j.Loggers
 * wrapping org.slf4j.Loggers.
 *
 * It keeps a hashtable of all created org.apache.log4j.Logger instances so that
 * all newly created instances are not duplicates of existing loggers.
 *
 * @author S&eacute;bastien Pennec
 */
class Log4jLoggerFactory {

    // String, Logger
    private static final ConcurrentMap<String, Logger> log4jLoggers = new ConcurrentHashMap<>();

    public static Logger getLogger(String name) {
        org.apache.log4j.Logger instance = log4jLoggers.get(name);
        if (instance != null) {
            return instance;
        } else {
            Logger newInstance = new Logger(name);
            Logger oldInstance = log4jLoggers.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }

    public static Logger getLogger(String name, LoggerFactory loggerFactory) {
        org.apache.log4j.Logger instance = log4jLoggers.get(name);
        if (instance != null) {
            return instance;
        } else {
            Logger newInstance = loggerFactory.makeNewLoggerInstance(name);
            Logger oldInstance = log4jLoggers.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
