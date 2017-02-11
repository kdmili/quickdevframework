package org.lm.jpa.meta;

import java.lang.reflect.Field;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

import org.hibernate.jpa.internal.metamodel.SingularAttributeImpl;
import org.lm.jpa.entity.BaseEntity;
import org.lm.jpa.ui.annotation.UIField;
import org.lm.jpa.util.EntityUtil;
import org.lm.jpa.util.TypeUtils;
import org.springframework.core.Ordered;

public class ColumnMeta implements Ordered {
	private String columnName;
	private Class<?> javaType;
	private boolean editAble=true;
	private boolean hidden=false;
	private PersistentAttributeType persistype;
	private Attribute<?, ?> att;
	private Class<?> entity;
	private UIField uiType;
	private int order=0;
	private Field field;
	 
	
	
	public String getDictName() {
		return uiType==null?null: uiType.dictionaryKey();
	}
	 
	public  Object getValue(Object entity)
	{
		return TypeUtils.getFieldValue(getColumnName(), entity);
	}
	public ColumnMeta() {
	 
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}
 
	public void setValue(Object entity,Object value){
		if(getField()!=null){
			this.getField().setAccessible(true);
			try {
				this.getField().set(entity, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	public ColumnMeta(Class<?> entity, Attribute<?, ?> att) {
		this.entity=entity;
		this.att = att;
		this.setColumnName(att.getName());
		this.setJavaType(att.getJavaType());
		// this.setEditAble(att.getPersistentAttributeType());
		this.setPersistype(att.getPersistentAttributeType());
		if (att instanceof SingularAttributeImpl) {
			SingularAttributeImpl signAtt = (SingularAttributeImpl) att;
			if (signAtt.isId() || signAtt.isVersion()) {
				this.setEditAble(false);
			}
			if (signAtt.isVersion()) {
				this.setHidden(true);
			}
		}

	}

	 public UIField getUiType() {
		return uiType;
	}
	 public void setUiType(UIField uiType) {
		this.uiType = uiType;
	}
	
	public Class<?> getEntity() {
		return entity;
	}
	
	public PersistentAttributeType getPersistype() {
		return persistype;
	}

	public void setPersistype(PersistentAttributeType persistype) {
		this.persistype = persistype;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public boolean isEditAble() {
		return editAble;
	}

	public void setEditAble(boolean editAble) {
		this.editAble = editAble;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	@Override
	public int getOrder() {
		return this.order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

}
