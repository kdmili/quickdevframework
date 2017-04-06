package org.lm.testapp;

import org.lm.quick.Booter;
import org.lm.testapp.controller.ProductController;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({Booter.class ,ProductController.class })
public class TestBoot {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TestBoot.class);
		app.setBannerMode(Mode.OFF);
		app.setHeadless(true);
		app.run(args);
	}
}
