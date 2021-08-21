package com.springboot.cassandra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.cassandra.model.Employee;
import com.springboot.cassandra.repository.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeRepository employeeRepository;

	public Employee getEmployeeById(Long employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new NullPointerException("Employee is not present"));
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

}
