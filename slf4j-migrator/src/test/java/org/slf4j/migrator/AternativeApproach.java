package org.slf4j.migrator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.migrator.line.MultiGroupConversionRule;

import junit.framework.TestCase;

public class AternativeApproach extends TestCase {

  /**
   * In this test we see that we cans use more simple Pattern to do the
   * conversion
   * 
   */
  public void test() {
    MultiGroupConversionRule cr2 = new MultiGroupConversionRule(Pattern
        .compile("(.*)(Log)"));
    cr2.addReplacement(2, "LOGGER");

    String s = "abcd Log";
    Pattern pat = cr2.getPattern();
    Matcher m = pat.matcher(s);

    assertTrue(m.matches());
    String r = cr2.replace(m);
    assertEquals("abcd LOGGER", r);

    System.out.println(r);
  }

  /**
   * In this test we replace, using the simple Pattern (Log), the full Log
   * declaration and instanciation. This is not convenient because we will also
   * replace all String containing "Log".
   */
  public void test2() {
    Pattern pat = Pattern.compile("(Log)");
    String s = "abcd Log =";
    Matcher m = pat.matcher(s);
    assertTrue(m.find());
    String r = m.replaceAll("Logger");
    assertEquals("abcd Logger =", r);

    String s1 = "Log l = LogFactory.getLog(MyClass.class);";
    m = pat.matcher(s1);
    assertTrue(m.find());
    r = m.replaceAll("Logger");
    assertEquals("Logger l = LoggerFactory.getLogger(MyClass.class);", r);

    String s2 = "Logabc ";
    m = pat.matcher(s2);
    assertTrue(m.find());

    String s3 = "abcLog";
    m = pat.matcher(s3);
    assertTrue(m.find());
  }

  /**
   * In this test we use a simple Pattern to replace the log instanciation
   * without influence on Log declaration.
   * 
   */
  public void test3() {
    Pattern pat = Pattern.compile("LogFactory.getFactory\\(\\).getInstance\\(");
    String s = "Log log =  LogFactory.getFactory().getInstance(\"x\");";
    Matcher m = pat.matcher(s);
    assertTrue(m.find());
    String r = m.replaceAll("LoggerFactory.getLogger(");
    assertEquals("Log log =  LoggerFactory.getLogger(\"x\");", r);

    String nonMatching = "Log log = xxx;";
    pat.matcher(nonMatching);
    assertFalse(m.find());
  }

  /**
   * In this test we try to replace keyword Log without influence on String
   * containg Log We see that we have to use two differents Patterns
   */
  public void test4() {
    Pattern pat = Pattern.compile("(\\sLog\\b)");
    String s = "abcd Log =";
    Matcher m = pat.matcher(s);
    assertTrue(m.find());
    String r = m.replaceAll(" Logger");
    assertEquals("abcd Logger =", r);

    String s2 = "Logabcd ";
    m = pat.matcher(s2);
    assertFalse(m.find());

    String s3 = "abcdLogabcd ";
    m = pat.matcher(s3);
    assertFalse(m.find());

    String s4 = "abcdLog";
    m = pat.matcher(s4);
    assertFalse(m.find());

    String s5 = "Log myLog";
    m = pat.matcher(s5);
    assertFalse(m.find());

    Pattern pat2 = Pattern.compile("^Log\\b");
    Matcher m2 = pat2.matcher(s5);
    assertTrue(m2.find());
    r = m2.replaceAll("Logger");
    assertEquals("Logger myLog", r);
  }

  /**
   * In this test we combine two Pattern to achieve the intended conversion
   */
  public void test5() {
    Pattern pat = Pattern.compile("(\\sLog\\b)");
    String s = "public Log myLog =LogFactory.getFactory().getInstance(myClass.class);";
    Matcher m = pat.matcher(s);
    assertTrue(m.find());
    String r = m.replaceAll(" Logger");
    assertEquals("public Logger myLog =LogFactory.getFactory().getInstance(myClass.class);", r);

    Pattern pat2 = Pattern.compile("LogFactory.getFactory\\(\\).getInstance\\(");
    m = pat2.matcher(r);
    assertTrue(m.find());
    r = m.replaceAll("LoggerFactory.getLogger(");
    assertEquals("public Logger myLog =LoggerFactory.getLogger(myClass.class);", r);
  }
}
