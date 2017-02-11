package org.lm.test.util;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;

import org.junit.Test;
import org.lm.jpa.entity.Home;
import org.lm.jpa.entity.Recorded;
import org.lm.jpa.entity.User;
import org.lm.jpa.meta.ColumnMeta;
import org.lm.jpa.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.util.Assert;

public class TestUtil extends BaseTest {

	@Autowired
	EntityManager em;

	@Test
	public void parse_entity_info() throws Exception {
		List<ColumnMeta> info = EntityUtil.getColumnMeta(em.getMetamodel(), User.class);
		Assert.notNull(info);
		Assert.isTrue(info.size() > 1);
		for (ColumnMeta c : info) {
			System.out.println(c.getOrder());
			System.out.print(c.getColumnName());
			System.out.print("->");
			System.out.print(c.getJavaType());
			System.out.print(" joinType:: ");
			System.out.print(c.getPersistype());
			System.out.println("editable::"+c.isEditAble());
			
			if (c.getPersistype().equals(PersistentAttributeType.ONE_TO_MANY)) {
		 
				Field f = c.getEntity().getDeclaredField(c.getColumnName());
				ResolvableType rf = ResolvableType.forField(f);
				System.out.println(rf.getGeneric(0).getSource());
			}
		}
	}

	
	@Test
	public void test_get_column_info() throws Exception {
		List<ColumnMeta> info = EntityUtil.getColumnMeta(em.getMetamodel(), Home.class);
		Assert.notNull(info);
		Assert.isTrue(info.size() > 1);
		for(ColumnMeta c:info){
			System.out.println(c.getColumnName());
		}
	}
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		
		Field f = EntityUtil.getColumnField(User.class, "recordInfo");
		System.out.println(f.getName());
		  f = EntityUtil.getColumnField(User.class, "recordInfo.createDate");
		System.out.println(f.getName());
		System.out.println(f.getAnnotation(Column.class).name());
		
		
		
	}
	
	@Test
	public void test_getColumn_value() throws Exception {
		List<ColumnMeta> info = EntityUtil.getColumnMeta(em.getMetamodel(), User.class);
		User u=new User();
		Recorded recordInfo=new Recorded();
		recordInfo.setCreateDate(new Date());
		recordInfo.setUpDate(new Date());
		u.setRecordInfo(recordInfo);
		u.setName("test Name");
		
		for(ColumnMeta c:info){
			System.out.println(c.getPersistype());
		 
			assertNotNull(c.getPersistype());
		}
		
		
		
	}
	
	@Test
	public void test_order_column() throws Exception {
		List<ColumnMeta> info = EntityUtil.getColumnMeta(em.getMetamodel(), User.class);
		for(ColumnMeta c:info){
			System.out.println(c.getColumnName());
		}
	}
	
	@Test
	public void test_stringMessageConverter() throws Exception {
		ConversionService conversionService = new DefaultConversionService(); 
		 ObjectToStringHttpMessageConverter converter = new ObjectToStringHttpMessageConverter(conversionService);
		 String obj="";
		 MockRenderRequest req = new MockRenderRequest();
		 req.addParameter("home.id", "1");
		 req.addParameter("id", "2");
		//converter.read(User.class, new ServletServerHttpRequest(req));
	}

 

}
