package com.spring.security.dto;

import java.io.Serializable;

public class Employee implements Serializable {
	private String firstName;
	private String lastName;

	public Employee(String firstName) {
		this.firstName = firstName;
	}

	public Employee() {
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

}
