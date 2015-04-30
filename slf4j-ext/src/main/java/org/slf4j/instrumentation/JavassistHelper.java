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
package org.slf4j.instrumentation;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;

/**
 * Helper methods for Javassist functionality.
 * 
 */
public class JavassistHelper {

    /**
     * Create a javaassist source snippet which either is empty (for anything
     * which does not return a value) or a explanatory text around the $_
     * javaassist return value variable.
     * 
     * @param method
     *            descriptor of method
     * @return source snippet
     * @throws NotFoundException
     */
    public static String returnValue(CtBehavior method) throws NotFoundException {

        String returnValue = "";
        if (methodReturnsValue(method)) {
            returnValue = " returns: \" + $_ + \".";
        }
        return returnValue;
    }

    /**
     * determine if the given method returns a value, and return true if so.
     * false otherwise.
     * 
     * @param method
     * @return
     * @throws NotFoundException
     */
    private static boolean methodReturnsValue(CtBehavior method) throws NotFoundException {

        if (method instanceof CtMethod == false) {
            return false;
        }

        CtClass returnType = ((CtMethod) method).getReturnType();
        String returnTypeName = returnType.getName();

        boolean isVoidMethod = "void".equals(returnTypeName);

        boolean methodReturnsValue = isVoidMethod == false;
        return methodReturnsValue;
    }

    /**
     * Return javaassist source snippet which lists all the parameters and their
     * values. If available the source names are extracted from the debug
     * information and used, otherwise just a number is shown.
     * 
     * @param method
     * @return
     * @throws NotFoundException
     */
    public static String getSignature(CtBehavior method) throws NotFoundException {

        CtClass[] parameterTypes = method.getParameterTypes();

        CodeAttribute codeAttribute = method.getMethodInfo().getCodeAttribute();

        LocalVariableAttribute locals = null;

        if (codeAttribute != null) {
            AttributeInfo attribute;
            attribute = codeAttribute.getAttribute("LocalVariableTable");
            locals = (LocalVariableAttribute) attribute;
        }

        String methodName = method.getName();

        StringBuilder sb = new StringBuilder(methodName).append("(\" ");
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                // add a comma and a space between printed values
                sb.append(" + \", \" ");
            }

            CtClass parameterType = parameterTypes[i];
            boolean isArray = parameterType.isArray();
            CtClass arrayType = parameterType.getComponentType();
            if (isArray) {
                while (arrayType.isArray()) {
                    arrayType = arrayType.getComponentType();
                }
            }

            sb.append(" + \"");
            try {
                sb.append(parameterNameFor(method, locals, i));
            } catch (Exception e) {
                sb.append(i + 1);
            }
            sb.append("\" + \"=");

            if (parameterType.isPrimitive()) {
                // let the compiler handle primitive -> string
                sb.append("\"+ $").append(i + 1);
            } else {
                String s = "org.slf4j.instrumentation.ToStringHelper.render";
                sb.append("\"+ ").append(s).append("($").append(i + 1).append(')');
            }
        }
        sb.append("+\")");

        String signature = sb.toString();
        return signature;
    }

    /**
     * Determine the name of parameter with index i in the given method. Use the
     * locals attributes about local variables from the classfile. Note: This is
     * still work in progress.
     * 
     * @param method
     * @param locals
     * @param i
     * @return the name of the parameter if available or a number if not.
     */
    static String parameterNameFor(CtBehavior method, LocalVariableAttribute locals, int i) {

        if (locals == null) {
            return Integer.toString(i + 1);
        }

        int modifiers = method.getModifiers();

        int j = i;

        if (Modifier.isSynchronized(modifiers)) {
            // skip object to synchronize upon.
            j++;
            // System.err.println("Synchronized");
        }
        if (Modifier.isStatic(modifiers) == false) {
            // skip "this"
            j++;
            // System.err.println("Instance");
        }
        String variableName = locals.variableName(j);
        // if (variableName.equals("this")) {
        // System.err.println("'this' returned as a parameter name for "
        // + method.getName() + " index " + j
        // +
        // ", names are probably shifted. Please submit source for class in slf4j bugreport");
        // }
        return variableName;
    }
}
