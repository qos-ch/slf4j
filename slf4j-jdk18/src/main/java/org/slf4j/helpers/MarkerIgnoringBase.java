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
package org.slf4j.helpers;

import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.Marker;


/**
 * This class serves as base for adapters or native implementations of logging systems 
 * lacking Marker support. In this implementation, methods taking marker data 
 * simply invoke the corresponding method without the Marker argument, discarding 
 * any marker data passed as argument.
 * 
 * @author Ceki Gulcu
 */
public abstract class MarkerIgnoringBase extends NamedLoggerBase implements Logger {

  private static final long serialVersionUID = 9044267456635152283L;

  @Override
  public boolean isTraceEnabled(Marker marker) {
    return isTraceEnabled();
  }
  
  @Override
  public void trace(Marker marker, String msg) {
    trace(msg);
  }
  
   public Consumer<String> trace(Marker marker){
        return msg-> trace(msg);
    }

  @Override
  public void trace(Marker marker, String format, Object arg) {
    trace(format, arg);
  }

  @Override
  public void trace(Marker marker, String format, Object arg1, Object arg2) {
    trace(format, arg1, arg2);
  }

  @Override
  public void trace(Marker marker, String format, Object... arguments) {
    trace(format, arguments);
  }

  @Override
  public void trace(Marker marker, String msg, Throwable t) {
    trace(msg, t);
  }
  
  @Override
  public boolean isDebugEnabled(Marker marker) {
    return isDebugEnabled();
  }

  @Override
  public void debug(Marker marker, String msg) {
    debug(msg);
  }

  @Override
  public void debug(Marker marker, String format, Object arg) {
    debug(format, arg);
  }

  @Override
  public void debug(Marker marker, String format, Object arg1, Object arg2) {
    debug(format, arg1, arg2);
  }

  @Override
  public void debug(Marker marker, String format, Object... arguments) {
    debug(format, arguments);
  }

  @Override
  public void debug(Marker marker, String msg, Throwable t) {
    debug(msg, t);
  }

  @Override
  public boolean isInfoEnabled(Marker marker) {
    return isInfoEnabled();
  }

  @Override
  public void info(Marker marker, String msg) {
    info(msg);
  }

  @Override
  public void info(Marker marker, String format, Object arg) {
    info(format, arg);
  }

  @Override
  public void info(Marker marker, String format, Object arg1, Object arg2) {
    info(format, arg1, arg2);
  }

  @Override
  public void info(Marker marker, String format, Object... arguments) {
    info(format, arguments);
  }

  @Override
  public void info(Marker marker, String msg, Throwable t) {
    info(msg, t);
  }

  @Override
  public boolean isWarnEnabled(Marker marker) {
    return isWarnEnabled();
  }

  @Override
  public void warn(Marker marker, String msg) {
    warn(msg);
  }

  @Override
  public void warn(Marker marker, String format, Object arg) {
    warn(format, arg);
  }

  @Override
  public void warn(Marker marker, String format, Object arg1, Object arg2) {
    warn(format, arg1, arg2);
  }

  @Override
  public void warn(Marker marker, String format, Object... arguments) {
    warn(format, arguments);
  }

  @Override
  public void warn(Marker marker, String msg, Throwable t) {
    warn(msg, t);
  }

 
  @Override
  public boolean isErrorEnabled(Marker marker) {
    return isErrorEnabled();
  }

  @Override
  public void error(Marker marker, String msg) {
    error(msg);
  }

  @Override
  public void error(Marker marker, String format, Object arg) {
    error(format, arg);
  }

  @Override
  public void error(Marker marker, String format, Object arg1, Object arg2) {
    error(format, arg1, arg2);
  }

  @Override
  public void error(Marker marker, String format, Object... arguments) {
    error(format, arguments);
  }

  @Override
  public void error(Marker marker, String msg, Throwable t) {
    error(msg, t);
  }

  @Override
  public String toString() {
	  return this.getClass().getName()+"("+getName()+")";
  }
  
}
