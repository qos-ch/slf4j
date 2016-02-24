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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NoConversionTest {

    /**
     * This test shows that performing JCL to SLF4J conversion has no impact on
     * Log4j implementation
     */
    @Test
    public void testJclOverLog4jConversion() {
        // running jcl to slf4j conversion
        // JCLMatcher jclMatcher =
        LineConverter jclLineConverter = new LineConverter(new JCLRuleSet());
        // no changes on log4j.LogManager import
        assertEquals("import org.apache.log4j.LogManager;", jclLineConverter.getOneLineReplacement("import org.apache.log4j.LogManager;"));
        // no changes on log4j.Logger import
        assertEquals("import org.apache.log4j.Logger;", jclLineConverter.getOneLineReplacement("import org.apache.log4j.Logger;"));
        // no changes on Logger instanciation using LogManager
        assertEquals("Logger log = LogManager.getLogger(MyClass.class);",
                        jclLineConverter.getOneLineReplacement("Logger log = LogManager.getLogger(MyClass.class);"));
        // no changes on Logger instanciation using Logger.getLogger
        assertEquals("public static Logger mylog1 = Logger.getLogger(MyClass.class);",
                        jclLineConverter.getOneLineReplacement("public static Logger mylog1 = Logger.getLogger(MyClass.class);"));
    }

    /**
     * This test shows that performing Log4j to SLF4J conversion has no impact on
     * JCL implementation
     */
    @Test
    public void testLog4jOverJclConversion() {
        // running log4j to slf4j conversion
        LineConverter log4jConverter = new LineConverter(new Log4jRuleSet());

        // no changes on LogFactory import
        assertEquals("import org.apache.commons.logging.LogFactory;", log4jConverter.getOneLineReplacement("import org.apache.commons.logging.LogFactory;"));
        // no changes on Log import
        assertEquals("import org.apache.commons.logging.Log;", log4jConverter.getOneLineReplacement("import org.apache.commons.logging.Log;"));
        // no changes on Log instanciation using Logfactory.getLog
        assertEquals("public static Log mylog1 = LogFactory.getLog(MyClass.class);",
                        log4jConverter.getOneLineReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
        // no changes on log instanciation using LogFactory.getFactory().getInstance
        assertEquals("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);",
                        log4jConverter.getOneLineReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"));

    }
}
