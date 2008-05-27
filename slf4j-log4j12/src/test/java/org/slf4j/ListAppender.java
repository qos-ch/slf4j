package org.slf4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class ListAppender extends AppenderSkeleton {

  List list = new ArrayList();
  
  protected void append(LoggingEvent event) {
    list.add(event);
  }

  public void close() {
  }

  public boolean requiresLayout() {
    return false;
  }

}
