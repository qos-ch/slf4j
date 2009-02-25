package org.slf4j.dummyExt;

import junit.framework.*;

public class PackageTest extends TestCase {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(MDCStrLookupTest.class);
    suite.addTestSuite(XLoggerTest.class);
    suite.addTestSuite(EventLoggerTest.class);
    return suite;
  }
}
