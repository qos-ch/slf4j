/**
 * Copyright (c) 2004-2015 QOS.ch
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
package org.slf4j.helpers.callingclass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Abstract base class to test derived classes of {@link CallingClassFinder}.
 * @author Alexander Dorokhine
 */
public abstract class CallingClassFinderTestBase {

    /**
     * @return the {@link CallingClassFinder} instance under test.
     */
    abstract CallingClassFinder getCallingClassFinder();

    private class InstanceNestedClass {
        String callingClassName = getCallingClassFinder().getCallingClassName(0);
    }

    @Test
    public void testSimpleClassNameFromMethod() {
        assertEquals("org.slf4j.helpers.callingclass.CallingClassFinderTestBase",
                     getCallingClassFinder().getCallingClassName(0));
    }

    @Test
    public void testNamedInnerClassName() {
        class MyTestInstanceClass {
            String callingClassName = getCallingClassFinder().getCallingClassName(0);
        }
        MyTestInstanceClass instance = new MyTestInstanceClass();
        assertEquals("org.slf4j.helpers.callingclass." +
                     "CallingClassFinderTestBase$1MyTestInstanceClass",
                     instance.callingClassName);
    }

    @Test
    public void testAnonymousInnerClassName() {
        new Runnable() {
            String callingClassName = getCallingClassFinder().getCallingClassName(0);
            public void run() {
                assertEquals("org.slf4j.helpers.callingclass." +
                             "CallingClassFinderTestBase$1", callingClassName);
            }
        }.run();
    }

    @Test
    public void testInstanceInnerClassName() {
        assertEquals("org.slf4j.helpers.callingclass." +
                     "CallingClassFinderTestBase$InstanceNestedClass",
                     new InstanceNestedClass().callingClassName);
    }

    @Test
    public void testNameFromThread() throws Exception {
        Thread thread = new Thread() {
            public void run() {
                String callingClassName = getCallingClassFinder().getCallingClassName(0);
                assertEquals("org.slf4j.helpers.callingclass.CallingClassFinderTestBase$2",
                             callingClassName);
            }
        };
        thread.start();
        thread.join();
    }

    @Test
    public void testStackDepth() {
        class MyTestInstanceClass {
            String callingClassName = getCallingClassFinder().getCallingClassName(1);
        }
        MyTestInstanceClass instance = new MyTestInstanceClass();
        assertEquals("org.slf4j.helpers.callingclass.CallingClassFinderTestBase",
                     instance.callingClassName);
    }
}
