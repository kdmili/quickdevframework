package org.lm.jpa.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.dialect.springdata.SpringDataDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@AutoConfigureAfter(value = SpringTemplateEngine.class)
@AutoConfigureBefore(value=ThymeleafViewResolver.class)
public class ThyemleafConfig implements InitializingBean {
	@Autowired
	SpringTemplateEngine engine;

	@Bean
	public SpringDataDialect dataDialect(){
		return new SpringDataDialect();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		engine.setEnableSpringELCompiler(true);
		engine.setCacheManager(null);
		for(IDialect d:engine.getDialects()){
			System.out.println(d.getName());
		}
	}

}
