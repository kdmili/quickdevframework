package org.lm.quick.componet.entity;

import java.util.Map;

import javax.persistence.AttributeConverter;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class MapPropertyConvert implements AttributeConverter<Map<?, ?>,String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3188565545504759943L;

	@Override
	public String convertToDatabaseColumn(Map<?, ?> attribute) {
		if(attribute==null || attribute.isEmpty())return null;
		return JSON.toJSONString(attribute);
	}

	@Override
	public Map<?, ?> convertToEntityAttribute(String dbData) {
		if(StringUtils.hasLength(dbData))
			return JSON.parseObject(dbData);
		return null;
	}

	 


}
