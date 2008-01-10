package org.slf4j.converter.line;

import org.slf4j.converter.line.JCLRuleSet;
import org.slf4j.converter.line.LineConverter;

import junit.framework.TestCase;

public class JCLRuleSetTest extends TestCase {

  LineConverter jclConverter = new LineConverter(new JCLRuleSet());
  
  public void testImportReplacement() {
    // LogFactory import replacement
    assertEquals("import org.slf4j.LoggerFactory;", jclConverter
        .getReplacement("import org.apache.commons.logging.LogFactory;"));
    // Log import replacement
    assertEquals("import org.slf4j.Logger;", jclConverter
        .getReplacement("import org.apache.commons.logging.Log;"));
  }

  public void testLogFactoryGetLogReplacement() {
    // Logger declaration and instanciation without modifier
    assertEquals("  Logger   l = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("  Log   l = LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("public Log mylog=LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with two modifier
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration",
        jclConverter
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class); //logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getReplacement(" myLog = LogFactory.getLog(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("//public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getReplacement("// myLog = LogFactory.getLog(MyClass.class);//logger instanciation"));
  }

  public void testLogFactoryGetFactoryReplacement() {
   
    // Logger declaration and instanciation without modifier
    assertEquals(
        "Logger l = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("Log l = LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with modifiers
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration",
        jclConverter
            .getReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class); //logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getReplacement(" myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getReplacement("//public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getReplacement("// myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"));
  }

  public void testLogDeclarationReplacement() {
   
    // simple Logger declaration
    assertEquals("Logger mylog;", jclConverter.getReplacement("Log mylog;"));
    // Logger declaration with a modifier
    assertEquals("private Logger mylog;", jclConverter
        .getReplacement("private Log mylog;"));

    // Logger declaration with modifiers
    assertEquals("public static final Logger myLog;", jclConverter
        .getReplacement("public static final Log myLog;"));
    // Logger declaration with modifiers and comment at the end of line
    assertEquals("public Logger myLog;//logger declaration", jclConverter
        .getReplacement("public Log myLog;//logger declaration"));
    // commented Logger declaration
    assertEquals("//private Logger myLog;", jclConverter
        .getReplacement("//private Log myLog;"));
  }

  public void testMultiLineReplacement() {
    // Logger declaration on a line
    assertEquals("protected Logger log =", jclConverter
        .getReplacement("protected Log log ="));

    // Logger instanciation on the next line
    assertEquals(" LoggerFactory.getLogger(MyComponent.class);", jclConverter
        .getReplacement(" LogFactory.getLog(MyComponent.class);"));
    // Logger declaration on a line
    assertEquals("protected Logger log ", jclConverter
        .getReplacement("protected Log log "));
    // Logger instanciation on the next line
    assertEquals(
        " = LoggerFactory.getLogger(MyComponent.class);",
        jclConverter
            .getReplacement(" = LogFactory.getFactory().getInstance(MyComponent.class);"));
  }
}
