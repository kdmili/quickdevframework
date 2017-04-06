package org.lm.quick.entity;

import javax.persistence.Entity;

@Entity(name = "roles_permissions")
public class RolePermission extends BaseEntity {
	
	private String permission;
	private String role_name;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

}
