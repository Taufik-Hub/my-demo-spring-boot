package com.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.Employee;
import com.springboot.service.IEmployeeService;

/*
 	* port:8080 
 	* http://localhost:8000/actuator
 	* http://localhost:8000/employees
	* http://localhost:8000/employees/2
*/

@CrossOrigin("*")
@RestController
public class EmployeesController {

	final static Logger LOGGER = LoggerFactory.getLogger(EmployeesController.class);

	@Autowired(required = true)
	IEmployeeService employeeService;

	@GetMapping(value = "**")
	public String defaultResponse() {
		return "application is up on 8000 port...!";

	}

	@GetMapping(value = "/employees/{empId}")
	public Optional<Employee> findEmployeeById(@PathVariable("empId") Long empId) {
		LOGGER.info("findEmployeeById :" + empId);
		return employeeService.findEmployeeById(empId);

	}

	@GetMapping("/employees")
	public List<Employee> findAllEmployee() {
		LOGGER.info("findAllEmployees :");
		return employeeService.findAllEmployees();

	}

	@DeleteMapping(value = "/employees/{empId}")
	public void deleteEmployee(@PathVariable("empId") Long empId) {
		LOGGER.info("deleteEmployeeById :" + empId);
		employeeService.deleteEmployee(empId);

	}

	@PutMapping(value = "/employees/{empId}/{newCity}")
	public Employee updateEmployeeCity(@PathVariable("empId") Long empId, @PathVariable("newCity") String newCity) {
		LOGGER.info("updateEmployeeCityById :" + empId);
		return employeeService.updateEmployeeCity(empId, newCity);

	}

	@PutMapping("/employees/{empId}")
	public Employee UpdateEmployee(@PathVariable("empId") Long empId, @RequestBody Employee emp) {
		emp.setEmpId(empId);
		LOGGER.info("Update Employee :" + empId);
		return employeeService.saveOrUpdateEmployee(emp);
	}

	@PostMapping("/employees")
	public Employee saveEmployee(@RequestBody Employee emp) {
		LOGGER.info("Save Employee :" + emp);
		return employeeService.saveOrUpdateEmployee(emp);
	}

}
