package com.springboot.reactive.annotation.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.springboot.reactive.service.ProductService;
import com.springboot.reactive.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.springboot.reactive.controller.ProductController;
import com.springboot.reactive.model.Product;
import com.springboot.reactive.model.ProductEvent;
import com.springboot.reactive.repository.ProductReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class JUnit5ControllerMockTest {
	private WebTestClient client;
	private List<Product> expectedList;

	@MockBean
	private ProductServiceImpl productServiceImpl;

	@BeforeEach
	void beforeEach() {
		this.client = WebTestClient.bindToController(new ProductController(productServiceImpl))
    							   .configureClient()
								   .baseUrl("/products")
								   .build();
		this.expectedList = Arrays.asList(new Product("1", "Big Latte", 2.99));
	}

	@Test
	void testGetAllProducts() {
		when(productServiceImpl.getAllProducts()).thenReturn(Flux.fromIterable(this.expectedList));
		client.get()
			  .uri("/")
			  .exchange()
			  .expectStatus().isOk()
			  .expectBodyList(Product.class).isEqualTo(expectedList);
	}

	@Test
	void testProductInvalidIdNotFound() {
		String id = "aaa";
		when(productServiceImpl.getProductById(id)).thenReturn(Mono.just(ResponseEntity.notFound().build()));
		client.get()
			  .uri("/{id}", id)
			  .exchange()
			  .expectStatus().isNotFound();
	}

	@Test
	void testProductIdFound() {
		Product expectedProduct = this.expectedList.get(0);
		when(productServiceImpl.getProductById(expectedProduct.getId())).thenReturn(Mono.just(ResponseEntity.ok(expectedProduct)));
		client.get()
			  .uri("/{id}", expectedProduct.getId())
			  .exchange()
			  .expectStatus().isOk()
			  .expectBody(Product.class).isEqualTo(expectedProduct);
	}

	@Test
	void testProductEvents() {
		ProductEvent expectedEvent = new ProductEvent(0L, "My Event");
		FluxExchangeResult<ProductEvent> result = client
													.get()
													.uri("/events")
													.accept(MediaType.TEXT_EVENT_STREAM)
													.exchange()
													.expectStatus().isOk()
													.returnResult(ProductEvent.class);

		StepVerifier.create(result.getResponseBody())
				   .expectSubscription()
				    .verifyComplete();
	}
}