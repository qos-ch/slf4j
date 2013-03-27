/*
 * Copyright (c) 2004-2013 QOS.ch
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
package org.slf4j.impl;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class AndroidLoggerFactoryTest {
    @Test
    public void shortLoggerNames() {
        assertEquals("o.test.p.TestClass", AndroidLoggerFactory.loggerNameToTag("o.test.p.TestClass"));
        assertEquals("ex.test.TestClass", AndroidLoggerFactory.loggerNameToTag("ex.test.TestClass"));
        assertEquals("MyClass", AndroidLoggerFactory.loggerNameToTag("MyClass"));
    }

    @Test
    public void emptyLoggerNames() {
        assertEquals(AndroidLoggerFactory.ANONYMOUS_TAG, AndroidLoggerFactory.loggerNameToTag(null));
        assertEquals("", AndroidLoggerFactory.loggerNameToTag(""));
    }

    @Test
    public void simpleLoggerName() {
        assertEquals("o*.t*.p*.TestClass", AndroidLoggerFactory.loggerNameToTag("org.test.package.TestClass"));
    }

    @Test
    public void loggerNameWithOneCharPackage() {
        assertEquals("o.t*.p*.p*.TestClass", AndroidLoggerFactory.loggerNameToTag("o.test.project.package.TestClass"));
        assertEquals("o.t*.p*.p.TestClass", AndroidLoggerFactory.loggerNameToTag("o.test.project.p.TestClass"));
    }

    @Test
    public void longLoggerName() {
        assertEquals("AndroidLoggerFactory", AndroidLoggerFactory.loggerNameToTag("org.slf4j.impl.AndroidLoggerFactory"));
    }

    @Test
    public void veryLongLoggerName() {
        assertEquals("*meAndShouldBeTruncated", AndroidLoggerFactory.loggerNameToTag("IAmAVeryLongLoggerNameAndShouldBeTruncated"));
    }

    @Test
    public void oneWordLoggerName() {
        assertEquals("TestClass", AndroidLoggerFactory.loggerNameToTag("TestClass"));
    }

    @Test
    public void weirdLoggerNames() {
        assertEquals("WeirdLoggerName.", AndroidLoggerFactory.loggerNameToTag("WeirdLoggerName."));
        assertEquals(".WeirdLoggerName", AndroidLoggerFactory.loggerNameToTag(".WeirdLoggerName"));
        assertEquals(".WeirdLoggerName.", AndroidLoggerFactory.loggerNameToTag(".WeirdLoggerName."));
        assertEquals(".", AndroidLoggerFactory.loggerNameToTag("."));
        assertEquals("..", AndroidLoggerFactory.loggerNameToTag(".."));
    }
}
