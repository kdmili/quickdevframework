package org.lm.test.thyemeleaf;

import static org.junit.Assert.*;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.EngineConfig;
import org.junit.Test;
import org.lm.jpa.entity.User;
import org.lm.jpa.ui.annotation.UIField;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.engine.TemplateManager;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

public class TestTemplate {
	@Test
	public void test_thyemeleaf() throws Exception {
		TemplateEngine engine=new TemplateEngine();
		FileTemplateResolver	templateResolver = new FileTemplateResolver();
		engine.addTemplateResolver(templateResolver);
		  Context context = new org.thymeleaf.context.Context();
		UIField f = User.class.getDeclaredField("name").getAnnotation(UIField.class);
		context.setVariable("uitype",   f);
		context.setVariable("url", "/home/index");
		System.out.println(f.ftype());
		String res = engine.process("D:\\javaproject\\spring-test\\jpa-test\\src\\main\\resources\\templates\\test.html", context);
		System.out.println(res);
	}
}
