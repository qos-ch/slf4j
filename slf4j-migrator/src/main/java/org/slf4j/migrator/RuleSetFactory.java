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
package org.slf4j.migrator;

import org.slf4j.migrator.line.EmptyRuleSet;
import org.slf4j.migrator.line.JCLRuleSet;
import org.slf4j.migrator.line.JULRuleSet;
import org.slf4j.migrator.line.Log4jRuleSet;
import org.slf4j.migrator.line.RuleSet;

/**
 * This class runs Pattern matching with java.util.regex using Patterns defined
 * in concrete implementations
 * 
 * @author jean-noelcharpin
 * 
 */
public abstract class RuleSetFactory {

   /**
   * Return matcher implementation depending on the conversion mode
   * 
   * @param conversionType
   * @return AbstractMatcher implementation
   */
  public static RuleSet getMatcherImpl(int conversionType) {
    switch (conversionType) {
    case Constant.JCL_TO_SLF4J:
      return new JCLRuleSet();
    case Constant.LOG4J_TO_SLF4J:
      return new Log4jRuleSet();
    case Constant.JUL_TO_SLF4J:
        return new JULRuleSet();
    case Constant.NOP_TO_SLF4J:
      return new EmptyRuleSet();
    default:
      return null;
    }
  }
}
