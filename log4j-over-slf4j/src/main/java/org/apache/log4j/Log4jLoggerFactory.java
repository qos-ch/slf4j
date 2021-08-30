/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
