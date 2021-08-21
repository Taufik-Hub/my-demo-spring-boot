package com.springboot.reactive.controller;

import com.springboot.reactive.model.Product;
import com.springboot.reactive.model.ProductEvent;
import com.springboot.reactive.service.ProductService;
import com.springboot.reactive.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/products")
public class ProductController {//

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);//

    private ProductService productService;//

    public ProductController(ProductServiceImpl productServiceImpl) {//
        this.productService = productServiceImpl;//
    }//

    @GetMapping//
    public Flux<Product> getAllProducts() {//
        LOGGER.info("@@@@@ ProductController getAllProducts");//
        return this.productService.getAllProducts();//
    }//

    @GetMapping(value = "{id}")//
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable String id) {//
        LOGGER.info("@@@@@ ProductController getProductById id : {} ", id);//
        return this.productService.getProductById(id);//
    }//

    @PostMapping//
    // @ResponseStatus(HttpStatus.CREATED)//
    public Mono<ResponseEntity<Product>> saveProduct(@RequestBody Product product) {//
        LOGGER.info("@@@@@ ProductController saveProduct : {} ", product);//
        return this.productService.saveProduct(product);//
    }//

    @PutMapping(value = "{id}")//
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id, @RequestBody Product product) {//
        LOGGER.info("@@@@@ ProductController updateProduct : {}  and id {}", product, id);//
        return this.productService.updateProduct(id, product);//
    }//

    @DeleteMapping(value = "{id}")//
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id) {//
        LOGGER.info("@@@@@ ProductController deleteProduct : {}", id);//
        return productService.deleteProduct(id);//
    }//

    @DeleteMapping//
    public Mono<ResponseEntity<Void>> deleteAllProduct() {//
        LOGGER.info("@@@@@ ProductController deleteAllProduct");//
        return productService.deleteAllProduct();//
    }//

    /**
     * @GetMapping(value="/events", produces = MediaType.APPLICATION_JSON_VALUE)
     * thread will wait to provide response until it is 100% present
     * @GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
     * here no thread blocking happens it produces stream of product event
     */
    @GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)//
    public Flux<ProductEvent> getEventStream() {//
        LOGGER.info("@@@@@ ProductController getEventStream start");//
        Flux<ProductEvent> result =//
                Flux.interval(Duration.ofSeconds(1))//
                        .map(value -> new ProductEvent(value, "My Event"))//
                        .take(5);//
        LOGGER.info("@@@@@ ProductController getEventStream end");//
        return result;//
    }//
}
