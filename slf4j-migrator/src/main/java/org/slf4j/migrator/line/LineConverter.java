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

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LineConverter {

  final RuleSet ruleSet;
  boolean atLeastOneMatchOccured = false;
  
  public LineConverter(RuleSet ruleSet) {
    this.ruleSet = ruleSet;
  }

  /**
   * Check if the specified text is matching some conversions rules. 
   * If a rule matches, ask for line replacement.
   * 
   * <p>In case no rule can be applied, then the input text is
   * returned without change.
   * 
   * @param text
   * @return String
   */
  public String[] getReplacement(String text) {
    ConversionRule conversionRule;
    Iterator<ConversionRule> conversionRuleIterator = ruleSet.iterator();
    String additionalLine = null;
    while (conversionRuleIterator.hasNext()) {
      conversionRule = conversionRuleIterator.next();
      String[] replacementLines = conversionRule.getReplacement(text);
      if(!text.equals(replacementLines[0])) {
        atLeastOneMatchOccured = true;
        text = replacementLines[0];
        if(replacementLines.length > 1) {
          additionalLine = replacementLines[1];
        }
      }
    }

    if(additionalLine == null) {
      return new String[] {text};
    } else {
      return new String[] {text, additionalLine};
    }
  }

  public String getOneLineReplacement(String text) {
    String[] r = getReplacement(text);
    if(r.length != 1) {
      throw new IllegalStateException("Expecting a single string but got "+Arrays.toString(r));
    } else {
      return r[0];
    }
  }
  public boolean atLeastOneMatchOccured() {
    return atLeastOneMatchOccured;
  }
}
