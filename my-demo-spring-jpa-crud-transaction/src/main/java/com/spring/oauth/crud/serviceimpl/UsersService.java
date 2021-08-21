package com.spring.oauth.crud.serviceimpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.oauth.crud.entity.Users;
import com.spring.oauth.crud.repository.UserRepository;
import com.spring.oauth.crud.service.IUsersService;

@Service
public class UsersService  implements IUsersService{
	
	@Autowired
	private UserRepository userRepository;
 
	@Transactional(rollbackFor=NullPointerException.class)
	public Users saveNewUser(int value) {
		Users user = new Users("test"+value+"@gmail.com", "username"+value, "password"+value);
		return user;
	}

	@Transactional(readOnly = true)
	public List<Users> getAllUsers(){
		return userRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveNewUser(Users user) {
		Users user1 = new Users("test1@gmail.com", "username1", "password1");
		Users savedUser = userRepository.save(user1);
		if(Objects.nonNull(savedUser))
			return true;
		return false;
	}
}
