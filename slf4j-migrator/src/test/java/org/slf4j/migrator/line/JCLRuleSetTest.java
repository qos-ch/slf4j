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

import org.slf4j.migrator.line.JCLRuleSet;
import org.slf4j.migrator.line.LineConverter;

import junit.framework.TestCase;

public class JCLRuleSetTest extends TestCase {

  LineConverter jclConverter = new LineConverter(new JCLRuleSet());
  
  public void testImportReplacement() {
    // LogFactory import replacement
    assertEquals("import org.slf4j.LoggerFactory;", jclConverter
        .getOneLineReplacement("import org.apache.commons.logging.LogFactory;"));
    // Log import replacement
    assertEquals("import org.slf4j.Logger;", jclConverter
        .getOneLineReplacement("import org.apache.commons.logging.Log;"));
  }

  public void testLogFactoryGetLogReplacement() {
    // Logger declaration and instanciation without modifier
    assertEquals("  Logger   l = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("  Log   l = LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("public Log mylog=LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with two modifier
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration",
        jclConverter
            .getOneLineReplacement("public static Log mylog1 = LogFactory.getLog(MyClass.class); //logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getOneLineReplacement(" myLog = LogFactory.getLog(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("//public static Log mylog1 = LogFactory.getLog(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getOneLineReplacement("// myLog = LogFactory.getLog(MyClass.class);//logger instanciation"));
  }

  public void testLogFactoryGetFactoryReplacement() {
   
    // Logger declaration and instanciation without modifier
    assertEquals(
        "Logger l = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("Log l = LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with one modifier
    assertEquals(
        "public Logger mylog=LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("public Log mylog=LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with modifiers
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"));
    // Logger declaration and instanciation with two modifier and comment at the
    // end of line
    assertEquals(
        "public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class); //logger instanciation and declaration",
        jclConverter
            .getOneLineReplacement("public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class); //logger instanciation and declaration"));
    // Logger instanciation without declaration and comment at the end of line
    assertEquals(
        " myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getOneLineReplacement(" myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"));
    // commented Logger declaration and instanciation with two modifier
    assertEquals(
        "//public static Logger mylog1 = LoggerFactory.getLogger(MyClass.class);",
        jclConverter
            .getOneLineReplacement("//public static Log mylog1 = LogFactory.getFactory().getInstance(MyClass.class);"));
    // commented Logger instanciation without declaration
    assertEquals(
        "// myLog = LoggerFactory.getLogger(MyClass.class);//logger instanciation",
        jclConverter
            .getOneLineReplacement("// myLog = LogFactory.getFactory().getInstance(MyClass.class);//logger instanciation"));
  }

  public void testLogDeclarationReplacement() {
   
    // simple Logger declaration
    assertEquals("Logger mylog;", jclConverter.getOneLineReplacement("Log mylog;"));
    // Logger declaration with a modifier
    assertEquals("private Logger mylog;", jclConverter
        .getOneLineReplacement("private Log mylog;"));

    // Logger declaration with modifiers
    assertEquals("public static final Logger myLog;", jclConverter
        .getOneLineReplacement("public static final Log myLog;"));
    // Logger declaration with modifiers and comment at the end of line
    assertEquals("public Logger myLog;//logger declaration", jclConverter
        .getOneLineReplacement("public Log myLog;//logger declaration"));
    // commented Logger declaration
    assertEquals("//private Logger myLog;", jclConverter
        .getOneLineReplacement("//private Log myLog;"));
  }

  public void testMultiLineReplacement() {
    // Logger declaration on a line
    assertEquals("protected Logger log =", jclConverter
        .getOneLineReplacement("protected Log log ="));

    // Logger instanciation on the next line
    assertEquals(" LoggerFactory.getLogger(MyComponent.class);", jclConverter
        .getOneLineReplacement(" LogFactory.getLog(MyComponent.class);"));
    // Logger declaration on a line
    assertEquals("protected Logger log ", jclConverter
        .getOneLineReplacement("protected Log log "));
    // Logger instanciation on the next line
    assertEquals(
        " = LoggerFactory.getLogger(MyComponent.class);",
        jclConverter
            .getOneLineReplacement(" = LogFactory.getFactory().getInstance(MyComponent.class);"));
  }
}
