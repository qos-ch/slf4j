package org.slf4j.ext;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XLoggerTest extends TestCase {

  Logger logger = LoggerFactory.getLogger(this.getClass());
  
  public void testSmoke() {
    XLogger xLogger = new XLogger(logger);
    xLogger.entry(logger);
  }
}
