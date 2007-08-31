package org.slf4j.converter;

import java.io.IOException;

import junit.framework.TestCase;

public class JCLMatcherTest extends TestCase {

  public void testImportReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // LogFactory import replacement
    assertEquals(jclMatcher
        .getReplacement("import org.apache.commons.logging.LogFactory;"),
        "import org.slf4j.LoggerFactory;");
    // Log import replacement
    assertEquals(jclMatcher
        .getReplacement("import org.apache.commons.logging.Log;"),
        "import org.slf4j.Logger;");
  }

  public void testLogFactoryGetLogReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // Logger declaration and instanciation without modifier
    assertEquals(jclMatcher
        .getReplacement("Log l = LogFactory.getLog(MyClass.class);"),
        "Logger l = LoggerFactory.getLogger(MyClass.class);");
    // Logger declaration and instanciation with one modifier
    assertEquals(jclMatcher
        .getReplacement("public Log mylog=LogFactory.getLog(MyClass.class);"),
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);");
    // Logger declaration and instanciation with two modifier
    assertEquals(
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"),
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);");
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class); //logger instanciation and declaration"),
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration");
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        jclMatcher
            .getReplacement(" myLog = LogFactory.getLog(MyClass.class);//logger instanciation"),
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation");
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        jclMatcher
            .getReplacement("//public static Log mylog1 = LogFactory.getLog(MyClass.class);"),
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);");
    // commented Logger instanciation without declaration
    assertEquals(
        jclMatcher
            .getReplacement("// myLog = LogFactory.getLog(MyClass.class);//logger instanciation"),
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation");
  }

  public void testLogFactoryGetFactoryReplacement() {
    JCLMatcher jclMatcher = new JCLMatcher();
    // Logger declaration and instanciation without modifier
    assertEquals(
        jclMatcher
            .getReplacement("Log l = LogFactory.getFactory().getInstance(MyClass.class);"),
        "Logger l = LoggerFactory.getLogger(MyClass.class);");
    // Logger declaration and instanciation with one modifier
    assertEquals(
        jclMatcher
            .getReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"),
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);");
    // Logger declaration and instanciation with modifiers
    assertEquals(
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"),
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);");
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        jclMatcher
            .getReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class); //logger instanciation and declaration"),
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration");
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        jclMatcher
            .getReplacement(" myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"),
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation");
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        jclMatcher
            .getReplacement("//public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"),
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);");
    // commented Logger instanciation without declaration
    assertEquals(
        jclMatcher
            .getReplacement("// myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"),
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation");
  }

  public void testLogDeclarationReplacement() throws IOException {
    JCLMatcher jclMatcher = new JCLMatcher();
    // simple Logger declaration
    assertEquals(jclMatcher.getReplacement("Log mylog;"), "Logger mylog;");
    // Logger declaration with a modifier
    assertEquals(jclMatcher.getReplacement("private Log mylog;"),
        "private Logger mylog;");
    // Logger declaration with modifiers
    assertEquals(jclMatcher.getReplacement("public static final Log myLog;"),
        "public static final Logger myLog;");
    // Logger declaration with modifiers and comment at the end of line
    assertEquals(jclMatcher
        .getReplacement("public Log myLog;//logger declaration"),
        "public Logger myLog;//logger declaration");
    // commented Logger declaration
    assertEquals(jclMatcher.getReplacement("//private Log myLog;"),
        "//private Logger myLog;");
  }

  public void testMultiLineReplacement() throws IOException {
    JCLMatcher jclMatcher = new JCLMatcher();
    // Logger declaration on a line
    assertEquals(jclMatcher.getReplacement("protected Log log ="),
        "protected Logger log =");
    // Logger instanciation on the next line
    assertEquals(jclMatcher
        .getReplacement(" LogFactory.getLog(MyComponent.class);"),
        " LoggerFactory.getLogger(MyComponent.class);");
    // Logger declaration on a line
    assertEquals(jclMatcher.getReplacement("protected Log log "),
        "protected Logger log ");
    // Logger instanciation on the next line
    assertEquals(
        jclMatcher
            .getReplacement(" = LogFactory.getFactory().getInstance(MyComponent.class);"),
        " = LoggerFactory.getLogger(MyComponent.class);");
  }

}
