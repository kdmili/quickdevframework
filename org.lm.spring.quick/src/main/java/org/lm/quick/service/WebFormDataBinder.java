package org.lm.quick.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.lm.quick.entity.BaseEntity;
import org.lm.quick.meta.ColumnMeta;
import org.lm.quick.util.EntityUtil;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

@Service
public class WebFormDataBinder {
	@Autowired
	private EntityManager em;

	@Autowired
	private List<Validator> validators;
	
 

	public BindingResult bind(Object obj, PropertyValues propvalues) {

		WebDataBinder binder = new WebDataBinder(obj,"entity");
		binder.addCustomFormatter(new DateFormatter());
		for(Validator v:this.validators)
		binder.addValidators(v);
		binder.bind(propvalues);
		binder.validate();
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
		
		return binder.getBindingResult();
	}

}
