package com.springboot.kafka.producer.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springboot.kafka.producer.kafka.KafkaPublisher;
import com.springboot.kafka.producer.model.Product;

import reactor.core.publisher.Mono;
@Component
public class ProductHandler {
	
	@Autowired
	private KafkaPublisher kafkaPublisher;
	
	
	public Mono<ServerResponse> saveProduct(ServerRequest request) {
		Mono<Product> productMono = request.bodyToMono(Product.class);
		return productMono
				.map(kafkaPublisher::publish)
				.flatMap(product -> ServerResponse
										.status(HttpStatus.CREATED)
										.contentType(APPLICATION_JSON)
										.body(product, Product.class)
				);

	}
//	private ProductRepository repository;

//	public ProductHandler(ProductRepository repository) {
//		this.repository = repository;
//	}

//	public Mono<ServerResponse> getAllProducts(ServerRequest request) {
//		Flux<Product> products = repository.findAll();
//
//		return ServerResponse.ok().contentType(APPLICATION_JSON).body(products, Product.class);
//	}
//
//	public Mono<ServerResponse> getProduct(ServerRequest request) {
//		String id = request.pathVariable("id");
//		Mono<Product> productMono = this.repository.findById(id);
//		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//
//		return productMono
//				.flatMap(product -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(product)))
//				.switchIfEmpty(notFound);
//	}

//	public Mono<ServerResponse> saveProduct(ServerRequest request) {
//		Mono<Product> productMono = request.bodyToMono(Product.class);
//
//		return productMono.flatMap(product -> ServerResponse.status(HttpStatus.CREATED).contentType(APPLICATION_JSON)
//				.body(repository.save(product), Product.class));
//	}

//	public Mono<ServerResponse> updateProduct(ServerRequest request) {
//		String id = request.pathVariable("id");
//		Mono<Product> existingProductMono = this.repository.findById(id);
//		Mono<Product> productMono = request.bodyToMono(Product.class);
//
//		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//
//		return productMono
//				.zipWith(existingProductMono,
//						(product, existingProduct) -> new Product(existingProduct.getId(), product.getName(),
//								product.getPrice()))
//				.flatMap(product -> ServerResponse.ok().contentType(APPLICATION_JSON).body(repository.save(product),
//						Product.class))
//				.switchIfEmpty(notFound);
//	}
//
//	public Mono<ServerResponse> deleteProduct(ServerRequest request) {
//		String id = request.pathVariable("id");
//
//		Mono<Product> productMono = this.repository.findById(id);
//		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
//
//		return productMono.flatMap(existingProduct -> ServerResponse.ok().build(repository.delete(existingProduct)))
//				.switchIfEmpty(notFound);
//	}
//
//	public Mono<ServerResponse> deleteAllProducts(ServerRequest request) {
//		return ServerResponse.ok().build(repository.deleteAll());
//	}
//
//	public Mono<ServerResponse> getProductEvents(ServerRequest request) {
//		Flux<ProductEvent> eventsFlux = Flux.interval(Duration.ofSeconds(1))
//				.map(val -> new ProductEvent(val, "Product Event"));
//
//		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(eventsFlux, ProductEvent.class);
//	}
}
