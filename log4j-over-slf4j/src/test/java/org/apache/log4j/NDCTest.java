package org.apache.log4j;

import junit.framework.TestCase;

/**
 * @author Ceki G&uuml;c&uuml;
 */
public class NDCTest extends TestCase {


  public void setUp() {
    assertEquals(0, NDC.getDepth());
  }

  public void tearDown() {
    NDC.clear();
  }

  public void testSmoke() {
    NDC.push("a");
    String back = NDC.pop();
    assertEquals("a", back);
  }

  public void testPop() {
    NDC.push("peek");
    String back = NDC.peek();
    assertEquals("peek", back);
  }

  public void testClear() {
    NDC.push("clear");
    NDC.clear();
    assertEquals(0, NDC.getDepth());
  }

}
