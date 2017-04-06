package org.lm.test.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class PromAop {

	public static void main(String[] args) {
		List<Advisor> advisor =new ArrayList<>();
		advisor.add(new TestAdvisor());
	List<Advisor> finds = AopUtils.findAdvisorsThatCanApply(advisor, TestAop.class);
	System.out.println(finds.size());
	System.out.println(AopUtils.canApply(new TestAdvisor(), TestAop.class));
		 
	}
	
	@Bean
	public Advisor testAop2() {
		TestAdvisor advisor = new TestAdvisor();

		advisor.setAdvice(new org.aopalliance.intercept.MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation paramMethodInvocation) throws Throwable {
				System.out.println("---before invoke--");
				Object v = paramMethodInvocation.proceed();
				System.out.println("----after invoke");
				return v;
			}
		});
		return advisor;
	}
}
