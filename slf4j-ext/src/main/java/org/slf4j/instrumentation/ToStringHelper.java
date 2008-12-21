package org.slf4j.instrumentation;

import java.util.Map;
import java.util.WeakHashMap;

public class ToStringHelper {

	final static Map<Class, Object> unrenderableClasses = new WeakHashMap<Class, Object>();

	public static String render(Object o) {
		if (o == null) {
			return String.valueOf(o);
		}
		Class objectClass = o.getClass();
		if (unrenderableClasses.containsKey(objectClass) == false) {
			try {
				if (objectClass.isArray()) {
					Class componentType = objectClass.getComponentType();
					StringBuffer sb = new StringBuffer("[");
					if (componentType.isPrimitive() == true) {
						if (Boolean.TYPE.equals(componentType)) {
							boolean[] ba = (boolean[]) o;
							for (int i = 0; i < ba.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ba[i]);
							}
						} else if (Integer.TYPE.equals(componentType)) {
							int[] ia = (int[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}

						} else if (Long.TYPE.equals(componentType)) {
							long[] ia = (long[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}
						} else if (Double.TYPE.equals(componentType)) {
							double[] ia = (double[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}
						} else if (Float.TYPE.equals(componentType)) {
							float[] ia = (float[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}
						} else if (Character.TYPE.equals(componentType)) {
							char[] ia = (char[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}
						} else if (Short.TYPE.equals(componentType)) {
							short[] ia = (short[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}
						} else if (Byte.TYPE.equals(componentType)) {
							byte[] ia = (byte[]) o;
							for (int i = 0; i < ia.length; i++) {
								if (i > 0) {
									sb.append(", ");
								}
								sb.append(ia[i]);
							}
						}
					} else {
						Object[] oa = (Object[]) o;
						for (int i = 0; i < oa.length; i++) {
							if (i > 0) {
								sb.append(", ");
							}
							sb.append(render(oa[i]));
						}
					}
					sb.append("]");
					return sb.toString();
				} else {
					return o.toString();
				}
			} catch (Exception e) {
				Long now = new Long(System.currentTimeMillis());
				unrenderableClasses.put(objectClass, now);
			}
		}
		return o.getClass().getName() + "@" + Integer.toHexString(o.hashCode());
	}
}
