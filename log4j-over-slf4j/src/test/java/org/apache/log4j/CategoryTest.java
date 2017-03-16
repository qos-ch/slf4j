package org.apache.log4j;

import org.dummy.ListHandler;
import org.junit.Test;

import java.util.List;
import java.util.logging.*;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * Created by thsnoopy@naver.com on 2017. 3. 16..
 */
public class CategoryTest {
  @Test
  public void testToStringExceptionGuard() {
    ListHandler listHandler = new ListHandler();
    java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
    root.addHandler(listHandler);
    root.setLevel(java.util.logging.Level.FINE);
    Logger log4jLogger = Logger.getLogger("testLogger");
    List<LogRecord> logRecordList = listHandler.getList();

    Object mockObject = mock(Object.class);
    when(mockObject.toString()).thenThrow(new RuntimeException("nope"));
    log4jLogger.log(org.apache.log4j.Level.DEBUG, mockObject);

    LogRecord logRecord = logRecordList.get(0);
    assertNull(logRecord.getMessage());
  }
}
