package com.bandaoti.employee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 程序入口
 * 
 * @author XiRuiQiang
 */
@SpringBootApplication
@MapperScan(basePackages = "com.bandaoti.employee.dao")
public class Application extends SpringBootServletInitializer {
	public static ConfigurableApplicationContext application=null;
	public static void main(String[] args) {
		application=SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}