package com.taufik.redis.cache.main.model;

import java.io.Serializable;

public class UserDetails implements Serializable{
	private static final long serialVersionUID = 7640997886566397714L;
	Long userId;
	String userName;
	String userDepartment;
	Integer userSalary;

	public UserDetails(Long userId, String userName, String userDepartment, Integer userSalary) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userDepartment = userDepartment;
		this.userSalary = userSalary;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public Integer getUserSalary() {
		return userSalary;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", userName=" + userName + ", userDepartment=" + userDepartment
				+ ", userSalary=" + userSalary + "]";
	}
	
}
