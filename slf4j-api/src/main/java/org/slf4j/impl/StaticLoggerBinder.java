/* 
 * Copyright (c) 2004-2007 QOS.ch
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
 */

package org.slf4j.impl;

import org.slf4j.ILoggerFactory;

/**
 * The binding of {@link LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 * 
 * This class is meant to provide a dummy StaticLoggerBinder to the slf4j-api module. 
 * Real implementations are found in  each SLF4J binding project, e.g. slf4j-nop, 
 * slf4j-log4j12 etc.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class StaticLoggerBinder {
 
  /**
   * The unique instance of this class.
   * 
   * @deprecated Please use the {@link #getSingleton()} method instead of
   *             accessing this field directly. In future versions, this field
   *             will become private.
   */
  public static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
  
  /**
   * Return the singleton of this class.
   * 
   * @return the StaticLoggerBinder singleton
   */
  public static final StaticLoggerBinder getSingleton() {
    return SINGLETON;
  }
  
  /**
   * Declare the version of the SLF4J API this implementation is compiled against. 
   * The value of this field is usually modified with each release. 
   */
  // to avoid constant folding by the compiler, this field must *not* be final
  public static String REQUESTED_API_VERSION = "1.5.10";  // !final
  
  private StaticLoggerBinder() {
    throw new UnsupportedOperationException("This code should have never made it into the jar");
  }

  public ILoggerFactory getLoggerFactory() {
    throw new UnsupportedOperationException("This code should never make it into the jar");
  }

  public String getLoggerFactoryClassStr() {
    throw new UnsupportedOperationException("This code should never make it into the jar");
  }
}
