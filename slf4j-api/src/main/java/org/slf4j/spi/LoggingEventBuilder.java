/**
 * Copyright (c) 2004-2021 QOS.ch
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
package org.slf4j.spi;

import java.util.function.Supplier;

import org.slf4j.Marker;

import org.slf4j.helpers.CheckReturnValue;

/**
 * This is the main interface in slf4j's fluent API for creating
 * {@link org.slf4j.event.LoggingEvent logging events}.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 2.0.0
 */
public interface LoggingEventBuilder {

    /**
     * Set the cause for the logging event being built.
     * @param cause a throwable
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder setCause(Throwable cause);

    /**
     * A {@link Marker marker} to the event being built.
     *
     * @param marker a Marker instance to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addMarker(Marker marker);

    /**
     * Add an argument to the event being built.
     * Synonymous with {@link #arg(Object)}.
     *
     * @param p an Object to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addArgument(Object p);

    /**
     * Add an argument to the event being built.
     * Synonymous with {@link #addArgument(Object)}.
     *
     * @param p an Object to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    @CheckReturnValue
    default LoggingEventBuilder arg(Object p) {
        return addArgument(p);
    }

    /**
     * <p>Add an argument supplier to the event being built. Synonymous with {@link #arg(Supplier)}.
     *  </p>
     * @param objectSupplier an Object supplier to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addArgument(Supplier<?> objectSupplier);


    /**
     * <p>Add an argument supplier to the event being built. Synonymous with {@link #addArgument(Supplier)}.
     * </p>
     *
     * @param objectSupplier an Object supplier to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since 2.1.0
     */
    @CheckReturnValue
    default LoggingEventBuilder arg(Supplier<?> objectSupplier) {
        return addArgument(objectSupplier);
    }

    /**
     * Add a value of type <code>boolean</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Boolean</code>. However, However, the NOP implementation, i.e.
     *  {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param b a value of type <code>boolean</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(boolean b) {
        return addArgument((Boolean) b);
    }

    /**
     * Add a value of type <code>char</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Character</code>. However, the NOP implementation, i.e.
     * {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param c a value of type <code>char</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(char c) {
        return addArgument((Character) c);
    }

    /**
     * Add a value of type <code>byte</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Byte</code>. However, the NOP implementation, i.e.
     *  {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param b a value of type <code>byte</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(byte b) {
        return addArgument((Byte) b);
    }

    /**
     * Add a value of type <code>short</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Short</code>. However, the NOP implementation, i.e.
     *  {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param s a value of type <code>short</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(short s) {
        return addArgument((Short) s);
    }

    /**
     * Add a value of type <code>int</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Integer</code>. However, the NOP implementation, i.e.
     * {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param i a value of type <code>int</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(int i) {
        return addArgument((Integer) i);
    }

    /**
     * Add a value of type <code>long</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Long</code>. However, the NOP implementation, i.e.
     *  {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param l a value of type <code>long</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(long l) {
        return addArgument((Long) l);
    }

    /**
     * Add a value of type <code>float</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Float</code>. However, the NOP implementation, i.e.
     * {@link NOPLoggingEventBuilder}, skips the cast.</p>
     *
     * @param f a value of type <code>float</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default public LoggingEventBuilder arg(float f) {
        return addArgument((Float) f);
    }

    /**
     * Add a value of type <code>double</code> to the event being built.
     *
     * <p>The default implementation simply casts to <code>Double</code>. However, the NOP implementation skips the cast.</p>
     *
     * @param d a value of type  <code>double</code> value to add.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     * @since  2.1.0
     */
    default LoggingEventBuilder arg(double d) {
        return arg((Double) d);
    }


    /**
     * Add a {@link org.slf4j.event.KeyValuePair key value pair} to the event being built.
     *
     * @param key the key of the key value pair.
     * @param value the value of the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addKeyValue(String key, Object value);

    /**
     * Add a {@link org.slf4j.event.KeyValuePair key value pair} to the event being built.
     *
     * @param key the key of the key value pair.
     * @param valueSupplier a supplier of a value for the key value pair.
     * @return a LoggingEventBuilder, usually <b>this</b>.
     */
    @CheckReturnValue
    LoggingEventBuilder addKeyValue(String key, Supplier<Object> valueSupplier);

    /**
     *  Sets the message of the logging event.
     *
     *  @since 2.0.0-beta0
     */
    @CheckReturnValue
    LoggingEventBuilder setMessage(String message);

    /**
     * Sets the message of the event via a message supplier.
     *
     * @param messageSupplier supplies a String to be used as the message for the event
     * @since 2.0.0-beta0
     */
    @CheckReturnValue
    LoggingEventBuilder setMessage(Supplier<String> messageSupplier);

    /**
     * After the logging event is built, performs actual logging. This method must be called
     * for logging to occur.
     *
     * If the call to {@link #log()}  is omitted, a {@link org.slf4j.event.LoggingEvent LoggingEvent}
     * will be built but no logging will occur.
     *
     * @since 2.0.0-beta0
     */
    void log();

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by {@link #log()};
     *
     * @param message the message to log
     */
    void log(String message);

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by {@link #addArgument(Object)}}
     * and then {@link #log()}
     *
     * @param message the message to log
     * @param arg an argument to be used with the message to log
     */
    void log(String message, Object arg);

    /**
     * Equivalent to calling {@link #setMessage(String)} followed by two calls to
     * {@link #addArgument(Object)} and then {@link #log()}
     *
     * @param message the message to log
     * @param arg0 first argument to be used with the message to log
     * @param arg1 second argument to be used with the message to log
     */
    void log(String message, Object arg0, Object arg1);


    /**
     * Equivalent to calling {@link #setMessage(String)} followed by zero or more calls to
     * {@link #addArgument(Object)} (depending on the size of args array) and then {@link #log()}
     *
     * @param message the message to log
     * @param args a list (actually an array) of arguments to be used with the message to log
     */
    void log(String message, Object... args);

    /**
     * Equivalent to calling {@link #setMessage(Supplier)} followed by {@link #log()}
     *
     * @param messageSupplier a Supplier returning a message of type String
     */
    void log(Supplier<String> messageSupplier);

}
