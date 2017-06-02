package org.slf4j.event;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Marker;
import org.slf4j.helpers.SubstituteLogger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Tests {@link EventRecodingLogger}.
 */
@RunWith(Parameterized.class)
public class EventRecordingLoggerTest {

  private Level level;
  private Queue<SubstituteLoggingEvent> eventQueue;
  private SubstituteLogger subLogger;
  private EventRecodingLogger eventRecodingLogger;
  private LevelScopedLoggerInvoker invoker;

  public EventRecordingLoggerTest(Level level) {
    this.level = level;
    this.eventQueue = new LinkedBlockingQueue<SubstituteLoggingEvent>();
    this.subLogger = new SubstituteLogger("logger.test", eventQueue, false);
    this.eventRecodingLogger = new EventRecodingLogger(subLogger, eventQueue);
    this.invoker = new LevelScopedLoggerInvoker(eventRecodingLogger, level);
  }

  @Parameterized.Parameters(name = "level: {0}")
  public static Collection<Level> levels() {
    return Arrays.asList(Level.values());
  }

  @Test
  public void logString() {
    invoker.log("foo");
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), "foo");
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), null);
    assertArrayEquals(event.getArgumentArray(), null);
  }

  @Test
  public void logStringObject() {
    String msg = "foo {}";
    invoker.log(msg, 5);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), null);
    assertArrayEquals(event.getArgumentArray(), new Object[]{5});
  }

  @Test
  public void logStringObjectObject() {
    String msg = "foo {} {}";
    invoker.log(msg, 5, false);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), null);
    assertArrayEquals(event.getArgumentArray(), new Object[]{5, false});
  }

  @Test
  public void logStringObjectObjectsVarArg() {
    String msg = "foo {} {} {}";
    invoker.log(msg, 5, false, 2.2);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), null);
    assertArrayEquals(event.getArgumentArray(), new Object[]{5, false, 2.2});
  }

  @Test
  public void logStringThrowable() {
    String msg = "foo";
    Throwable t = new IllegalArgumentException("pony");
    invoker.log(msg, t);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), t);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), null);
    assertArrayEquals(event.getArgumentArray(), null);
  }

  @Test
  public void isEnabled() {
    assertTrue(invoker.isEnabled());
  }

  @Test
  public void isEnabledMarker() {
    Marker marker = new NullMarker();
    assertTrue(invoker.isEnabled(marker));
  }

  @Test
  public void logMarkerString() {
    Marker marker = new NullMarker();
    invoker.log(marker, "foo");
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), "foo");
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), marker);
    assertArrayEquals(event.getArgumentArray(), null);
  }

  @Test
  public void logMarkerStringObject() {
    Marker marker = new NullMarker();
    String msg = "foo {}";
    invoker.log(marker, msg, 5);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), marker);
    assertArrayEquals(event.getArgumentArray(), new Object[]{5});
  }

  @Test
  public void logMarkerStringObjectObject() {
    Marker marker = new NullMarker();
    String msg = "foo {} {}";
    invoker.log(marker, msg, 5, false);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), marker);
    assertArrayEquals(event.getArgumentArray(), new Object[]{5, false});
  }

  @Test
  public void logMarkerStringObjectObjectsVarArg() {
    Marker marker = new NullMarker();
    String msg = "foo {} {} {}";
    invoker.log(marker, msg, 5, false, 2.2);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), null);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), marker);
    assertArrayEquals(event.getArgumentArray(), new Object[]{5, false, 2.2});
  }

  @Test
  public void logMarkerStringThrowable() {
    Marker marker = new NullMarker();
    String msg = "foo";
    Throwable t = new IllegalArgumentException("pony");
    invoker.log(marker, msg, t);
    assertEquals(eventQueue.size(), 1);
    SubstituteLoggingEvent event = eventQueue.remove();
    assertEquals(event.getLevel(), level);
    assertEquals(event.getMessage(), msg);
    assertEquals(event.getThrowable(), t);
    assertEquals(event.getLogger(), subLogger);
    assertEquals(event.getMarker(), marker);
    assertArrayEquals(event.getArgumentArray(), null);
  }

}
