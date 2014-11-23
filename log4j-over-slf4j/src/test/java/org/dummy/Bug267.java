package org.dummy;

import org.apache.log4j.Level;

public class Bug267 extends CategoryTestCase {
    
    public void testLogsAllPriorityToTrace() throws Exception {
        try {
            underTest.log(Level.ALL, "should be logged with TRACE level");
            assertEquals(java.util.logging.Level.FINEST, handler.logRecord.getLevel());
        } catch (IllegalStateException e) {
            fail("throws exception on unknown priority");
        }
    }

}
