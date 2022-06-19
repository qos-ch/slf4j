package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Marker;

/**
 * A no-operation implementation of {@link FluentLogApiStub}.
 * As the name indicates, this implementation does nothing or alternatively returns
 * a singleton, i.e. the unique instance of this class.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 *
 */
public class NopFluentApiStub implements FluentLogApiStub {

    static final NopFluentApiStub SINGLETON = new NopFluentApiStub();

    
    private NopFluentApiStub() {
    }
    
    public static NopFluentApiStub singleton() {
        return SINGLETON;
    }

    @Override
    public NopFluentApiStub addMarker(Marker marker) {
        return singleton();
    }

    @Override
    public NopFluentApiStub addArgument(Object p) {
        return singleton();
    }

    @Override
    public NopFluentApiStub addArgument(Supplier<?> objectSupplier) {
        return singleton();
    }

    @Override
    public NopFluentApiStub addKeyValue(String key, Object value) {
        return singleton();
    }

    @Override
    public NopFluentApiStub addKeyValue(String key, Supplier<Object> value) {
        return singleton();
    }

    @Override
    public NopFluentApiStub setCause(Throwable cause) {
        return singleton();
    }

    @Override
    public void log(String message) {

    }

    @Override
    public void log(Supplier<String> messageSupplier) {
    }

    @Override
    public void log(String message, Object arg) {
    }

    @Override
    public void log(String message, Object arg0, Object arg1) {
    }

    @Override
    public void log(String message, Object... args) {

    }

}
