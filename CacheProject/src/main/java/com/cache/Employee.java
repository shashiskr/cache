package com.cache;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;
	private String userName;
	private String role;
	private String password;

	public Employee() {
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Employee(Long id, String userName, String role,String password) {
		this.id = id;
		this.userName = userName;
		this.role = role;
		this.password= password;

	}

	public Employee(String userName, String role,String password) {
		this.userName = userName;
		this.role = role;
		this.password = password;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", userName=" + userName + ", role=" + role + "]";
	}

}
