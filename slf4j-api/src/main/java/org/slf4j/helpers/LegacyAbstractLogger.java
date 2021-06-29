package org.slf4j.helpers;

import org.slf4j.Marker;

/**
 * Provides minimal default implementations for {@link #isTraceEnabled(Marker)}, {@link #isDebugEnabled(Marker)} and other similar methods.
 * 
 * @since 2.0
 */
abstract public class LegacyAbstractLogger extends AbstractLogger {

    private static final long serialVersionUID = -7041884104854048950L;

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }

}
