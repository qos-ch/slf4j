package org.slf4j.converter;

import java.io.IOException;

import junit.framework.TestCase;

public class JCLMatcherTest extends TestCase {

  public void testConversion() throws IOException {
    AbstractMatcher jclMatcher = AbstractMatcher
        .getMatcherImpl(Constant.JCL_TO_SLF4J);
    jclMatcher.setCommentConversion(true);

    assertEquals(jclMatcher
        .replace("import org.apache.commons.logging.LogFactory;"),
        "import org.slf4j.LoggerFactory;");
    assertEquals(jclMatcher.replace("import org.apache.commons.logging.Log;"),
        "import org.slf4j.Logger;");
    assertEquals(jclMatcher
        .replace("Log l = LogFactory.getLog(MyClass.class);"),
        "Logger l = LoggerFactory.getLogger(MyClass.class);");
    assertEquals(jclMatcher
        .replace("public Log mylog=LogFactory.getLog(MyClass.class);"),
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);");
    assertEquals(
        jclMatcher
            .replace("public static Log mylog1 = LogFactory.getLog(MyClass.class);"),
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);");
    assertEquals(
        jclMatcher
            .replace("Log log3=LogFactory.getFactory().getInstance(MyClass.class);"),
        "Logger log3=LoggerFactory.getLogger(MyClass.class);");
    assertEquals(
        jclMatcher
            .replace("Log mylog4 = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"),
        "Logger mylog4 = LoggerFactory.getLogger(MyClass.class);//logger instanciation");
    assertEquals(jclMatcher.replace("Log myLog6;//logger declaration"),
        "Logger myLog6;//logger declaration");
    assertEquals(jclMatcher
        .replace("//log7=LogFactory.getFactory().getInstance(MyClass.class);"),
        "//log7=LoggerFactory.getLogger(MyClass.class);");
    assertEquals(jclMatcher
        .replace(" log8 =LogFactory.getFactory().getInstance(MyClass.class);"),
        " log8 =LoggerFactory.getLogger(MyClass.class);");
    assertEquals(jclMatcher
        .replace(" myLog9 = LogFactory.getLog(MyClass.class);"),
        " myLog9 = LoggerFactory.getLogger(MyClass.class);");
    assertEquals(jclMatcher.replace("private Log mylog10;"),
        "private Logger mylog10;");
    assertEquals(jclMatcher.replace("protected final Log myLog11;"),
        "protected final Logger myLog11;");
    assertEquals(jclMatcher.replace("public static final Log myLog12;"),
        "public static final Logger myLog12;");
    assertEquals(
        jclMatcher
            .replace("System.out.println(\"Log\") ;System.out.println(\"Log2\");  public static final Log myLog13;"),
        "System.out.println(\"Log\") ;System.out.println(\"Log2\");  public static final Logger myLog13;");
    assertEquals(
        jclMatcher
            .replace("public static final Log myLog14;System.out.println(\"Log\");"),
        "public static final Logger myLog14;System.out.println(\"Log\");");
    assertEquals(
        jclMatcher
            .replace("System.out.println(\"\");public static final Log myLog15;System.out.println(\"Log\")  ;System.out.println(\"Log2\");"),
        "System.out.println(\"\");public static final Logger myLog15;System.out.println(\"Log\")  ;System.out.println(\"Log2\");");
    assertEquals(
        jclMatcher
            .replace("((Pojo)pojo.getPojo()).get(\"pojo\",pojo);public static final Log myLog16;"),
        "((Pojo)pojo.getPojo()).get(\"pojo\",pojo);public static final Logger myLog16;");
    assertEquals(jclMatcher.replace("protected Log log ="),
        "protected Logger log =");
    assertEquals(jclMatcher
        .replace("	    LogFactory.getLog(MyComponent.class);"),
        "	    LoggerFactory.getLogger(MyComponent.class);");
    assertEquals(jclMatcher.replace("protected Log log "),
        "protected Logger log ");
    assertEquals(
        jclMatcher
            .replace(" =	    LogFactory.getFactory().getInstance(MyComponent.class);"),
        " =	    LoggerFactory.getLogger(MyComponent.class);");
  }

}
