package com.springboot.service;

import java.util.List;
import java.util.Optional;

import com.springboot.model.Employee;

public interface IEmployeeService {
	public Optional<Employee> findEmployeeById(Long empId);

	public void deleteEmployee(Long empId);

	public Employee updateEmployeeCity(Long empId, String newCity);

	public List<Employee> findAllEmployees();

	public Employee saveOrUpdateEmployee(Employee emp);

}
