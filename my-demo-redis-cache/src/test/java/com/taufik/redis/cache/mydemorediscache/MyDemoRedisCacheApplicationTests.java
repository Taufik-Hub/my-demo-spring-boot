package com.taufik.redis.cache.mydemorediscache;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;


class MyDemoRedisCacheApplicationTests {
	String a;
	
	@BeforeTestClass
	public void init() {
		a ="ONE";
	}

	@Test
	void contextLoads() {
		assertEquals("ONE", a);
	}

}
