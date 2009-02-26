package org.slf4j.ext;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

import java.util.Map;

/**
 * Simple Logger used to log events. All events are directed to a logger named "EventLogger"
 * with a level of ERROR (the closest to "always" that SLF4J gets) and with an Event marker.
 *
 * @author Ralph Goers
 */
public class EventLogger {

  private static final String FQCN = EventLogger.class.getName();

  static Marker EVENT_MARKER = MarkerFactory.getMarker("EVENT");

  private static LoggerWrapper eventLogger =
      new LoggerWrapper(LoggerFactory.getLogger("EventLogger"), FQCN);

  /**
   * There can only be a single EventLogger.
   */
  private EventLogger() {
  }

  /**
   * Logs the event.
   *
   * @param data The EventData.
   */
  public static void logEvent(EventData data) {
    if (eventLogger.instanceofLAL) {
      ((LocationAwareLogger) eventLogger.logger).log(EVENT_MARKER, FQCN,
          LocationAwareLogger.ERROR_INT, data.toXML(), null);
    } else {
      eventLogger.logger.error(EVENT_MARKER, data.toXML(), data);
    }
  }
}
