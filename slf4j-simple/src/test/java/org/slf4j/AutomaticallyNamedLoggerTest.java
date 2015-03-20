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
package org.slf4j;

import static org.junit.Assert.assertEquals;

import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test whether the automatically named Logger implementation works.
 *
 * @author Alexander Dorokhine
 */
public class AutomaticallyNamedLoggerTest {

  private static final Logger STATIC_LOGGER = LoggerFactory.getLogger();

  // Static class used for testing logger names
  private static class StaticNestedClass {
    Logger logger = LoggerFactory.getLogger();
  }

  // Instance class used for testing logger names
  private class InstanceNestedClass {
    Logger logger = LoggerFactory.getLogger();
  }

  PrintStream old = System.err;

  @Before
  public void setUp() {
    System.setErr(new SilentPrintStream(old));
  }

  @After
  public void tearDown() {
    System.setErr(old);
  }

  @Test
  public void testSimpleClassNameFromInitializer() {
    assertEquals("org.slf4j.AutomaticallyNamedLoggerTest", STATIC_LOGGER.getName());
  }

  @Test
  public void testSimpleClassNameFromMethod() {
    Logger logger = LoggerFactory.getLogger();
    assertEquals("org.slf4j.AutomaticallyNamedLoggerTest", logger.getName());
  }

  @Test
  public void testNamedInnerClassName() {
    class MyTestInstanceClass {
      Logger logger = LoggerFactory.getLogger();
    }
    MyTestInstanceClass instance = new MyTestInstanceClass();
    assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$1MyTestInstanceClass",
                 instance.logger.getName());
  }

  @Test
  public void testAnonymousInnerClassName() {
    new Runnable() {
      Logger logger = LoggerFactory.getLogger();
      public void run() {
        assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$1", logger.getName());
      }
    }.run();
  }

  @Test
  public void testStaticInnerClassName() {
    assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$StaticNestedClass",
                 new StaticNestedClass().logger.getName());
  }

  @Test
  public void testInstanceInnerClassName() {
    assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$InstanceNestedClass",
                 new InstanceNestedClass().logger.getName());
  }

  @Test
  public void testNestedInnerClassName() {
    new StaticNestedClass() {
      Logger nestedLogger = LoggerFactory.getLogger();
      public void run() {
        assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$StaticNestedClass",
                     logger.getName());
        assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$2", nestedLogger.getName());
      }
    }.run();
  }

  @Test
  public void testLoggerThroughReflection() throws Exception {
    Method getLoggerMethod = LoggerFactory.class.getMethod("getLogger");
    Object logger = getLoggerMethod.invoke(null);
    assertEquals("org.slf4j.AutomaticallyNamedLoggerTest", ((Logger)logger).getName());
  }

  @Test
  public void testLoggerFromThread() throws Exception {
    Thread thread = new Thread() {
      public void run() {
        Logger logger = LoggerFactory.getLogger();
        assertEquals("org.slf4j.AutomaticallyNamedLoggerTest$3", logger.getName());
      }
    };
    thread.start();
    thread.join();
  }
}
