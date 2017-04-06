package org.lm.quick.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class MonitorController {
	@Autowired
	ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@RequestMapping("/service/console")
	public ModelAndView executExpress(ModelMap map, String express) {
		Expression ex = new SpelExpressionParser().parseExpression(express);
		map.put("express", express);
		try {
			Object value = ex.getValue(new StandardEvaluationContext(applicationContext));
			map.put("value", JSONObject.toJSONString(value));
		} catch (Exception e) {
			map.put("error", e.getMessage());
		}
		ModelAndView mv = new ModelAndView("service/console");
		mv.addAllObjects(map);
		return mv;
	}
	
	
	
}
