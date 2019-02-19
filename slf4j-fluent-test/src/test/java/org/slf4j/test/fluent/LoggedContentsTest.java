package org.slf4j.test.fluent;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.slf4j.Logger.lazy;

public class LoggedContentsTest {

  @Before
  public void setUp() {
    TestConsoleAppender.init();
  }

  @Test
  public void errorLoggerContents() {
    Logger logger = LoggerFactory.getLogger("org.slf4j.test.fluent.error.Test");

    logger.atError().log("error no args");
    logger.atError().log("error 1 arg {}", "one");
    logger.atError().log("error 2 args {} {}", "one", (Supplier<?>) () -> "two");
    logger.atError().log("error 2 args {} {}", "one", lazy(() -> "two"));
    logger.atError().log("error 3 args {} {} {}", "one", (Supplier<?>) () -> null, lazy(null));
    logger.atError().log("error 4 args {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four");
    logger.atError().log("error 5 args {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five");
    logger.atError().log("error 6 args {} {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five", "six");
    logger.atError().log("error null varargs", (Object[]) null);
    logger.atError().withCause(new Exception()).log("error 2 args with exception {} {}", "one", (Supplier<?>) () -> "two");

    assertEquals(10, TestConsoleAppender.EVENTS.size());

    assertEquals("error no args", TestConsoleAppender.EVENTS.get(0).getFormattedMessage());
    assertEquals("error 1 arg one", TestConsoleAppender.EVENTS.get(1).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(2).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(3).getFormattedMessage());
    assertEquals("error 3 args one null null", TestConsoleAppender.EVENTS.get(4).getFormattedMessage());
    assertEquals("error 4 args one null null four", TestConsoleAppender.EVENTS.get(5).getFormattedMessage());
    assertEquals("error 5 args one null null four five", TestConsoleAppender.EVENTS.get(6).getFormattedMessage());
    assertEquals("error 6 args one null null four five six", TestConsoleAppender.EVENTS.get(7).getFormattedMessage());
    assertEquals("error null varargs", TestConsoleAppender.EVENTS.get(8).getFormattedMessage());
    assertEquals("error 2 args with exception one two", TestConsoleAppender.EVENTS.get(9).getFormattedMessage());

    int lineNumber = 26;
    for(ILoggingEvent event : TestConsoleAppender.EVENTS) {
      assertTrue(event.getCallerData().length > 0);
      assertEquals(getClass().getName(), event.getCallerData()[0].getClassName());
      assertEquals("errorLoggerContents", event.getCallerData()[0].getMethodName());
      assertEquals(lineNumber, event.getCallerData()[0].getLineNumber());
      lineNumber++;
    }
  }

}
