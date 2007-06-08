package org.slf4j.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

public abstract class AbstractMatcher {

  protected Logger logger;

  protected HashMap<Pattern, String> rulesMap;

  protected Writer writer;

  public static AbstractMatcher getMatcherImpl() {
    // TODO criterias
    return new JCLMatcher();
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  public void matches(String text) {
    Pattern pattern;
    Matcher matcher;
    String replacement;
    Iterator rulesIter = rulesMap.keySet().iterator();
    boolean found = false;
    while (rulesIter.hasNext()) {      
      pattern = (Pattern) rulesIter.next();
      matcher = pattern.matcher(text);//
      if (matcher.find()) {        
        logger.info("found " + text);
        replacement = (String) rulesMap.get(pattern);
        writer.rewrite(matcher, replacement);        
        found = true;  
        break;
      }      
    }
    if(!found){
      writer.write(text);
    }
  }

  protected abstract void initRules();
}
