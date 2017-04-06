package org.lm.test.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;
import org.lm.quick.entity.Users;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;

import com.alibaba.fastjson.JSON;

public class NoWebTest {
	@Test
	public void test_json_convert() throws Exception {
		String str = "{\"id\":\"12\",\"recordInfo.createDate\":\"2017-12-16\",\"recordInfo.upDate\":\"\",\"home.id\":\"12\",\"classRoom.id\":\"\",\"sex\":\"0\",\"regDate\":\"2017-02-16\"}";
		Users u = JSON.parseObject(str, Users.class);
		System.out.println(u.getRecordInfo().getCreateDate());
		System.out.println(u.getHome().getId());
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

	}

	@Test
	public void test_web_data_binder() throws Exception {
		Users u = new Users();
		WebDataBinder binder = new WebDataBinder(u);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 12);
		map.put("home.id", 2);
		map.put("regDate", "2012-1-1");
		 
	 
		binder.bind(new MutablePropertyValues(map));
		System.out.println(u.getId());
		System.out.println(u.getHome().getId());
		Assert.isTrue(u.getId().equals(12));
		Assert.isTrue(u.getHome().getId().equals(2));
		System.out.println(u.getRegDate());
		Assert.notNull(u.getRegDate());
		
	}
	
	@Test
	public void test_bundMessageSource() throws Exception {
		ResourceBundleMessageSource bundResource = new ResourceBundleMessageSource();
		bundResource.addBasenames("18n/User");
		 String msg = bundResource.getMessage("user.name", null, null);
		 System.out.println(msg);
		 Assert.notNull(msg);
	}
	
	@Test
	public void test_java_message_resource() throws Exception {
		String ag = ResourceBundle.getBundle("18n/User").getString("user.name");
		System.out.println(ag);
	}
	 

}
