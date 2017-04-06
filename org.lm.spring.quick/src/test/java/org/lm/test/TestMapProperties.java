package org.lm.test;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;

public class TestMapProperties {
	@Test
	public void test_parse_properties() throws Exception {

		// Properties p = new Properties();
		//
		// FileInputStream stream = new FileInputStream(
		// "D:/javaproject/spring-test/jpa-test/src/main/resources/b.properties");
		// p.load(stream);
		// MutablePropertySources mutSource = new MutablePropertySources();
		// PropertyPlaceholderConfigurer pc = new
		// PropertyPlaceholderConfigurer();
		// pc.setProperties(p);

	}

	public static void main(String[] args) {

		MutablePropertyValues mv = new MutablePropertyValues();
		mv.add("user", "{name=name,pwd=123}");
	 
		Map<String, String> map = (Map<String, String>) mv.get("user");
		for (Entry<String, String> en : map.entrySet()) {
			System.out.println(en.getKey() + "," + en.getValue());
		}
		 
		
		  
	}
}
