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

package org.apache.log4j.xml;

import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

import javax.xml.parsers.FactoryConfigurationError;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;


import org.w3c.dom.Element;

public class DOMConfigurator implements Configurator {

  public static void configure(Element element) {
  }

  public static void configure(String filename) throws FactoryConfigurationError {
  }

  static public void configure(URL url) throws FactoryConfigurationError {
  }

  static public void configureAndWatch(String configFilename) {
  }

  public static void configureAndWatch(String configFilename, long delay) {
  }

  public void doConfigure(Element element, LoggerRepository repository) {
  }

  public void doConfigure(InputStream inputStream, LoggerRepository repository) throws FactoryConfigurationError {
  }

  public void doConfigure(Reader reader, LoggerRepository repository) throws FactoryConfigurationError {
  }

  public void doConfigure(String filename, LoggerRepository repository) {
  }

  public void doConfigure(URL url, LoggerRepository repository) {
  }

  public static String subst(String value, Properties props) {
    return value;
  }

}
