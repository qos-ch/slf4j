package org.slf4j.impl;

import java.util.Random;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecursiveAppender extends AppenderSkeleton {

  int diff = new Random().nextInt();
  
  public RecursiveAppender() {
    System.out.println("in RecursiveAppender constructor");
    Logger logger = LoggerFactory.getLogger("RecursiveAppender"+diff);
    System.out.println("logger class="+logger.getClass().getName());
    logger.info("Calling a logger in the constructor");
  }
  
  protected void append(LoggingEvent arg0) {
  }

  public void close() {
  }

  public boolean requiresLayout() {
    return false;
  }
}
