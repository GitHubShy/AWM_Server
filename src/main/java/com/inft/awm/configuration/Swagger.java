package com.inft.awm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//http://47.104.167.88:8080/swagger-ui.html#/
//http://localhost:8080/swagger-ui.html
/**
 * The class is used to generate api web site with the framework Swagger
 *
 * @author Yao Shi
 * @version 1.0
 * @date 2020/09/15 21:47 pm
 */
@Configuration
public class Swagger {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_12)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.inft.awm"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Document For AWM Server")
                .description("Author:Yao Shi")
                .contact(new Contact("Yao Shi","","147573224@qq.com"))
                .version("1.3")
                .build();
    }

}
