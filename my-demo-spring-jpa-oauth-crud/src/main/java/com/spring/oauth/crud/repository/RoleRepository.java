package com.spring.oauth.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.oauth.crud.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}


