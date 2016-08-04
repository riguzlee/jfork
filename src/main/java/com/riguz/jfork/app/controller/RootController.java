package com.riguz.jfork.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.Controller;
import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.Authenticator;
import com.riguz.commons.auth.TokenEncryptor;
import com.riguz.commons.auth.User;
import com.riguz.commons.auth.impl.CookieTokenEncryptor;
import com.riguz.jfork.app.auth.PasswordAuthenticator;
import com.riguz.jfork.app.config.Constants;

public class RootController extends Controller{
	private Authenticator authenticator = new PasswordAuthenticator();
	private TokenEncryptor tokenEncryptor = new CookieTokenEncryptor(Constants.ENCRYPT_RAND, Constants.EXPIRES_TIME);

	private static Logger logger = LoggerFactory.getLogger(RootController.class.getName());

	public void index(){
		this.render("index.ftl");
	}

	public void doLogin(){
		String error = "Login failed:\n";
		try {
			User user = this.authenticator.authenticate(this.getRequest(), this.getResponse());
			if(user != null){
				String token = this.tokenEncryptor.encrypt(user);
				logger.info("Login success, token:{}", token);
				this.setCookie(Constants.TOKEN_COOKIE_NAME, token, (int)Constants.EXPIRES_TIME/1000);
				this.renderJson("Login success:" + user.getUserId());
				return;
			}
			else{
				logger.warn("Error in login");
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			error += e.getMessage();
		}
		this.renderJson(error);
	}

	public void test1(){
		this.renderJson("TEST 1 OK");
	}

	public void test2(){
		this.renderJson("TEST 2 OK");
	}
}
