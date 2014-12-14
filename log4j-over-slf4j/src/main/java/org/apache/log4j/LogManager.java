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

import java.util.Enumeration;
import java.util.Vector;

/**
 * <p/>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.LogManager</code> class (as found in log4j 1.2)
 * delegating all calls to SLF4J.
 * <p/>
 * <p/>
 * This implementation does <b>NOT</b> implement the setRepositorySelector(),
 * getLoggerRepository(), exists(), getCurrentLoggers(), shutdown() and
 * resetConfiguration() methods which do not have SLF4J equivalents.
 *
 * @author Ceki G&uuml;lc&uuml;
 */
@SuppressWarnings("rawtypes")
public class LogManager {

  public static Logger getRootLogger() {
    return Log4jLoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
  }

  public static Logger getLogger(final String name) {
    return Log4jLoggerFactory.getLogger(name);
  }

  public static Logger getLogger(final Class clazz) {
    return Log4jLoggerFactory.getLogger(clazz.getName());
  }

  /**
   * Returns a logger instance created by loggerFactory. This method was requested in
   * <a href="http://bugzilla.slf4j.org/show_bug.cgi?id=234">bug #234</a>. Note that
   * log4j-over-slf4j does not ship with a LoggerFactory implementation. If this
   * method is called, the caller must provide his/her own implementation.
   *
   * @param name          the name of the desired logger
   * @param loggerFactory an instance of {@link LoggerFactory}
   * @return returns a logger instance created by loggerFactory
   * @since 1.6.6
   */
  public static Logger getLogger(String name, LoggerFactory loggerFactory) {
    return loggerFactory.makeNewLoggerInstance(name);
  }

  /**
   * This bogus implementation returns an empty enumeration.
   *
   * @return
   */
  public static Enumeration getCurrentLoggers() {
    return new Vector().elements();
  }

  /**
   * Implemented as NOP.
   */
  public static void shutdown() {
  }

  /**
   * Implemented as NOP.
   */
  public static void resetConfiguration() {
  }
}
