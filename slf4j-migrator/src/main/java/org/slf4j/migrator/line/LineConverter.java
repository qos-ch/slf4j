package org.slf4j.migrator.line;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LineConverter {

  final RuleSet ruleSet;
  boolean atLeastOneMatchOccured = false;
  
  public LineConverter(RuleSet ruleSet) {
    this.ruleSet = ruleSet;
  }

  /**
   * Check if the specified text is matching some conversions rules. 
   * If a rule matches, ask for line replacement.
   * 
   * <p>In case no rule can be applied, then the input text is
   * returned without change.
   * 
   * @param text
   * @return String
   */
  public String[] getReplacement(String text) {
    ConversionRule conversionRule;
    Pattern pattern;
    Matcher matcher;
    Iterator<ConversionRule> conversionRuleIterator = ruleSet.iterator();
    String additionalLine = null;
    while (conversionRuleIterator.hasNext()) {
      conversionRule = conversionRuleIterator.next();
      pattern = conversionRule.getPattern();
      matcher = pattern.matcher(text);
      if (matcher.find()) {
        // System.out.println("matching " + text);
        atLeastOneMatchOccured = true;
        String replacementText = conversionRule.replace(matcher);
        text = matcher.replaceAll(replacementText);
        if(conversionRule.getAdditionalLine() != null) {
          additionalLine = conversionRule.getAdditionalLine();
        }
      }
    }
    
    if(additionalLine == null) {
      return new String[] {text};
    } else {
      return new String[] {text, additionalLine};
    }
  }

  public String getOneLineReplacement(String text) {
    String[] r = getReplacement(text);
    if(r.length != 1) {
      throw new IllegalStateException("Expecting a single string but got "+Arrays.toString(r));
    } else {
      return r[0];
    }
  }
  public boolean atLeastOneMatchOccured() {
    return atLeastOneMatchOccured;
  }
}
