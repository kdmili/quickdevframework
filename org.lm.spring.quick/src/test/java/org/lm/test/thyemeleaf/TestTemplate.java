package org.lm.test.thyemeleaf;

import org.junit.Test;
import org.lm.quick.entity.Users;
import org.lm.quick.ui.annotation.UIField;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

public class TestTemplate {
	@Test
	public void test_thyemeleaf() throws Exception {
		TemplateEngine engine=new TemplateEngine();
		FileTemplateResolver	templateResolver = new FileTemplateResolver();
		engine.addTemplateResolver(templateResolver);
		  Context context = new org.thymeleaf.context.Context();
		UIField f = Users.class.getDeclaredField("name").getAnnotation(UIField.class);
		context.setVariable("uitype",   f);
		context.setVariable("url", "/home/index");
		System.out.println(f.ftype());
		String res = engine.process("D:\\javaproject\\spring-test\\jpa-test\\src\\main\\resources\\templates\\test.html", context);
		System.out.println(res);
	}
}
