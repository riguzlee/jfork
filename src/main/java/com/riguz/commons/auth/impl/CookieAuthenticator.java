package com.riguz.commons.auth.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.Authenticator;
import com.riguz.commons.auth.ServletKit;
import com.riguz.commons.auth.TokenEncryptor;
import com.riguz.commons.auth.User;
import com.riguz.jfork.app.config.Constants;

public class CookieAuthenticator implements Authenticator{
	private static Logger logger = LoggerFactory.getLogger(CookieAuthenticator.class.getName());
	protected String tokenCookieName;

	protected TokenEncryptor tokenEncryptor = new CookieTokenEncryptor(Constants.ENCRYPT_RAND, Constants.EXPIRES_TIME);
	public CookieAuthenticator(String tokenCookieName) {
		this.tokenCookieName = tokenCookieName;
	}

	@Override
	public User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String cookie = ServletKit.getCookieByName(request, this.tokenCookieName);
        if (Strings.isNullOrEmpty(cookie)){
        	logger.warn("Empty token:sessionid={}", request.getRequestedSessionId());
            return null;
        }
        return this.getUserByCookie(cookie);
	}

	protected User getUserByCookie(String cookie){
		try {
			return this.tokenEncryptor.auth(cookie);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
