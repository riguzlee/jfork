package com.riguz.commons.auth.impl;

import java.util.Date;

import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.TokenEncryptor;
import com.riguz.commons.auth.User;
import com.riguz.commons.auth.UserService;
import com.riguz.commons.util.EncryptUtil;
import com.riguz.jfork.app.auth.JdbcUserService;

public class CookieTokenEncryptor implements TokenEncryptor{
	protected String rand;
	protected long expires;
	protected UserService userService = new JdbcUserService();

	public static final String SPLIT_STRING = ":";
	public static final String ENCRYPT_METHOD = "sha-512";

	public CookieTokenEncryptor(String rand, long expires){
		this.rand = rand;
		this.expires = expires;
	}

	@Override
	public String encrypt(User user) {
		long thruTime = new Date().getTime() + this.expires;
		String hash = EncryptUtil.encrypt(ENCRYPT_METHOD, this.rand + user.getAuthHash());
		return String.format("%s%s%d%s%s", user.getUserId(), SPLIT_STRING, thruTime, SPLIT_STRING, hash);
	}

	@Override
	public User auth(String token) throws AuthenticationException{
		String[] arr = token.split(SPLIT_STRING);
		if(arr.length < 3)
			throw new AuthenticationException("Invalid token length");

		User user = this.userService.getUser(arr[0]);
		if(user == null)
			throw new AuthenticationException("User not found:" + arr[0]);
		String expected = this.encrypt(user);
		if(!expected.equals(token))
			throw new AuthenticationException("Invalid token");
		return user;
	}
}
