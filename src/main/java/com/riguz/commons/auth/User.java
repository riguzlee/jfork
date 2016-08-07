package com.riguz.commons.auth;

import java.io.Serializable;

public class User implements Serializable{
	String userId;
	String authHash;
	String cookie;
	public User(){

	}

	public User(String userId, String authHash){
		this.userId = userId;
		this.authHash = authHash;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCookie() {
		return this.cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getAuthHash() {
		return this.authHash;
	}

	public void setAuthHash(String authHash) {
		this.authHash = authHash;
	}

	@Override
	public String toString(){
		return "<User:" + this.userId + ">";
	}
}
