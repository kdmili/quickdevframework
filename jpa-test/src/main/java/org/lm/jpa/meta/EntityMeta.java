package org.lm.jpa.meta;

import javax.persistence.metamodel.EntityType;

public class EntityMeta {
	private String entityName;
	private EntityType<?> entityType;
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public EntityType<?> getEntityType() {
		return entityType;
	}
	public void setEntityType(EntityType<?> entityType) {
		this.entityType = entityType;
	}
	
}
