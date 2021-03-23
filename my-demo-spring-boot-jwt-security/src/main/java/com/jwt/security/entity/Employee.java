package com.jwt.security.entity;

public class Employee {
	  
	private String empName;
	private int age;
	
	
	public String getEmpName() {
		return empName;
	}
	public int getAge() {
		return age;
	}
	public Employee(String empName, int age) {
		super();
		this.empName = empName;
		this.age = age;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
