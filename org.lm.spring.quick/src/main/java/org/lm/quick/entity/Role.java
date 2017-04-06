package org.lm.quick.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="roles")
public class Role extends BaseEntity{
	
	@NotEmpty
	@NaturalId
	private String role_name;
	
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	
}
