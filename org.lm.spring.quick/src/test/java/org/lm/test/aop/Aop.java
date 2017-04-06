package org.lm.test.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

 
public class Aop {
	@Before("execution(* *.test(..))")
	public void beforCut() {
		System.out.println("before=======");
	}

	@After("execution(void *.test(..))")
	public void afterCut(){
		System.out.println("after execute ");
	}
}
