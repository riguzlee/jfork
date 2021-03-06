package com.riguz.jfork.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.Controller;
import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.Authenticator;
import com.riguz.commons.auth.TokenEncryptor;
import com.riguz.commons.auth.User;
import com.riguz.commons.auth.UserContext;
import com.riguz.commons.auth.impl.CookieTokenEncryptor;
import com.riguz.commons.session.SessionKit;
import com.riguz.jfork.app.auth.PasswordAuthenticator;
import com.riguz.jfork.app.config.Constants;

public class RootController extends Controller{
	private Authenticator authenticator = new PasswordAuthenticator();
	private TokenEncryptor tokenEncryptor = new CookieTokenEncryptor(Constants.ENCRYPT_RAND, Constants.EXPIRES_TIME);

	private static Logger logger = LoggerFactory.getLogger(RootController.class.getName());

	public void index(){
		String cookie = this.getCookie(Constants.TOKEN_COOKIE_NAME);
		String jsession = this.getCookie("JSESSIONID");
		String sessionid = this.getCookie("PHPSESSID");
		this.setAttr("token", cookie);
		this.setAttr("jsessionid", jsession);
		this.setAttr("jforksessionid", sessionid);
		this.setAttr("user", UserContext.getCurrentUser());
		this.render("index.ftl");
	}

	public void loginView(){
		this.render("login.ftl");
	}

	public void doLogin(){
		String error = "Login failed:\n";
		try {
			User user = this.authenticator.authenticate(this.getRequest(), this.getResponse());
			if(user != null){
				String token = this.tokenEncryptor.encrypt(user);
				logger.info("Login success, token:{}", token);
				this.setCookie(Constants.TOKEN_COOKIE_NAME, token, (int)Constants.EXPIRES_TIME/1000);
				SessionKit.set("loginUser", user);
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
