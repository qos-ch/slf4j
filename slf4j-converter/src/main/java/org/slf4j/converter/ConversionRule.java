package org.slf4j.converter;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a conversion rule It uses a Pattern and defines for
 * each capturing group of this Pattern a replacement text
 * 
 * @author jean-noelcharpin
 * 
 */
public class ConversionRule {

  private Pattern pattern;

  private HashMap<Integer, String> replacementMap;

  public ConversionRule(Pattern pattern) {
    this.pattern = pattern;
    this.replacementMap = new HashMap<Integer, String>();
  }

  public Pattern getPattern() {
    return pattern;
  }

  public void addReplacement(Integer groupIndex, String replacement) {
    replacementMap.put(groupIndex, replacement);
  }

  public String getReplacement(Integer groupIndex) {
    return replacementMap.get(groupIndex);
  }

  /**
   * Given replacement rules, replace each capturing group in matcher's pattern
   * 
   * @param matcher
   * @return String
   */
  public String replace(Matcher matcher) {
    StringBuffer replacementBuffer = new StringBuffer();
    String replacementText;
    for (int group = 0; group <= matcher.groupCount(); group++) {
      replacementText = getReplacement(group);
      if (replacementText != null) {
        System.out.println("replacing group " + group + " : "
            + matcher.group(group) + " with " + replacementText);
        replacementBuffer.append(replacementText);
      } else if (group > 0) {
        replacementBuffer.append(matcher.group(group));
      }
    }
    return replacementBuffer.toString();
  }
}
