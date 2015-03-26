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
package org.slf4j.profiler;

/**
 * This interface sets the methods that must be implemented by 
 * {@link Profiler} and {@link  StopWatch} classes. It settles the 
 * general feel of the profiler package.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
public interface TimeInstrument {

    /**
     * All time instruments are named entities.
     * @return the name of this instrument
     */
    String getName();

    TimeInstrumentStatus getStatus();

    /**
     * Start tis time instrument.
     * 
     * @param name
     */
    void start(String name);

    /**
     * Stop this time instrument.
     * 
     * @return this
     */
    TimeInstrument stop();

    /**
     * Time elapsed between start and stop, in nanoseconds.
     * 
     * @return time elapsed in nanoseconds
     */
    long elapsedTime();

    /**
     * Print information about this time instrument on the console.
     */
    void print();

    /**
     * If the time instrument has an associated logger, then log information about 
     * this time instrument. Note that {@link StopWatch} instances cannot log while {@link Profiler}
     * instances can.
     */
    void log();
}
