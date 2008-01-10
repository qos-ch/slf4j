package org.slf4j.converter.line;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;


public class Log4jRuleSet implements RuleSet {

  private ArrayList<ConversionRule> conversionRuleList;
  
  public Log4jRuleSet() {
  
    SingleConversionRule cr0 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.Logger;"),
        "import org.slf4j.Logger;",
        "import org.slf4j.LoggerFactory;");
    
    SingleConversionRule cr1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.LogManager;"),
        "import org.slf4j.LoggerFactory;");

  

    SingleConversionRule cr2 = new SingleConversionRule(Pattern
        .compile("Logger.getLogger\\("), "LoggerFactory.getLogger(");

    SingleConversionRule cr3 = new SingleConversionRule(Pattern
        .compile("LogManager.getLogger\\("), "LoggerFactory.getLogger(");

    conversionRuleList = new ArrayList<ConversionRule>();
    conversionRuleList.add(cr0);
    conversionRuleList.add(cr1);
    conversionRuleList.add(cr2);
    conversionRuleList.add(cr3);
  }

  public Iterator<ConversionRule> iterator() {
    return conversionRuleList.iterator();
  }

}
