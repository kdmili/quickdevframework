package org.lm.test.controller;

import java.net.URI;
import java.util.Locale;
import java.util.Map.Entry;

import org.junit.Test;
import org.lm.quick.controller.entity.EntityController;
import org.lm.test.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

public class TestController extends BaseTest {
	@Autowired
	EntityController entityController;
	@Autowired
	ApplicationContext app;
	
	@Test
	public void test_message_source() throws Exception {
		String message = app.getMessage("name", null, Locale.getDefault());
		System.out.println(message);
		Assert.notNull(message);
	}

	@Test
	public void test_EntityController_List() throws Exception {

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(new URI("/entity/list/User"));
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(entityController).build();
		ResultActions ac = mockMvc.perform(request);
		MvcResult result = ac.andReturn();
		ModelAndView mv = result.getModelAndView();

		Assert.notNull(mv);
		System.out.println(mv.getViewName());

		for (Entry<String, Object> map : mv.getModel().entrySet()) {
			System.out.println(map.getKey() + "," + map.getValue());
		}
		Page page = (Page) mv.getModel().get("page");
		System.out.println(page.getContent().size());
		System.out.println(page.getTotalPages());

	}

}
