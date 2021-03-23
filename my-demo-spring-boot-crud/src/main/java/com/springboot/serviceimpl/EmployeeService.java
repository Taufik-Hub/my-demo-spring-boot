package com.springboot.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.model.Employee;
import com.springboot.repository.IEmployeeReposatory;
import com.springboot.service.IEmployeeService;

@Service
public class EmployeeService implements IEmployeeService {

	final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private IEmployeeReposatory employeeReposatory;

	@Override
	public Optional<Employee> findEmployeeById(Long empId) {
		LOGGER.info("find Employee By Id {} ", empId);
		return employeeReposatory.findById(empId);
	}

	@Override
	public List<Employee> findAllEmployees() {
		LOGGER.info("Find All Employees");
		return employeeReposatory.findAll();

	}

	@Override
	public void deleteEmployee(Long empId) {
		LOGGER.info("delete Employee By Id {} ", empId);
		employeeReposatory.deleteById(empId);
	}

	@Override
	public Employee updateEmployeeCity(Long empId, String newCity) {
		LOGGER.info("update Employee City {}", empId);
		Employee upadedEmpCity = null;
		Employee cityFromDb = employeeReposatory.findById(empId).get();
		if (cityFromDb != null) {
			cityFromDb.setEmpCity(newCity);
			upadedEmpCity = employeeReposatory.save(cityFromDb);
		}
		return upadedEmpCity;
	}

	// insert and update
	@Override
	public Employee saveOrUpdateEmployee(Employee emp) {
		LOGGER.info("add new Employee {} ", emp);
		return employeeReposatory.save(emp);

	}
}
