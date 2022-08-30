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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.event.EventRecordingLogger;

/**
 * @author Chetan Mehrotra
 * @author Ceki Gülcü
 */
public class SubstitutableLoggerTest {

    // NOTE: previous implementations of this class performed a handcrafted conversion of
    // a method to a string. In this implementation we just invoke method.toString().
    
    // WARNING: if you need to add an excluded method to have tests pass, ask yourself whether you
    // forgot to implement the said method with delegation in SubstituteLogger. You probably did.
    private static final Set<String> EXCLUDED_METHODS = new HashSet<>(
            Arrays.asList("getName"));

    
    /**
     * Test that all SubstituteLogger methods invoke the delegate, except for explicitly excluded  methods.
     */
    @Test
    public void delegateIsInvokedTest() throws Exception {
        SubstituteLogger substituteLogger = new SubstituteLogger("foo", null, false);
        assertTrue(substituteLogger.delegate() instanceof EventRecordingLogger);

        Set<String> expectedMethodSignatures = determineMethodSignatures(Logger.class);
        LoggerInvocationHandler loggerInvocationHandler = new LoggerInvocationHandler();
        Logger proxyLogger = (Logger) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { Logger.class }, loggerInvocationHandler);
        substituteLogger.setDelegate(proxyLogger);

        invokeAllMethodsOf(substituteLogger);

        // Assert that all methods are delegated
        expectedMethodSignatures.removeAll(loggerInvocationHandler.getInvokedMethodSignatures());
        if (!expectedMethodSignatures.isEmpty()) {
            fail("Following methods are not delegated " + expectedMethodSignatures.toString());
        }
    }

    private void invokeAllMethodsOf(Logger logger) throws InvocationTargetException, IllegalAccessException {
        for (Method m : Logger.class.getDeclaredMethods()) {
            if (!EXCLUDED_METHODS.contains(m.getName())) {
                m.invoke(logger, new Object[m.getParameterTypes().length]);
            }
        }
    }

    private static Set<String> determineMethodSignatures(Class<Logger> loggerClass) {
        Set<String> methodSignatures = new HashSet<>();
        // Note: Class.getDeclaredMethods() does not include inherited methods
        for (Method m : loggerClass.getDeclaredMethods()) {
            if (!EXCLUDED_METHODS.contains(m.getName())) {
                methodSignatures.add(m.toString());
            }
        }
        return methodSignatures;
    }

    
    // implements InvocationHandler 
    private class LoggerInvocationHandler implements InvocationHandler {
        private final Set<String> invokedMethodSignatures = new HashSet<>();

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            invokedMethodSignatures.add(method.toString());
            if (method.getName().startsWith("is")) {
                return true;
            }
            return null;
        }

        public Set<String> getInvokedMethodSignatures() {
            return invokedMethodSignatures;
        }
    }
}
