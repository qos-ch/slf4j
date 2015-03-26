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
 * This class represents java.util.logging (JUL) to SLF4J conversion rules
 * 
 * @author Jean-Noel Charpin
 * @author Ceki Gulcu
 */
public class JULRuleSet implements RuleSet {

    private ArrayList<ConversionRule> conversionRuleList;

    public JULRuleSet() {

        SingleConversionRule crImport0 = new SingleConversionRule(Pattern.compile("import\\s*+java.util.logging.Logger;"), "import org.slf4j.Logger;",
                        "import org.slf4j.LoggerFactory;");

        SingleConversionRule crImport1 = new SingleConversionRule(Pattern.compile("import\\s*+org.apache.log4j.LogManager;"), "import org.slf4j.LoggerFactory;");

        SingleConversionRule crImport2 = new SingleConversionRule(Pattern.compile("import\\s*+java.util.logging.*;"), "import org.slf4j.Logger;",
                        "import org.slf4j.LoggerFactory;");

        SingleConversionRule crFactory0 = new SingleConversionRule(Pattern.compile("Logger.getLogger\\("), "LoggerFactory.getLogger(");

        SingleConversionRule crFactory1 = new SingleConversionRule(Pattern.compile("LogManager.getLogger\\("), "LoggerFactory.getLogger(");

        SingleConversionRule crWarning = new SingleConversionRule(Pattern.compile("\\.warning\\("), ".warn(");
        SingleConversionRule crSevere = new SingleConversionRule(Pattern.compile("\\.severe\\("), ".error(");

        conversionRuleList = new ArrayList<ConversionRule>();
        conversionRuleList.add(crImport0);
        conversionRuleList.add(crImport1);
        conversionRuleList.add(crImport2);
        conversionRuleList.add(crFactory0);
        conversionRuleList.add(crFactory1);
        conversionRuleList.add(crWarning);
        conversionRuleList.add(crSevere);
    }

    public Iterator<ConversionRule> iterator() {
        return conversionRuleList.iterator();
    }

}
