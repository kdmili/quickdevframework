package org.lm.quick.controller.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;

import org.lm.quick.entity.BaseEntity;
import org.lm.quick.meta.ColumnMeta;
import org.lm.quick.meta.EntityMeta;
import org.lm.quick.util.EntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@org.springframework.transaction.annotation.Transactional(readOnly=true)
public abstract class AbsBaseEntityController<T extends BaseEntity> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Class<T> entityType;
	private PagingAndSortingRepository<T, Integer> repository;

	@Autowired
	private EntityManager em;

	public AbsBaseEntityController() {
		Type gc = getClass().getGenericSuperclass();
		if (gc instanceof ParameterizedType) {
			ParameterizedType ptype = (ParameterizedType) getClass().getGenericSuperclass();
			setEntityType((Class<T>) ptype.getActualTypeArguments()[0]);
		}
	}

	public EntityManager getEm() {
		return this.em;
	}

	public Class<T> getEntityType() {
		return entityType;
	}

	public void setEntityType(Class<T> entityType) {
		this.entityType = entityType;

	}

	public PagingAndSortingRepository<T, Integer> getRepository() {
		if (repository == null)
			repository = new SimpleJpaRepository<T, Integer>(getEntityType(), em);
		return repository;
	}

	@ModelAttribute(name = "entityMeta")
	public EntityMeta entityMeta() {
		return EntityUtil.getEntityMeta(em.getMetamodel(), entityType);
	}

	@ModelAttribute(name = "columns")
	public List<ColumnMeta> columns() {
		return EntityUtil.getColumnMeta(em.getMetamodel(), entityType);
	}

	
	public static void main(String[] args) {
		String json="{id:1,name:2}";
		JSONObject m = JSON.parseObject(json);
		System.out.println(m.get("id"));
		System.out.println(m.get("name"));
	}
	
	
}
