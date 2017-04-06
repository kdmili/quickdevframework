package org.lm.quick.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

import org.lm.quick.componet.entity.MapPropertyConvert;

@Entity
public class Dictionary extends BaseEntity {

	@Column(name="dkey")
	private String key;

	@Convert(converter=MapPropertyConvert.class)
	@Column(name="dvalue")
	private Map<String, Object> value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getValue() {
		return value;
	}
	
	public void setValue(Map<String, Object> value) {
		this.value = value;
	}

}
