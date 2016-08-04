package com.riguz.commons.auth;

public interface TokenEncryptor {
	public String encrypt(User user);
	public User auth(String token) throws AuthenticationException;
}
