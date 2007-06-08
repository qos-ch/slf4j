package org.slf4j.converter;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

public class JCLMatcher extends AbstractMatcher {

  public JCLMatcher() {
    super();
    logger = LoggerFactory.getLogger(JCLMatcher.class);
    initRules();
  }

  protected void initRules() {
    rulesMap = new HashMap<Pattern, String>();
    rulesMap.put(Pattern.compile("import org.slf4j.Logger;"), "import org.toto.Log;");
    rulesMap.put(Pattern.compile("Logger"), "Log");    
  }
}
