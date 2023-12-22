package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * <p>A no-operation implementation of {@link LoggingEventBuilder}.</p>
 * <p></p>
 * <p>As the name indicates, the methods in this class do nothing. In case a return value is expected, a singleton,
 * i.e. the unique instance of this class, is returned.
 * </p
 * <p></p>
 * <p>Note that the default implementations of {@link Logger#atTrace()}, {@link Logger#atDebug()} , {@link Logger#atInfo()},
 * {@link Logger#atWarn()}  and {@link Logger#atError()}, return an instance of {@link NOPLoggingEventBuilder}
 * when the relevant level is disabled for current logger. This is the core optimization in the SLF4J fluent API.</p>
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 *
 */
public class NOPLoggingEventBuilder implements LoggingEventBuilder {

    static final NOPLoggingEventBuilder SINGLETON = new NOPLoggingEventBuilder();

    private NOPLoggingEventBuilder() {
    }

    /**
     * <p>Returns the singleton instance of this class.
     * Used by {@link org.slf4j.Logger#makeLoggingEventBuilder(Level) makeLoggingEventBuilder(Level)}.</p>
     *
     * @return the singleton instance of this class
     */
    public static LoggingEventBuilder singleton() {
        return SINGLETON;
    }

    /**
     * <p>NOP implementation that does nothing.</p>
     * 
     * @param marker a Marker instance to add.
     * @return
     */
    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing.</p>
     *
     * @param p 
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder addArgument(Object p) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips calling get() call on the object supplier.</p>
     *
     * @param objectSupplier
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder addArgument(Supplier<?> objectSupplier) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips calling get() call on the object supplier.</p>
     *
     * @param objectSupplier 
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(Supplier<?> objectSupplier) {
        return this;
    }

    
    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param b a value of type <code>boolean</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(boolean b) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param c a value of type <code>char</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(char c) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param b a value of type <code>byte</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(byte b) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param s a value of type <code>short</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(short s) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param i a value of type <code>int</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(int i) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param l a value of type <code>long</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(long l) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param f a value of type <code>float</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(float f) {
        return this;
    }

    /**
     * <p>NOP implementation that does nothing and thus skips the type cast.</p>
     *
     * @param d a value of type <code>float</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @Override
    public LoggingEventBuilder arg(double d) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param value the value of the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @Override
    public LoggingEventBuilder addKeyValue(String key, Object value) {
        return this;
    }

    /**
     * NOP implementation that doesnothing.
     *
     * @param key the key of the key value pair.
     * @param value the value of the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @Override
    public LoggingEventBuilder kv(String key, Object value) {
        return this;
    }

    /**
     * NOP implementation that doesnothing.
     *
     * @param key the key of the key value pair.
     * @param value a supplier of a value for the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @Override
    public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
        return this;
    }

    /**
     * NOP implementation that doesnothing.
     *
     * @param key the key of the key value pair.
     * @param value a supplier of a value for the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @Override
    public LoggingEventBuilder kv(String key, Supplier<Object> value) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param b the value of type <code>boolean</code> of the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @Override
    public LoggingEventBuilder kv(String key, boolean b) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param c the value of type <code>char</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, char c) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param b the value of type <code>byte</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, byte b) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param s the value of type <code>short</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, short s) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param i the value of type <code>int</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, int i) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param l the value of type <code>long</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, long l) {
        return this;
    }


    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param f the value of type <code>float</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, float f) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param key the key of the key value pair.
     * @param d the value of type <code>double</code> of the key value pair.
     * @return
     */
    @Override
    public LoggingEventBuilder kv(String key, double d) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param cause a throwable
     * @return
     */
    @Override
    public LoggingEventBuilder setCause(Throwable cause) {
        return this;
    }

    /**
     *
     */
    @Override
    public void log() {
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param message the message of the event
     * @return
     */
    @Override
    public LoggingEventBuilder setMessage(String message) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param messageSupplier supplies a String to be used as the message for the event
     * @return
     */
    @Override
    public LoggingEventBuilder setMessage(Supplier<String> messageSupplier) {
        return this;
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param message the message to log
     */
    @Override
    public void log(String message) {
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param messageSupplier a Supplier returning a message of type String
     */
    @Override
    public void log(Supplier<String> messageSupplier) {
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param message the message to log
     * @param arg an argument to be used with the message to log
     */
    @Override
    public void log(String message, Object arg) {
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param message the message to log
     * @param arg0 first argument to be used with the message to log
     * @param arg1 second argument to be used with the message to log
     */
    @Override
    public void log(String message, Object arg0, Object arg1) {
    }

    /**
     * NOP implementation that does nothing.
     *
     * @param message the message to log
     * @param args a list (actually an array) of arguments to be used with the message to log
     */
    @Override
    public void log(String message, Object... args) {
    }
}
