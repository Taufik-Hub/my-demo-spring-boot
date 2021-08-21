package com.springboot.reactive.actuator.heallth;

import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomHealthIndicatorComponent extends AbstractReactiveHealthIndicator {

    /**
     * Use the builder to build the health status details that should be reported.
     * If you throw an exception, the status will be DOWN with the exception message.
     * */
    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder){
    System.out.println("@@@@@@@@@@@");
        return Mono.just(builder.up()
                .withDetail("application", "I am Alive")
                .withDetail("error", "Nothing!")
                .build());
    }
}