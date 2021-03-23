package com.spring.oauth.crud.entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * Tshaha
 */

@MappedSuperclass
public abstract class BaseIdEntity extends Auditable<String>{

	private static final long serialVersionUID = 4951314065957838353L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	
	public int getId() {
		return id;
	}

	//Override :toString-hashCode-equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
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
		BaseIdEntity other = (BaseIdEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseIdEntity [id=" + id + "]";
	}

}