package com.taufik.redis.cache.main.repository;

import java.util.List;

import com.taufik.redis.cache.main.model.UserDetails;

public interface IUserDetailsRepository {

	void saveUserDetails(UserDetails userDetails);
	UserDetails findById(Long userId);
	void deleteByid(Long userid);
	List<Object> findAllUserDetails();
	void updateUserDetails(UserDetails userDetails);
	
}
