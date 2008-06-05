package org.slf4j.migrator.line;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmptyRuleSet implements RuleSet {

  List<ConversionRule> list = new ArrayList<ConversionRule>();
  
  public Iterator<ConversionRule> iterator() {
    return list.iterator();
  }
  
}
