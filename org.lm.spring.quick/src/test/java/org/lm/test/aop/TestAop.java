package org.lm.test.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(PromAop.class)
public class TestAop {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TestAop.class);
		app.setBannerMode(Mode.OFF);
		app.setWebEnvironment(false);
		ConfigurableApplicationContext c = app.run(args);
		
		for(String n:c.getBeanDefinitionNames()){
			System.out.println(n);
		}
		for(String n:c.getBeanNamesForType(TestAdvisor.class)){
			System.out.println(n);
		}
		 c.getBean(TestAop.class).test();
	}



	public void test() {
		System.out.println("test");
	}

	
 

}
