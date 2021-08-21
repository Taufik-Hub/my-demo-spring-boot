package com.jwt.security.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.entity.Employee;

@RestController
public class EmployeeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	@GetMapping("/employees")
	public List<Employee> allEmployees() {
		UserDetails UserDetailsFromCOntext = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LOGGER.info("USER DETAILS FOR CONTEXT {}", UserDetailsFromCOntext);
		return Arrays.asList(new Employee("aaa", 23), new Employee("aaa", 23), new Employee("aaa", 23),
				new Employee("aaa", 23));
	}

	@GetMapping("/")
	public String home(HttpServletRequest request) {
		System.out.println(request.getRequestURL());
		return ("<h1>Welcome To Taufiq's Demo</h1>");
	}

	@GetMapping("/user")
	public String user() {
		System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
		return ("<h1>for user there is only one employee</h1>");
	}

	@GetMapping("/admin")
	public String admin() {
		return ("<h1>for admin all employee</h1>");
	}


}
