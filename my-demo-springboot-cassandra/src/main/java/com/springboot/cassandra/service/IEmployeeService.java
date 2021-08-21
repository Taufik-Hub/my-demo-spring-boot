package com.springboot.cassandra.service;

import java.util.List;

import com.springboot.cassandra.model.Employee;

public interface IEmployeeService {
	public Employee getEmployeeById(Long employeeId);

	public List<Employee> getAllEmployees();

	public Employee saveEmployee(Employee employee);
}
