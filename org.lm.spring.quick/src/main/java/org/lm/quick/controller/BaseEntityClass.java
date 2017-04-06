package org.lm.quick.controller;

import java.util.Iterator;

import javax.persistence.metamodel.EntityType;

import org.lm.quick.controller.entity.AbsBaseEntityController;
import org.lm.quick.entity.BaseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{entity}")
public class BaseEntityClass extends AbsBaseEntityController {


	@ModelAttribute("entityName")
	public String findEntityType(@PathVariable("entity") String entityName) {
		Iterator<EntityType<?>> iter = getEm().getMetamodel().getEntities().iterator();
		while (iter.hasNext()) {
			EntityType<?> entity = iter.next();
			if (entityName.equals(entity.getName())) {
				{
					setEntityType(entity.getJavaType());
					break;
				}
			}
		}
		return entityName;
	}
	
	
	@ModelAttribute(value = "baseEntity")
	public BaseEntity baseEntity() {
		try {
			if(getEntityType()==null)return null;
			return (BaseEntity) getEntityType().newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}


}
