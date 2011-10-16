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

/**
 * Exception used to identify issues related to an event that is being logged.
 */
public class EventException extends RuntimeException {

  private static final long serialVersionUID = -22873966112391992L;

  /**
   * Default constructor.
   */
  public EventException() {
    super();
  }

  /**
   * Constructor that allows an exception message.
   * @param exceptionMessage The exception message.
   */
  public EventException(String exceptionMessage) {
    super(exceptionMessage);
  }

  /**
   * Constructor that chains another Exception or Error.
   * @param originalException The original exception.
   */
  public EventException(Throwable originalException) {
    super(originalException);
  }

  /**
   * Constructor that chains another Exception or Error and also allows a message
   * to be specified.
   * @param exceptionMessage The exception message.
   * @param originalException The original excepton.
   */
  public EventException(String exceptionMessage, Throwable originalException) {
    super(exceptionMessage, originalException);
  }
}
