package com.riguz.commons.auth.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.Authenticator;
import com.riguz.commons.auth.User;

public class CookieAuthenticator implements Authenticator{
	private static Logger logger = LoggerFactory.getLogger(CookieAuthenticator.class.getName());

	@Override
	public User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String cookie = this.getCookieByName(request, "token");
        if (Strings.isNullOrEmpty(cookie)){
        	logger.warn("Empty token:sessionid={}", request.getRequestedSessionId());
            return null;
        }
        return this.getUserByCookie(cookie);
	}

	protected String getCookieByName(HttpServletRequest request, String cookieName){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			if(cookie.getName().equals(cookieName))
				return cookie.getValue();
		}
		return null;
	}

	protected User getUserByCookie(String cookie){
		return new User();
	}
}
