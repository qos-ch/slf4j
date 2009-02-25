package org.slf4j.ext;

/**
 * 
 */
public class EventException extends RuntimeException
{
    public EventException()
    {
        super();
    }

    public EventException(String exceptionMessage)
    {
        super(exceptionMessage);
    }

    public EventException(Throwable originalException)
    {
        super(originalException);
    }

    public EventException(String exceptionMessage, Throwable originalException)
    {
        super(exceptionMessage, originalException);
    }
}
