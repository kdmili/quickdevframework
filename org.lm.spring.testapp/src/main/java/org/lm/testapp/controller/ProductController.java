package org.lm.testapp.controller;

import org.lm.quick.controller.entity.AbsBaseEntityController;
import org.lm.testapp.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController extends AbsBaseEntityController<Product>{
	
	@RequestMapping("/entity/list/Product")
	public String test_override_url_map(){
		return "custProduct";
	}
	
 
}
