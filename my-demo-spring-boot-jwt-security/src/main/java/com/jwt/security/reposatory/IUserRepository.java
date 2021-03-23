package com.jwt.security.reposatory;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	 Optional<User> findByUserName(String userName);

}
