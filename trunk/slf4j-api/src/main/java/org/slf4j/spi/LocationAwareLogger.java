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

package org.slf4j.spi;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * An <b>optional</b> interface helping integration with logging systems capable of 
 * extracting location information. This interface is mainly used by SLF4J bridges 
 * such as jcl104-over-slf4j which need to provide hints so that the underlying logging
 * system can extract the correct locatin information (method name, line number, etc.).
 * 
 * 
 * @author Ceki Gulcu
 * @since 1.3
 */
public interface LocationAwareLogger extends Logger {

  final public int TRACE_INT = 00;
  final public int DEBUG_INT = 10;
  final public int INFO_INT = 20;
  final public int WARN_INT = 30;
  final public int ERROR_INT = 40;
  
  
  /**
   * Printing method which support for location information. 
   * 
   * @param marker
   * @param fqcn The fully qualified class name of the <b>caller</b>
   * @param level
   * @param message
   * @param t
   */  
  public void log(Marker marker, String fqcn, int level, String message, Throwable t);
  
}
