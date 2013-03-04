package org.slf4j.profiler;

import org.slf4j.profiler.logging.LoggerOutput;

final class DefaultLoggerOutput implements LoggerOutput {
  static final String TOP_PROFILER_FIRST_PREFIX = "+";
  static final String NESTED_PROFILER_FIRST_PREFIX = "|---+";
  static final String TOTAL_ELAPSED = " Total        ";
  static final String SUBTOTAL_ELAPSED = " Subtotal     ";
  static final String ELAPSED_TIME = " elapsed time ";
  static final int MIN_SW_NAME_LENGTH = 24;
  static final int MIN_SW_ELAPSED_TIME_NUMBER_LENGTH = 9;

  public static DefaultLoggerOutput getInstance() {
    return Holder.INSTANCE;
  }

  public String format(Profiler profiler) {
    DurationUnit du = Util
        .selectDurationUnitForDisplay(profiler.globalStopWatch);
    return buildProfilerString(profiler, du, TOP_PROFILER_FIRST_PREFIX,
        TOTAL_ELAPSED, "", "");
  }

  private static String buildProfilerString(Profiler profiler, DurationUnit du,
      String firstPrefix, String label, String prefixIndentation,
      String indentation) {
    StringBuffer buf = new StringBuffer();

    buf.append(prefixIndentation);
    buf.append(firstPrefix);
    buf.append(" Profiler [");
    buf.append(profiler.name);
    buf.append("]");
    buf.append(SpacePadder.LINE_SEP);
    for (TimeInstrument child : profiler.childTimeInstrumentList) {
      if (child instanceof StopWatch) {
        buildStopWatchString(buf, du, ELAPSED_TIME, indentation,
            (StopWatch) child);
      } else if (child instanceof Profiler) {
        Profiler childProfiler = (Profiler) child;
        String subString = buildProfilerString(childProfiler, du,
            NESTED_PROFILER_FIRST_PREFIX, SUBTOTAL_ELAPSED, indentation,
            indentation + "    ");
        buf.append(subString);
        buildStopWatchString(buf, du, ELAPSED_TIME, indentation,
            profiler.globalStopWatch);
      }
    }
    buildStopWatchString(buf, du, label, indentation, profiler.globalStopWatch);
    return buf.toString();
  }

  private static void buildStopWatchString(StringBuffer buf, DurationUnit du,
      String prefix, String indentation, StopWatch sw) {

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

  private static final class Holder {
    private static final DefaultLoggerOutput INSTANCE = new DefaultLoggerOutput();
  }
}
