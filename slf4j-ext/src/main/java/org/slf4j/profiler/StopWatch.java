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
 * A very basic @{link TimeInstrument} which can be started and stopped 
 * once and only once.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
public class StopWatch implements TimeInstrument {

    private String name;
    private long startTime;
    private long stopTime;
    TimeInstrumentStatus status;

    public StopWatch(String name) {
        start(name);
    }

    StopWatch(StopWatch original) {
        this.name = original.name;
        this.startTime = original.startTime;
        this.stopTime = original.stopTime;
        this.status = original.status;
    }

    public void start(String name) {
        this.name = name;
        startTime = System.nanoTime();
        status = TimeInstrumentStatus.STARTED;
    }

    public String getName() {
        return name;
    }

    public TimeInstrument stop() {
        if (status == TimeInstrumentStatus.STOPPED) {
            return this;
        }
        return stop(System.nanoTime());
    }

    public StopWatch stop(long stopTime) {
        this.status = TimeInstrumentStatus.STOPPED;
        this.stopTime = stopTime;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("StopWatch [");
        buf.append(name);
        buf.append("] ");

        switch (status) {
        case STARTED:
            buf.append("STARTED");
            break;
        case STOPPED:
            buf.append("elapsed time: ");
            buf.append(Util.durationInDurationUnitsAsStr(elapsedTime(), DurationUnit.MICROSECOND));
            break;
        default:
            throw new IllegalStateException("Status " + status + " is not expected");
        }
        return buf.toString();
    }

    public final long elapsedTime() {
        if (status == TimeInstrumentStatus.STARTED) {
            return 0;
        } else {
            return stopTime - startTime;
        }
    }

    public TimeInstrumentStatus getStatus() {
        return status;
    }

    public void print() {
        System.out.println(toString());
    }

    public void log() {
        throw new UnsupportedOperationException("A stopwatch instance does not know how to log");
    }

}
