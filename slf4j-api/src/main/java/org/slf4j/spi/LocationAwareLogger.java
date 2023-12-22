/**
 * Copyright (c) 2004-2011 QOS.ch
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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * An <b>optional</b> interface helping integration with logging systems capable of 
 * extracting location information. This interface is mainly used by SLF4J bridges 
 * such as jcl-over-slf4j, jul-to-slf4j and log4j-over-slf4j or {@link Logger} wrappers
 * which need to provide hints so that the underlying logging system can extract
 * the correct location information (method name, line number).
 *
 *  <p></p>
 *
 * <p>In response to <a href="https://jira.qos.ch/browse/SLF4J-118">SLF4J-118</a>,
 * the {@link #log(Marker, String, int, String, Object[], Throwable) log()}  method was modified to accept
 * an additional <code>Object[]</code> parameter.
 * This Object[] represents arguments of the log request. Due to this modification, slf4j-api
 * version 1.6.x no longer work with bindings shipping with SLF4J 1.5.x, instead
 * bindings shipping with 1.6.x or later are required. See also
 * <a href="https://github.com/qos-ch/slf4j/commit/6dd2bd58cff">commit 6dd2</a> dated
 * April 9th, 2010.
 * </p>
 *
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.3
 */
public interface LocationAwareLogger extends Logger {

    // these constants should be in EventConstants. However, in order to preserve binary backward compatibility
    // we keep these constants here. {@link EventConstants} redefines these constants using the values  below.
    final public int TRACE_INT = 00;
    final public int DEBUG_INT = 10;
    final public int INFO_INT = 20;
    final public int WARN_INT = 30;
    final public int ERROR_INT = 40;

    /**
     * Printing method with support for location information. 
     * 
     * @param marker The marker to be used for this event, may be null.
     * 
     * @param fqcn The fully qualified class name of the <b>logger instance</b>,
     * typically the logger class, logger bridge or a logger wrapper.
     * 
     * @param level One of the level integers defined in this interface
     * 
     * @param message The message for the log event
     * @param t Throwable associated with the log event, may be null.
     */
    public void log(Marker marker, String fqcn, int level, String message, Object[] argArray, Throwable t);

}
