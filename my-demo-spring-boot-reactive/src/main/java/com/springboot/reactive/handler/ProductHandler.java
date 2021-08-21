package com.springboot.reactive.handler;

import com.springboot.reactive.exceptions.ProductNotFoundException;
import com.springboot.reactive.model.Product;
import com.springboot.reactive.model.ProductEvent;
import com.springboot.reactive.repository.ProductReactiveRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.*;

@Component
public class ProductHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductHandler.class);
    private ProductReactiveRepository repository;

    //constructor injection - same as autowired
    public ProductHandler(ProductReactiveRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse//
                .ok()//
                .contentType(APPLICATION_JSON)//
                .body(repository.findAll(), Product.class)//
                .doOnError(throwable -> LOGGER.error("Exception while getAllProducts exception message : [{}]", throwable.getMessage()))//
                .doOnSuccess(res -> LOGGER.info("Successfully response sent by getAllProducts"));//
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        String id = request.pathVariable("id");
        return this
                .repository.findById(id)//
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product is not found for ProductId ".concat(id))))//
                .flatMap(product -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(product)))//
                .doOnError(throwable -> LOGGER.error("Exception while getProductById exception message : {}", throwable.getMessage()))//
                .doOnSuccess(response -> LOGGER.info("Successfully response sent by getProductById [{}]", id))//
                .onErrorResume(throwable -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .filterWhen(product -> checkProductIsNotExist(product.getId()))//not allowed to updated on existing record
                .map(productToBeSaved -> repository.save(productToBeSaved))
                .flatMap(productSaved -> ServerResponse.status(HttpStatus.CREATED).contentType(APPLICATION_JSON).body(productSaved, Product.class))
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product already exist with id ")))
                .doOnError(throwable -> LOGGER.error("Exception while saving Product in database. exceptionMessage : {}", throwable.getMessage()))//
                .doOnSuccess(response -> LOGGER.info("Successfully product saved in database.", response.toString()))//
                .onErrorResume(throwable -> ServerResponse.badRequest().build());//
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> productToBeUpdatedMono = request.bodyToMono(Product.class);
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product is not found for ProductId ".concat(id))))
                .zipWith(productToBeUpdatedMono, this::updateProductDetails)//this::updateProductDetails=(existingProduct, productToBeUpdated) -> new Product(existingProduct.getId(), productToBeUpdated.getName(), productToBeUpdated.getPrice())
                .map(productToBeUpdated -> repository.save(productToBeUpdated))
                .flatMap(productSaved -> ServerResponse.ok().contentType(APPLICATION_JSON).body(productSaved, Product.class))
                .doOnError(throwable -> LOGGER.error("Exception while updating Product in database. exceptionMessage : {}", throwable.getMessage()))//
                .doOnSuccess(response -> LOGGER.info("Successfully product updated in database.", response.toString()))//
                .onErrorResume(throwable -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.repository//
                .findById(id)//
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product is not found for ProductId ".concat(id))))//
                .flatMap(productToBeDeleted -> repository.deleteById(productToBeDeleted.getId()))//
                .flatMap(existingProduct -> ServerResponse.ok().build())//
                .doOnSuccess(response -> LOGGER.info("Successfully products deleted from database. product id {}", id))//
                .doOnError(throwable -> LOGGER.error("Exception while deleting Product from database. exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> ServerResponse.badRequest().build());//
    }

    public Mono<ServerResponse> deleteAllProducts(ServerRequest request) {
        return this.repository//
                .deleteAll()//
                .doOnSuccess(response -> LOGGER.info("Successfully all products deleted from database."))//
                .flatMap(result -> ServerResponse.ok().build())//
                .doOnError(throwable -> LOGGER.error("Exception while deleting all Products. exceptionMessage : {}", throwable.getMessage()))//
                .onErrorResume(throwable -> ServerResponse.badRequest().build());//;
    }

    public Mono<ServerResponse> getProductEvents(ServerRequest request) {
        Flux<ProductEvent> eventsFlux = Flux
                                            .interval(Duration.ofSeconds(1))
                                            .map(val -> new ProductEvent(val, "Product Event"));
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM).body(eventsFlux, ProductEvent.class);
    }

    private Mono<Boolean> checkProductIsNotExist(String id){
        if(StringUtils.isBlank(StringUtils.trimToEmpty(id))) {
            LOGGER.info("product Id is either null or blank");
            return Mono.just(Boolean.TRUE);
        }
        LOGGER.info("Checking product id is already present in database");
        return repository.existsById(id).map(exist -> !exist);
    }

    private Product updateProductDetails(Product existingProduct,Product productToBeUpdated){
        return  new Product(existingProduct.getId(), productToBeUpdated.getName(), productToBeUpdated.getPrice());
    }
}
