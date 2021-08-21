package com.springboot.cassandra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cassandra.model.Employee;
import com.springboot.cassandra.service.IEmployeeService;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController {

	@Autowired
	private Environment environment;

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping(value = "/**")
	public String fallbackMethod() {
		String port = environment.getProperty("server.port");
		return "Server is up and running on port : " + port;
	}

	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		Employee saveEmployee = employeeService.saveEmployee(employee);
		return ResponseEntity.ok().body(saveEmployee);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> allEmployees = employeeService.getAllEmployees();
		return ResponseEntity.ok().body(allEmployees);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		return ResponseEntity.ok().body(employee);
	}

}
