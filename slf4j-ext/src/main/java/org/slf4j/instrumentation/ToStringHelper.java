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

import java.util.Map;
import java.util.WeakHashMap;

public class ToStringHelper {

    /**
     * Prefix to use at the start of the representation. Always used.
     */
    private static final String ARRAY_PREFIX = "[";

    /**
     * Suffix to use at the end of the representation. Always used.
     */
    private static final char ARRAY_SUFFIX = ']';

    /**
     * String separating each element when rendering an array. To be compatible
     * with lists comma-space is used.
     */

    private static final char[] ELEMENT_SEPARATOR = ", ".toCharArray();

    /**
     * unrenderableClasses is essentially a Set of Class objects which has for
     * some reason failed to render properly when invoked through a toString
     * method call. To avoid memory leaks a data structure using weak references
     * is needed, but unfortunately the runtime library does not contain a
     * WeakHashSet class, so the behavior is emulated with a WeakHashmap with
     * the class as the key, and a Long containing the value of
     * System.currentTimeMillis when an instance of the class failed to render.
     */

    final static Map<Class<?>, Object> unrenderableClasses = new WeakHashMap<>();

    /**
     * Returns o.toString() unless it throws an exception (which causes it to be
     * stored in unrenderableClasses) or already was present in
     * unrenderableClasses. If so, the same string is returned as would have
     * been returned by Object.toString(). Arrays get special treatment as they
     * don't have usable toString methods.
     * 
     * @param o
     *            incoming object to render.
     * @return
     */

    public static String render(Object o) {
        if (o == null) {
            return String.valueOf(o);
        }
        Class<?> objectClass = o.getClass();

        if (unrenderableClasses.containsKey(objectClass) == false) {
            try {
                if (objectClass.isArray()) {
                    return renderArray(o, objectClass).toString();
                } else {
                    return o.toString();
                }
            } catch (Exception e) {
                Long now = Long.valueOf(System.currentTimeMillis());

                System.err.println("Disabling exception throwing class " + objectClass.getName() + ", " + e.getMessage());

                unrenderableClasses.put(objectClass, now);
            }
        }
        String name = o.getClass().getName();
        return name + "@" + Integer.toHexString(o.hashCode());
    }

    /**
     * renderArray returns an array similar to a List. If the array type is an
     * object they are rendered with "render(object)" for each. If the array
     * type is a primitive each element is added directly to the string buffer
     * collecting the result.
     * 
     * @param o
     * @param objectClass
     * @return
     */
    private static StringBuilder renderArray(Object o, Class<?> objectClass) {
        Class<?> componentType = objectClass.getComponentType();
        StringBuilder sb = new StringBuilder(ARRAY_PREFIX);

        if (componentType.isPrimitive() == false) {
            Object[] oa = (Object[]) o;
            for (int i = 0; i < oa.length; i++) {
                if (i > 0) {
                    sb.append(ELEMENT_SEPARATOR);
                }
                sb.append(render(oa[i]));
            }
        } else {
            if (Boolean.TYPE.equals(componentType)) {
                boolean[] ba = (boolean[]) o;
                for (int i = 0; i < ba.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ba[i]);
                }
            } else if (Integer.TYPE.equals(componentType)) {
                int[] ia = (int[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }

            } else if (Long.TYPE.equals(componentType)) {
                long[] ia = (long[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }
            } else if (Double.TYPE.equals(componentType)) {
                double[] ia = (double[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }
            } else if (Float.TYPE.equals(componentType)) {
                float[] ia = (float[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }
            } else if (Character.TYPE.equals(componentType)) {
                char[] ia = (char[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }
            } else if (Short.TYPE.equals(componentType)) {
                short[] ia = (short[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }
            } else if (Byte.TYPE.equals(componentType)) {
                byte[] ia = (byte[]) o;
                for (int i = 0; i < ia.length; i++) {
                    if (i > 0) {
                        sb.append(ELEMENT_SEPARATOR);
                    }
                    sb.append(ia[i]);
                }
            }
        }
        sb.append(ARRAY_SUFFIX);
        return sb;
    }
}
