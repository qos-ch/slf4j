package org.slf4j.migrator;

import org.slf4j.migrator.line.EmptyRuleSet;
import org.slf4j.migrator.line.JCLRuleSet;
import org.slf4j.migrator.line.JULRuleSet;
import org.slf4j.migrator.line.Log4jRuleSet;
import org.slf4j.migrator.line.RuleSet;

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
    case Constant.JUL_TO_SLF4J:
        return new JULRuleSet();
    case Constant.NOP_TO_SLF4J:
      return new EmptyRuleSet();
    default:
      return null;
    }
  }
}
