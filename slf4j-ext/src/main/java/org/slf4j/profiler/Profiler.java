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
import org.slf4j.bean.AbstractSingleInstanceBuilder;
import org.slf4j.profiler.logging.LoggerOutput;

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

  String name;
  StopWatch globalStopWatch;

  List<TimeInstrument> childTimeInstrumentList = new ArrayList<TimeInstrument>();

  // optional field
  ProfilerRegistry profilerRegistry;
  // optional field
  Logger logger;

  // defaulted field; configurable
  LoggerOutput loggerOutput;
  // defaulted field; configurable
  String markerName;

  /**
   * @deprecated use {@link #builder(String)} instead
   */
  @Deprecated
  public Profiler(String name) {
    this.name = name;
    this.globalStopWatch = new StopWatch(name);
    this.loggerOutput = DefaultLoggerOutput.getInstance();
    this.markerName = PROFILER_MARKER_NAME;
  }

  private Profiler() {
  }

  public String getName() {
    return name;
  }

  public ProfilerRegistry getProfilerRegistry() {
    return profilerRegistry;
  }

  /**
   * @deprecated use {@link Builder#registerWith(ProfilerRegistry)} instead
   */
  @Deprecated
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

  /**
   * @deprecated use {@link Builder#logger(Logger)} instead
   */
  @Deprecated
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
    Profiler nestedProfiler = Profiler.builder(name)
        .registerWith(profilerRegistry).logger(logger).build();
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
      throw new IllegalStateException("time instrument [" + getName()
          + " is not stopped");
    }

    long totalElapsed = globalStopWatch.elapsedTime();
    long childTotal = 0;

    for (TimeInstrument ti : childTimeInstrumentList) {
      childTotal += ti.elapsedTime();
      if (ti.getStatus() != TimeInstrumentStatus.STOPPED) {
        throw new IllegalStateException("time instrument [" + ti.getName()
            + " is not stopped");
      }
      if (ti instanceof Profiler) {
        Profiler nestedProfiler = (Profiler) ti;
        nestedProfiler.sanityCheck();
      }
    }
    if (totalElapsed < childTotal) {
      throw new IllegalStateException(
          "children have a higher accumulated elapsed time");
    }
  }

  public void print() {
    System.out.println(toString());
  }

  @Override
  public String toString() {
    return loggerOutput.format(this);
  }

  public void log() {
    Marker profilerMarker = MarkerFactory.getMarker(markerName);
    if (logger == null) {
      throw new NullPointerException(
          "If you invoke the log() method, then you must associate a logger with this profiler.");
    }
    if (logger.isDebugEnabled(profilerMarker)) {
      String r = loggerOutput.format(this);
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
    List<TimeInstrument> copy = new ArrayList<TimeInstrument>(
        childTimeInstrumentList);
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

  public static Builder builder(String name) {
    return new Builder().name(name)
        .loggerOutput(DefaultLoggerOutput.getInstance())
        .markerName(PROFILER_MARKER_NAME);
  }

  public static final class Builder extends
      AbstractSingleInstanceBuilder<Profiler> {

    private Builder() {
      super(Profiler.class);
    }

    private Builder name(String name) {
      Profiler profiler = bean();
      profiler.name = name;
      profiler.globalStopWatch = new StopWatch(name);
      return this;
    }

    public Builder registerWith(ProfilerRegistry profilerRegistry) {
      if (null != profilerRegistry) {
        Profiler profiler = bean();
        profiler.profilerRegistry = profilerRegistry;
        profilerRegistry.put(profiler);
      }
      return this;
    }

    public Builder logger(Logger logger) {
      bean().logger = logger;
      return this;
    }

    public Builder loggerOutput(LoggerOutput loggerOutput) {
      if (null != loggerOutput) {
        bean().loggerOutput = loggerOutput;
      }
      return this;
    }

    public Builder markerName(String markerName) {
      if (null != markerName) {
        bean().markerName = markerName;
      }
      return this;
    }

    @Override
    protected Profiler initialBeanState() {
      return new Profiler();
    }
  }
}
