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
package org.slf4j.ext;

import org.slf4j.LoggerFactory;

/**
 * 
 * This class is essentially a wrapper around an
 * {@link LoggerFactory} producing {@link XLogger} instances.
 * 
 * <p>Contrary to {@link LoggerFactory#getLogger(String)} method of 
 * {@link LoggerFactory}, each call to {@link #getXLogger(String)} 
 * produces a new instance of <code>XLogger</code>. This should not matter 
 * because an <code>XLogger</code> instance does not have any state beyond that of 
 * the {@link org.slf4j.Logger Logger} instance it wraps.
 * 
 * @author Ralph Goers
 * @author Ceki G&uuml;lc&uuml;
 */
public class XLoggerFactory {

  /**
   * Get an XLogger instance by name.
   * 
   * @param name
   * @return XLogger instance 
   */
  public static XLogger getXLogger(String name) {
    return new XLogger(LoggerFactory.getLogger(name));
  }

  /**
   * Get a new XLogger instance by class. The returned XLogger
   * will be named after the class.
   * 
   * @param clazz
   * @return XLogger instance by name
   */
  public static XLogger getXLogger(Class<?> clazz) {
    return getXLogger(clazz.getName());
  }
}
