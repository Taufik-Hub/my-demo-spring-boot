package com.javatechie.email.api.dto;

public class BikeInfo {

	private String year;
	private String make;
	private String model;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BikeInfo(String year, String make, String model) {
		super();
		this.year = year;
		this.make = make;
		this.model = model;
	}

	public BikeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BikeInfo [year=" + year + ", make=" + make + ", model=" + model + "]";
	}

}
