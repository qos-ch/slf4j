package org.slf4j.helpers;

/**
 * Holds normalized calling call parameters.
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


	
}
