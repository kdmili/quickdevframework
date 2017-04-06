package org.lm.quick.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceBean {
	@Bean("messageSource")
	public MessageSource ms() {
		ResourceBundleMessageSource bundResource = new ResourceBundleMessageSource();
		bundResource.setBasename("18n/User");
		bundResource.setUseCodeAsDefaultMessage(true);
		return bundResource;
	}
}
