package org.dummy;

import org.apache.log4j.Category;
import org.apache.log4j.Level;

public class Bug174 extends CategoryTestCase {
    
    private static final int UNKNOWN_PRIORITY_VALUE = 10002;
    
    public void testLogsCustomPriorityAsDebug() throws Exception {
        try {
            underTest.log(new CustomPriority(), "should be logged with default level DEBUG");
            assertEquals(java.util.logging.Level.FINE, handler.logRecord.getLevel());
        } catch (IllegalStateException e) {
            fail("throws exception on unknown priority");
        }
    }
    
    /**
     * A custom priority to provoke an exception in {@link Category}'s level mapping.
     *
     */
    private static class CustomPriority extends Level {
        private static final long serialVersionUID = 1L;
        public CustomPriority() {
            super(UNKNOWN_PRIORITY_VALUE, "DAC", 7);
        }
    }
}
