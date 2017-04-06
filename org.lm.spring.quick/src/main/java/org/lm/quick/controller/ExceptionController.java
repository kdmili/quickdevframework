package org.lm.quick.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionController {

	@ExceptionHandler
	public String error(ModelMap map, HttpServletRequest req, HttpServletResponse res, Exception ex) {
		map.put("error", ex.getMessage());
		map.put("exception", ex);
		map.put("code", res.getStatus());
		return "exception";
	}

}
