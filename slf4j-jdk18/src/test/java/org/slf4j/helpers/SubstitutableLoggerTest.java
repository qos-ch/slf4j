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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import junit.framework.TestCase;
import org.slf4j.Logger;

/**
 * @author Chetan Mehrotra
 */
public class SubstitutableLoggerTest extends TestCase {
  private static final Set<String> EXCLUDED_METHODS = new HashSet<>(Arrays.asList("getName"));

  public void testDelegate() throws Exception {
    SubstituteLogger log = new SubstituteLogger("foo");
    assertTrue(log.delegate() instanceof NOPLogger);

    Set<String> expectedMethodSignatures = determineMethodSignatures(Logger.class);
    LoggerInvocationHandler ih = new LoggerInvocationHandler();
    Logger proxyLogger = (Logger) Proxy.newProxyInstance(getClass().getClassLoader(),
        new Class[]{Logger.class}, ih);
    log.setDelegate(proxyLogger);

    invokeMethods(log);
    
//Assert that all methods are delegated
    expectedMethodSignatures.removeAll(ih.getInvokedMethodSignatures());
    if (!expectedMethodSignatures.isEmpty()) {
      fail("Following methods are not delegated " + expectedMethodSignatures.toString());
    }
  }

  private void invokeMethods(Logger proxyLogger) throws InvocationTargetException, IllegalAccessException {
    for (Method m : Logger.class.getMethods()) {
      if (!EXCLUDED_METHODS.contains(m.getName())) {
        try{
          m.invoke(proxyLogger, fillParameters(m));          
        }catch (Exception e) {
            System.out.println(""+m);
        }
      }
    }
  }
  
  private Object[] fillParameters(Method method){
      List<Object> params=  new ArrayList<>();
      Supplier<String> supplier = () -> " message ";
      for(Class<?> clazz : method.getParameterTypes()) {
         if(clazz.getName().equals(Supplier.class.getName())) {
             params.add(supplier);
         }
         else {
             params.add(null);
         }          
      }
      return params.toArray();
  } 

  private class LoggerInvocationHandler implements InvocationHandler {
    private final Set<String> invokedMethodSignatures = new HashSet<>();
  

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      invokedMethodSignatures.add(getMethodSignature(method));   
     
      if (method.getName().startsWith("is")) {
        return true;
      }
      return null;
    }

    public Set<String> getInvokedMethodSignatures() { 
      return invokedMethodSignatures;
    }
  }

  private static Set<String> determineMethodSignatures(Class<Logger> loggerClass) {
    Set<String> methodSignatures = new HashSet<>();
    for (Method m : loggerClass.getMethods()) {
      if (!EXCLUDED_METHODS.contains(m.getName())) {
        methodSignatures.add(getMethodSignature(m));
      }
    }
    return methodSignatures;
  }

  private static String getMethodSignature(Method m) {
    List<String> result = new ArrayList<>();
    result.add(m.getName());
    for (Class<?> clazz : m.getParameterTypes()) {
      result.add(clazz.getSimpleName());
    }
   return result.toString();
  }
}
