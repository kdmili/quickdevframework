package org.lm.quick.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.lm.quick.meta.ColumnMeta;
import org.lm.quick.meta.EntityMeta;
import org.lm.quick.ui.annotation.Ordered;
import org.lm.quick.ui.annotation.UIField;

public abstract class EntityUtil {
	public static EntityMeta getEntityMeta(Metamodel metaModel, Class<?> entityType) {
		for (EntityType<?> ety : metaModel.getEntities()) {
			if (ety.getJavaType().equals(entityType)) {
				EntityMeta entityMeta = new EntityMeta();
				entityMeta.setEntityName(ety.getName());
				entityMeta.setEntityType(ety);
				return entityMeta;
			}
		}
		return null;

	}

	public static List<ColumnMeta> getColumnMeta(Metamodel metalModel, Class<?> entityType) {
		EntityMeta entityMeta = getEntityMeta(metalModel, entityType);
		if (entityMeta == null)
			return null;
		ArrayList<ColumnMeta> result = new ArrayList<ColumnMeta>();

		Map<String, ColumnMeta> map = new HashMap<String, ColumnMeta>();

		for (Attribute<?, ?> at : entityMeta.getEntityType().getAttributes()) {
			for (ColumnMeta m : ConvertAttToColumnMeta(result.size(), entityType, metalModel, at, "")) {
				map.put(m.getColumnName(), m);
			}
		}
		// order
		 Ordered orderinfo = entityType.getAnnotation( Ordered.class);
		if (orderinfo != null) {
			String[] orderedColunm = orderinfo.value().split("[,]");
			for (String c : orderedColunm) {
				if (map.containsKey(c)) {
					result.add(map.get(c));
					map.remove(c);
					continue;
				}
				String key = c;
				List<String> flagKey = new ArrayList<String>();
				for (String k : map.keySet()) {
					if (k.indexOf(key + ".") > -1) {
						result.add(map.get(k));
						flagKey.add(k);
						continue;
					}
				}
				for (String k : flagKey) {
					map.remove(k);
				}

			}
		

		}
		// remain not ordered
		for (Entry<String, ColumnMeta> en : map.entrySet()) {
			result.add(en.getValue());
		}
		return result;
	}

	static List<ColumnMeta> ConvertAttToColumnMeta(int order, Class<?> entity, Metamodel m, Attribute<?, ?> att,
			String profix) {
		switch (att.getPersistentAttributeType()) {
		case BASIC:
			ColumnMeta cm = new ColumnMeta(entity, att);
			cm.setColumnName(profix + att.getName());
		
			Field f = getColumnField(entity, cm.getColumnName());
			if (f != null) {
				UIField uif = f.getAnnotation(UIField.class);
				cm.setUiType(uif);
				cm.setOrder(order);
				cm.setField(f);
				Column c = f.getAnnotation( Column.class);
				if(c!=null &&  !c.updatable()){
					cm.setEditAble(false);
				}
				if(uif!=null)
				cm.setHidden(uif.hidden());
			}

			return Arrays.asList(cm);
		case EMBEDDED:
			EmbeddableType<?> et = getEmbeddType(m, att.getJavaType());
			List<ColumnMeta> list = new ArrayList<ColumnMeta>();
			String p = att.getName() + ".";
			int innerOrder = order + 1;
			for (Attribute<?, ?> at : et.getAttributes()) {
				list.addAll(ConvertAttToColumnMeta(++innerOrder, entity, m, at, p));
			}
			return list;
		case ELEMENT_COLLECTION:
			ColumnMeta meta = new ColumnMeta(entity, att);
			meta.setOrder(order);
			meta.setField(getColumnField(entity, att.getName()));
			meta.setJavaType(att.getJavaType());
			return Arrays.asList(meta);
		case MANY_TO_ONE:
			ColumnMeta mtone = new ColumnMeta(entity, att);
			mtone.setJavaType(att.getJavaType());
			mtone.setOrder(order);
			mtone.setField(getColumnField(entity, att.getName()));
		default:
			ColumnMeta defm = new ColumnMeta(entity, att);
			defm.setOrder(order);
			defm.setField(getColumnField(entity, att.getName()));
			return Arrays.asList(defm);

		}

	}

	public static Field getColumnField(Class<?> entityType, String columnName) {
		if (columnName.indexOf(".") < 0) {
			return TypeUtils.getFieldByName(entityType, columnName);
		}
		Field f = null;
		for (String c : columnName.split("[.]")) {
			f = (f == null ? TypeUtils.getFieldByName(entityType, c) : TypeUtils.getFieldByName(f.getType(), c));
		}
		return f;
	}

	static EmbeddableType<?> getEmbeddType(Metamodel m, Class<?> embaddedType) {
		for (EmbeddableType<?> t : m.getEmbeddables()) {
			if (embaddedType.equals(t.getJavaType()))
				return t;
		}
		return null;
	}

}
