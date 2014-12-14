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
package org.slf4j.helpers;

import junit.framework.TestCase;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SubstituteLoggerFactoryTest extends TestCase{
  private SubstituteLoggerFactory factory = new SubstituteLoggerFactory();

  public void testFactory() {
    Logger log = factory.getLogger("foo");
    assertNotNull(log);

    Logger log2 = factory.getLogger("foo");
    assertTrue("Loggers with same name must be same",log == log2);
  }

  public void testLoggerNameList() {
    factory.getLogger("foo1");
    factory.getLogger("foo2");

    Set<String> expectedNames = new HashSet<String>(Arrays.asList("foo1","foo2"));
    Set<String> actualNames = new HashSet<String>(factory.getLoggerNames());

    assertEquals(expectedNames, actualNames);
  }

  public void testLoggers() {
    factory.getLogger("foo1");
    factory.getLogger("foo2");

    Set<String> expectedNames = new HashSet<String>(Arrays.asList("foo1","foo2"));

    Set<String> actualNames = new HashSet<String>();
    for(SubstituteLogger slog : factory.getLoggers()){
      actualNames.add(slog.getName());
    }

    assertEquals(expectedNames, actualNames);
  }

}
