package org.xyhaoda.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.lm.quick.entity.BaseEntity;

@Entity
public class Product extends BaseEntity{

	private String name;
	@ManyToOne(optional=true,cascade=CascadeType.DETACH)
	private Category category;
	
	private String desc;
	
	
	
	
	
}
