package com.oys.pojo;

public class User {
	// 用户名
	private String user;
	// 密码
	private String password;

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public User(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	public User() {
		super();
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
