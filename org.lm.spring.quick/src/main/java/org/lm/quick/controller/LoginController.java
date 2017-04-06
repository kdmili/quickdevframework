package org.lm.quick.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
	SecurityManager securityManager;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/loginout")
	public String loginout() {
		SecurityUtils.getSubject().logout();
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(ModelMap map, String username, String password, boolean remeber) {
		AuthenticationToken token = new UsernamePasswordToken(username, password, remeber);
		try {
			SecurityUtils.getSubject().login(token);
			return new ModelAndView("index");
		} catch (AuthenticationException ex) {
			ex.printStackTrace();
			map.put("error", ex.getMessage());
			return new ModelAndView("login");
		}

	}
}
