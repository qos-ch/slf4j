package org.slf4j.helpers;

import org.slf4j.event.LoggingEvent;

/**
 * Holds normalized call parameters.
 * 
 * Includes utility methods such as {@link #normalize(String, Object[], Throwable)} to help the normalization of parameters.
 * 
 * @author ceki
 * @since 2.0
 */
public class NormalizedParameters {

    final String message;
    final Object[] arguments;
    final Throwable throwable;

    public NormalizedParameters(String message, Object[] arguments, Throwable throwable) {
        this.message = message;
        this.arguments = arguments;
        this.throwable = throwable;
    }

    public NormalizedParameters(String message, Object[] arguments) {
        this(message, arguments, null);
    }

    public String getMessage() {
        return message;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Helper method to determine if an {@link Object} array contains a
     * {@link Throwable} as last element
     *
     * @param argArray The arguments off which we want to know if it contains a
     *                 {@link Throwable} as last element
     * @return if the last {@link Object} in argArray is a {@link Throwable} this
     *         method will return it, otherwise it returns null
     */
    public static Throwable getThrowableCandidate(final Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            return null;
        }

        final Object lastEntry = argArray[argArray.length - 1];
        if (lastEntry instanceof Throwable) {
            return (Throwable) lastEntry;
        }

        return null;
    }

    /**
     * Helper method to get all but the last element of an array
     *
     * @param argArray The arguments from which we want to remove the last element
     *
     * @return a copy of the array without the last element
     */
    public static Object[] trimmedCopy(final Object[] argArray) {
        if (argArray == null || argArray.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }

        final int trimmedLen = argArray.length - 1;

        Object[] trimmed = new Object[trimmedLen];

        if (trimmedLen > 0) {
            System.arraycopy(argArray, 0, trimmed, 0, trimmedLen);
        }

        return trimmed;
    }

    /**
     * This method serves to normalize logging call invocation parameters.
     * 
     * More specifically, if a throwable argument is not supplied directly, it
     * attempts to extract it from the argument array.
     */
    public static NormalizedParameters normalize(String msg, Object[] arguments, Throwable t) {

        if (t != null) {
            return new NormalizedParameters(msg, arguments, t);
        }

        if (arguments == null || arguments.length == 0) {
            return new NormalizedParameters(msg, arguments, t);
        }

        Throwable throwableCandidate = NormalizedParameters.getThrowableCandidate(arguments);
        if (throwableCandidate != null) {
            Object[] trimmedArguments = MessageFormatter.trimmedCopy(arguments);
            return new NormalizedParameters(msg, trimmedArguments, throwableCandidate);
        } else {
            return new NormalizedParameters(msg, arguments);
        }

    }

    public static NormalizedParameters normalize(LoggingEvent event) {
        return normalize(event.getMessage(), event.getArgumentArray(), event.getThrowable());
    }

}
