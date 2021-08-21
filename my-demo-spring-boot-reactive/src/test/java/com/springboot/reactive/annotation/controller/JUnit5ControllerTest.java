package com.springboot.reactive.annotation.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.springboot.reactive.controller.ProductController;
import com.springboot.reactive.model.Product;
import com.springboot.reactive.model.ProductEvent;
import com.springboot.reactive.repository.ProductReactiveRepository;

import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JUnit5ControllerTest {

	private WebTestClient client;

	private List<Product> expectedList;

	@Autowired
	private ProductReactiveRepository repository;
	
	@Autowired
	private ProductController controller;
	
	 /**
	  * Everything is same in ProductApiAnnotationApplicationTests, JUnit5ControllerTest, JUnit5ApplicationContextTest
	  **/

	@BeforeEach
	void beforeEach() {
		this.client = WebTestClient
							.bindToController(controller)//.bindToController(new ProductController(repository))
							.configureClient()
							.baseUrl("/products")
							.build();
		this.expectedList = repository.findAll().collectList().block();//async to sync call 
	}
	/**
	 * WebTestClient client = WebTestClient.bindToController(new ProductController(repository)).configureClient().baseUrl("/products").build();
	 * WebTestClient client = WebTestClient.bindToRouterFunction(routerFunction).configureClient().baseUrl("/products").build();
	 * WebTestClient client = WebTestClient.bindToApplicationContext(applicationContext).configureClient().baseUrl("/products").build();
	 * WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
	 * retrive () cannot use in webclient test which return complete response object
	 **/

	@Test
	void testGetAllProducts() {
		client
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk()
			//.expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
			//.expectBody(Void.class) void response
			//.expectBody().isEmpty() empty or void response
			.expectBodyList(Product.class).isEqualTo(expectedList);
			//.consumeWith(response -> Assert.assertEquals(response.getStatus(), "OK")); custom assertion test
	}

	@Test
	void testProductInvalidIdNotFound() {
		client
			.get()
			.uri("/aaa")
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	void testProductIdFound() {
		Product expectedProduct = expectedList.get(0);
		client
			.get()
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
													//.accept(MediaType.TEXT_EVENT_STREAM)
													.exchange()
													.expectStatus().isOk()
													.returnResult(ProductEvent.class);

		StepVerifier
				.create(result.getResponseBody())
				.expectNext(expectedEvent)
				.expectNextCount(2)
				.consumeNextWith(event -> assertEquals(Long.valueOf(3), event.getEventId()))
				.thenCancel()
				.verify();
	}
}
