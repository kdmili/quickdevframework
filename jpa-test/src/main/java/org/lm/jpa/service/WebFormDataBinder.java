package org.lm.jpa.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.lm.jpa.entity.BaseEntity;
import org.lm.jpa.meta.ColumnMeta;
import org.lm.jpa.util.EntityUtil;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;

@Service
public class WebFormDataBinder {
	@Autowired
	private EntityManager em;


	public void bind(Object obj, PropertyValues propvalues) {

		WebDataBinder binder = new WebDataBinder(obj);
		binder.addCustomFormatter(new DateFormatter());
		binder.bind(propvalues);
		// jpa entity reference
		List<ColumnMeta> columns = EntityUtil.getColumnMeta(this.em.getMetamodel(), obj.getClass());
		for (ColumnMeta c : columns) {
			Object entityProperty = c.getValue(obj);
			if (entityProperty != null && entityProperty instanceof BaseEntity) {
				Integer id = ((BaseEntity) entityProperty).getId();
				if (id != null) {
					Object v = this.em.find(c.getJavaType(), id);
					c.setValue(obj, v);
				} else {
					c.setValue(obj, null);
				}
			}
		}
	}

}
