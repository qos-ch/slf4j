package org.dummy;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import junit.framework.TestCase;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

public class Bug131 extends TestCase {

  public void testBug131() {

    ListHandler listHandler = new ListHandler();
    java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
    root.addHandler(listHandler);
    root.setLevel(Level.FINEST);
    Logger log4jLogger = Logger.getLogger("a");
    Category log4jCategory = Logger.getLogger("b");

    int n = 0;

    log4jLogger.trace("msg" +(n++));
    log4jLogger.debug("msg" +(n++));
    log4jLogger.info("msg" +(n++));
    log4jLogger.warn("msg" +(n++));
    log4jLogger.error("msg" +(n++));
    log4jLogger.fatal("msg" +(n++));
    
    log4jCategory.debug("msg" +(n++));
    log4jCategory.info("msg" +(n++));
    log4jCategory.warn("msg" +(n++));
    log4jCategory.error("msg" +(n++));
    log4jCategory.fatal("msg" +(n++));
 
    assertEquals(n, listHandler.list.size());
    
    for(int i = 0; i < n; i++) {
      LogRecord logRecord = (LogRecord) listHandler.list.get(i);
      assertEquals("testBug131", logRecord.getSourceMethodName());   
    }
  }
}
