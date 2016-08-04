package com.riguz.commons.auth;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
	public User getUser(String userId);
	public User authUser(HttpServletRequest request) throws AuthenticationException;
}
