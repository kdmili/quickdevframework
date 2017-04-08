package org.lm.quick.controller;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.shiro.SecurityUtils;
import org.lm.quick.entity.Users;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@Autowired
	private List<Validator> validators;
	@Autowired
	EntityManager em;

	@InitBinder
	void initbinder(WebDataBinder binder) {
		binder.addCustomFormatter(new DateFormatter("YYYY-MM-dd"));
	}

	@RequestMapping(path = { "/", "index" })
	public ModelAndView index(ModelMap map) {
		// map.put("user", new User());
		ModelAndView mv = new ModelAndView("index");

		String username = SecurityUtils.getSubject().getPrincipal().toString();
		Users user = new Users();
		user.setUsername(username);
		SimpleJpaRepository<Users, Long> jpa = new SimpleJpaRepository<>(Users.class, em);
		user = jpa.findOne(Example.of(user));
		mv.addObject("user", user);
		return mv;
	}

	
	
	
	@RequestMapping("/post")
	public ModelAndView test_bind(@RequestParam Map<String, Object> map) {
		Users u = new Users();
		WebDataBinder binder = new WebDataBinder(u, "user");
		binder.bind(new MutablePropertyValues(map));
		for (Validator v : validators) {
			System.out.println("vvvvvvvvvvv:" + v.getClass());
			binder.addValidators(v);
		}
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("user", u);
		binder.validate();
		BindingResult bindresult = binder.getBindingResult();
		if (bindresult.hasErrors()) {
			for (ObjectError er : bindresult.getAllErrors()) {
				System.out.println(er.getObjectName() + "," + er.getDefaultMessage());
			}

			mv.addAllObjects(bindresult.getModel());
			// mv.addObject("user", u);
		}

		return mv;

	}
	
	@RequestMapping("/uptest")
	public String test_upload(ModelMap m){
		m.put("user", new Users());
		return "inputTest";
	}

}
