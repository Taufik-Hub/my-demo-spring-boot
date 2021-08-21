package com.maven.reactor.my_demo_maven_reacor;

import org.junit.Test;

import reactor.core.publisher.Mono;

public class MonoTest {
	
	@Test
	public void firstMono() {
		Mono
		.just("A")
		.log()
		.subscribe();
	}
	
	@Test
	public void monoWithConsumer() {
		Mono
		.just("B")
		.log()
		.subscribe(System.out::println);
	}
	
	@Test
	public void monoWithDoOn() {
		Mono
		.just("C")
		.log()
		.doOnSubscribe(s -> System.out.println("Subscribed: " + s))
		.doOnRequest(s -> System.out.println("Requeste: " + s))
		.doOnSuccess(s -> System.out.println("Complete: " + s))
		.subscribe(System.out::println);
	}
	
	@Test//void return we use empty mono
	public void emptyMono() {
		Mono
		.empty()
		.log()
		.subscribe(System.out::println);
	}
	
	@Test
	public void emptyCompleteConsumerMono() {
		Mono
		.empty()
		.log()
		.subscribe(
				System.out::println,
				null,
				() -> System.out.println("Done")
		);
	}
	
	@Test//Error or exception throwing
	public void errorRuntimeExceptionMono() {
		Mono
		.error(new RuntimeException())
		.log()
		.subscribe();
	}

	
	@Test//even with check exception we dont have tp write try catch block
	public void errorExceptionMono() {
		Mono
		.error(new Exception())
		.log()
		.subscribe();
	}
	
	@Test//even with check exception we dont have tp write try catch block
	public void errorConsumerMono() {
		Mono
		.error(new Exception())
		.log()
		.subscribe(System.out::println,
				s -> System.out.println("ERROR: "+s));
	}
	
	@Test//handle exception
	public void errorDoOnErrorMono() {
		Mono
		.error(new Exception())
		.doOnError(s -> System.out.println("ERROR: "+s))
		.log()
		.subscribe();
	}
	
	@Test//handle exception and perform operation if exception occurs
	public void onErrorResumeMono() {
		Mono
		.error(new Exception())
		.onErrorResume(s -> {
				System.out.println("Caught: "+s);
				return Mono.just("X");
			}
		)
		.log()
		.subscribe();
	}
	
	@Test//handle exception and return value if exception occurs
	public void onErrorReturnMono() {
		Mono
		.error(new Exception())
		.onErrorReturn(new Long(5))
		.log()
		.subscribe();
	}
}
