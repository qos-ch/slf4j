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
import static org.junit.Assert.assertNull;

import java.security.Permission;

import org.junit.Test;


public class SecurityManagerCallingClassFinderTest extends CallingClassFinderTestBase {

    private static final String STATIC_CALLING_CLASS_NAME =
        SecurityManagerCallingClassFinder.createInstance().getCallingClassName(0);

    // Static class used for testing logger names
    private static class StaticNestedClass {
        static final String callingClassName =
            SecurityManagerCallingClassFinder.createInstance().getCallingClassName(0);
    }

    @Override
    CallingClassFinder getCallingClassFinder() {
        return SecurityManagerCallingClassFinder.createInstance();
    }

    @Test
    public void testSimpleClassNameFromInitializer() {
        assertEquals("org.slf4j.helpers.callingclass." +
                     "SecurityManagerCallingClassFinderTest",
                     STATIC_CALLING_CLASS_NAME);
    }

    @Test
    public void testStaticInnerClassName() {
        assertEquals("org.slf4j.helpers.callingclass." +
                     "SecurityManagerCallingClassFinderTest$StaticNestedClass",
                     StaticNestedClass.callingClassName);
    }

    @Test
    public void testNestedInnerClassName() {
        new StaticNestedClass() {
            String nestedCallingClassName = getCallingClassFinder().getCallingClassName(0);
            public void run() {
                assertEquals("org.slf4j.helpers.callingclass." +
                             "SecurityManagerCallingClassFinderTest$StaticNestedClass",
                             callingClassName);
                assertEquals("org.slf4j.helpers.callingclass." +
                             "SecurityManagerCallingClassFinderTest$1",
                             nestedCallingClassName);
            }
        }.run();
    }

    @Test
    public void testSecurityManagerCreationDisabled() {
        SecurityManager newSm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm.implies(new RuntimePermission("createSecurityManager"))) {
                    throw new SecurityException("Security Manager creation disabled");
                }
            }
        };
        assertNull(System.getSecurityManager());
        try {
            System.setSecurityManager(newSm);
            CallingClassFinder finder = getCallingClassFinder();
            assertNull(finder);
        } finally {
            System.setSecurityManager(null);
        }
    }
}
