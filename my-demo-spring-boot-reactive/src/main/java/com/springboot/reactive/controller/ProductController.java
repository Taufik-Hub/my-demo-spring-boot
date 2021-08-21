package com.springboot.reactive.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.reactive.model.Product;
import com.springboot.reactive.model.ProductEvent;
import com.springboot.reactive.repository.ProductReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/products")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	ProductReactiveRepository productRepository;

	public ProductController(ProductReactiveRepository productRepository) {
		this.productRepository = productRepository;
		LOGGER.info("@@@@@ constructor of ProductController Initialize");
	}

	@GetMapping
	public Flux<Product> getAllProducts() {
		LOGGER.info("@@@@@ ProductController getAllProducts");
		return this.productRepository.findAll();
	}

	@GetMapping(value = "{id}")
	public Mono<ResponseEntity<Product>> getProductById(@PathVariable String id) {
		LOGGER.info("@@@@@ ProductController getProductById id : {} ", id);
		return this.productRepository.findById(id).map(product -> ResponseEntity.ok(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	// @ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<Product>> saveProduct(@RequestBody Product product) {
		LOGGER.info("@@@@@ ProductController saveProduct : {} ", product);
		return this.productRepository.save(product)
				.map(savedProduct -> ResponseEntity.status(HttpStatus.CREATED).body(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping(value = "{id}")
	public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id,
			@RequestBody Product product) {
		LOGGER.info("@@@@@ ProductController updateProduct : {}  and id {}", product, id);
		return this.productRepository.findById(id).flatMap(existingProduct -> {
			existingProduct.setName(product.getName());
			existingProduct.setPrice(product.getPrice());
			return productRepository.save(existingProduct);
		}).map(updateProduct -> ResponseEntity.ok(updateProduct)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "{id}")
	public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id) {
		LOGGER.info("@@@@@ ProductController deleteProduct : {}", id);
		return productRepository.findById(id)
				.flatMap(
						product -> productRepository.delete(product).then(Mono.just(ResponseEntity.ok().<Void>build())))
				.defaultIfEmpty(ResponseEntity.notFound().build());

	}

	@DeleteMapping
	public Mono<ResponseEntity<Void>> deleteAllProduct() {
		LOGGER.info("@@@@@ ProductController deleteAllProduct");
		return productRepository.deleteAll().then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	// @GetMapping(value="/events", produces =
	// MediaType.APPLICATION_JSON_VALUE)//thread will wait to provide response until
	// it is 100% present
	@GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE) // here no thread blocking
																						// happens it poduces stream of
																						// product event
	public Flux<ProductEvent> getEventStream() {
		LOGGER.info("@@@@@ ProductController getEventStream start");

		Flux<ProductEvent> result = Flux.interval(Duration.ofSeconds(1))
				.map(value -> new ProductEvent(value, "My Event")).take(5);
		LOGGER.info("@@@@@ ProductController getEventStream end");
		return result;
	}
}
