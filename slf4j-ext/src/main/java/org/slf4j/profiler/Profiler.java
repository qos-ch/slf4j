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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

// +  Profiler [BAS]
// |-- elapsed time            [doX]     0 milliseconds.
// |-- elapsed time        [doYYYYY]    56 milliseconds.
// |--+ Profiler Y
//    |-- elapsed time            [doZ]    21 milliseconds.
//    |-- elapsed time            [doZ]    21 milliseconds.
//    |-- Total elapsed time        [Y]    78 milliseconds.
// |-- elapsed time            [doZ]    21 milliseconds.
// |-- Total elapsed time      [BAS]    78 milliseconds.

/**
 * A poor man's profiler to measure the time elapsed performing some lengthy
 * task.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public class Profiler implements TimeInstrument {

    final static String PROFILER_MARKER_NAME = "PROFILER";

    final static int MIN_SW_NAME_LENGTH = 24;
    final static int MIN_SW_ELAPSED_TIME_NUMBER_LENGTH = 9;

    final String name;
    final StopWatch globalStopWatch;

    List<TimeInstrument> childTimeInstrumentList = new ArrayList<TimeInstrument>();

    // optional field
    ProfilerRegistry profilerRegistry;
    // optional field
    Logger logger;

    public Profiler(String name) {
        this.name = name;
        this.globalStopWatch = new StopWatch(name);
    }

    public String getName() {
        return name;
    }

    public ProfilerRegistry getProfilerRegistry() {
        return profilerRegistry;
    }

    public void registerWith(ProfilerRegistry profilerRegistry) {
        if (profilerRegistry == null) {
            return;
        }
        this.profilerRegistry = profilerRegistry;
        profilerRegistry.put(this);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Starts a child stop watch and stops any previously started time
     * instruments.
     */
    public void start(String name) {
        stopLastTimeInstrument();
        StopWatch childSW = new StopWatch(name);
        childTimeInstrumentList.add(childSW);
    }

    public Profiler startNested(String name) {
        stopLastTimeInstrument();
        Profiler nestedProfiler = new Profiler(name);
        nestedProfiler.registerWith(profilerRegistry);
        nestedProfiler.setLogger(logger);
        childTimeInstrumentList.add(nestedProfiler);
        return nestedProfiler;
    }

    TimeInstrument getLastTimeInstrument() {
        if (childTimeInstrumentList.size() > 0) {
            return childTimeInstrumentList.get(childTimeInstrumentList.size() - 1);
        } else {
            return null;
        }
    }

    void stopLastTimeInstrument() {
        TimeInstrument last = getLastTimeInstrument();
        if (last != null) {
            last.stop();
        }
    }

    // void stopNestedProfilers() {
    // for (Object child : childTimeInstrumentList) {
    // if (child instanceof Profiler)
    // ((Profiler) child).stop();
    // }
    // }

    public long elapsedTime() {
        return globalStopWatch.elapsedTime();
    }

    public TimeInstrument stop() {
        stopLastTimeInstrument();
        globalStopWatch.stop();
        return this;
    }

    public TimeInstrumentStatus getStatus() {
        return globalStopWatch.status;
    }

    /**
     * This method is used in tests.
     */
    void sanityCheck() throws IllegalStateException {
        if (getStatus() != TimeInstrumentStatus.STOPPED) {
            throw new IllegalStateException("time instrument [" + getName() + " is not stopped");
        }

        long totalElapsed = globalStopWatch.elapsedTime();
        long childTotal = 0;

        for (TimeInstrument ti : childTimeInstrumentList) {
            childTotal += ti.elapsedTime();
            if (ti.getStatus() != TimeInstrumentStatus.STOPPED) {
                throw new IllegalStateException("time instrument [" + ti.getName() + " is not stopped");
            }
            if (ti instanceof Profiler) {
                Profiler nestedProfiler = (Profiler) ti;
                nestedProfiler.sanityCheck();
            }
        }
        if (totalElapsed < childTotal) {
            throw new IllegalStateException("children have a higher accumulated elapsed time");
        }
    }

    static String TOP_PROFILER_FIRST_PREFIX = "+";
    static String NESTED_PROFILER_FIRST_PREFIX = "|---+";
    static String TOTAL_ELAPSED = " Total        ";
    static String SUBTOTAL_ELAPSED = " Subtotal     ";
    static String ELAPSED_TIME = " elapsed time ";

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        DurationUnit du = Util.selectDurationUnitForDisplay(globalStopWatch);
        return buildProfilerString(du, TOP_PROFILER_FIRST_PREFIX, TOTAL_ELAPSED, "");
    }

    public void log() {
        Marker profilerMarker = MarkerFactory.getMarker(PROFILER_MARKER_NAME);
        if (logger == null) {
            throw new NullPointerException("If you invoke the log() method, then you must associate a logger with this profiler.");
        }
        if (logger.isDebugEnabled(profilerMarker)) {
            DurationUnit du = Util.selectDurationUnitForDisplay(globalStopWatch);
            String r = buildProfilerString(du, TOP_PROFILER_FIRST_PREFIX, TOTAL_ELAPSED, "");
            logger.debug(profilerMarker, SpacePadder.LINE_SEP + r);
        }
    }

    /**
     * Return a copy of the child instrument list for this Profiler instance.
     * 
     * @return a copy of this instance's child time instrument list
     * @since 1.5.9
     */
    public List<TimeInstrument> getCopyOfChildTimeInstruments() {
        List<TimeInstrument> copy = new ArrayList<TimeInstrument>(childTimeInstrumentList);
        return copy;
    }

    /**
     * Return a copy of the global stopwath of this Profiler instance.
     * 
     * @return a copy of this instance's global stop watch
     * @since 1.5.9
     */
    public StopWatch getCopyOfGlobalStopWatch() {
        StopWatch copy = new StopWatch(globalStopWatch);
        return copy;
    }

    private String buildProfilerString(DurationUnit du, String firstPrefix, String label, String indentation) {
        StringBuilder buf = new StringBuilder();

        buf.append(firstPrefix);
        buf.append(" Profiler [");
        buf.append(name);
        buf.append("]");
        buf.append(SpacePadder.LINE_SEP);
        for (TimeInstrument child : childTimeInstrumentList) {
            if (child instanceof StopWatch) {
                buildStopWatchString(buf, du, ELAPSED_TIME, indentation, (StopWatch) child);
            } else if (child instanceof Profiler) {
                Profiler profiler = (Profiler) child;
                String subString = profiler.buildProfilerString(du, NESTED_PROFILER_FIRST_PREFIX, SUBTOTAL_ELAPSED, indentation + "    ");
                buf.append(subString);
                buildStopWatchString(buf, du, ELAPSED_TIME, indentation, profiler.globalStopWatch);
            }
        }
        buildStopWatchString(buf, du, label, indentation, globalStopWatch);
        return buf.toString();
    }

    private static void buildStopWatchString(StringBuilder buf, DurationUnit du, String prefix, String indentation, StopWatch sw) {

        buf.append(indentation);
        buf.append("|--");
        buf.append(prefix);
        SpacePadder.leftPad(buf, "[" + sw.getName() + "]", MIN_SW_NAME_LENGTH);
        buf.append(" ");
        String timeStr = Util.durationInDurationUnitsAsStr(sw.elapsedTime(), du);
        SpacePadder.leftPad(buf, timeStr, MIN_SW_ELAPSED_TIME_NUMBER_LENGTH);
        buf.append(" ");
        Util.appendDurationUnitAsStr(buf, du);
        buf.append(SpacePadder.LINE_SEP);
    }
}
