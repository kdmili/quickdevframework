package org.lm.quick.componet.entity;

import java.util.Collection;

import javax.persistence.AttributeConverter;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class ArrayProprertyConvert implements AttributeConverter<Collection<?>, String> {

	@Override
	public String convertToDatabaseColumn(Collection<?> attribute) {
		if (attribute == null)
			return null;
		String data = JSON.toJSONString(attribute);
		return data;
	}

	@Override
	public Collection<?> convertToEntityAttribute(String dbData) {
		if(!StringUtils.hasLength(dbData))return null;
		return JSON.parseObject(dbData, Collection.class);
	}

}
