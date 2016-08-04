package com.riguz.commons.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.riguz.commons.auth.impl.CookieAuthenticator;

public class GlobalFilter implements Filter{
	private static Logger logger = LoggerFactory.getLogger(GlobalFilter.class.getName());
	Authenticator[] authenticators;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.authenticators = new Authenticator[]{new CookieAuthenticator()};
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User user = this.tryGetAuthenticatedUser(request, response);
		logger.debug("auth:user={}", user);
		try (UserContext context = new UserContext(user)) {
			chain.doFilter(request, response);
		}
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
