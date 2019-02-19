package org.slf4j.test.fluent;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class LazyArgsTest {

  @Before
  public void setUp() {
    TestConsoleAppender.init();
  }

  private static class SupplierWithCallCheck implements Supplier<String> {

    private final String value;
    private int calls;

    public SupplierWithCallCheck(String value) {
      this.value = value;
      this.calls = 0;
    }

    @Override
    public String get() {
      calls++;
      return value;
    }

    public int getCalls() {
      return calls;
    }
  }

  @Test
  public void lazyArgsNotCalledWhenLoggingIsDisabled() {
    // when logging is enabled at error level, we evaluate the lazy args
    Logger logger = LoggerFactory.getLogger("org.slf4j.test.fluent.error.Test");

    SupplierWithCallCheck supplier = new SupplierWithCallCheck("one");
    logger.atError().log("error 1 arg {}", supplier);
    assertEquals("error 1 arg one", TestConsoleAppender.EVENTS.get(0).getFormattedMessage());
    assertEquals(1, supplier.getCalls());

    TestConsoleAppender.EVENTS.clear();

    // when logging is DISABLED instead, we don't evaluate the lazy args
    logger = LoggerFactory.getLogger("org.slf4j.test.fluent.off.Test");

    supplier = new SupplierWithCallCheck("one");
    logger.atError().log("error 1 arg {}", supplier);
    assertEquals(0, TestConsoleAppender.EVENTS.size());
    assertEquals(0, supplier.getCalls());
  }

}
