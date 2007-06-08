package org.slf4j.converter;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

public abstract class AbstractMatcher {

  protected Logger logger;

  protected TreeMap<PatternWrapper, String> rulesMap;

  protected Writer writer;

  public AbstractMatcher() {
  }

  public static AbstractMatcher getMatcherImpl() {
    // TODO criterias
    return new JCLMatcher();
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  public void matches(String text) {
    PatternWrapper patternWrapper;
    Pattern pattern;
    Matcher matcher;
    String replacement;
    Iterator rulesIter = rulesMap.keySet().iterator();
    boolean found = false;
    while (rulesIter.hasNext()) {
      patternWrapper = (PatternWrapper) rulesIter.next();
      pattern = patternWrapper.getPattern();      
      matcher = pattern.matcher(text);
      if (matcher.matches()) {
        logger.info("match " + text);
        replacement = (String) rulesMap.get(patternWrapper);
        writer.rewrite(matcher, replacement);
        found = true;
        break;
      } 
//      else if (matcher.find()) {
//        logger.info("found " + text + " pattern " + pattern.toString());
//        replacement = (String) rulesMap.get(patternWrapper);
//        writer.rewrite(matcher, replacement);
//        found = true;
//        break;
//      }
    }
    if (!found) {
      writer.write(text);
    }
  }

  protected abstract void initRules();
}
