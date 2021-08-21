package com.springboot.reactive.controller;

import com.springboot.reactive.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Component
public class ProductRouter {

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
		return RouterFunctions
				.route(GET("/products").and(accept(APPLICATION_JSON)), productHandler::getAllProducts)
                .andRoute(GET("/products/events").and(accept(TEXT_EVENT_STREAM)), productHandler::getProductEvents)
                .andRoute(GET("/products/{id}").and(accept(APPLICATION_JSON)), productHandler::getProductById)
				.andRoute(POST("/products").and(contentType(APPLICATION_JSON)), productHandler::saveProduct)
				.andRoute(DELETE("/products").and(accept(APPLICATION_JSON)), productHandler::deleteAllProducts)
				.andRoute(PUT("/products/{id}").and(contentType(APPLICATION_JSON)), productHandler::updateProduct)
				.andRoute(DELETE("/products/{id}").and(accept(APPLICATION_JSON)), productHandler::deleteProduct);

//        return nest(path("/products"),
//                nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
//                        route(GET("/"), productHandler::getAllProducts).andRoute(method(HttpMethod.POST), productHandler::saveProduct)
//                                .andRoute(DELETE("/"), productHandler::deleteAllProducts)
//                                .andRoute(GET("/events"), productHandler::getProductEvents).andNest(path("/{id}"),
//                                route(method(HttpMethod.GET), productHandler::getProduct)
//                                        .andRoute(method(HttpMethod.PUT), productHandler::updateProduct)
//                                        .andRoute(method(HttpMethod.DELETE), productHandler::deleteProduct))));
    }
}
