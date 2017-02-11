package org.lm.jpa.util;

import java.lang.reflect.Field;

public abstract class TypeUtils {

	public static Field getFieldByName(Class<?> type, String fieldName) {

		try {
			Field f = type.getDeclaredField(fieldName);
			return f;
		} catch (NoSuchFieldException e) {
			return getFieldByName(type.getSuperclass(), fieldName);
		} catch (SecurityException e) {
			return null;
		}
	}

	public static Object getFieldValue(String fieldName, Object obj) {
		if (fieldName.contains(".")) {
			Object p = obj;
			for (String n : fieldName.split("[.]")) {
				Field f = getFieldByName(p.getClass(), n);
				f.setAccessible(true);
				try {
					p = f.get(p);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
			return p;
		} else {
			try {
				Field f = getFieldByName(obj.getClass(), fieldName);
				f.setAccessible(true);
				return f.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
