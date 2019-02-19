package org.slf4j.test.fluent;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

/**
 * Hacky appender that will expose logged events via a public static var, thus allowing tests to check what was logged
 * Not thread safe
 */
public class TestListAppender extends ConsoleAppender<ILoggingEvent> {

  public static final List<ILoggingEvent> EVENTS = new Vector<>();

  public static void init() {
    EVENTS.clear();
  }

  @Override
  protected void append(ILoggingEvent event) {
    EVENTS.add(event);
    super.append(event);
  }

  @Override
  public void start() {
    super.start();
    setOutputStream(new OutputStream() {
      @Override
      public void write(int b) {

      }
    });
  }
}
