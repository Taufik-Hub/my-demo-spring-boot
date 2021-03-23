package com.jwt.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jwt.security.entity.User;
import com.jwt.security.reposatory.IUserRepository;


@EnableJpaRepositories
@SpringBootApplication(scanBasePackages="com")
@EntityScan
@EnableTransactionManagement
public class MyJwtSecurityApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MyJwtSecurityApplication.class, args);
	}

	
	@Autowired
	IUserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
//		User save = userRepository.save(new User("Taufiq", "Taufiq", Boolean.TRUE, "Admin"));
//		System.out.println("@@@@@@@@@@@@@@@@@  ID "+save.getId());
		List<User> list=userRepository.findAll();
		System.out.println("All Users In Database: "+list);
		
	}

}
