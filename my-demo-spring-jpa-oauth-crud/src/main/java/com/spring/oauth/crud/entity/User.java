package com.spring.oauth.crud.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Tshaha
 */


@Entity
@Table(schema = "test", name = "user_details")
public class User extends BaseIdEntity implements UserDetails {

	private static final long serialVersionUID = -7363386765018582402L;

	@Column(name = "user_email", nullable = false)
	private String email;
	
	@Column(name = "username", nullable = false, unique=true)
	private String username;
	
	@Column(name = "user_password", nullable = false)
	private String password;
	
	@Column(name = "is_valid_user", nullable = false)
	private boolean isValidUser;
	
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	
	@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id") }, 
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<Role> roles;
	
	
	//constructor
	public User() {
		credentialsNonExpired = Boolean.FALSE;
		accountNonLocked = Boolean.FALSE;
		accountNonExpired = Boolean.FALSE;
		isValidUser = Boolean.FALSE;
	}
	
	/*
	 * Get roles and permissions and add them as a Set of GrantedAuthority
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getPermissions().forEach(permission -> {
				authorities.add(new SimpleGrantedAuthority(permission.getName()));
			});
		});

		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return isValidUser;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public User setRoles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public User setPassword(String password) {
		if(Objects.nonNull(password)) {
			this.password = new BCryptPasswordEncoder().encode(password);
		}
		return this;
	}

	public User setIsValidUser(boolean isValidUser) {
		this.isValidUser = isValidUser;
		return this;
	}

	public User setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
		return this;
	}

	public User setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
		return this;
	}

	public User setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
		return this;
	}

	
	//Override :toString-hashCode-equals
	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username + ", password=" + password + ", isEnabled=" + isValidUser
				+ ", accountNonLocked=" + accountNonLocked + ", accountNonExpired=" + accountNonExpired
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", roles=" + roles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (accountNonExpired ? 1231 : 1237);
		result = prime * result + (accountNonLocked ? 1231 : 1237);
		result = prime * result + (credentialsNonExpired ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (isValidUser ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountNonExpired != other.accountNonExpired)
			return false;
		if (accountNonLocked != other.accountNonLocked)
			return false;
		if (credentialsNonExpired != other.credentialsNonExpired)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isValidUser != other.isValidUser)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
