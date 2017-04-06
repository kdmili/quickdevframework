package org.xyhaoda.entity;

import java.util.Collection;

import javax.persistence.Entity;

import org.lm.quick.entity.BaseEntity;

@Entity
public class About extends BaseEntity {
	private String hotTel;
	private String busTel;
	private String qq;
	private String contractName;
	private String email;
	private String address;
	private String introduce;
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getHotTel() {
		return hotTel;
	}

	public void setHotTel(String hotTel) {
		this.hotTel = hotTel;
	}

	public String getBusTel() {
		return busTel;
	}

	public void setBusTel(String busTel) {
		this.busTel = busTel;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
