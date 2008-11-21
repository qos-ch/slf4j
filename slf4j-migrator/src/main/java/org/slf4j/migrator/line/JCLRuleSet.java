package org.slf4j.migrator.line;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;


/**
 * This class represents JCL to SLF4J conversion rules
 * 
 * @author Jean-Noel Charpin
 * 
 */
public class JCLRuleSet implements RuleSet {

  private ArrayList<ConversionRule> conversionRuleList;
  
  public JCLRuleSet() {
    // matching : import org.apache.commons.logging.LogFactory;
    SingleConversionRule cr0 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.LogFactory;"),
        "import org.slf4j.LoggerFactory;");

    // matching : import org.apache.commons.logging.Log;
    SingleConversionRule cr1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.Log;"), 
        "import org.slf4j.Logger;");
    
    SingleConversionRule cr2 = new SingleConversionRule(Pattern
        .compile("(\\sLog\\b)")," Logger");
    
    SingleConversionRule cr3 = new SingleConversionRule(Pattern
        .compile("(^Log\\b)"),"Logger");
    
    SingleConversionRule cr4 = new SingleConversionRule(Pattern
        .compile("LogFactory.getFactory\\(\\).getInstance\\("),
            "LoggerFactory.getLogger(");

    SingleConversionRule cr5 = new SingleConversionRule(Pattern
        .compile("LogFactory.getLog\\("),"LoggerFactory.getLogger(");
    

    conversionRuleList = new ArrayList<ConversionRule>();
    conversionRuleList.add(cr0);
    conversionRuleList.add(cr1);
    conversionRuleList.add(cr2);
    conversionRuleList.add(cr3);
    conversionRuleList.add(cr4);
    conversionRuleList.add(cr5);
  }


  public Iterator<ConversionRule> iterator() {
    return conversionRuleList.iterator();
  }
}
