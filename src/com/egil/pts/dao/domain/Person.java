package com.egil.pts.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Person implements Serializable{

	private static final long serialVersionUID = 3262542384786138978L;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String name;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer(32);

		sb.append("firstName=");
		sb.append(firstName);
		sb.append(", ");
		
		sb.append("middleName=");
		sb.append(middleName);
		sb.append(", ");

		sb.append("lastName=");
		sb.append(lastName);
		sb.append(", ");
		
		sb.append("name=");
		sb.append(name);
		sb.append(", ");

		sb.append("email=");
		sb.append(email);

		return sb.toString();

	}

}