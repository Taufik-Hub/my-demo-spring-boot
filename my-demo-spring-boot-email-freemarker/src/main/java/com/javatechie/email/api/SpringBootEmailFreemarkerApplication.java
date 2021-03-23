package com.javatechie.email.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootEmailFreemarkerApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootEmailFreemarkerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailFreemarkerApplication.class, args);
		LOGGER.info("@@@@@ Service is UP...");

	}
}
