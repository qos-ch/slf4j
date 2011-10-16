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
package org.apache.commons.logging;

/**
 * <p>
 * An exception that is thrown only if a suitable <code>LogFactory</code> or
 * <code>Log</code> instance cannot be created by the corresponding factory
 * methods.
 * </p>
 * 
 * <p>
 * In this version of JCL, this exception will never be thrown in practice.
 * However, it is included here to ensure total compile time and run time
 * compatibility with the original JCL 1.0.4.
 * 
 * @author Craig R. McClanahan
 */

public class LogConfigurationException extends RuntimeException {

  private static final long serialVersionUID = 8486587136871052495L;

  /**
   * Construct a new exception with <code>null</code> as its detail message.
   */
  public LogConfigurationException() {
    super();
  }

  /**
   * Construct a new exception with the specified detail message.
   * 
   * @param message
   *          The detail message
   */
  public LogConfigurationException(String message) {
    super(message);
  }

  /**
   * Construct a new exception with the specified cause and a derived detail
   * message.
   * 
   * @param cause
   *          The underlying cause
   */
  public LogConfigurationException(Throwable cause) {

    this((cause == null) ? null : cause.toString(), cause);

  }

  /**
   * Construct a new exception with the specified detail message and cause.
   * 
   * @param message
   *          The detail message
   * @param cause
   *          The underlying cause
   */
  public LogConfigurationException(String message, Throwable cause) {
    super(message + " (Caused by " + cause + ")");
    this.cause = cause; // Two-argument version requires JDK 1.4 or later
  }

  /**
   * The underlying cause of this exception.
   */
  protected Throwable cause = null;

  /**
   * Return the underlying cause of this exception (if any).
   */
  public Throwable getCause() {
    return (this.cause);
  }

}
