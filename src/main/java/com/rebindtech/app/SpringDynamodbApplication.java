package com.rebindtech.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.rebindtech.blog", "com.rebindtech.config", "com.rebindtech.repository", "com.rebindtech.util" })
public class SpringDynamodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDynamodbApplication.class, args);
	}

}
