package com.springboot.reactive.annotation.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
	private ProductReactiveRepository repository;

	@BeforeEach
	void beforeEach() {
		this.client = WebTestClient.bindToController(new ProductController(repository))
    							   .configureClient()
								   .baseUrl("/products")
								   .build();
		this.expectedList = Arrays.asList(new Product("1", "Big Latte", 2.99));
	}

	@Test
	void testGetAllProducts() {
		when(repository.findAll()).thenReturn(Flux.fromIterable(this.expectedList));
		client.get()
			  .uri("/")
			  .exchange()
			  .expectStatus().isOk()
			  .expectBodyList(Product.class).isEqualTo(expectedList);
	}

	@Test
	void testProductInvalidIdNotFound() {
		String id = "aaa";
		when(repository.findById(id)).thenReturn(Mono.empty());
		client.get()
			  .uri("/{id}", id)
			  .exchange()
			  .expectStatus().isNotFound();
	}

	@Test
	void testProductIdFound() {
		Product expectedProduct = this.expectedList.get(0);
		when(repository.findById(expectedProduct.getId())).thenReturn(Mono.just(expectedProduct));
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
				    .expectNext(expectedEvent).expectNextCount(2)
				    .consumeNextWith(event -> assertEquals(Long.valueOf(3), event.getEventId()))
				    .thenCancel()
				    .verify();
	}
}