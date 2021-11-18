package com.egil.pts.dao.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.egil.pts.util.DesEncrypter;


@Embeddable
public class Credential implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "username")
	private String userName;
	@Column
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return DesEncrypter.decrypt(password);
	}

	public void setPassword(String password) {		
		this.password = DesEncrypter.encrypt(password);
	
	}

	public String toString() {

		StringBuffer sb = new StringBuffer(16);

		sb.append("userName=");
		sb.append(userName);

		return sb.toString();

	}

}