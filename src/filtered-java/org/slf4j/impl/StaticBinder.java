/*
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */

package org.slf4j.impl;

import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.slf4j.spi.MarkerFactoryBinder;

/**
 * 
 * The binding of {@link LoggerFactory} class with an actual instance of 
 * {@link ILoggerFactory} is performed using information returned by this class. 
 * 
 * This class also contains the information for binding {@link MarkerFactory}
 * with the appropriate {@link IMarkerFactory} instance.
 * 
 * @author <a href="http://www.qos.ch/log4j/">Ceki G&uuml;lc&uuml;</a>
 */
public class StaticBinder implements LoggerFactoryBinder, MarkerFactoryBinder {

  /**
   * The unique instance of this class.
   */
  public static final StaticBinder SINGLETON = new StaticBinder();
  
  private StaticBinder() {
  }
  
  // Note: @IMPL@ gets substituted at build time by an appropriate Ant task
  static final String loggerFactoryClassStr ="org.slf4j.impl.@IMPL@LoggerFactory";
  
  public ILoggerFactory getLoggerFactory() {
    // Note: @IMPL@ gets substituted at build time by an appropriate Ant task
    return new org.slf4j.impl.@IMPL@LoggerFactory();
  }
  
  public String getLoggerFactoryClassStr() {
    return loggerFactoryClassStr;
  }   
  
  /**
   * Currently this method always returns an instance of 
   * {@link BasicMarkerFactory}.
   */
  public IMarkerFactory getMarkerFactory() {
    return new BasicMarkerFactory();
  }
  
  /**
   * Currrently, this method returns the class name of
   * {@link BasicMarkerFactory}.
   */
  public String getMarkerFactoryClassStr() {
    return BasicMarkerFactory.class.getName();
  }
  
  
}
