package com.fs.dishes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 配乐送 鲜蔬配送系统
 */
@SpringBootApplication
public class FsDishesDeliveryWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FsDishesDeliveryWebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FsDishesDeliveryWebApplication.class, args);
	}
}
