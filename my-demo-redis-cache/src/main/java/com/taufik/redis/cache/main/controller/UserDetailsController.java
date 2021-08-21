package com.taufik.redis.cache.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taufik.redis.cache.main.model.UserDetails;
import com.taufik.redis.cache.main.repository.IUserDetailsRepository;

@RestController
@RequestMapping("/redis-chache/user")
public class UserDetailsController {

	@Autowired
	private IUserDetailsRepository userDetailsRepository;

	@PostMapping(value = "/")
	void saveUserDetails(@RequestBody UserDetails userDetails) {
		userDetailsRepository.saveUserDetails(userDetails);
	}

	@GetMapping(value = "/{id}")
	UserDetails findById(@PathVariable("id") Long userId) {
		return userDetailsRepository.findById(userId);
	}

	@DeleteMapping(value = "/{id}")
	void deleteByid(@PathVariable("id") Long userid) {
		userDetailsRepository.deleteByid(userid);
	}

	@GetMapping(value = "/")
	List<Object> findAllUserDetails() {
		return userDetailsRepository.findAllUserDetails();
	}

	@PutMapping(value = "/")
	void updateUserDetails(@RequestBody UserDetails userDetails) {
		userDetailsRepository.updateUserDetails(userDetails);
	}

	@GetMapping(value = "/**")
	public String fallbackMessage() {
		return "my-demo-redis-cach Service is up and Reunning";
	}

}
