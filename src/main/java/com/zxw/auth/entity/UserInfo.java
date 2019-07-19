package com.zxw.auth.entity;

public class  UserInfo{

	private Long id;

	private String username;

	private String qx;

	public UserInfo() {
	}

	public UserInfo(Long id, String username, String qx) {
		super();
		this.id = id;
		this.username = username;
		this.qx = qx;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getQx() {
		return qx;
	}

	public void setQx(String qx) {
		this.qx = qx;
	}

}