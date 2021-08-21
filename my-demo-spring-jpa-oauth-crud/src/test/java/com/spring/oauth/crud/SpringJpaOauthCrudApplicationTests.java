package com.spring.oauth.crud;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.oauth.crud.entity.Permission;
import com.spring.oauth.crud.entity.Role;
import com.spring.oauth.crud.entity.User;
import com.spring.oauth.crud.repository.PermissionRepository;
import com.spring.oauth.crud.repository.RoleRepository;
import com.spring.oauth.crud.repository.UserRepository;

@SpringBootTest
//@Rollback(value = true)
//@Runw
class SpringJpaOauthCrudApplicationTests {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(SpringJpaOauthCrudApplicationTests.class);
//	
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PermissionRepository permissionRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//	
//	//@Test
//	@Transactional
//	public void insertAllPermissions() {
//		List<Permission> permissionList = Arrays.asList(new Permission("can_create_user" , true),
//				new Permission("can_update_user", true),
//				new Permission("can_read_user", true),
//				new Permission("can_delete_user", true));
//		permissionList.forEach(permisson -> 
//		permissionRepository.save(permisson)
//		);
//	}
//
//	//@Test
//	@Transactional
//	public void insertAllRoles() {
//		List<Permission> allPermissionList = permissionRepository.findAll();
//		
//		Role role = new Role();
//		role.setName("role_admin");
//		role.setPermissions(new HashSet<>(allPermissionList));
//		roleRepository.save(role);
//		
//		Map<String, List<Permission>> permissonGroup = allPermissionList.stream().collect(Collectors.groupingBy(Permission::getName));
//		
//		role = new Role();
//		role.setName("role_user");
//		role.setPermissions(new HashSet<>(permissonGroup.get("can_read_user")));
//		roleRepository.save(role);
//	}
//	
//	//@Test
//	@Transactional
//	public void insertAllUsers() {
//		List<Role> allRolesList = roleRepository.findAll();
//		Map<String, List<Role>> rolesGroupByName = allRolesList.stream().collect(Collectors.groupingBy(Role::getName));
//		LOGGER.info("@@@@@  rolesGroupByName Map : {}", rolesGroupByName);
//		User user = new User()
//				.setAccountNonExpired(true).setAccountNonLocked(true).setEmail("tshaha@gmail.com")
//				.setCredentialsNonExpired(true).setIsValidUser(true).setPassword("tshaha").setUsername("tshaha")
//				.setRoles(new HashSet<>(rolesGroupByName.get("role_admin")));
//		user = userRepository.save(user);
//		LOGGER.info("@@@@@ User ID : {}", user.getId());
//		
//		User user2 = new User()
//				.setAccountNonExpired(true).setAccountNonLocked(true).setEmail("raj12@gmail.com")
//				.setCredentialsNonExpired(true).setIsValidUser(true).setPassword("raj12").setUsername("raj12")
//				.setRoles(new HashSet<>(rolesGroupByName.get("role_user")));
//		user2 = userRepository.save(user2);
//		LOGGER.info("@@@@@  User2 ID : {}", user2.getId());
//	}
//	
//	//@Test
//	@Transactional
//	public void getAllUsers() {
//		List<User> allUsers = userRepository.findAll();
//		try {
//			String writeValueAsString = new ObjectMapper().writeValueAsString(allUsers);
//			LOGGER.info("@@@@@ all users in json : {}", writeValueAsString);
//		} catch (JsonProcessingException e) {
//			LOGGER.error("Error in fetching all users  : {}",e.getMessage());
//		}
//	}
//	
	
	@Test
	public void testCheckThis() {
		assertFalse(TestStudent.checkThis(null));
		assertTrue(TestStudent.checkThis("aba"));
		assertFalse(TestStudent.checkThis("abc"));
		
		
	}
	
}
