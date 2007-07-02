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

package org.slf4j;

import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMarkerBinder;

/**
 * MDC class serves as an abstraction for the underlying logging system's 
 * MDC implementation. At this time, only log4j and logback offer MDC
 * functionality. For other systems, this class defaults to nop (empty)
 * implemnetation.
 * 
 * <p>
 * Please note that all methods in this class are static.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class MDC {
  static IMarkerFactory markerFactory;

  private MDC() {
  }

  static {
    try {
      markerFactory = StaticMarkerBinder.SINGLETON.getMarkerFactory();
    } catch (Exception e) {
      // we should never get here
      Util.reportFailure("Could not instantiate instance of class ["
          + StaticMarkerBinder.SINGLETON.getMarkerFactoryClassStr() + "]", e);
    }
  }

  /**
   * Return a Marker instance as specified by the name parameter using the
   * previously bound {@link IMarkerFactory}instance.
   * 
   * @param name
   *          The name of the {@link Marker} object to return.
   * @return marker
   */
  public static Marker getMarker(String name) {
    return markerFactory.getMarker(name);
  }

  /**
   * Return the {@link IMarkerFactory}instance in use.
   * 
   * <p>The IMarkerFactory instance is usually bound with this class at 
   * compile time.
   * 
   * @return the IMarkerFactory instance in use
   */
  public static IMarkerFactory getIMarkerFactory() {
    return markerFactory;
  }
}