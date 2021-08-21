package my.demo.spring.boot.apache.drools.handler;

import org.apache.commons.math3.exception.NullArgumentException;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import my.demo.spring.boot.apache.drools.model.OrderDetails;
import my.demo.spring.boot.apache.drools.model.PersonalDetails;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OrderDetailsHandler {

	@Autowired
	private KieSession session;

	private final static Logger LOGGER = LoggerFactory.getLogger(OrderDetailsHandler.class);

	public Mono<ServerResponse> handlePOSTRequest(ServerRequest serverRequest) {
		Mono<OrderDetails> orderDetailsMono = serverRequest.bodyToMono(OrderDetails.class);
		return orderDetailsMono.flatMap(order -> {
			session.insert(order);
			session.fireAllRules();
			LOGGER.info("@@@@ handlePOSTRequest orderDetails - 1 : {}", order);
			return Mono.just(order);
		}).flatMap(orderData -> ServerResponse.ok().body(BodyInserters.fromValue(orderData)));

	}

	public Mono<ServerResponse> handleGETRequest(ServerRequest serverRequest) {
		String orderId = serverRequest.pathVariable("id");
		LOGGER.info("@@@@ handleGETRequest orderId : {}", orderId);
		//new ObjectMapper().writeValueAsString(orderDetails));
		return ServerResponse.ok().body(BodyInserters.fromValue(orderId));

	}
	
	
	public Mono<ServerResponse> handlePOSTPersonalData(ServerRequest serverRequest) {
			Mono<PersonalDetails> personalDataMono = serverRequest.bodyToMono(PersonalDetails.class);
			
			return personalDataMono.flatMap(personalData -> {
				session.insert(personalData);
				session.fireAllRules();
				//session.dispose();
				LOGGER.info("@@@@ handlePOSTRequest personalData - 1 : {}", personalData);
				return Mono.just(personalData);
			})
			.flatMap(personalData -> ServerResponse.ok().body(BodyInserters.fromValue(personalData)));

	}

	public Mono<ServerResponse> handleFallbackUrl(ServerRequest serverRequest) {
		LOGGER.info("@@@@ handleFallbackUrl");
		return ServerResponse.badRequest().body(BodyInserters.fromValue("URL not found"));

	}

}
