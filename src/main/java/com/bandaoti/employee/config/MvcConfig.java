package com.bandaoti.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// excludePathPatterns 用户排除拦截
		 registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);

	}

}
