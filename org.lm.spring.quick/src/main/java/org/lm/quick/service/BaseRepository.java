package org.lm.quick.service;

import javax.persistence.EntityManager;

import org.lm.quick.entity.BaseEntity;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public  class BaseRepository<T extends BaseEntity> extends SimpleJpaRepository<T, Long> implements PagingAndSortingRepository<T, Long> {
	private Class<?> genericType;
	
 
	public BaseRepository(Class<T> entityType, EntityManager em){
		super(entityType,em);
		this.genericType=entityType;
	}
	
//	 void initGenericType(){
//		Type type = getClass().getGenericSuperclass();
//		if(type instanceof java.lang.reflect.ParameterizedType)
//		{
//			Type[] args = ((java.lang.reflect.ParameterizedType)type).getActualTypeArguments();
//			if(args!=null && args.length>0)
//			{
//				this.genericType=args[0].getClass();
//			}
//		}
//	}
	 
	 public Class<?> getGenericType() {
		return genericType;
	}

}
