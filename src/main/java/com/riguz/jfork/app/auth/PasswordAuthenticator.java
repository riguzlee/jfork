package com.riguz.jfork.app.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.Authenticator;
import com.riguz.commons.auth.User;
import com.riguz.commons.auth.UserService;

public class PasswordAuthenticator implements Authenticator{
	private static Logger logger = LoggerFactory.getLogger(PasswordAuthenticator.class.getName());
	private UserService userService = new JdbcUserService();

	@Override
	public User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		return this.userService.authUser(request);
	}
}
