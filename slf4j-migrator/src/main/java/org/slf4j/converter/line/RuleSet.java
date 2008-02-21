package org.slf4j.converter.line;

import java.util.Iterator;


public interface RuleSet {

  Iterator<ConversionRule> iterator();
  
}
