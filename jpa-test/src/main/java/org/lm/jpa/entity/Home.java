package org.lm.jpa.entity;

import javax.persistence.Entity;

@Entity
public class Home extends BaseEntity{
	private String  address;
	private String name;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
