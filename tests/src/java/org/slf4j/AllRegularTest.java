// TOTO

package org.slf4j;

import org.slf4j.impl.JDK14AdapterLoggerNameTest;
import org.slf4j.impl.MessageFormatterTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllRegularTest extends TestCase {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(MessageFormatterTest.class);
    suite.addTestSuite(BasicMarkerTest.class);
    suite.addTestSuite(JDK14AdapterLoggerNameTest.class);
    return suite;
  }

}
