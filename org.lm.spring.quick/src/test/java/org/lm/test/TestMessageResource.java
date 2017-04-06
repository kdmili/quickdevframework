package org.lm.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lm.test.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

public class TestMessageResource extends BaseTest {
	@Autowired
	ApplicationContext app;

	@Test
	public void test_message_resource() throws Exception {
		String msg=app.getMessage("name", null, null);
		System.out.println(msg);
		Assert.notNull(msg);
	}
}
