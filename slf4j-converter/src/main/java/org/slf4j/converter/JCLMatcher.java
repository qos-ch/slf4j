package org.slf4j.converter;

import java.util.TreeMap;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

public class JCLMatcher extends AbstractMatcher {

  private static PatternWrapper p1 =  new PatternWrapper("1",Pattern.compile("import org.apache.commons.logging.LogFactory;"));
  private static PatternWrapper p2 =  new PatternWrapper("2",Pattern.compile("import org.apache.commons.logging.Log"));
  private static PatternWrapper p3 =  new PatternWrapper("3",Pattern.compile("LogFactory.getFactory().getInstance"));
  private static PatternWrapper p4 =  new PatternWrapper("4",Pattern.compile("LogFactory.getLog"));
  private static PatternWrapper p5 =  new PatternWrapper("5",Pattern.compile("Log"));
//private static Pattern p6 = new PatternWrapper("6",Pattern.compile("fatal\("));
//private static Pattern p7 = new PatternWrapper("7",Pattern.compile("isFatalEnabled\("));
  
  public JCLMatcher() {
    super();
    logger = LoggerFactory.getLogger(JCLMatcher.class);    
    initRules();
  }

  protected void initRules() {    
    rulesMap = new TreeMap<PatternWrapper, String>();
    rulesMap.put(p1,"import org.slf4j.LoggerFactory;");
    rulesMap.put(p2,"import org.slf4j.Logger;");    
    rulesMap.put(p3,"LoggerFactory.getLogger");
    rulesMap.put(p4,"LoggerFactory.getLogger");
    rulesMap.put(p5,"Logger");
//    rulesMap.put(p6,"error(");
//    rulesMap.put(p7,"isErrorEnabled(");    
  }
}
