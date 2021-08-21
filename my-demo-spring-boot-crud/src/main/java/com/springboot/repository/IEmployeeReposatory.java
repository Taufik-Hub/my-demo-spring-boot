package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.model.Employee;

@Repository
public interface IEmployeeReposatory  extends JpaRepository<Employee, Long>{

	/*@Query("select emp from employee emp where empCity=:empCity")
	public Employee findEmployeeByCity(@Param ("empCity") String empCity);*/
	
}
