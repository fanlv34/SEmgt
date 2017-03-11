package com.semgt.model;

import com.semgt.exception.SeException;

public class User {
	private Integer userId;

	private String username;

	private String password;

	private String email;

	private String mobile;

	private String regDate;

	private String lastLoginTime;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getRegDate() {
		return regDate;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}
}