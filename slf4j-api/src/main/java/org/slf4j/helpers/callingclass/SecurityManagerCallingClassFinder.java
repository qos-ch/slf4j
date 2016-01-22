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

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A utility class for finding a method's caller using a security manager.
 *
 * @author Alexander Dorokhine
 */
public class SecurityManagerCallingClassFinder implements CallingClassFinder {

    /**
     * In order to call {@link SecurityManager#getClassContext()}, which is a
     * protected method, we add this wrapper which allows the method to be visible
     * inside this package.
     */
    private static final class ClassContextSecurityManager extends SecurityManager {
        protected Class<?>[] getClassContext() {
            return super.getClassContext();
        }
    }

    private final ClassContextSecurityManager securityManager;

    private SecurityManagerCallingClassFinder(ClassContextSecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public String getCallingClassName(int depth) {
        Class<?>[] trace = securityManager.getClassContext();
        String thisClassName = SecurityManagerCallingClassFinder.class.getName();

        // Advance until SecurityManagerCallingClassFinder is found
        int i;
        for (i = 0; i < trace.length; i++) {
            if (thisClassName.equals(trace[i].getName()))
                break;
        }

        // trace[i] = SecurityManagerCallingClassFinder
        // trace[i+1] = caller [depth 0]
        // trace[i+2] = caller's caller [depth 1]
        if (i + 1 + depth >= trace.length) {
            throw new IllegalArgumentException(
                "Tried to find the " + depth + "th caller, but the stack depth is only " +
                 trace.length);
        }

        return trace[i + 1 + depth].getName();
    }

    /**
     * @return A new instance of {@link SecurityManagerCallingClassFinder} if creation was
     * possible, or {@code null} if {@link SecurityManager} creation is disabled by the
     * security policy.
     */
    public static CallingClassFinder createInstance() {
        if (System.getSecurityManager() == null) {
            return new SecurityManagerCallingClassFinder(new ClassContextSecurityManager());
        } else {
            try {
                return AccessController.doPrivileged(
                    new PrivilegedAction<SecurityManagerCallingClassFinder>() {
                        public SecurityManagerCallingClassFinder run() {
                            return new SecurityManagerCallingClassFinder(
                                new ClassContextSecurityManager());
                        }
                    });
            } catch (SecurityException e) {
                // Unable to create a new SecurityManager, so this approach for finding
                // callers is not useable.
                return null;
            }
        }
    }
}
