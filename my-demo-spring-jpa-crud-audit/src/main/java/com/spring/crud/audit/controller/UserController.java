package com.spring.crud.audit.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.crud.audit.entity.ServiceUser;
import com.spring.crud.audit.repository.UserRepository;


@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

 
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
   	public String upCheck() {
   		return "spring-boot-crud-audit is up at 8080mm";
   	}
 
    @GetMapping("/users")
    public List<ServiceUser> getAllUsers() {
         return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ServiceUser> getUserById(@PathVariable(value = "id") Long userId)  {
    	LOGGER.info("#### UserController - getUserById userId = {}",userId);
    	LOGGER.debug("#### UserController - getUserById userId = {}",userId);
    	LOGGER.error("#### UserController - getUserById userId : {}",userId);
    	LOGGER.warn("#### UserController - getUserById userId - {}",userId);
    	ServiceUser user = userRepository.findById(userId)
            .orElseThrow(() -> new NullPointerException("User not found :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public ServiceUser createUser(@Valid @RequestBody ServiceUser user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ServiceUser> updateUser(@PathVariable(value = "id") Long userId, @RequestBody ServiceUser userDetails)  {
    	ServiceUser user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("User not found :: " + userId));
        user.setEmailId(userDetails.getEmailId());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setLastModifiedDate(LocalDateTime.now());
        final ServiceUser updatedUser = userRepository.save(user);
        LOGGER.info("#### UserController - updateUser User {}",user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)  {
    	ServiceUser user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("User not found :: " + userId));
         userRepository.delete(user);
         Map<String, Boolean> response = new HashMap<>();
         response.put("deleted", Boolean.TRUE);
         return response;
    }
    
}
	
	


