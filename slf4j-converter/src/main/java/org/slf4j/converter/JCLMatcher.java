package org.slf4j.converter;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This class represents JCL to SLF4J conversion rules
 * 
 * @author jean-noelcharpin
 * 
 */
public class JCLMatcher extends AbstractMatcher {

  public JCLMatcher() {
    super();
    initRules();
  }

  protected void initRules() {
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
    

    rules = new ArrayList<ConversionRule>();
    rules.add(cr0);
    rules.add(cr1);
    rules.add(cr2);
    rules.add(cr3);
    rules.add(cr4);
    rules.add(cr5);
  }
}
