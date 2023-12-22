package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * <p>A no-operation implementation of {@link LoggingEventBuilder}.</p>
 *
 * <p>As the name indicates, the methods in this class do nothing, except when a return value is expected
 * in which case a singleton, i.e. the unique instance of this class is returned.
 * </p
 *
 * <p>The default implementations of {@link Logger#atTrace()}, {@link Logger#atDebug()} , {@link Logger#atInfo()},
 * {@link Logger#atWarn()}  and {@link Logger#atError()}, return an instance of {@link NOPLoggingEventBuilder}
 * when the relevant level is disabled for current logger. This is the core optimization in the fluent API.</p>
 *
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

    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        return singleton();
    }

    @Override
    public LoggingEventBuilder addArgument(Object p) {
        return singleton();
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> objectSupplier) {
        return singleton();
    }

    /**
     * Add a value of type <code>boolean</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Boolean</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param b a value of type <code>boolean</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(boolean b) {
        return singleton();
    }

    /**
     * Add a value of type <code>char</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Character</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param c a value of type <code>char</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(char c) {
        return singleton();
    }

    /**
     * Add a value of type <code>byte</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Byte</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param b a value of type <code>byte</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(byte b) {
        return singleton();
    }

    /**
     * Add a value of type <code>short</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Short</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param s a value of type <code>short</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(short s) {
        return singleton();
    }

    /**
     * Add a value of type <code>int</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Integer</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param i a value of type <code>int</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(int i) {
        return singleton();
    }

    /**
     * Add a value of type <code>long</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Long</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param l a value of type <code>long</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(long l) {
        return singleton();
    }

    /**
     * Add a value of type <code>float</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Float</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param f a value of type <code>float</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    public LoggingEventBuilder arg(float f) {
        return singleton();
    }


    @Override
    public LoggingEventBuilder addKeyValue(String key, Object value) {
        return singleton();
    }

    @Override
    public LoggingEventBuilder addKeyValue(String key, Supplier<Object> value) {
        return singleton();
    }

    @Override
    public LoggingEventBuilder setCause(Throwable cause) {
        return singleton();
    }

    @Override
    public void log() {
    }

    @Override
    public LoggingEventBuilder setMessage(String message) {
        return this;
    }
    @Override
    public LoggingEventBuilder setMessage(Supplier<String> messageSupplier) {
        return this;
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
