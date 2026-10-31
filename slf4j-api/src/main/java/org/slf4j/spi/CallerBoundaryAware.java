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

package org.slf4j.spi;

import org.slf4j.event.LoggingEvent;

/**
 * Additional interface to {@link LoggingEventBuilder} and 
 * {@link org.slf4j.event.LoggingEvent LoggingEvent}.
 * 
 * Implementations of {@link LoggingEventBuilder} and  {@link LoggingEvent} may optionally
 * implement {@link CallerBoundaryAware} in order to support caller info extraction.
 *
 * This interface is intended for use by logging backends or logging bridges. 
 * 
 * @author Ceki Gulcu
 *
 */
public interface CallerBoundaryAware {

    /**
     * Add a fqcn (fully qualified class name) to this event, presumed to be the caller boundary.
     * 
     * @param fqcn
     */
    void setCallerBoundary(String fqcn);
}
