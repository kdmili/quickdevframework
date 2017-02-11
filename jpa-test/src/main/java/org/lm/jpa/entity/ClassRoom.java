package org.lm.jpa.entity;

import javax.persistence.Entity;

@Entity
public class ClassRoom extends BaseEntity{
	private String className;
	private String classNo;
	public String getClassName() {
		return className;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
}
