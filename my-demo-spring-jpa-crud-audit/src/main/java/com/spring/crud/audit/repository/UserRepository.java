package com.spring.crud.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.crud.audit.entity.ServiceUser;

@Repository
public interface UserRepository extends JpaRepository<ServiceUser, Long>{
	 
	Optional<ServiceUser> findByUsername(String username);
}