package com.spring.oauth.crud.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Tshaha
 */


@Entity
@Table(schema = "test", name = "role")
public class Role extends BaseIdEntity {

	private static final long serialVersionUID = -8793309543493770934L;

	@Column(name = "role_name", nullable = false)
	private String name;
	
	@Column(name = "is_valid_role", nullable = false)
	private Boolean isValidRole;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(	joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id") }, 
				inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private Set<Permission> permissions;
	
	public Role() {
		isValidRole = Boolean.FALSE;
	}

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public Role setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
		return this;
	}
	
	public Boolean getIsValidRole() {
		return isValidRole;
	}

	public Role setIsValidRole(Boolean isValidRole) {
		this.isValidRole = isValidRole;
		return this;
	}
	
	//Override : tostring-hashCode-equals
	@Override
	public String toString() {
		return "Role [name=" + name + ", isValidRole=" + isValidRole + ", permissions=" + permissions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((isValidRole == null) ? 0 : isValidRole.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
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
		Role other = (Role) obj;
		if (isValidRole == null) {
			if (other.isValidRole != null)
				return false;
		} else if (!isValidRole.equals(other.isValidRole))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (!permissions.equals(other.permissions))
			return false;
		return true;
	}

}