/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.migrator.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class represents a conversion rule It uses a Pattern and defines for
 * each capturing group of this Pattern a replacement text
 * 
 * @author jean-noelcharpin
 * 
 */
public class MultiGroupConversionRule implements ConversionRule {
 
  // It is extremely unlikely to encounter more than 10 groups in one of 
  // our conversion reg-expressions
  final private static int MAX_GROUPS = 10;

  private Pattern pattern;
  private String[] replacementTable = new String[MAX_GROUPS];

  public MultiGroupConversionRule(Pattern pattern) {
    this.pattern = pattern;
  }

  /* (non-Javadoc)
   * @see org.slf4j.converter.ConversionRule#getPattern()
   */
  public Pattern getPattern() {
    return pattern;
  }

  public void addReplacement(int groupIndex, String replacement) {
    if(groupIndex == 0) {
      throw new IllegalArgumentException("regex groups start at 1, not zero");
    }
    replacementTable[groupIndex] = replacement;
  }

  /* (non-Javadoc)
   * @see org.slf4j.converter.ConversionRule#getReplacement(java.lang.Integer)
   */
  public String getReplacement(int groupIndex) {
    return  replacementTable[groupIndex];
  }

  /* (non-Javadoc)
   * @see org.slf4j.converter.ConversionRule#replace(java.util.regex.Matcher)
   */
  public String replace(Matcher matcher) {
    StringBuilder replacementBuffer = new StringBuilder();
    String replacementText;
    
    for (int group = 1; group <= matcher.groupCount(); group++) {
      replacementText = getReplacement(group);
      if (replacementText != null) {
        //System.out.println("replacing group " + group + " : "
        //    + matcher.group(group) + " with " + replacementText);
        replacementBuffer.append(replacementText);
      } else  {
        replacementBuffer.append(matcher.group(group));
      }
    }
    return replacementBuffer.toString();
  }

  public String getAdditionalLine() {
    return null;
  }
}
