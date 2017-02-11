package org.lm.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

@Embeddable
public class Recorded {
	@Column(updatable = false)
	private Date createDate;
	
	@Column(insertable = false)
	private Date upDate;
 
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpDate() {
		return upDate;
	}

	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}

}
