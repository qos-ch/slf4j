package org.slf4j.converter;

import java.util.ArrayList;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class SimpleMatcherTest  extends TestCase {

  
  
  private class SimpleMatcher extends AbstractMatcher{
 
    protected void initRules() {
      //simple rule no capturing group is defined, we use default capturing group which is group zero
      ConversionRule cr = new ConversionRule(Pattern.compile("import org.slf4j.converter"));
      //we define an unique replacement text for group 0
      cr.addReplacement(Constant.INDEX_0, "simple replacement with an unique capturing group");
      
      //we define 4 differents capturing groups
      ConversionRule cr1 = new ConversionRule(Pattern.compile("(first group)( second group)( third group)( 4th group)"));
      //group zero is ignored during treatment
      //replacement for the first
      cr1.addReplacement(Constant.INDEX_1, "1st group");
      //no replacement for the second group it will remains the same
      //empty string for the third group it will be deleted
      cr1.addReplacement(Constant.INDEX_3, "");
      //no replacement for the third group it will remains the same
      
      rules = new ArrayList<ConversionRule>();
      rules.add(cr);
      rules.add(cr1);
    }
    
  }
  
  
  
  public void testSimpleReplacement() {
    SimpleMatcher simpleMatcher = new SimpleMatcher();
    simpleMatcher.initRules();
    
    assertEquals(simpleMatcher.getReplacement("no replacement for this text, it remains the same!"),"no replacement for this text, it remains the same!");
    
    assertEquals(simpleMatcher.getReplacement("import org.slf4j.converter"),"simple replacement with an unique capturing group");
    
    assertEquals(simpleMatcher.getReplacement("first group second group third group 4th group"),"1st group second group 4th group");
    
  }
  
  
}
