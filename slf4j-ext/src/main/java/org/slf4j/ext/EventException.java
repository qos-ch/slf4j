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
