package org.dummy;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import junit.framework.TestCase;

abstract class CategoryTestCase extends TestCase {
    
    Category underTest = Logger.getLogger("for.test.only");
    
    SingleMessageHandler handler = new SingleMessageHandler();
    
    private static final java.util.logging.Logger ROOT = java.util.logging.Logger.getLogger("for.test.only");
    
    static {
        ROOT.setLevel(java.util.logging.Level.FINEST);
    }
    
    public void setUp() {
        ROOT.addHandler(handler);
    }
    
    public void tearDown() {
        ROOT.removeHandler(handler);
    }
}
