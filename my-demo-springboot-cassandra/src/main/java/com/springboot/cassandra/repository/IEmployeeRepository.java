package com.springboot.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.springboot.cassandra.model.Employee;

@Repository
public interface IEmployeeRepository extends CassandraRepository<Employee, Long> {

}
