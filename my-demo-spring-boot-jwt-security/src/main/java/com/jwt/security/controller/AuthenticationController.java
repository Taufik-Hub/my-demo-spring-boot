package com.jwt.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.config.JwtUtility;
import com.jwt.security.service.MyUserDetailsService;
import com.jwt.security.vo.AuthenticationRequest;
import com.jwt.security.vo.AuthenticationResponse;

@RestController
public class AuthenticationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtility jwtUtility;
	@Autowired
	private MyUserDetailsService myUserDetailsService;


	@SuppressWarnings("static-access")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> authenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<AuthenticationResponse>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtility.generateToken(userDetails);
		return ResponseEntity.ok(
				new AuthenticationResponse(jwt, jwtUtility.getExpirationInMin(), authenticationRequest.getUsername()));
	}

}
