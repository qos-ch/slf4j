package org.slf4j.converter;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class runs Pattern matching with java.util.regex 
 * using Patterns defined in concretes implementations
 * 
 * @author jean-noelcharpin
 * 
 */
public abstract class AbstractMatcher {

  protected ArrayList<ConversionRule> rules;

  protected boolean blockComment = false;

  public AbstractMatcher() {
  }

  /**
   * Return matcher implementation depending on the conversion mode
   * @param conversionType
   * @return AbstractMatcher implementation
   */
  public static AbstractMatcher getMatcherImpl(int conversionType) {
    switch(conversionType){
    case Constant.JCL_TO_SLF4J :
      return new JCLMatcher();
    case Constant.LOG4J_TO_SLF4J :
      return new Log4jMatcher();
    default :  
      return null;
    }
  }
  

  /**
   * Check if the specified text is matching one of the conversion rules
   * If a rule is resolved, ask for replacement
   * If no rule can be applied the text is returned without change 
   * @param text
   * @return String 
   */
  public String getReplacement(String text) {
    ConversionRule conversionRule;
    Pattern pattern;
    Matcher matcher;
    Iterator rulesIter = rules.iterator();
    while (rulesIter.hasNext()) {
      conversionRule = (ConversionRule) rulesIter.next();
      pattern = conversionRule.getPattern();
      matcher = pattern.matcher(text);
      if (matcher.matches()) {
        System.out.println("matching " + text);
        return conversionRule.replace(matcher);
      }
    }
    return text;
  }

  protected abstract void initRules();
}
