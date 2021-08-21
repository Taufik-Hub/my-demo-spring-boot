package com.spring.oauth.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tshaha
 */


@Entity
@Table(schema = "test", name = "user_details_one")
public class Users {

	private static final long serialVersionUID = -7363386765018582402L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	@Column(name = "user_email", nullable = false)
	private String email;
	
	@Column(name = "username", nullable = false, unique=true)
	private String username;
	
	@Column(name = "user_password", nullable = false)
	private String password;

	//constructor
	public Users() {
	}

	public Users(String email, String username, String password) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
