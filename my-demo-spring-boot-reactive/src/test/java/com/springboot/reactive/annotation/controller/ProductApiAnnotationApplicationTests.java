package com.springboot.reactive.annotation.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.springboot.reactive.controller.ProductController;
import com.springboot.reactive.model.Product;
import com.springboot.reactive.model.ProductEvent;
import com.springboot.reactive.repository.ProductReactiveRepository;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApiAnnotationApplicationTests {

	private WebTestClient client;

	private List<Product> expectedList;

	@Autowired
	private ProductReactiveRepository repository;

	 /**Everything is same in ProductApiAnnotationApplicationTests, JUnit5ControllerTest, JUnit5ApplicationContextTest
     * Difference is @ExtendWith(SpringExtension.class) vs @RunWith(SpringRunner.class)
     * */
	
	@Before
	public void beforeEach() {
		this.client = WebTestClient.bindToController(new ProductController(repository)).configureClient()
				.baseUrl("/products").build();

		this.expectedList = repository.findAll().collectList().block();
	}

	@Test
	public void testGetAllProducts() {
		client.get().uri("/").exchange().expectStatus().isOk().expectBodyList(Product.class).isEqualTo(expectedList);
	}

	@Test
	public void testProductInvalidIdNotFound() {
		client.get().uri("/aaa").exchange().expectStatus().isNotFound();
	}

	@Test
	public void testProductIdFound() {
		Product expectedProduct = expectedList.get(0);
		client.get().uri("/{id}", expectedProduct.getId()).exchange().expectStatus().isOk().expectBody(Product.class)
				.isEqualTo(expectedProduct);
	}

	@Test
	public void testProductEvents() {
		ProductEvent expectedEvent = new ProductEvent(0L, "Product Event");

		FluxExchangeResult<ProductEvent> result = client.get().uri("/events")//.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange().expectStatus().isOk().returnResult(ProductEvent.class);

		StepVerifier.create(result.getResponseBody()).expectNext(expectedEvent).expectNextCount(2)
				.consumeNextWith(event -> assertEquals(Long.valueOf(3), event.getEventId())).thenCancel().verify();
	}

}
