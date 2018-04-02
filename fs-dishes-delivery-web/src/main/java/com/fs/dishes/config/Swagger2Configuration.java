package com.fs.dishes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	/**
     * Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。
     * 总体目标是使客户端和文件系统作为服务器以同样的速度来更新。 文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。
	 * Swagger 让部署管理和使用功能强大的API从未如此简单。
	 * 
	 * 前后端分离开发 API文档非常明确 测试的时候不需要再使用URL输入浏览器的方式来访问Controller
	 * 传统的输入URL的测试方式对于post请求的传参比较麻烦（当然，可以使用postman这样的浏览器插件）
	 * spring-boot与swagger的集成简单的一逼
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		           .apiInfo(apiInfo()).select()
		           .apis(RequestHandlerSelectors.basePackage("com.fs.dishes.module"))
		           .paths(PathSelectors.any())
		           .build();
	}
	
	private ApiInfo apiInfo() {
		Contact contact = new Contact("liuwu", "liuwu", "liuwu513@163.com");
		return new ApiInfoBuilder()
		           .title("佛山菜品配送系统")
		           .description("佛山菜品配送系统 API文档")
		           .termsOfServiceUrl("")
		           .contact(contact)
		           .version("1.0.0")
		           .build();
	}
}