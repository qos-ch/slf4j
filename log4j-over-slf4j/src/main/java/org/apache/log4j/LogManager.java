/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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

/**
 * <p>
 * This class is a minimal implementation of the original
 * <code>org.apache.log4j.LogManager</code> class (as found in log4j 1.2)
 * delegating all calls to SLF4J.
 * 
 * <p>
 * This implementation does <b>NOT</b> implement the setRepositorySelector(),
 * getLoggerRepository(), exists(), getCurrentLoggers(), shutdown() and
 * resetConfiguration() methods which do not have SLF4J equivalents.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * */
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
}
