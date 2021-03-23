package com.spring.crud.audit.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
/**
 * Tshaha
 */
@Entity
@Table(name = "service_user")
@EntityListeners(AuditingEntityListener.class)
public class ServiceUser extends Auditable<String> {

	private static final long serialVersionUID = 7393428002064717987L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
    private long id;
 
    @Column(name = "first_name", nullable = false)
    private String firstName;
 
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String emailId;
    
    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;
    
    @Transient
    private String passwordConfirm;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles;
    
    @Column(name = "is_valid", columnDefinition = "boolean default false")
 	private Boolean isValid;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}

