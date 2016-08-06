package com.riguz.commons.auth.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.Authenticator;
import com.riguz.commons.auth.ServletKit;
import com.riguz.commons.auth.User;
import com.riguz.commons.session.Session;
import com.riguz.commons.session.SessionKit;

public class SessionAuthenticator implements Authenticator{
	private static Logger logger = LoggerFactory.getLogger(SessionAuthenticator.class.getName());
	private String sessionKey;
	private String userKey;

	public SessionAuthenticator(String sessionKey, String userKey){
		this.sessionKey = sessionKey;
		this.userKey = userKey;
	}

	@Override
	public User authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String sessionId = ServletKit.getCookieByName(request, this.sessionKey);
        if (Strings.isNullOrEmpty(sessionId))
        	sessionId = request.getParameter(this.sessionKey);
        if (Strings.isNullOrEmpty(sessionId)){
        	logger.warn("Empty session id:check param [{}]", this.sessionKey);
        	return null;
        }
        logger.debug("Trying to get user from session:{}", sessionId);
        Session session = SessionKit.getSession(sessionId);
    	if(session == null)
    		return null;
    	return (User) session.get(this.userKey);
	}

	public String getSessionKey() {
		return this.sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
}
