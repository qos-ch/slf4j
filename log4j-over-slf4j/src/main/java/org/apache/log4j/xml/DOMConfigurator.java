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
