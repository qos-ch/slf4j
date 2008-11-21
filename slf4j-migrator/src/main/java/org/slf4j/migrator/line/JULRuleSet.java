package org.slf4j.migrator.line;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * This class represents java.util.logging (JUL) to SLF4J conversion rules
 * 
 * @author Jean-Noel Charpin
 * @author Ceki Gulcu
 */
public class JULRuleSet implements RuleSet {

  private ArrayList<ConversionRule> conversionRuleList;
  
  public JULRuleSet() {
  
    
    SingleConversionRule crImport0 = new SingleConversionRule(Pattern
        .compile("import\\s*+java.util.logging.Logger;"),
        "import org.slf4j.Logger;",
        "import org.slf4j.LoggerFactory;");
    
    SingleConversionRule crImport1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.LogManager;"),
        "import org.slf4j.LoggerFactory;");

    SingleConversionRule crImport2 = new SingleConversionRule(Pattern
        .compile("import\\s*+java.util.logging.*;"),
        "import org.slf4j.Logger;",
        "import org.slf4j.LoggerFactory;");


    SingleConversionRule crFactory0 = new SingleConversionRule(Pattern
        .compile("Logger.getLogger\\("), "LoggerFactory.getLogger(");

    SingleConversionRule crFactory1 = new SingleConversionRule(Pattern
        .compile("LogManager.getLogger\\("), "LoggerFactory.getLogger(");

    SingleConversionRule crWarning = new SingleConversionRule(Pattern
            .compile("\\.warning\\("), ".warn(");
    SingleConversionRule crSevere = new SingleConversionRule(Pattern
            .compile("\\.severe\\("), ".error(");
  
    
    conversionRuleList = new ArrayList<ConversionRule>();
    conversionRuleList.add(crImport0);
    conversionRuleList.add(crImport1);
    conversionRuleList.add(crImport2);
    conversionRuleList.add(crFactory0);
    conversionRuleList.add(crFactory1);
    conversionRuleList.add(crWarning);
    conversionRuleList.add(crSevere);
  }

  public Iterator<ConversionRule> iterator() {
    return conversionRuleList.iterator();
  }

}
