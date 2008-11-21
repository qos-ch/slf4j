package org.slf4j.migrator.line;

import java.util.Iterator;


public interface RuleSet {

  Iterator<ConversionRule> iterator();
  
}
