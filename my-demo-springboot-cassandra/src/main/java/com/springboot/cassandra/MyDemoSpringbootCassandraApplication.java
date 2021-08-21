package com.springboot.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCassandraRepositories
@EnableTransactionManagement
public class MyDemoSpringbootCassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDemoSpringbootCassandraApplication.class, args);
	}

}
