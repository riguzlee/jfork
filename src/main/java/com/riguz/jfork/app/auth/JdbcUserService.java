package com.riguz.jfork.app.auth;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;

import com.google.common.base.Strings;
import com.jfinal.plugin.activerecord.Sqls;
import com.riguz.commons.auth.AuthenticationException;
import com.riguz.commons.auth.User;
import com.riguz.commons.auth.UserService;
import com.riguz.jfork.model.AuthPassword;

public class JdbcUserService implements UserService{
	public static final String ENCRYPT_METHOD = "sha-512";

	@Override
	public User getUser(String userId) {
		AuthPassword auth = AuthPassword.dao.findFirst(Sqls.get("getPasswordAuthByUserId"), userId);
		if(auth == null)
			return null;
		User user = new User(auth.getUserId(), auth.getPassword());
		return user;
	}


	@Override
	public User authUser(HttpServletRequest request) throws AuthenticationException {
		String userName = request.getParameter("user");
		String passwd = request.getParameter("passwd");

		if(Strings.isNullOrEmpty(userName) || Strings.isNullOrEmpty(passwd))
			throw new AuthenticationException("User and password required");
		AuthPassword auth = AuthPassword.dao.findFirst(Sqls.get("getPasswordAuthByUserName"), userName);
		if(auth == null)
			throw new AuthenticationException("User not found:" + userName);
		if(!BCrypt.checkpw(passwd, auth.getPassword()))
			throw new AuthenticationException("Password not match:" + userName);
		return new User(auth.getUserId(), auth.getPassword());
	}

	protected String encryptPassword(String plaintextPassword){
		return BCrypt.hashpw(plaintextPassword, BCrypt.gensalt(12));
	}
}
