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

public class Log4jRuleSet implements RuleSet {

    private ArrayList<ConversionRule> conversionRuleList;

    public Log4jRuleSet() {

        SingleConversionRule crImport0 = new SingleConversionRule(Pattern.compile("import\\s*+org.apache.log4j.Logger;"), "import org.slf4j.Logger;",
                        "import org.slf4j.LoggerFactory;");

        SingleConversionRule catImport = new SingleConversionRule(Pattern.compile("import\\s*+org.apache.log4j.Category;"), "import org.slf4j.Logger;",
                        "import org.slf4j.LoggerFactory;");

        SingleConversionRule crImport1 = new SingleConversionRule(Pattern.compile("import\\s*+org.apache.log4j.LogManager;"), "import org.slf4j.LoggerFactory;");

        SingleConversionRule crImport2 = new SingleConversionRule(Pattern.compile("import\\s*+org.apache.log4j.*;"), "import org.slf4j.Logger;",
                        "import org.slf4j.LoggerFactory;");

        SingleConversionRule crImportMDC = new SingleConversionRule(Pattern.compile("import\\s*+org.apache.log4j.MDC;"), "import org.slf4j.MDC;");

        SingleConversionRule crFactory0 = new SingleConversionRule(Pattern.compile("Logger.getLogger\\("), "LoggerFactory.getLogger(");

        SingleConversionRule crFactory1 = new SingleConversionRule(Pattern.compile("\\sCategory.getInstance\\("), " LoggerFactory.getLogger(");

        SingleConversionRule crFactory2 = new SingleConversionRule(Pattern.compile("LogManager.getLogger\\("), "LoggerFactory.getLogger(");

        SingleConversionRule variable0 = new SingleConversionRule(Pattern.compile("(\\sCategory\\b)"), " Logger");

        SingleConversionRule variable1 = new SingleConversionRule(Pattern.compile("(^Category\\b)"), "Logger");

        conversionRuleList = new ArrayList<ConversionRule>();
        conversionRuleList.add(crImport0);
        conversionRuleList.add(catImport);
        conversionRuleList.add(crImport1);
        conversionRuleList.add(crImport2);
        conversionRuleList.add(crImportMDC);
        conversionRuleList.add(crFactory0);
        conversionRuleList.add(crFactory1);
        conversionRuleList.add(crFactory2);

        conversionRuleList.add(variable0);
        conversionRuleList.add(variable1);
    }

    public Iterator<ConversionRule> iterator() {
        return conversionRuleList.iterator();
    }

}
