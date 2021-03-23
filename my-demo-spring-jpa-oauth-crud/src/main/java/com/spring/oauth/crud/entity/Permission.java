package com.spring.oauth.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Tshaha
 */

@Entity
@Table(schema = "test", name = "permission")
public class Permission extends BaseIdEntity {

	private static final long serialVersionUID = 4239312485984615568L;
	
	@Column(name = "permission_name", nullable = false)
	private String name;
	
	@Column(name = "is_valid_permission", nullable = false)
	private Boolean isValidPermission;

	//constructor
	public Permission() {
		super();
		isValidPermission = Boolean.FALSE;
	}

	public Permission(String permissionName, Boolean isValidPermission) {
		this.name = permissionName;
		this.isValidPermission = isValidPermission;
	}

	public String getName() {
		return name;
	}

	public Permission setName(String name) {
		this.name = name;
		return this;
	}

	
	//Override :toString-hashCode-equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Permission other = (Permission) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permission [name=" + name + "]";
	}
	
}
