package org.slf4j.osgi.logservice.impl.equinox;

import org.eclipse.equinox.log.ExtendedLogService;
import org.eclipse.equinox.log.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.slf4j.osgi.logservice.impl.LogServiceImpl;

/**
 *
 * 
 * <p>Implementation make use of base SLF4J's LogService implementation
 * {@link LogServiceImpl}. Calls that are identical are delegated.</p> 
 * 
 * 
 * @author Libor Jelinek, ljelinek@virtage.com, http://www.virtage.com
 */
public class ExtendedLogServiceImpl extends LogServiceImpl implements ExtendedLogService {
	
	//--------------------------------------------------------------------------
	// Class fields - public
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Class fields - non-public
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Instance fields - public
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Instance fields - non-public
	//--------------------------------------------------------------------------
			
	Bundle bundle;
	
	//--------------------------------------------------------------------------
	// Instance fields - bean properties
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Constructors, static initializers, factories
	//--------------------------------------------------------------------------
	
	public ExtendedLogServiceImpl(Bundle bundle) {
		super(bundle);
		this.bundle = bundle;
	}
	
	public ExtendedLogServiceImpl(String loggerName) {
		super(loggerName);
	}
	
	//--------------------------------------------------------------------------
	// Public API - class (static)
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Public API - instance
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Overrides and implementations
	//--------------------------------------------------------------------------
	// Equinox ExtendedLogService methods	

	@Override
	public void log(Object context, int level, String message) {
		super.log(LOG_DEBUG, "Discarding Equinox context object of " +
				context.getClass() + " (contexts unsupported " +
				"by osgi-over-slf4j bridge).");
		super.log(level, message);
	}

	@Override
	public void log(Object context, int level, String message,
			Throwable exception) {
		super.log(LOG_DEBUG, "Discarding Equinox context object of " +
				context.getClass() + " (contexts unsupported " +
				"by osgi-over-slf4j bridge).");
		super.log(level, message, exception);
	}

	@Override
	public boolean isLoggable(int level) {
		switch (level) {
			case LOG_ERROR:
				return super.getDelegate().isErrorEnabled();
			
			case LOG_WARNING:
				return super.getDelegate().isWarnEnabled();
				
			case LOG_INFO:
				return super.getDelegate().isInfoEnabled();
				
			case LOG_DEBUG:
				return super.getDelegate().isDebugEnabled();
				
			default:
				throw new IllegalArgumentException("Invalid severity value (" + level + ").");
		}
	}

	@Override
	public String getName() {
		return super.getDelegate().getName();
	}

	@Override
	public Logger getLogger(String loggerName) {
		if (loggerName == null) { 
			return this;
		}
		
		return new ExtendedLogServiceImpl(loggerName);
	}

	@Override
	public Logger getLogger(Bundle bundle, String loggerName) {
		if ((bundle == null) || (this.bundle == bundle))
			return this;
		
		return new ExtendedLogServiceImpl(bundle);
	}
	
	//--------------------------------------------------------------------------
	// Consider toString(), equals(), hashCode()
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Getters/setters of properties
	//--------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------
	// Helper methods (mostly private)
	//--------------------------------------------------------------------------

}
