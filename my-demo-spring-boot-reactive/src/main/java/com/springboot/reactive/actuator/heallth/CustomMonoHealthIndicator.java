package com.springboot.reactive.actuator.heallth;

import org.bson.Document;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.mongo.MongoReactiveHealthIndicator;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

@Component
public class CustomMonoHealthIndicator extends MongoReactiveHealthIndicator {

    private ReactiveMongoTemplate reactiveMongoTemplate;

    public CustomMonoHealthIndicator(ReactiveMongoTemplate reactiveMongoTemplate) {
        super(reactiveMongoTemplate);
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder){
        Mono<Document> buildInfo = this.reactiveMongoTemplate.executeCommand("{ buildInfo: 1 }");
        return buildInfo.map((document) -> up(builder, document));
    }

    private Health up(Health.Builder builder, Document document) {
        return builder.up().withDetail("versions", document.getString("version")).build();
    }
}
