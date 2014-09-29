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

import java.text.DecimalFormat;

/**
 * 
 * A collection of utility methods.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *  
 */
class Util {

  static final long NANOS_IN_ONE_MICROSECOND = 1000;
  static final long NANOS_IN_ONE_MILLISECOND = NANOS_IN_ONE_MICROSECOND * 1000;
  static final long NANOS_IN_ONE_SECOND =NANOS_IN_ONE_MILLISECOND * 1000;
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");
  
  static DurationUnit selectDurationUnitForDisplay(StopWatch sw) {
    return selectDurationUnitForDisplay(sw.elapsedTime());
  }
  
  static DurationUnit selectDurationUnitForDisplay(long durationInNanos) {
    if (durationInNanos < 10*NANOS_IN_ONE_MICROSECOND) {
      return DurationUnit.NANOSECOND;
    } else if (durationInNanos < 10*NANOS_IN_ONE_MILLISECOND) {
      return DurationUnit.MICROSECOND;
    } else if (durationInNanos < 10*NANOS_IN_ONE_SECOND) {
      return DurationUnit.MILLISSECOND;
    } else {
      return DurationUnit.SECOND;
    }
  }
  
  static public double convertToMicros(long nanos) {
    return (double) nanos / NANOS_IN_ONE_MICROSECOND;
  }

  static public double convertToMillis(long nanos) {
    return (double) nanos / NANOS_IN_ONE_MILLISECOND;
  }

  static public double convertToSeconds(long nanos) {
    return ((double) nanos / NANOS_IN_ONE_SECOND);
  }
  
  static String durationInDurationUnitsAsStr(StringBuilder buf, StopWatch sw) {
    DurationUnit du = selectDurationUnitForDisplay(sw);
    return durationInDurationUnitsAsStr(sw.elapsedTime(), du);
  }
  
  static String durationInDurationUnitsAsStr(long nanos, DurationUnit durationUnit) {
    StringBuilder buf = new StringBuilder();
    switch (durationUnit) {
    case NANOSECOND:
      buf.append(nanos);
      break;
    case MICROSECOND:
      double micros = convertToMicros(nanos);
      buf.append(DECIMAL_FORMAT.format(micros));
      break;
    case MILLISSECOND:
      double millis = convertToMillis(nanos);
      buf.append(DECIMAL_FORMAT.format(millis));
      break;
    case SECOND:
      double seconds = convertToSeconds(nanos);
      buf.append(DECIMAL_FORMAT.format(seconds));
      break;
    }
    return buf.toString();
  }
  
  static void appendDurationUnitAsStr(StringBuilder buf, DurationUnit durationUnit) {
    switch (durationUnit) {
    case NANOSECOND:
      buf.append("nanoseconds.");
      break;
    case MICROSECOND:
      buf.append("microseconds.");
      break;
    case MILLISSECOND:
      buf.append("milliseconds.");
      break;
    case SECOND:
      buf.append(" seconds.");
      break;
    }
  }
}
