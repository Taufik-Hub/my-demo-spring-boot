package com.spring.crud.audit.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.crud.audit.entity.ServiceUser;
import com.spring.crud.audit.repository.RoleRepository;
import com.spring.crud.audit.repository.UserRepository;
import com.spring.crud.audit.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(ServiceUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

	@Override
	public ServiceUser findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
