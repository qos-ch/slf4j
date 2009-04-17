package org.apache.log4j;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class Trivial extends TestCase {

  public void testSmoke() {
    Logger l = Logger.getLogger("a");
    l.trace("t");
    l.debug("d");
    l.info("i");
    l.warn("w");
    l.error("e");
    l.fatal("f");

    Exception e = new Exception("testing");
    l.trace("t", e);
    l.debug("d", e);
    l.info("i", e);
    l.warn("w", e);
    l.error("e", e);
    l.fatal("f", e);
  }

}
