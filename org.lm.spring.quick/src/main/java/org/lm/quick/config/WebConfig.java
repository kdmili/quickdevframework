package org.lm.quick.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements InitializingBean {

	Logger log = LogManager.getLogger(WebConfig.class);

	@Autowired
	ServerProperties serverProperties;

	@Value("${server.error.includeStacktrace}")
	IncludeStacktrace includeStacktrace;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		serverProperties.getError().setIncludeStacktrace(includeStacktrace);
		log.info("server.error.includeStacktrace ="+includeStacktrace);
	}
}
