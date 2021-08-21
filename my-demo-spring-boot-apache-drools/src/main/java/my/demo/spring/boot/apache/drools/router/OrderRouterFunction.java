package my.demo.spring.boot.apache.drools.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import my.demo.spring.boot.apache.drools.handler.OrderDetailsHandler;

@Configuration
public class OrderRouterFunction {
	
	private static final String ORDER_URL = "/order-data/";
	private static final String PERSONAL_DATA_URL = "/personal-data/";
	private static final String PATH_VARIABLE_ID = "{id}";
	
	@Bean
	public RouterFunction<ServerResponse> routerOrderDetails(OrderDetailsHandler orderDetailsHandler) {
		return RouterFunctions.route(RequestPredicates.POST(ORDER_URL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), orderDetailsHandler::handlePOSTRequest)
				.andRoute(RequestPredicates.GET(ORDER_URL + PATH_VARIABLE_ID).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), orderDetailsHandler:: handleGETRequest)
				.andRoute(RequestPredicates.POST(PERSONAL_DATA_URL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), orderDetailsHandler:: handlePOSTPersonalData)
				.andRoute(RequestPredicates.GET("/**"), orderDetailsHandler:: handleFallbackUrl)
				//.filter(personalDataFilter)
				;
		
	}

}
