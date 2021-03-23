package com.jwt.security.service;

import java.time.LocalTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.security.entity.User;
import com.jwt.security.reposatory.IUserRepository;
import com.jwt.security.vo.MyUserDetails;

@Service("MyUserDetailsService") // for qualifier
@Scope("singleton") // Default
public class MyUserDetailsService implements UserDetailsService {

	public static String NAME = "MyUserDetailsService";
	private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

	@Autowired
	IUserRepository userRepository;

	@Override // For Practice added
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(userName);

		if (!userOptional.isPresent())
			LOGGER.error("User not found in DB {}", userOptional.map(MyUserDetails::new).get());
		userOptional.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
		return userOptional.map(MyUserDetails::new).get();
	}

}
