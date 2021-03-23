package com.taufik.redis.cache.main.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.taufik.redis.cache.main.model.UserDetails;
@Repository
public class UserDetailsRepositoryImpl implements IUserDetailsRepository{
	
	private static final String KEY = "USERDETAILS";
	
	@Autowired
	private RedisTemplate<String, UserDetails> redisTemplate;
	
	//private HashOperations<String, String, UserDetails>  redisTemplate.opsForHash() = redisTemplate.opsForHash();
	
	@Override
	public void saveUserDetails(UserDetails userDetails) {
		redisTemplate.opsForHash().put(KEY, userDetails.getUserId().toString(), userDetails);
	}

	@Override
	public UserDetails findById(Long userId) {
		return (UserDetails) redisTemplate.opsForHash().get(KEY, userId.toString());
	}

	@Override
	public void deleteByid(Long userid) {
		redisTemplate.opsForHash().delete(KEY, userid.toString());
	}

	@Override
	public List<Object> findAllUserDetails() {
		List<Object> list = redisTemplate.opsForHash().values(KEY);
		return list;
	}

	@Override
	public void updateUserDetails(UserDetails userDetails) {
		saveUserDetails(userDetails);
	}

}
