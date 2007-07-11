package org.slf4j.converter;

import java.util.TreeMap;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

public class JCLMatcher extends AbstractMatcher {

   private static PatternWrapper p0 =  new PatternWrapper("0",Pattern.compile("import\\s*+org.apache.commons.logging.LogFactory;"));
   private static PatternWrapper p1 =  new PatternWrapper("1",Pattern.compile("import\\s*+org.apache.commons.logging.Log;"));
   private static PatternWrapper p2 =  new PatternWrapper("2",Pattern.compile("(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)"));
   private static PatternWrapper p3 =  new PatternWrapper("3",Pattern.compile("(Log)(\\s+\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)"));
   private static PatternWrapper p4 =  new PatternWrapper("4",Pattern.compile("(\\w+\\s*+=\\s*+)(LogFactory.getFactory\\(\\).getInstance\\()(\\w+)(.class\\);)"));
   private static PatternWrapper p5 =  new PatternWrapper("5",Pattern.compile("(\\w+\\s*+=\\s*+)(LogFactory.getLog\\()(\\w+)(.class\\);)"));
   private static PatternWrapper p6 =  new PatternWrapper("6",Pattern.compile("(Log)(\\s+(\\w+)\\s*;)"));
//   private static PatternWrapper p8 = new PatternWrapper("8",Pattern.compile("fatal\\("));
//   private static PatternWrapper p9 = new PatternWrapper("9",Pattern.compile("isFatalEnabled\\("));
 
   private static ReplacementWrapper r0 = new ReplacementWrapper("0","import org.slf4j.LoggerFactory;");
   private static ReplacementWrapper r1 = new ReplacementWrapper("0","import org.slf4j.Logger;");
   private static ReplacementWrapper r2 = new ReplacementWrapper("1","Logger");
   static{
	   r2.addReplacement("3", "LoggerFactory.getLogger(");
   }
   private static ReplacementWrapper r3 = new ReplacementWrapper("2","LoggerFactory.getLogger(");
   private static ReplacementWrapper r4 = new ReplacementWrapper("1", "Logger");
   
   
  public JCLMatcher() {
    super();
    logger = LoggerFactory.getLogger(JCLMatcher.class);    
    initRules();
  }

  protected void initRules() {    
    rulesMap = new TreeMap<PatternWrapper, ReplacementWrapper>();
    rulesMap.put(p0,r0);
    rulesMap.put(p1,r1);   
    rulesMap.put(p2,r2);
    rulesMap.put(p3,r2);
    rulesMap.put(p4,r3);
    rulesMap.put(p5,r3);
    rulesMap.put(p6,r4);
  }
}
