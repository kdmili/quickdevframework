package org.lm.jpa.controller;
 

import javax.validation.Valid;

import org.lm.jpa.entity.User;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

 
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/post")
	@ResponseBody
	public User test_bind(@Valid User user,BindingResult bindresult){
		System.out.println(user.getName());
		System.out.println(user.getRegDate());
		System.out.println(user.getRecordInfo().getCreateDate());
		System.out.println(user.getUserBooks().size());
		if(bindresult.hasErrors())
		{
			for(ObjectError er:bindresult.getAllErrors()){
				System.out.println(er.getObjectName()+","+er.getDefaultMessage());
			}
		}
		return user;
	}
	
	
	 
	
	
	
}
