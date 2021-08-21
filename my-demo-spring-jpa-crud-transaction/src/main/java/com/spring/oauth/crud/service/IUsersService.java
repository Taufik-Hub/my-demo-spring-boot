package com.spring.oauth.crud.service;

import java.util.List;

import com.spring.oauth.crud.entity.Users;

public interface IUsersService {

	public Boolean saveNewUser(Users user);
	
	public Users saveNewUser(int value);

	public List<Users> getAllUsers();
}
