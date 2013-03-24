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
import org.slf4j.helpers.Util;

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
  private static ConcurrentMap<String, Logger> log4jLoggers = new ConcurrentHashMap<String, Logger>();

  private static final String LOG4J_DELEGATION_LOOP_URL = "http://www.slf4j.org/codes.html#log4jDelegationLoop";

  // check for delegation loops
  static {
    try {
      Class.forName("org.slf4j.impl.Log4jLoggerFactory");
      String part1 = "Detected both log4j-over-slf4j.jar AND slf4j-log4j12.jar on the class path, preempting StackOverflowError. ";
      String part2 = "See also " + LOG4J_DELEGATION_LOOP_URL
              + " for more details.";

      Util.report(part1);
      Util.report(part2);
      throw new IllegalStateException(part1 + part2);
    } catch (ClassNotFoundException e) {
      // this is the good case
    }
  }

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
