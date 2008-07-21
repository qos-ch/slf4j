/*
 * Copyright (c) 2004-2008 QOS.ch
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
 */
package org.slf4j.profiler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

// + Profiler [BAS]
// |-- elapsed time [doX] 0 milliseconds.
// |-- elapsed time [doYYYYY] 56 milliseconds.
// |--+ Profiler Y
// |-- elapsed time [doZ] 21 milliseconds.
// |-- elapsed time [doZ] 21 milliseconds.
// |-- Total elapsed time [Y] 78 milliseconds.
// |-- elapsed time [doZ] 21 milliseconds.
// |-- Total elapsed time [BAS] 78 milliseconds.

// + Profiler [TOP]
// |--+ Profiler [IIII]
// |-- elapsed time [A] 0.006 milliseconds.
// |-- elapsed time [B] 75.777 milliseconds.
// |-- elapsed time [VVVVVV] 161.589 milliseconds.
// |-- Total elapsed time [IIII] 240.580 milliseconds.
// |--+ Profiler [RRRRRRRRR]
// |-- elapsed time [R0] 9.390 milliseconds.
// |-- elapsed time [R1] 6.555 milliseconds.
// |-- elapsed time [R2] 5.995 milliseconds.
// |-- elapsed time [R3] 115.502 milliseconds.
// |-- elapsed time [R4] 0.064 milliseconds.
// |-- Total elapsed time [R] 138.340 milliseconds.
// |--+ Profiler [S]
// |-- Total elapsed time [S0] 3.091 milliseconds.
// |--+ Profiler [P]
// |-- elapsed time [P0] 87.550 milliseconds.
// |-- Total elapsed time [P] 87.559 milliseconds.
// |-- Total elapsed time [TOP] 467.548 milliseconds.

public class Profiler {

  final static String PROFILER_MARKER_NAME = "PROFILER";

  final static int MIN_SW_NAME_LENGTH = 24;
  final static int MIN_SW_ELAPSED_TIME_NUMBER_LENGTH = 9;
  
  final String name;
  final StopWatch globalStopWatch;

  List<StopWatch> stopwatchList = new ArrayList<StopWatch>();
  List<Object> childList = new ArrayList<Object>();

  ProfilerRegistry profilerRegistry;
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

  public void setProfilerRegistry(ProfilerRegistry profilerRegistry) {
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

  public void start(String name) {
    stopLastStopWatch();
    StopWatch childSW = new StopWatch(name);
    stopwatchList.add(childSW);
    childList.add(childSW);
  }

  public Profiler startNested(String name) {
    Profiler nestedProfiler = new Profiler(name);
    nestedProfiler.setProfilerRegistry(profilerRegistry);
    nestedProfiler.setLogger(logger);
    childList.add(nestedProfiler);
    return nestedProfiler;
  }

  StopWatch getLastStopWatch() {
    if (stopwatchList.size() > 0) {
      return stopwatchList.get(stopwatchList.size() - 1);
    } else {
      return null;
    }
  }

  void stopLastStopWatch() {
    StopWatch last = getLastStopWatch();
    if (last != null) {
      last.stop();
    }
  }

  void stopNestedProfilers() {
    for (Object child : childList) {
      if (child instanceof Profiler)
        ((Profiler) child).stop();
    }
  }

  public Profiler stop() {
    stopLastStopWatch();
    stopNestedProfilers();
    globalStopWatch.stop();
    return this;
  }

  public void print() {
    DurationUnit du = Util.selectDurationUnitForDisplay(globalStopWatch);
    String r = buildString(du, "+", "");
    System.out.println(r);
  }

  public void log() {
    Marker profilerMarker = MarkerFactory.getMarker(PROFILER_MARKER_NAME);
    if (logger.isDebugEnabled(profilerMarker)) {
      DurationUnit du = Util.selectDurationUnitForDisplay(globalStopWatch);
      String r = buildString(du, "+", "");
      logger.debug(profilerMarker, r);
    }
  }

  private String buildString(DurationUnit du, String prefix, String indentation) {
    StringBuffer buf = new StringBuffer();

    buf.append(prefix);
    buf.append(" Profiler [");
    buf.append(name);
    buf.append("]");
    buf.append(SpacePadder.LINE_SEP);
    for (Object child : childList) {
      if (child instanceof StopWatch) {
        buildStringForChildStopWatch(buf, indentation, (StopWatch) child, du);
      } else if (child instanceof Profiler) {
        Profiler profiler = (Profiler) child;
        profiler.stop();
        String subString = profiler
            .buildString(du, "|--+", indentation + "   ");
        buf.append(subString);
      }
    }
    buildStringForGlobalStopWatch(buf, indentation, globalStopWatch, du);
    return buf.toString();
  }

  private static void buildStringForChildStopWatch(StringBuffer buf,
      String indentation, StopWatch sw, DurationUnit du) {

    buf.append(indentation);
    buf.append("|--");
    buf.append(" elapsed time       ");
    SpacePadder.leftPad(buf, "[" + sw.getName() + "]", MIN_SW_NAME_LENGTH);
    buf.append(" ");
    String timeStr = Util.durationInDunrationUnitsAsStr(sw.getResultInNanos(),
        du);
    SpacePadder.leftPad(buf, timeStr, MIN_SW_ELAPSED_TIME_NUMBER_LENGTH);
    buf.append(" ");
    Util.appendDurationUnitAsStr(buf, du);
    buf.append(SpacePadder.LINE_SEP);
  }

  private static void buildStringForGlobalStopWatch(StringBuffer buf,
      String indentation, StopWatch sw, DurationUnit du) {
    buf.append(indentation);
    buf.append("|--");
    buf.append(" Total elapsed time ");
    SpacePadder.leftPad(buf, "[" + sw.getName() + "]", MIN_SW_NAME_LENGTH);
    buf.append(" ");
    String timeStr = Util.durationInDunrationUnitsAsStr(sw.getResultInNanos(),
        du);
    SpacePadder.leftPad(buf, timeStr, MIN_SW_ELAPSED_TIME_NUMBER_LENGTH);
    buf.append(" ");
    Util.appendDurationUnitAsStr(buf, du);
    buf.append(SpacePadder.LINE_SEP);
  }

}
