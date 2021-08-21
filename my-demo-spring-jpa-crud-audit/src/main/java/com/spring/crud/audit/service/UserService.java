package com.spring.crud.audit.service;

import com.spring.crud.audit.entity.ServiceUser;

public interface UserService {
    
	public void save(ServiceUser user);

    public ServiceUser findByUsername(String username);
}