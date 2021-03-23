package com.spring.crud.audit.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "role")
public class Role extends Auditable<String> {
    /**
	 * Tshaha
	 */
	private static final long serialVersionUID = 1278778371577241752L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ServiceUser> users;
    
    @Column(name = "is_valid", nullable = false, columnDefinition = "boolean default false")
	private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ServiceUser> getUsers() {
        return users;
    }

    public void setUsers(Set<ServiceUser> users) {
        this.users = users;
    }
}
