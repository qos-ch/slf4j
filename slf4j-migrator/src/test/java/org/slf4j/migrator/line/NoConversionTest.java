package org.slf4j.migrator.line;

import org.slf4j.migrator.line.JCLRuleSet;
import org.slf4j.migrator.line.LineConverter;
import org.slf4j.migrator.line.Log4jRuleSet;

import junit.framework.TestCase;

public class NoConversionTest extends TestCase {

  /**
   * This test shows that performing JCL to SLF4J conversion has no impact on
   * Log4j implementation
   */
  public void testJclOverLog4jConversion() {
    // running jcl to slf4j conversion
    //JCLMatcher jclMatcher = 
    LineConverter jclLineConverter = new LineConverter(new JCLRuleSet());
    // no changes on log4j.LogManager import
    assertEquals("import org.apache.log4j.LogManager;", jclLineConverter
        .getOneLineReplacement("import org.apache.log4j.LogManager;"));
    // no changes on log4j.Logger import
    assertEquals("import org.apache.log4j.Logger;", jclLineConverter
        .getOneLineReplacement("import org.apache.log4j.Logger;"));
    // no changes on Logger instanciation using LogManager
    assertEquals(
        "Logger log = LogManager.getLogger(MyClass.class);",
        jclLineConverter
            .getOneLineReplacement("Logger log = LogManager.getLogger(MyClass.class);"));
    // no changes on Logger instanciation using Logger.getLogger
    assertEquals(
        "public static Logger mylog1 = Logger.getLogger(MyClass.class);",
        jclLineConverter
            .getOneLineReplacement("public static Logger mylog1 = Logger.getLogger(MyClass.class);"));
  }

  /**
   * This test shows that performing Log4j to SLF4J conversion has no impact on
   * JCL implementation
   */
  public void testLog4jOverJclConversion() {
    // running log4j to slf4j conversion
    LineConverter log4jConverter = new LineConverter(new Log4jRuleSet());
    
    // no changes on LogFactory import
    assertEquals("import org.apache.commons.logging.LogFactory;", log4jConverter
        .getOneLineReplacement("import org.apache.commons.logging.LogFactory;"));
    // no changes on Log import
    assertEquals("import org.apache.commons.logging.Log;", log4jConverter
        .getOneLineReplacement("import org.apache.commons.logging.Log;"));
    // no changes on Log instanciation using Logfactory.getLog
    assertEquals(
        "public static Log mylog1 = LogFactory.getLog(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // no changes on log instanciation using LogFactory.getFactory().getInstance
    assertEquals(
        "public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"));

  }
}
