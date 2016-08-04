package com.riguz.commons.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Authenticator {
	User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException;
}
