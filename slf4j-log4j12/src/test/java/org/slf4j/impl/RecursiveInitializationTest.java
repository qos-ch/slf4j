package org.slf4j.impl;

import java.util.Random;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecursiveInitializationTest extends TestCase {

  // value of LogManager.DEFAULT_CONFIGURATION_KEY;
  static String CONFIG_FILE_KEY = "log4j.configuration";

  int diff = new Random().nextInt(10000);
  
  protected void setUp() throws Exception {
    System.setProperty(CONFIG_FILE_KEY, "recursiveInit.properties");
    super.setUp();
  }

  protected void tearDown() throws Exception {
    System.clearProperty(CONFIG_FILE_KEY);
    super.tearDown();
  }

  public void testLog4j() {
    Logger logger = LoggerFactory.getLogger("x"+diff);
    System.out.println("logger class="+logger.getClass().getName());
    logger.info("hello");
  }

}
