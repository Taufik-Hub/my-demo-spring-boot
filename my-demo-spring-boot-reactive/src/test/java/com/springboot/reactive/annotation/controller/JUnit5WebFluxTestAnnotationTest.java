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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
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
@WebFluxTest(ProductController.class)
public class JUnit5WebFluxTestAnnotationTest {

	@Autowired
	private WebTestClient client;

	private List<Product> expectedList;

	@MockBean
	private ProductServiceImpl productServiceImpl;

	@MockBean
	private CommandLineRunner commandLineRunner;

	@BeforeEach
	void beforeEach() {//
		this.expectedList = Arrays.asList(new Product("1", "Big Latte", 2.99));//
	}//

	@Test
	void testGetAllProducts() {
		when(productServiceImpl.getAllProducts()).thenReturn(Flux.fromIterable(this.expectedList));//
		client.get()//
				.uri("/products")//
				.exchange()//
				.expectStatus().isOk()//
				.expectBodyList(Product.class).isEqualTo(expectedList);//
	}

	@Test
	void testProductInvalidIdNotFound() {
		String id = "aaa";
		when(productServiceImpl.getProductById(id)).thenReturn(Mono.just(ResponseEntity.notFound().build()));
		client.get().uri("/products/{id}", id).exchange().expectStatus().isNotFound();
	}

	@Test
	void testProductIdFound() {
		Product expectedProduct = this.expectedList.get(0);
		when(productServiceImpl.getProductById(expectedProduct.getId())).thenReturn(Mono.just(ResponseEntity.ok(expectedProduct)));
		client.get()
				.uri("/products/{id}", expectedProduct.getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody(Product.class).isEqualTo(expectedProduct);
	}

	@Test
	void testProductEvents() {
		ProductEvent expectedEvent = new ProductEvent(0L, "Product Event");
		FluxExchangeResult<ProductEvent> result = client.get().uri("/products/events")
				.accept(MediaType.TEXT_EVENT_STREAM).exchange().expectStatus().isOk().returnResult(ProductEvent.class);
		StepVerifier//
				.create(result.getResponseBody())//
				.verifyComplete();//
	}
}