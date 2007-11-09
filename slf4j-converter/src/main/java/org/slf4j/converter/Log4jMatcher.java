package org.slf4j.converter;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Log4jMatcher extends AbstractMatcher {

  public Log4jMatcher(){
    super();
    initRules();
  }
  
  @Override
  protected void initRules() {
    SingleConversionRule cr0 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.LogManager;"),
        "import org.slf4j.LoggerFactory;");

    SingleConversionRule cr1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.log4j.Logger;"),
        "import org.slf4j.Logger;");

    SingleConversionRule cr2 = new SingleConversionRule(Pattern
        .compile("Logger.getLogger\\("), "LoggerFactory.getLogger(");

    SingleConversionRule cr3 = new SingleConversionRule(Pattern
        .compile("LogManager.getLogger\\("), "LoggerFactory.getLogger(");

    rules = new ArrayList<ConversionRule>();
    rules.add(cr0);
    rules.add(cr1);
    rules.add(cr2);
    rules.add(cr3);
  }

}
