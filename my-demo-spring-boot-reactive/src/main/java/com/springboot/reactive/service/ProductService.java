package com.springboot.reactive.service;

import com.springboot.reactive.model.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> getAllProducts();

    Mono<ResponseEntity<Product>> getProductById(String id);

    Mono<ResponseEntity<Product>> saveProduct(Product product);

    Mono<ResponseEntity<Product>> updateProduct(String id, Product product);

    Mono<ResponseEntity<Void>> deleteProduct(String id);

    Mono<ResponseEntity<Void>> deleteAllProduct();
}
