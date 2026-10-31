/*
 * Copyright (C) 2004-2026, QOS.ch (Switzerland)
 * All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *  without limitation  the rights to  use, copy, modify,  merge, publish,
 *  distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *  permit persons to whom the Software  is furnished to do so, subject to
 *  the following conditions:
 *
 *  The  above  copyright  notice  and  this permission  notice  shall  be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 *  EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 *  MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.slf4j.event;

import java.util.List;

import org.slf4j.Marker;

/**
 * The minimal interface sufficient for the restitution of data passed
 * by the user to the SLF4J API.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @since 1.7.15
 */
public interface LoggingEvent {

    Level getLevel();

    String getLoggerName();

    String getMessage();

    List<Object> getArguments();

    Object[] getArgumentArray();

    /**
     * List of markers in the event, might be null.
     * @return markers in the event, might be null.
     */
    List<Marker> getMarkers();

    List<KeyValuePair> getKeyValuePairs();

    Throwable getThrowable();

    long getTimeStamp();

    String getThreadName();
 
    /**
     * Returns the presumed caller boundary provided by the logging library (not the user of the library). 
     * Null by default.
     *  
     * @return presumed caller, null by default.
     */
    default String getCallerBoundary() {
        return null;
    }


    /**
     * Returns the caller data associated with this event without triggering the computation of caller data.
     *
     * <p>>Note that calling this event should return existing data without triggering the
     * computation of caller data. Note that this is a departure from the behavior of
     * event classes in logging-backends where getCallerData() which usually triggers the computation
     * of caller data.
     * </p>
     *
     * @return the caller data associated with this event without triggering the computation of caller data, null by default.
     * @since 3.0.0
     */
    default StackTraceElement[] getCallerData() {
        return null;
    }

}
