package com.realstate.dto;

import com.realstate.entities.User;

public class UserLoggedDto {
	
	private String userId;
	private String name;
	private String password;
	private String email;
	private String token;
	private boolean active;
	
	/* Constructor */
	public UserLoggedDto(User user, String token) {
		userId = user.getUserId();
		name = user.getName();
		password = user.getPassword();
		email = user.getEmail();
		active = user.isActive();
		this.token = token;
	}
	
	/* Getters and Setters */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
