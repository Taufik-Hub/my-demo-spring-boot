package com.maven.reactor.my_demo_maven_reacor;

import java.time.Duration;

import org.junit.Test;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorTest {
	//incoming element transform to other element stream
	//Operator->map/flatmap/concat and merge/zip
	@Test
	public void fluxMap(){
		Flux
		.range(1, 5)
		.log()
		.map(i -> i*i)
		.subscribe(System.out::println);
	}
	
	@Test
	public void fluxFlatmap(){
		Flux
		.range(1, 5)
		//.log()
		.flatMap(i -> Flux.range(i*10, 3))
		.subscribe(System.out::println);
	}
	
	@Test//Transform Mono into Flux. 
	public void monoFlatMapMany(){
		Mono
		.just(3)
		.flatMapMany(i -> Flux.range(i*10, 3))
		.subscribe(System.out::println);
	}
	
	@Test//concat two or more mono or flux
	//combine publishers in same sequence
	public void fluxConcat() throws InterruptedException{
		Flux<Integer> oneToFive = Flux
									.range(1, 5)
									.delayElements(Duration.ofMillis(200));
		Flux<Integer> sexToTen = Flux
									.range(6, 5)
									.delayElements(Duration.ofMillis(800));
		Flux
		 .concat(oneToFive, sexToTen)
		 .subscribe(System.out::println);
		
//		oneToFive.concatWith(sexToTen).subscribe(System.out::println);
		
		Thread.sleep(8000);
	}
	
	
	//merge or mergeWith two flux/mono
	//doesn't combine publishers in same sequence
	@Test
	public void fluxMerge() throws InterruptedException{
		Flux<Integer> oneToFive = Flux
									.range(1, 5);
//									.delayElements(Duration.ofMillis(200));
		Flux<Integer> sexToTen = Flux
									.range(6, 5)
									.delayElements(Duration.ofMillis(800));
		Flux
		 .merge(oneToFive, sexToTen)
		 .subscribe(System.out::println);
		
//		oneToFive.mergeWith(sexToTen).subscribe(System.out::println);
		
		Thread.sleep(8000);
	}
	
		//Count of 2 or more flux must be same & specify format
		//zip or zipWith two flux/mono
		//combine publishers in default or specified format
		//[1,6,11],[2,7,12],[3,8,13],[4,9,14],[5,10,15]
		@Test
		public void fluxZip() throws InterruptedException{
			Flux<Integer> oneToFive = Flux
										.range(1, 5);
										
			Flux<Integer> sexToTen = Flux
										.range(6, 2);
			
			Flux<Integer> elevenToFifteen = Flux
					.range(11, 5);
			
			Flux
			 .zip(oneToFive, sexToTen, elevenToFifteen)
			 .subscribe(System.out::println);
										
//			Flux
//			 .zip(oneToFive, sexToTen, (item1, item2) -> item1 + " : " + item2)
//			 .subscribe(System.out::println);
			
//			oneToFive.zipWith(sexToTen).subscribe(System.out::println);
			
		}
		
		@Test
		public void testMapVsFlatMap() {
			Flux<String> fluxString = Flux.just("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N")
			.flatMap(alph ->printValur(alph));
			fluxString.subscribe(System.out::println);
		}
		
		public Mono<String> printValur(String value) {
			System.out.println("Flatmap : "+value);
			if("H".equalsIgnoreCase(value)) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return Mono.just(value);
		}
}
