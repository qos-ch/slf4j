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
 * A logger capable of logging from org.slf4j.event.LoggingEvent implements this interface.
 *
 * <p>Please note that when the {@link #log(LoggingEvent)} method assumes that
 * the event was filtered beforehand and no further filtering needs to occur by the method itself.
 * <p>
 *
 * <p>Implementations of this interface <b>may</b> apply further filtering but they are not
 * required to do so. In other words, {@link #log(LoggingEvent)} method is free to assume that
 * the event was filtered beforehand and no further filtering needs to occur in the method itself.</p>
 *
 * See also https://jira.qos.ch/browse/SLF4J-575
 *
 * @author Ceki Gulcu
 * @since 2.0.0
 */
public interface LoggingEventAware {
    void log(LoggingEvent event);
}
