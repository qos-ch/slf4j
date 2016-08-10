package org.slf4j.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

// inspired by http://stackoverflow.com/questions/7364443/how-to-format-a-date-in-java-me
public class SimpleMicroDateFormat {

  private static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

  /**
   * 2016-07-26 11:04:57.999
   */
  public static final int CODE_FULL = 1;
  public static final SimpleMicroDateFormat FULL = new SimpleMicroDateFormat(CODE_FULL);

  private final int format;
  private TimeZone timeZone;

  public SimpleMicroDateFormat(int format, TimeZone timeZone) {
    this.format = format;
    this.timeZone = timeZone;
  }

  public SimpleMicroDateFormat(int format) {
    this(format, TZ_UTC);
  }

  public String format(Date date) {
    Calendar cal = Calendar.getInstance();
    if (timeZone != null) {
      cal.setTimeZone(timeZone);
    }
    cal.setTime(date);
    StringBuffer sb = new StringBuffer(30);
    switch (format) {
    case CODE_FULL:
      appendDate(cal, sb, '-', 4);
      sb.append(' ');
      appendTime(cal, sb);
      sb.append('.');
      leftPadWithZeros(sb, cal.get(Calendar.MILLISECOND), 3);
      appendTimeZone(cal, sb);
      break;
    default:
      throw new IllegalArgumentException("Unknown format: " + format);
    }
    return sb.toString();
  }

  private void appendTime(Calendar cal, StringBuffer sb) {
    leftPadWithZeros(sb, cal.get(Calendar.HOUR_OF_DAY), 2);
    sb.append(':');
    leftPadWithZeros(sb, cal.get(Calendar.MINUTE), 2);
    sb.append(':');
    leftPadWithZeros(sb, cal.get(Calendar.SECOND), 2);
  }

  private void appendTimeZone(Calendar cal, StringBuffer sb) {
    if (cal.getTimeZone() != null) {
      sb.append(cal.getTimeZone().getID());
    }
  }

  private void appendDate(Calendar cal, StringBuffer sb, char separator, int yearLen) {
    leftPadWithZeros(sb, cal.get(Calendar.YEAR), yearLen);
    sb.append(separator);
    leftPadWithZeros(sb, cal.get(Calendar.MONTH), 2);
    sb.append(separator);
    leftPadWithZeros(sb, cal.get(Calendar.DAY_OF_MONTH), 2);
  }

  public static void leftPadWithZeros(StringBuffer sb, int str, int minSize) {
    leftPadWithZeros(sb, str, minSize, minSize);
  }
  public static void leftPadWithZeros(StringBuffer sb, int str, int minSize, int maxSize) {
    leftPad(sb, String.valueOf(str), minSize, maxSize, '0');
  }

  private static void leftPad(StringBuffer sb, String str, int minSize, int maxSize, char padChar) {
    if (str.length() > maxSize) {
      str = str.substring(str.length()-maxSize, str.length());
    }
    int numOfCharsToPad = minSize - str.length();
    for (int i = 0; i < numOfCharsToPad; i++) {
      sb.append(padChar);
    }
    sb.append(str);
  }

}
