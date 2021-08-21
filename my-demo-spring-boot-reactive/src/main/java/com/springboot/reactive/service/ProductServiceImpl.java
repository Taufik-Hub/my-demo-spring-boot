package com.springboot.reactive.service;

import com.springboot.reactive.exceptions.ProductNotFoundException;
import com.springboot.reactive.model.Product;
import com.springboot.reactive.repository.ProductReactiveRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductReactiveRepository productRepository;

    public ProductServiceImpl(ProductReactiveRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getAllProducts() {//
        return this.productRepository//
                .findAll()//
                .doOnError(throwable -> LOGGER.error("Exception while getAllProducts exceptionMessage : [{}]", throwable.getMessage()));//
    }

    @Override
    public Mono<ResponseEntity<Product>> getProductById(String id) {
        return this.productRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product already exist with id ")))//
                .map(product -> ResponseEntity.ok(product))
                .doOnSuccess(response -> LOGGER.info("Successfully response sent by getProductById [{}]", id))//
                .doOnError(throwable -> LOGGER.error("Exception while getProductById exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> Mono.just(ResponseEntity.notFound().build()));//
    }

    @Override
    public Mono<ResponseEntity<Product>> saveProduct(Product product) {//
        return Mono.just(product)//
                .filterWhen(productSaved -> checkProductIsNotExist(productSaved.getId()))//not allowed to updated on existing record//
                .map(productToBeSaved -> productRepository.save(productToBeSaved))//
                .doOnSuccess(response -> LOGGER.info("Successfully product saved in database.", response.toString()))//)//
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product already exist with id ")))//
                .map(savedProduct -> ResponseEntity.status(HttpStatus.CREATED).body(product))//
                .doOnError(throwable -> LOGGER.error("Exception while saving Product in database. exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> Mono.just(ResponseEntity.notFound().build()));//
    }

    private Mono<Boolean> checkProductIsNotExist(String id) {//
        if (StringUtils.isBlank(StringUtils.trimToEmpty(id))) {//
            LOGGER.info("product Id is either null or blank");//
            return Mono.just(Boolean.TRUE);//
        }//
        LOGGER.info("Checking product id is already present in database");//
        return productRepository.existsById(id).map(exist -> !exist);//
    }//

    @Override
    public Mono<ResponseEntity<Product>> updateProduct(String id, Product product) {//
        return this.productRepository//
                .findById(id)//
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product is not found for ProductId ".concat(id))))//
                .map(existingProduct -> updateProductDetails(existingProduct, product))//
                .flatMap(productToBeSaved -> productRepository.save(productToBeSaved))//
                .map(updateProduct -> ResponseEntity.ok(updateProduct))//
                .doOnSuccess(response -> LOGGER.info("Successfully product updated in database.", response.toString()))//
                .doOnError(throwable -> LOGGER.error("Exception while updating Product in database. exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> Mono.just(ResponseEntity.notFound().build()));//
    }

    private Product updateProductDetails(Product existingProduct, Product productToBeUpdated) {//
        existingProduct.setName(productToBeUpdated.getName());//
        existingProduct.setPrice(productToBeUpdated.getPrice());//
        return existingProduct;//
    }//

    @Override
    public Mono<ResponseEntity<Void>> deleteProduct(String id) {//
        return productRepository//
                .findById(id)//
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product is not found for ProductId ".concat(id))))//
                .flatMap(productToBeDeleted -> productRepository.deleteById(productToBeDeleted.getId()))//
                .doOnSuccess(response -> LOGGER.info("Successfully products deleted from database. product id {}", id))//
                .then(Mono.just(ResponseEntity.ok().<Void>build()))//
                .doOnError(throwable -> LOGGER.error("Exception while deleting Product from database. exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> Mono.just(ResponseEntity.notFound().build()));//
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAllProduct() {//
        return productRepository//
                .deleteAll()//
                .then(Mono.just(ResponseEntity.ok().<Void>build()))//
                .doOnSuccess(response -> LOGGER.info("Successfully all products deleted from database."))//
                .doOnError(throwable -> LOGGER.error("Exception while deleting all Products. exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> Mono.just(ResponseEntity.notFound().build()));//
    }
}
