package com.riguz.commons.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.riguz.commons.auth.impl.CookieAuthenticator;
import com.riguz.commons.auth.impl.SessionAuthenticator;
import com.riguz.commons.session.Session;
import com.riguz.commons.session.SessionKit;

public class GlobalFilter implements Filter{
	private static Logger logger = LoggerFactory.getLogger(GlobalFilter.class.getName());
	private Authenticator[] authenticators;

	private String sessionKey;

	private String getFilterConfig(FilterConfig filterConfig, String param, String defaultValue){
		String config = filterConfig.getInitParameter(param);
		return config == null ? defaultValue : config;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.sessionKey = this.getFilterConfig(filterConfig, "sessionKey", "PHPSESSID");
		String userKey = this.getFilterConfig(filterConfig, "userKey", "loginUser");
		String tokenKey = this.getFilterConfig(filterConfig, "tokenKey", "PHPTOKEN");

		Authenticator sessionAuthenticator = new SessionAuthenticator(this.sessionKey, userKey);
		Authenticator cookieAuthenticator =  new CookieAuthenticator(tokenKey);
		this.authenticators = new Authenticator[]{sessionAuthenticator
				//, cookieAuthenticator
				};
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String sessionId = ServletKit.getCookieByNameOrParam((HttpServletRequest) request, this.sessionKey);
		if(Strings.isNullOrEmpty(sessionId))
			sessionId = this.createSession((HttpServletRequest)request, (HttpServletResponse)response);
		User user = this.tryGetAuthenticatedUser(request, response);
		logger.debug("auth:user={}", user);
		try (UserContext context = new UserContext(sessionId, user)) {
			chain.doFilter(request, response);
		}
	}

	private String createSession(HttpServletRequest request, HttpServletResponse response){
		Session session = SessionKit.getNewSession();
		logger.info("Creating new session:[{}]", session.getSessionId());
		SessionKit.save(session);
		Cookie cookie = new Cookie(this.sessionKey, session.getSessionId());
		cookie.setMaxAge(3600);
		cookie.setPath("/");
		response.addCookie(cookie);
		return session.getSessionId();
	}

	protected User tryGetAuthenticatedUser(ServletRequest request, ServletResponse response) {
		for (Authenticator auth : this.authenticators) {
			try{
				User user = auth.authenticate((HttpServletRequest)request, (HttpServletResponse)response);
				if (user != null)
					return user;
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void destroy() {

	}
}
