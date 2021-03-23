package com.spring.oauth.crud;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.spring.oauth.crud.entity.Users;
import com.spring.oauth.crud.service.IUsersService;

@SpringBootTest
@Rollback(value = true)
class SpringJpaOauthCrudApplicationTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringJpaOauthCrudApplicationTests.class);
	
	@Autowired
	private IUsersService usersService;

	@Test
	public void getSaveNewUser() {
		Users saveNewUser = usersService.saveNewUser(9);
		LOGGER.info("@@@@@ saved user id : {}",saveNewUser.getUserId());
	}
	
	
	@Test
	public void getAllUsers() {
		List<Users> listSavedUser = usersService.getAllUsers();
		listSavedUser.forEach(user -> LOGGER.info("@@@@@ getAllUsers user id : {}",user.getUserId()));
	}
}
