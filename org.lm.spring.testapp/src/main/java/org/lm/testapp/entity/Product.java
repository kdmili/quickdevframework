package org.lm.testapp.entity;

import javax.persistence.Entity;

import org.lm.quick.entity.BaseEntity;

@Entity
public class Product extends BaseEntity{
	private String pname;
	private float price;
	private String desc;
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
