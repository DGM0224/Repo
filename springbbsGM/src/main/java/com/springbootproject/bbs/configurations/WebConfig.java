package com.springbootproject.bbs.configurations;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	registry.addViewController("/writeForm").setViewName("views/writeForm");
	registry.addViewController("/writeBoard").setViewName("views/writeForm");
	}

}
