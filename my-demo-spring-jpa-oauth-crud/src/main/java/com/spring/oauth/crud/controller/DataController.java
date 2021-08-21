package com.spring.oauth.crud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}

	@GetMapping("/**")
	public String falloutPage() {
		return "you Reached at correct place";
	}

//	@PostMapping("/signup")
//	public void signUp(@Validated @RequestBody User user) {
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		applicationUserRepository.save(user);
//	}
}
