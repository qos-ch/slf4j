package org.slf4j.migrator.line;

import java.util.Arrays;

import org.slf4j.migrator.line.LineConverter;
import org.slf4j.migrator.line.Log4jRuleSet;

import junit.framework.TestCase;

public class Log4jRuleSetTest extends TestCase {

  LineConverter log4jConverter = new LineConverter(new Log4jRuleSet());
  
  public void testImportReplacement() {
    // LogFactory import replacement
    assertEquals("import org.slf4j.LoggerFactory;", log4jConverter
        .getOneLineReplacement("import org.apache.log4j.LogManager;"));
    // Log import replacement
    assertTrue(Arrays.equals( 
        new String[] {"import org.slf4j.Logger;", "import org.slf4j.LoggerFactory;" },
        log4jConverter.getReplacement("import org.apache.log4j.Logger;")));
  }

  public void testLogManagerGetLoggerReplacement() {
    // Logger declaration and instanciation without modifier
    assertEquals(" Logger l = LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement(" Logger l = LogManager.getLogger(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("public Logger mylog=LogManager.getLogger(MyClass.class);"));
    // Logger declaration and instanciation with two modifier
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("public static Logger mylog1 = LogManager.getLogger(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);//logger instanciation and declaration",
        log4jConverter
            .getOneLineReplacement("public static Logger mylog1 = LogManager.getLogger(MyClass.class);//logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        log4jConverter
            .getOneLineReplacement(" myLog = LogManager.getLogger(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("//public static Logger mylog1 = LogManager.getLogger(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        log4jConverter
            .getOneLineReplacement("// myLog = LogManager.getLogger(MyClass.class);//logger instanciation"));
  }

  public void testLoggerGetLoggerReplacement() {
    // Logger declaration and instanciation without modifier
    assertEquals("Logger l = LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("Logger l = Logger.getLogger(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("public Logger mylog=Logger.getLogger(MyClass.class);"));
    // Logger declaration and instanciation with modifiers
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("public static Logger mylog1 = Logger.getLogger(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); // logger instanciation and declaration",
        log4jConverter
            .getOneLineReplacement("public static Logger mylog1 = Logger.getLogger(MyClass.class); // logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        log4jConverter
            .getOneLineReplacement(" myLog = Logger.getLogger(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        log4jConverter
            .getOneLineReplacement("//public static Logger mylog1 = Logger.getLogger(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        log4jConverter
            .getOneLineReplacement("// myLog = Logger.getLogger(MyClass.class);//logger instanciation"));
  }

  public void testLogDeclarationReplacement() {
    // simple Logger declaration
    assertEquals("Logger mylog;", log4jConverter.getOneLineReplacement("Logger mylog;"));
    // Logger declaration with a modifier
    assertEquals("private Logger mylog;", log4jConverter
        .getOneLineReplacement("private Logger mylog;"));

    // Logger declaration with modifiers
    assertEquals("public static final Logger myLog;", log4jConverter
        .getOneLineReplacement("public static final Logger myLog;"));
    // Logger declaration with modifiers and comment at the end of line
    assertEquals("public Logger myLog;//logger declaration", log4jConverter
        .getOneLineReplacement("public Logger myLog;//logger declaration"));
    // commented Logger declaration
    assertEquals("//private Logger myLog;", log4jConverter
        .getOneLineReplacement("//private Logger myLog;"));
  }

  public void testMultiLineReplacement()  {
   // Logger declaration on a line
   assertEquals("protected Logger log =", log4jConverter
   .getOneLineReplacement("protected Logger log ="));
    
   // Logger instanciation on the next line
   assertEquals(" LoggerFactory.getLogger(MyComponent.class);", log4jConverter
   .getOneLineReplacement(" LogManager.getLogger(MyComponent.class);"));
   // Logger declaration on a line
   assertEquals("protected Logger log ", log4jConverter
   .getOneLineReplacement("protected Logger log "));
   // Logger instanciation on the next line
   assertEquals(
   " = LoggerFactory.getLogger(MyComponent.class);",
   log4jConverter
   .getOneLineReplacement(" = LogManager.getLogger(MyComponent.class);"));
   }
}
