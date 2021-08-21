package com.springboot.reactive;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.springboot.reactive.model.Product;
import com.springboot.reactive.repository.ProductReactiveRepository;

import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@SpringBootApplication
public class MyDemoSpringBootReactiveApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyDemoSpringBootReactiveApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MyDemoSpringBootReactiveApplication.class, args);
		LOGGER.info("Inside MyDemoSpringBootReactiveApplication : {} ", run);
	}
	/**
	 * ApplicationRunner and CommandLineRunner functional Interface -> to get application context
	 * */

	@Bean
	public CommandLineRunner init(ReactiveMongoOperations reactiveMongoOperations, ProductReactiveRepository productRepository) {
		return args -> {
					Flux.just(new Product(null, "Big Latte", 13.99),
							  new Product(null, "Big Decaf", 14.99),
							  new Product(null, "Green Tea", 15.99))
					.flatMap(productRepository::save)
					.thenMany(productRepository.findAll())
					.subscribe(savedProducts -> System.out.println("@@@@ Saved products on start of application: " + savedProducts));

//			reactiveMongoOperations
//				.collectionExists(Product.class)
//				.flatMap(exist -> exist? reactiveMongoOperations.dropCollection(Product.class) : Mono.just(exist))
//				.thenMany(v -> reactiveMongoOperations.createCollection(Product.class))
//				.thenMany(productFlux)
//				.thenMany(productRepository.findAll())
//				.subscribe(System.out::println);

			productRepository.findProductByName("Green Tea").subscribe(findByName -> System.out.println("@@@@ Find By Name 'Green Tea' : " + findByName));
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ my-demo-springboot-reactive : started on time : "+ LocalDateTime.now());
		};

	}



}
