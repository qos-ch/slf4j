package org.slf4j;

import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A utility that provides standard mechanisms for logging certain kinds of
 * activities.
 * 
 * @author Ralph Goers
 * @author Ceki Gulcu
 */
public class LoggerX {

  private static final String FQCN = LoggerX.class.getName();
  static Marker FLOW_MARKER = MarkerFactory.getMarker("FLOW");
  static Marker ENTRY_MARKER = MarkerFactory.getMarker("ENTER");
  static Marker EXIT_MARKER = MarkerFactory.getMarker("EXIT");

  static Marker EXCEPTION_MARKER = MarkerFactory.getMarker("EXCEPTION");
  static Marker DIAG_MARKER = MarkerFactory.getMarker("DIAG");

  static String ENTRY_MESSAGE_0 = "entry";
  static String ENTRY_MESSAGE_1 = "entry with params ({})";
  static String ENTRY_MESSAGE_2 = "entry with params ({}, {})";
  static String ENTRY_MESSAGE_3 = "entry with params ({}, {}, {})";
  static String ENTRY_MESSAGE_4 = "entry with params ({}, {}, {}, {})";
  static int ENTRY_MESSAGE_ARRAY_LEN = 5;
  static String[] ENTRY_MESSAGE_ARRAY = new String[ENTRY_MESSAGE_ARRAY_LEN];
  static {
    ENTRY_MARKER.add(FLOW_MARKER);
    EXIT_MARKER.add(FLOW_MARKER);
    ENTRY_MESSAGE_ARRAY[0] = ENTRY_MESSAGE_0;
    ENTRY_MESSAGE_ARRAY[1] = ENTRY_MESSAGE_1;
    ENTRY_MESSAGE_ARRAY[2] = ENTRY_MESSAGE_2;
    ENTRY_MESSAGE_ARRAY[3] = ENTRY_MESSAGE_3;
    ENTRY_MESSAGE_ARRAY[4] = ENTRY_MESSAGE_4;
  }

  /**
   * Log entry to a method
   * 
   * @param logger
   *                the Logger to log with.
   */
  public static void entering(Logger logger, Object... argArray) {
    if (logger.isDebugEnabled(ENTRY_MARKER)
        && logger instanceof LocationAwareLogger) {
      String messagePattern = "";
      if(argArray.length <= ENTRY_MESSAGE_ARRAY_LEN) {
        messagePattern = ENTRY_MESSAGE_ARRAY[argArray.length];
      }
      
      String msg = MessageFormatter.arrayFormat(messagePattern, argArray);
      
      ((LocationAwareLogger) logger).log(ENTRY_MARKER, FQCN,
          LocationAwareLogger.ERROR_INT, msg, null);
    }
  }

}
