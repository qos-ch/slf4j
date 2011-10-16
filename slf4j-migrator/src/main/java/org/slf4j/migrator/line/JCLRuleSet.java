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
import java.util.Iterator;
import java.util.regex.Pattern;


/**
 * This class represents JCL to SLF4J conversion rules
 * 
 * @author Jean-Noel Charpin
 * 
 */
public class JCLRuleSet implements RuleSet {

  private ArrayList<ConversionRule> conversionRuleList;
  
  public JCLRuleSet() {
    // matching : import org.apache.commons.logging.LogFactory;
    SingleConversionRule cr0 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.LogFactory;"),
        "import org.slf4j.LoggerFactory;");

    // matching : import org.apache.commons.logging.Log;
    SingleConversionRule cr1 = new SingleConversionRule(Pattern
        .compile("import\\s*+org.apache.commons.logging.Log;"), 
        "import org.slf4j.Logger;");
    
    SingleConversionRule cr2 = new SingleConversionRule(Pattern
        .compile("(\\sLog\\b)")," Logger");
    
    SingleConversionRule cr3 = new SingleConversionRule(Pattern
        .compile("(^Log\\b)"),"Logger");
    
    SingleConversionRule cr4 = new SingleConversionRule(Pattern
        .compile("LogFactory.getFactory\\(\\).getInstance\\("),
            "LoggerFactory.getLogger(");

    SingleConversionRule cr5 = new SingleConversionRule(Pattern
        .compile("LogFactory.getLog\\("),"LoggerFactory.getLogger(");
    

    conversionRuleList = new ArrayList<ConversionRule>();
    conversionRuleList.add(cr0);
    conversionRuleList.add(cr1);
    conversionRuleList.add(cr2);
    conversionRuleList.add(cr3);
    conversionRuleList.add(cr4);
    conversionRuleList.add(cr5);
  }


  public Iterator<ConversionRule> iterator() {
    return conversionRuleList.iterator();
  }
}
