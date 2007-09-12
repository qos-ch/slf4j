package org.slf4j.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class AternativeApproach extends TestCase {

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
  
  
  public void test2() {
    Pattern pat = Pattern.compile("Log");
    String s = "abcd Log";
    Matcher m = pat.matcher(s);
    assertTrue(m.find());
    String r = m.replaceAll("LOGGER");
    assertEquals("abcd LOGGER", r);
  }
  
  public void test3() {
    Pattern pat = Pattern.compile("LogFactory.getFactory\\(\\).getInstance\\(");
    String s = "Log log =  LogFactory.getFactory().getInstance(\"x\");";
    Matcher m = pat.matcher(s);
    assertTrue(m.find());
    String r = m.replaceAll("LoggerFactory.getLogger(");
    assertEquals("Log log =  LoggerFactory.getLogger(\"x\");", r);
    
    String nonMatching = "Log log = xxx;";
    assertFalse(m.find());
  }
}
