// TOTO

package org.slf4j;

import org.slf4j.impl.MessageFormatterTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTest extends TestCase {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(MessageFormatterTest.class);
    suite.addTestSuite(BasicMarkerTest.class);
    return suite;
  }

}
