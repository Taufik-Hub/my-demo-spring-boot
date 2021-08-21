package com.springboot.kafka.producer.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springboot.kafka.producer.handler.ProductHandler;

@Controller
public class ProductController {
	
	@Bean
	RouterFunction<ServerResponse> routes(ProductHandler handler) {
		return 	route(POST("/products").and(contentType(APPLICATION_JSON)), handler::saveProduct);
		
		
		
//		route(GET("/products").and(accept(APPLICATION_JSON)), handler::getAllProducts)
//		.andRoute(POST("/products").and(contentType(APPLICATION_JSON)), handler::saveProduct)
//		.andRoute(DELETE("/products").and(accept(APPLICATION_JSON)), handler::deleteAllProducts)
//		.andRoute(GET("/products/events").and(accept(TEXT_EVENT_STREAM)), handler::getProductEvents)
//		.andRoute(GET("/products/{id}").and(accept(APPLICATION_JSON)), handler::getProduct)
//		.andRoute(PUT("/products/{id}").and(contentType(APPLICATION_JSON)), handler::updateProduct)
//		.andRoute(DELETE("/products/{id}").and(accept(APPLICATION_JSON)), handler::deleteProduct);
	}

}
