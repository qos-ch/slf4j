package org.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ListHandler extends Handler {

  List list = new ArrayList();
  
  public void close() throws SecurityException {

  }

  public void flush() {

  }

  public void publish(LogRecord logRecord) {
    logRecord.getSourceClassName();
    list.add(logRecord);
  }

}
