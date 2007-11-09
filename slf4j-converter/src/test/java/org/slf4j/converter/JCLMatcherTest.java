package org.slf4j.converter;

import junit.framework.TestCase;

public class JCLMatcherTest extends TestCase {

  public void testImportReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // LogFactory import replacement
    assertEquals("import org.slf4j.LoggerFactory;", jclMatcher
        .getReplacement("import org.apache.commons.logging.LogFactory;"));
    // Log import replacement
    assertEquals("import org.slf4j.Logger;", jclMatcher
        .getReplacement("import org.apache.commons.logging.Log;"));
  }

  public void testLogFactoryGetLogReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // Logger declaration and instanciation without modifier
    assertEquals("  Logger   l = LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("  Log   l = LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("public Log mylog=LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with two modifier
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration",
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class); //logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclMatcher
            .getReplacement(" myLog = LogFactory.getLog(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("//public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclMatcher
            .getReplacement("// myLog = LogFactory.getLog(MyClass.class);//logger instanciation"));
  }

  public void testLogFactoryGetFactoryReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // Logger declaration and instanciation without modifier
    assertEquals(
        "Logger l = LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("Log l = LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with modifiers
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration",
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class); //logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclMatcher
            .getReplacement(" myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclMatcher
            .getReplacement("//public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclMatcher
            .getReplacement("// myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"));
  }

  public void testLogDeclarationReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // simple Logger declaration
    assertEquals("Logger mylog;", jclMatcher.getReplacement("Log mylog;"));
    // Logger declaration with a modifier
    assertEquals("private Logger mylog;", jclMatcher
        .getReplacement("private Log mylog;"));

    // Logger declaration with modifiers
    assertEquals("public static final Logger myLog;", jclMatcher
        .getReplacement("public static final Log myLog;"));
    // Logger declaration with modifiers and comment at the end of line
    assertEquals("public Logger myLog;//logger declaration", jclMatcher
        .getReplacement("public Log myLog;//logger declaration"));
    // commented Logger declaration
    assertEquals("//private Logger myLog;", jclMatcher
        .getReplacement("//private Log myLog;"));
  }

  public void testMultiLineReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // Logger declaration on a line
    assertEquals("protected Logger log =", jclMatcher
        .getReplacement("protected Log log ="));

    // Logger instanciation on the next line
    assertEquals(" LoggerFactory.getLogger(MyComponent.class);", jclMatcher
        .getReplacement(" LogFactory.getLog(MyComponent.class);"));
    // Logger declaration on a line
    assertEquals("protected Logger log ", jclMatcher
        .getReplacement("protected Log log "));
    // Logger instanciation on the next line
    assertEquals(
        " = LoggerFactory.getLogger(MyComponent.class);",
        jclMatcher
            .getReplacement(" = LogFactory.getFactory().getInstance(MyComponent.class);"));
  }
}
