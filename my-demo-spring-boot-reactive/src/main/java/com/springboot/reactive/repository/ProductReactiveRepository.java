package com.springboot.reactive.repository;

import com.springboot.reactive.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductReactiveRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findProductByName(String name);
}
