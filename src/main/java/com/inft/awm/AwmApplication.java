package com.inft.awm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringBootApplication, entry of this project
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@SpringBootApplication
@EnableSwagger2
public class AwmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwmApplication.class, args);
	}

}
