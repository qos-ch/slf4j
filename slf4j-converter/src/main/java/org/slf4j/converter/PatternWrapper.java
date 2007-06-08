package org.slf4j.converter;

import java.util.regex.Pattern;

public class PatternWrapper implements Comparable {

  private String  index;
  private Pattern pattern;
  
  public PatternWrapper(String index, Pattern pattern){
    this.index = index;
    this.pattern = pattern;
  }
  
  public String getIndex(){
    return index;
  }
  
  public Pattern getPattern(){
    return pattern;
  }

  public int compareTo(Object o) {    
    String oIndex = ((PatternWrapper)o).index;  
    return this.index.compareTo(oIndex);
  }
}
