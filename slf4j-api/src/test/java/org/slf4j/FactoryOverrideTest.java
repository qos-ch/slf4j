package org.slf4j;

import org.junit.Test;
import org.slf4j.helpers.MarkerIgnoringBase;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

public class FactoryOverrideTest {

  @Test
  public void shouldWriteToCustomLogger() throws Exception {
    // Given
    ILoggerFactory factory = new TestFactory();
    // When
    LoggerFactory.setLoggerFactory(factory);
    final Logger logger1 = LoggerFactory.getLogger("test1");
    logger1.info("Info in logger 1");
    // Then
    assertSame(factory.getLogger("test1"), logger1);
    assertTrue(logger1 instanceof LastEventLogger);
    assertEquals("Info in logger 1", ((LastEventLogger) logger1).getLastMessage());
    assertEquals(2, ((LastEventLogger) logger1).getLastLevel());
  }


  static class TestFactory implements ILoggerFactory {
    private final Map<String, LastEventLogger> loggerMap;

    public TestFactory() {
      loggerMap = new HashMap<String, LastEventLogger>(8);
    }

    public Logger getLogger(String name) {
      LastEventLogger logger = loggerMap.get(name);
      if (null == logger) {
        logger = new LastEventLogger(name);
        loggerMap.put(name, logger);
      }
      return logger;
    }
  }


  static class LastEventLogger extends MarkerIgnoringBase {
    private long timestamp;
    private int level;
    private String message;
    private int eventCount;

    public LastEventLogger(String name) {
      this.name = name;
    }

    public boolean isErrorEnabled() {
      return true;
    }

    public boolean isDebugEnabled() {
      return true;
    }

    public boolean isInfoEnabled() {
      return true;
    }

    public boolean isWarnEnabled() {
      return true;
    }

    public boolean isTraceEnabled() {
      return true;
    }

    private void log(int level, String message) {
      this.timestamp = System.currentTimeMillis();
      this.level = level;
      this.message = message;
      ++this.eventCount;
    }

    public int getEventCount() {
      return eventCount;
    }

    public int getLastLevel() {
      return level;
    }

    public String getLastMessage() {
      return message;
    }

    public long getLastTimestamp() {
      return timestamp;
    }

    public void error(String msg) {
      log(0, msg);
    }

    public void warn(String msg) {
      log(1, msg);

    }

    public void info(String msg) {
      log(2, msg);
    }

    public void debug(String msg) {
      log(3, msg);
    }

    public void trace(String msg) {
      log(4, msg);
    }

    public void error(String format, Object arg1, Object arg2) {
      throw new UnsupportedOperationException();
    }

    public void error(String format, Object arg) {
      throw new UnsupportedOperationException();
    }

    public void error(String format, Object... arguments) {
      throw new UnsupportedOperationException();
    }

    public void error(String msg, Throwable t) {
      throw new UnsupportedOperationException();
    }

    public void warn(String format, Object arg1, Object arg2) {
      throw new UnsupportedOperationException();
    }

    public void warn(String format, Object arg) {
      throw new UnsupportedOperationException();
    }

    public void warn(String format, Object... arguments) {
      throw new UnsupportedOperationException();
    }

    public void warn(String msg, Throwable t) {
      throw new UnsupportedOperationException();
    }

    public void info(String format, Object arg1, Object arg2) {
      throw new UnsupportedOperationException();
    }

    public void info(String format, Object arg) {
      throw new UnsupportedOperationException();
    }

    public void info(String format, Object... arguments) {
      throw new UnsupportedOperationException();
    }

    public void info(String msg, Throwable t) {
      throw new UnsupportedOperationException();
    }

    public void debug(String format, Object arg1, Object arg2) {
      throw new UnsupportedOperationException();
    }

    public void debug(String format, Object arg) {
      throw new UnsupportedOperationException();
    }

    public void debug(String format, Object... arguments) {
      throw new UnsupportedOperationException();
    }

    public void debug(String msg, Throwable t) {
      throw new UnsupportedOperationException();
    }

    public void trace(String format, Object arg1, Object arg2) {
      throw new UnsupportedOperationException();
    }

    public void trace(String format, Object arg) {
      throw new UnsupportedOperationException();
    }

    public void trace(String format, Object... arguments) {
      throw new UnsupportedOperationException();
    }

    public void trace(String msg, Throwable t) {
      throw new UnsupportedOperationException();
    }
  }

}
