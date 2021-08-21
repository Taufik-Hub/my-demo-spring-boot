package com.maven.reactor.my_demo_maven_reacor;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxTest {
	
	@Test
	public void firstFlux() {
		Flux
		.just("A", "B", "C")
		.log()
		.subscribe();
	}
	
	
	@Test//publish each element from list stream array group of number
	public void fluxFromIterable() {
		//from, fromStream, fromIterable, fromArray
		Flux
		.fromIterable(Arrays.asList("A", "B", "C"))
		.log()
		.subscribe();
	}
	
	@Test
	public void fluxFromRange() {
		Flux
		.range(10, 5)
		.log()
		.subscribe();
	}
	
	@Test
	public void fluxFromInterval() throws InterruptedException {
		Flux
		.interval(Duration.ofSeconds(1))
		.log()
		.take(3)
		.subscribe();
		Thread.sleep(10000);
	}
	
	
	@Test//to work with backpressure we need to use request() method on subscription object
	public void fluxRequest(){
		Flux
		.range(10, 5)
		.log()
		.subscribe(null, null, null, s-> s.request(3));
		//this flux produce 5 element but we request 3 then this will not call onComplete()
	}
	
	@Test
	public void fluxCustomSubscriber(){
		Flux
		.range(1, 10)
		.log()
		.subscribe(new BaseSubscriber<Integer>() {
			int elementsToProcess = 3;
			int counter = 0;
			
			@Override
			public void hookOnSubscribe(Subscription subscription) {
				System.out.println("Subscribed!");
				request(elementsToProcess);
			}
			@Override
			public void hookOnNext(Integer value) {
				counter++;
				if(counter == elementsToProcess) {
					counter = 0;
					Random r = new Random();
					elementsToProcess = r.ints(1, 4).findFirst().getAsInt();
					request(elementsToProcess);
				}
			}
		}
		);
		
	}
	
	@Test//call request() after each 3 elements publish
	public void fluxLimitRate(){
		Flux
		.range(1, 10)
		.log()
		.limitRate(3)
		.subscribe();
	}
}
