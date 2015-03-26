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
public class SingleConversionRule implements ConversionRule {

    final private Pattern pattern;
    final private String replacementText;
    final private String additionalLine;

    public SingleConversionRule(Pattern pattern, String replacementText) {
        this(pattern, replacementText, null);
    }

    public SingleConversionRule(Pattern pattern, String replacementText, String additionalLine) {
        this.pattern = pattern;
        this.replacementText = replacementText;
        this.additionalLine = additionalLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.slf4j.converter.ConversionRule#getPattern()
     */
    public Pattern getPattern() {
        return pattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.slf4j.converter.ConversionRule#replace(java.util.regex.Matcher)
     */
    public String replace(Matcher matcher) {
        return replacementText;
    }

    public String getAdditionalLine() {
        return additionalLine;
    }

}
