package org.slf4j.converter;

import junit.framework.TestCase;

public class NoConversionTest extends TestCase {

  /**
   * This test shows that performing JCL to SLF4J conversion has no impact on
   * Log4j implementation
   */
  public void testJclOverLog4jConversion() {
    // running jcl to slf4j conversion
    JCLMatcher jclMatcher = new JCLMatcher();
    // no changes on log4j.LogManager import
    assertEquals("import org.apache.log4j.LogManager;", jclMatcher
        .getReplacement("import org.apache.log4j.LogManager;"));
    // no changes on log4j.Logger import
    assertEquals("import org.apache.log4j.Logger;", jclMatcher
        .getReplacement("import org.apache.log4j.Logger;"));
    // no changes on Logger instanciation using LogManager
    assertEquals(
        "Logger log = LogManager.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("Logger log = LogManager.getLogger(MyClass.class);"));
    // no changes on Logger instanciation using Logger.getLogger
    assertEquals(
        "public static Logger mylog1 = Logger.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("public static Logger mylog1 = Logger.getLogger(MyClass.class);"));
  }

  /**
   * This test shows that performing Log4j to SLF4J conversion has no impact on
   * JCL implementation
   */
  public void testLog4jOverJclConversion() {
    // running log4j to slf4j conversion
    Log4jMatcher log4jMatcher = new Log4jMatcher();
    // no changes on LogFactory import
    assertEquals("import org.apache.commons.logging.LogFactory;", log4jMatcher
        .getReplacement("import org.apache.commons.logging.LogFactory;"));
    // no changes on Log import
    assertEquals("import org.apache.commons.logging.Log;", log4jMatcher
        .getReplacement("import org.apache.commons.logging.Log;"));
    // no changes on Log instanciation using Logfactory.getLog
    assertEquals(
        "public static Log mylog1 = LogFactory.getLog(MyClass.class);",
        log4jMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // no changes on log instanciation using LogFactory.getFactory().getInstance
    assertEquals(
        "public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);",
        log4jMatcher
            .getReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"));

  }
}
