package org.slf4j.converter;

import org.slf4j.converter.line.EmptyRuleSet;
import org.slf4j.converter.line.JCLRuleSet;
import org.slf4j.converter.line.Log4jRuleSet;
import org.slf4j.converter.line.RuleSet;

/**
 * This class runs Pattern matching with java.util.regex using Patterns defined
 * in concrete implementations
 * 
 * @author jean-noelcharpin
 * 
 */
public abstract class RuleSetFactory {

   /**
   * Return matcher implementation depending on the conversion mode
   * 
   * @param conversionType
   * @return AbstractMatcher implementation
   */
  public static RuleSet getMatcherImpl(int conversionType) {
    switch (conversionType) {
    case Constant.JCL_TO_SLF4J:
      return new JCLRuleSet();
    case Constant.LOG4J_TO_SLF4J:
      return new Log4jRuleSet();
    case Constant.EMPTY_RULE_SET:
      return new EmptyRuleSet();
    default:
      return null;
    }
  }
}
