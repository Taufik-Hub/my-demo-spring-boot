package com.spring.crud.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.crud.audit.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
