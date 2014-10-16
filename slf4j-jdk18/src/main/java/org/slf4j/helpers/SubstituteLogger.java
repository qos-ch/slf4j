/**
 * Copyright (c) 2004-2011 QOS.ch All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.slf4j.helpers;

import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A logger implementation which logs via a delegate logger. By default, the
 * delegate is a {@link NOPLogger}. However, a different delegate can be set at
 * anytime.
 * <p/>
 * See also the <a
 * href="http://www.slf4j.org/codes.html#substituteLogger">relevant error
 * code</a> documentation.
 *
 * @author Chetan Mehrotra
 */
public class SubstituteLogger implements Logger {

    private final String name;

    private volatile Logger _delegate;

    public SubstituteLogger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return delegate().isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        delegate().trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        delegate().trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        delegate().trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        delegate().trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        delegate().trace(msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return delegate().isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        delegate().trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        delegate().trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        delegate().trace(marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... arguments) {
        delegate().trace(marker, format, arguments);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        delegate().trace(marker, msg, t);
    }

    @Override
    public void trace(Supplier<String> msg) {
        delegate().trace(msg);
    }

    @Override
    public void trace(Supplier<String> msg, Throwable t) {
        delegate().trace(msg, t);
    }

    @Override
    public void trace(Marker marker, Supplier<String> msg) {
        delegate().trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, Supplier<String> msg, Throwable t) {
        delegate().trace(marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return delegate().isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        delegate().debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        delegate().debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        delegate().debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        delegate().debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        delegate().debug(msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return delegate().isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        delegate().debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        delegate().debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        delegate().debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        delegate().debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        delegate().debug(marker, msg, t);
    }

    @Override
    public void debug(Supplier<String> msg) {
        delegate().debug(msg);
    }

    @Override
    public void debug(Supplier<String> msg, Throwable t) {
        delegate().debug(msg, t);
    }

    @Override
    public void debug(Marker marker, Supplier<String> msg) {
        delegate().debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, Supplier<String> msg, Throwable t) {
        delegate().debug(marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return delegate().isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        delegate().info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        delegate().info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        delegate().info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        delegate().info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        delegate().info(msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return delegate().isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        delegate().info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        delegate().info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        delegate().info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        delegate().info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        delegate().info(marker, msg, t);
    }

    @Override
    public void info(Supplier<String> msg) {
        delegate().info(msg);
    }

    @Override
    public void info(Supplier<String> msg, Throwable t) {
        delegate().info(msg, t);
    }

    @Override
    public void info(Marker marker, Supplier<String> msg) {
        delegate().info(marker, msg);
    }

    @Override
    public void info(Marker marker, Supplier<String> msg, Throwable t) {
        delegate().info(marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return delegate().isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        delegate().warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        delegate().warn(format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        delegate().warn(format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        delegate().warn(format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        delegate().warn(msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return delegate().isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        delegate().warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        delegate().warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        delegate().warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        delegate().warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        delegate().warn(marker, msg, t);
    }

    @Override
    public void warn(Supplier<String> msg) {
        delegate().warn(msg);
    }

    @Override
    public void warn(Supplier<String> msg, Throwable t) {
        delegate().warn(msg, t);
    }

    @Override
    public void warn(Marker marker, Supplier<String> msg) {
        delegate().warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, Supplier<String> msg, Throwable t) {
        delegate().warn(marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return delegate().isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        delegate().error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        delegate().error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        delegate().error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        delegate().error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        delegate().error(msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return delegate().isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        delegate().error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        delegate().error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        delegate().error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        delegate().error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        delegate().error(marker, msg, t);
    }

    @Override
    public void error(Supplier<String> msg) {
        delegate().error(msg);
    }

    @Override
    public void error(Supplier<String> msg, Throwable t) {
        delegate().error(msg, t);
    }

    @Override
    public void error(Marker marker, Supplier<String> msg) {
        delegate().error(marker, msg);
    }

    @Override
    public void error(Marker marker, Supplier<String> msg, Throwable t) {
        delegate().error(marker, msg, t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubstituteLogger that = (SubstituteLogger) o;

        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Return the delegate logger instance if set. Otherwise, return a
     * {@link NOPLogger} instance.
     */
    Logger delegate() {
        return _delegate != null ? _delegate : NOPLogger.NOP_LOGGER;
    }

    /**
     * Typically called after the {@link org.slf4j.LoggerFactory} initialization
     * phase is completed.
     *
     * @param delegate
     */
    public void setDelegate(Logger delegate) {
        this._delegate = delegate;
    }

}
