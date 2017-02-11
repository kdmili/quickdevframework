package org.lm.jpa.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.core.annotation.Order;

@MappedSuperclass
public abstract class BaseEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8724123487787860711L;
	@Id
	@GeneratedValue
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
