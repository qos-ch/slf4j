package org.slf4j.test.fluent;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class LogEveryAmountOfTimeTest {

  @Before
  public void setUp() {
    TestListAppender.init();
    TestConsoleAppender.init();
  }

  @Test
  public void everyOneSecond() {
    Logger logger = LoggerFactory.getLogger("org.slf4j.test.fluent.error.Test");

    for(int i = 0; i < 60; i++) {
      logger.atError().every(1, ChronoUnit.SECONDS).log("error");
      sleep(50);
    }

    assertEquals(3, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void everyHalfSecond() {
    Logger logger = LoggerFactory.getLogger("org.slf4j.test.fluent.error.Test");

    for(int i = 0; i < 60; i++) {
      logger.atError().every(500, ChronoUnit.MILLIS).log("error");
      sleep(50);
    }

    assertEquals(6, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void everyConcurrent() throws Exception {
    Logger logger1 = LoggerFactory.getLogger("org.slf4j.test.fluent.list.error.Test1");
    Logger logger2 = LoggerFactory.getLogger("org.slf4j.test.fluent.list.error.Test2");

    Thread t1 = new Thread(() -> {
      for(int i = 0; i < 60; i++) {
        logger1.atError().every(1, ChronoUnit.SECONDS).log("error every 1 second");
        sleep(50);
      }
    });

    Thread t2 = new Thread(() -> {
      for(int i = 0; i < 60; i++) {
        logger2.atError().every(500, ChronoUnit.MILLIS).log("error every half second");
        sleep(50);
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    assertEquals(9, TestListAppender.EVENTS.size());
  }

  private static void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
