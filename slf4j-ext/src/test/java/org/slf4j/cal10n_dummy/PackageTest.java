package org.slf4j.cal10n_dummy;

import junit.framework.*;

public class PackageTest extends TestCase {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(LocLoggerTest.class);
    return suite;
  }
}
