package org.slf4j.migrator.line;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;


public class Log4jRuleSet implements RuleSet {

  private ArrayList<ConversionRule> conversionRuleList;
  
  public Log4jRuleSet() {
  
    
    SingleConversionRule crImport0 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.Logger;"),
        "import org.slf4j.Logger;",
        "import org.slf4j.LoggerFactory;");
    
    SingleConversionRule crImport1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.LogManager;"),
        "import org.slf4j.LoggerFactory;");

    SingleConversionRule crImport2 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.*;"),
        "import org.slf4j.Logger;",
        "import org.slf4j.LoggerFactory;");

    SingleConversionRule crImportMDC = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.MDC;"),
        "import org.slf4j.MDC;");
  

    SingleConversionRule crFactory0 = new SingleConversionRule(Pattern
        .compile("Logger.getLogger\\("), "LoggerFactory.getLogger(");

    SingleConversionRule crFactory1 = new SingleConversionRule(Pattern
        .compile("LogManager.getLogger\\("), "LoggerFactory.getLogger(");

    conversionRuleList = new ArrayList<ConversionRule>();
    conversionRuleList.add(crImport0);
    conversionRuleList.add(crImport1);
    conversionRuleList.add(crImport2);
    conversionRuleList.add(crImportMDC);
    conversionRuleList.add(crFactory0);
    conversionRuleList.add(crFactory1);
  }

  public Iterator<ConversionRule> iterator() {
    return conversionRuleList.iterator();
  }

}
