package org.lm.quick.entity;

import javax.persistence.Entity;

@Entity(name="user_roles")
public class UserRoles extends BaseEntity{
	 
	private String role_name;
	private String username;
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
