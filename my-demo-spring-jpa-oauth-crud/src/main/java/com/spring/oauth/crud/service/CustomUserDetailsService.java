package com.spring.oauth.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.oauth.crud.entity.User;
import com.spring.oauth.crud.repository.UserRepository;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String input) {
		User user = userRepository.findByUsername(input).orElseThrow(() ->  new UsernameNotFoundException("Bad credentials username : " + input));
		new AccountStatusUserDetailsChecker().check(user);
		return user;
	}
}
