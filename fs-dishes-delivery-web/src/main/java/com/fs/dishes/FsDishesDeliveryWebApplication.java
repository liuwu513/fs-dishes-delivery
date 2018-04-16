package com.fs.dishes;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 配乐送 鲜蔬配送系统
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MybatisAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class FsDishesDeliveryWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FsDishesDeliveryWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FsDishesDeliveryWebApplication.class, args);
    }
}
