package org.lm.quick.util;

import java.lang.reflect.Field;

public abstract class TypeUtils {

	public static Field getFieldByName(Class<?> type, String fieldName) {
		if (type == null)
			return null;
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
		if (obj == null)
			return null;
		if (fieldName.contains(".")) {
			Object p = obj;
			for (String n : fieldName.split("[.]")) {
				if (p == null)
					return null;
				Field f = getFieldByName(p.getClass(), n);
				try {
					if(f==null)return null;
					f.setAccessible(true);
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
				if (f != null) {
					f.setAccessible(true);
					return f.get(obj);
				}
				return null;
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
