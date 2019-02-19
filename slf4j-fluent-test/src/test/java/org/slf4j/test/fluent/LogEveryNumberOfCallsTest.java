package org.slf4j.test.fluent;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class LogEveryNumberOfCallsTest {

  @Before
  public void setUp() {
    TestListAppender.init();
    TestConsoleAppender.init();
  }

  @Test
  public void logEveryNumberOfCalls() {
    Logger logger = LoggerFactory.getLogger("org.slf4j.test.fluent.error.Test");

    for(int i = 0; i < 13; i++) {
      logger.atError().every(5).log("error");
    }

    assertEquals(3, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void logEveryNumberOfCallsConcurrent() throws Exception {
    int iterations = 10000;
    int t1Every = 5;
    int t2Every = 7;

    Logger logger1 = LoggerFactory.getLogger("org.slf4j.test.fluent.list.error.Test1");
    Logger logger2 = LoggerFactory.getLogger("org.slf4j.test.fluent.list.error.Test2");

    Thread t1 = new Thread(() -> {
      for(int i = 0; i < iterations; i++) {
        logger1.atError().every(t1Every).log("error");
      }
    });

    Thread t2 = new Thread(() -> {
      for(int i = 0; i < iterations; i++) {
        logger2.atError().every(t2Every).log("error");
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    assertEquals((1 + iterations / t1Every) + (1 + iterations / t2Every), TestListAppender.EVENTS.size());
  }
}
