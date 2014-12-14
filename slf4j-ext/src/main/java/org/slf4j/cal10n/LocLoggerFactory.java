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
package org.slf4j.cal10n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.cal10n.IMessageConveyor;

/**
 * 
 * This class is essentially a wrapper around an {@link LoggerFactory} producing
 * {@link LocLogger} instances.
 * 
 * <p>
 * Contrary to {@link LoggerFactory#getLogger(String)} method of
 * {@link LoggerFactory}, each call to {@link getLocLogger} produces a new
 * instance of {@link LocLogger}. This should not matter because a LocLogger
 * instance does have any state beyond that of the {@link Logger} in stance it
 * wraps and its message conveyor.
 * 
 * @author Ceki G&uuml;c&uuml;
 * 
 */
public class LocLoggerFactory {

  final IMessageConveyor imc;

  public LocLoggerFactory(IMessageConveyor imc) {
    this.imc = imc;
  }

  /**
   * Get an LocLogger instance by name.
   * 
   * @param name
   * @return LocLogger instance by name.
   */
  public LocLogger getLocLogger(String name) {
    return new LocLogger(LoggerFactory.getLogger(name), imc);
  }

  /**
   * Get a new LocLogger instance by class. The returned LocLogger will be named
   * after the class.
   * 
   * @param clazz
   * @return LocLogger instance by class
   */
  public LocLogger getLocLogger(Class<?> clazz) {
    return getLocLogger(clazz.getName());
  }
}
