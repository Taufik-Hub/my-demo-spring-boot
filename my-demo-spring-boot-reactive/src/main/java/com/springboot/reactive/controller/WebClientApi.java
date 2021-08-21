package com.springboot.reactive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.springboot.reactive.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebClientApi.class);

	/**
	 * webclient
	 * .get()//post()
	 * .uri("hhtp://localhost:8080/{id}", id)
	 * .contentType(MediaType.APPLICATION_JSON)
	 * .body(BodyInserts.fromMultipartData("Key", "Value").with("Key", "Value"))
	 * .syscBody(MultiValueMap/MultiPartBodyBuilder)
	 * .retrive()//exchange()
	 * .flatMap(response -> response.toEntity(Product.class))
	 * .onStatus(HTTPStatus::Ok, response -> ...)
	 * .bodyToMono(Employee.class)//bodyToFlux(Employee.class)*/

	private static final String BASE_URL = "http://localhost:8085/products";
	private WebClient webClient = WebClient
										.builder()
										.baseUrl(BASE_URL)
										.build();

	public static void main(String[] args) {
		WebClientApi webClientApi = new WebClientApi();
		webClientApi
				.createNewProduct()
				.thenMany(webClientApi.getAllProducts())
				.take(1)
				.flatMap(pro -> webClientApi.updateProductById(pro.getId(), new Product(pro.getId(), "Rasmalai", 22.99)))
				.flatMap(pro -> webClientApi.deleteProductById(pro.getId())).thenMany(webClientApi.getAllProducts())
				.thenMany(webClientApi.getAllEvents()).subscribe(System.out::println);

	}

	public Mono<ResponseEntity<Product>> createNewProduct() {
		return webClient
					.post()//
					.body(Mono.just(new Product(null, "Gulab Jamun", 12.99)), Product.class)//
					.exchange()//
					.flatMap(response -> response.toEntity(Product.class))//
					.doOnError(ex -> LOGGER.info("error : response : {}", ex.getMessage()))//
					.doOnSuccess(res -> LOGGER.info("success : response : {}", res.getBody()));//
	}

	public Flux<Product> getAllProducts() {
		return webClient
					.get()
					.retrieve()
					.bodyToFlux(Product.class)
					.doOnError(ex -> LOGGER.info("error : response : {}", ex.getMessage()))//
					.doOnNext(res -> LOGGER.info("success : response : {}", res));
	}

	public Mono<Product> updateProductById(String id, Product product) {
		return webClient
					.put().uri("/{id}", id)
					.body(Mono.just(new Product(null, product.getName(), product.getPrice())), Product.class)
					.retrieve()
					.bodyToMono(Product.class)
					.doOnError(ex -> LOGGER.info("error : response : {}", ex.getMessage()))//
					.doOnSuccess(res -> LOGGER.info("success : response : {}", res));//
	}

	public Mono<Void> deleteProductById(String id) {
		return webClient
					.delete().uri("/{id}", id)
					.retrieve()
					.bodyToMono(Void.class)
					.doOnError(ex -> LOGGER.info("error : response : {}", ex.getMessage()))//
					.doOnSuccess(res -> LOGGER.info("success : response : {}", res));//
	}

	public Mono<Void> getAllEvents() {
		return webClient
					.delete().uri("/events")
					.retrieve()
					.bodyToMono(Void.class)
					.doOnError(ex -> LOGGER.info("error : response : {}", ex.getMessage()))//
					.doOnSuccess(res -> LOGGER.info("success : response : {}", res));//
	}

}
