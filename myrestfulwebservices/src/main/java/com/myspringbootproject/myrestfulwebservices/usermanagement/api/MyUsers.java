package com.myspringbootproject.myrestfulwebservices.usermanagement.api;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class MyUsers {
	
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	@Size(min=2)
	private String name;

	@Id
	@GeneratedValue
	private Integer userId;

	private String address;
	@Past
	private Date birthdate;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	protected MyUsers() {
		
	}
	public MyUsers(String name, Integer userId, String address, Date birthdate) {
		super();
		this.name = name;
		this.userId = userId;
		this.address = address;
		this.birthdate = birthdate;
	}
	@Override
	public String toString() {
		return "MyUsers [name=" + name + ", userId=" + userId + ", address=" + address + ", birthdate=" + birthdate
				+ "]";
	}
	
	

}
