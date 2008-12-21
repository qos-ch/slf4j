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
				return o.toString();
			} catch (Exception e) {
				Long now = new Long(System.currentTimeMillis());
				unrenderableClasses.put(objectClass, now);
			}
		}
		return o.getClass().getName() + "@" + Integer.toHexString(o.hashCode());
	}
}
