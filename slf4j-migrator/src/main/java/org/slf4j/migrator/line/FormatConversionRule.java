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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a conversion rule that makes use of a parameterized
 * format message that avoids unnecessary String concatination in log messages
 * 
 * @author James Stauffer
 */
public class FormatConversionRule implements ConversionRule {

  final private Pattern pattern;
  final private Pattern concatPatternStart;
  final private Pattern concatPatternEndMiddle;//will have another string after
  final private Pattern concatPatternEndFinal;//Final variable in concat String
  final private Pattern logEndPattern;
  final private Pattern logThrowablePattern;
  
  public FormatConversionRule(Pattern pattern) {
    this.pattern = pattern;
    concatPatternStart = Pattern.compile("\"\\s*\\+\\s*");
    concatPatternEndMiddle = Pattern.compile("\\s*\\+\\s*\"");
    concatPatternEndFinal = Pattern.compile("\\s*\\)\\s*;");
    logThrowablePattern = Pattern.compile("\\s*,\\s*\\w+\\s\\)\\s*;");
    logEndPattern = Pattern.compile("\\s*\\)\\s*;\\s*");
  }

  /* (non-Javadoc)
   * @see org.slf4j.converter.ConversionRule#getReplacement(String)
   */
  public String[] getReplacement(String originalText) {
    Matcher matcher = pattern.matcher(originalText);
    if(matcher.find()) {
        StringBuilder replacementLine = new StringBuilder(originalText.substring(0, matcher.end()));
        String text = originalText.substring(matcher.end());
        List<String> concatObjects = new ArrayList<String>();
        for(matcher = concatPatternStart.matcher(text); matcher.find(); matcher = concatPatternStart.matcher(text)) {
            replacementLine.append(text.substring(0, matcher.start()));//Keep everything up to the concat
            Matcher matcherEndMiddle = concatPatternEndMiddle.matcher(text);
            if(matcherEndMiddle.find(matcher.end())) {
                concatObjects.add(text.substring(matcher.end(), matcherEndMiddle.start()));
                replacementLine.append("{}");
                text = text.substring(matcherEndMiddle.end());
            } else {
                Matcher matcherEndfinal = concatPatternEndFinal.matcher(text);
                if(matcherEndfinal.find(matcher.end())) {
                    concatObjects.add(text.substring(matcher.end(), matcherEndfinal.start()));
                    replacementLine.append("{}");
                    text = "\");" + text.substring(matcherEndfinal.end());
                    break;
                } else if(!originalText.contains("{}")) {
                    return new String[]{originalText + "//TODO SLF4J Migrator unable to automatically change to parameterized logging"};
                } else {//probably already converted
                    return new String[]{originalText};
                }
                
            }
        }
        Matcher endMatcher = logEndPattern.matcher(text);
        if(endMatcher.find()) {
            replacementLine.append(text.substring(0, endMatcher.start()));
            if(concatObjects.size() > 2) {
                replacementLine.append(", new Object[]{");
                for(int concatObjecIndex = 0; concatObjecIndex < concatObjects.size(); concatObjecIndex++) {
                    if(concatObjecIndex > 0) {
                        replacementLine.append(", ");
                    }
                    replacementLine.append(concatObjects.get(concatObjecIndex));
                }
                replacementLine.append("}");
            } else {
                for(String concatObject : concatObjects) {
                    replacementLine.append(", ").append(concatObject);
                }
            }
            replacementLine.append(");");
            return new String[]{replacementLine.toString()};
        } else if(!originalText.contains("{}")) {//Didn't find end of log call so probably wrapped to multiple lines
            return new String[]{originalText + "//TODO SLF4J Migrator unable to automatically change to parameterized logging because it appears the line wraps"};
        }//else probably already converted
    }
    return new String[]{originalText};
  }
}
